package com.goldmsg.gmvcs.syn.mapper.local;

import com.goldmsg.gmvcs.syn.core.base.BaseMapper;
import com.goldmsg.gmvcs.syn.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser,String> {

    @Override
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    @Override
    int insertSelective(SysUser record);

    @Override
    SysUser selectByPrimaryKey(String id);

    @Override
    int updateByPrimaryKeySelective(SysUser record);

    @Override
    int updateByPrimaryKey(SysUser record);

    SysUser login(@Param("username") String username);

    @Override
    List<SysUser> selectListByPage(SysUser sysUser);

    int count();

    int add(SysUser user);

    int delById(String id);

    int checkUser(String username);

    /**
     * 更新密码
     * @param user
     * @return
     */
    int rePass(SysUser user);


}