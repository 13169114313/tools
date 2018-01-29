package com.goldmsg.gmvcs.syn.core.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties
public class SystemConfig {

    @Value("${logging.file}")
    public String loggingFile;

    public String getLoggingFile() {
        return loggingFile;
    }

    public void setLoggingFile(String loggingFile) {
        this.loggingFile = loggingFile;
    }
}
