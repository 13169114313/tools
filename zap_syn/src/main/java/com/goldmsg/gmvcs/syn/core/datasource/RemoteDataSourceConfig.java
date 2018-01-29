package com.goldmsg.gmvcs.syn.core.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.goldmsg.gmvcs.syn.mapper.remote", sqlSessionTemplateRef  = "remoteSqlSessionTemplate")
public class RemoteDataSourceConfig {

    @Value("${spring.datasource.remote.url}")
    private String url;
    @Value("${spring.datasource.remote.username}")
    private String username;
    @Value("${spring.datasource.remote.password}")
    private String password;
    @Value("${spring.datasource.remote.filters}")
    private String filters;
    @Value("${spring.datasource.remote.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.remote.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.remote.minIdle}")
    private int minIdle;

    @Bean(name = "remoteDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.remote")
    public DataSource getDataSource(){
        System.out.println("-------初始化druid------");
        DruidDataSource datasource = new DruidDataSource();
//
        datasource.setUrl(url);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return datasource;
    }

//    @Bean(name = "remoteDataSource")
//    @ConfigurationProperties(prefix = "spring.datasource.remote")
//    @Primary
    public DataSource remoteDataSource() {


        return DataSourceBuilder.create().build();
    }

    @Bean(name = "remoteSqlSessionFactory")
    public SqlSessionFactory remoteSqlSessionFactory(@Qualifier("remoteDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.goldmsg.gmvcs.syn.mapper.remote");
        return bean.getObject();
    }

    @Bean(name = "remoteTransactionManager")
    public DataSourceTransactionManager remoteTransactionManager(@Qualifier("remoteDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "remoteSqlSessionTemplate")
    public SqlSessionTemplate remoteSqlSessionTemplate(@Qualifier("remoteSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
