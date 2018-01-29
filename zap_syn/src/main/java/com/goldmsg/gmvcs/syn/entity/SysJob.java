package com.goldmsg.gmvcs.syn.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class SysJob implements Serializable {
    private String id;

    private String jobName;

    private String cron;

    private Boolean status;

    private String clazzPath;
    private String clazzPath_val;

    private String jobDesc;

    private String createBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date createDate;

    private String updateBy;
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date updateDate;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastDate;

    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    private Date lastEndDate;

    private long executeSuccNum;
    private long executeFailedNum;

    private long executeTime;
    private long jobExcuteStatus;
    private String jobExcuteStatus_cn;

    public String getJobExcuteStatus_cn() {
        return jobExcuteStatus_cn;
    }

    public void setJobExcuteStatus_cn(String jobExcuteStatus_cn) {
        this.jobExcuteStatus_cn = jobExcuteStatus_cn;
    }

    public long getExecuteSuccNum() {
        return executeSuccNum;
    }

    public void setExecuteSuccNum(long executeSuccNum) {
        this.executeSuccNum = executeSuccNum;
    }

    public long getExecuteFailedNum() {
        return executeFailedNum;
    }

    public void setExecuteFailedNum(long executeFailedNum) {
        this.executeFailedNum = executeFailedNum;
    }

    public long getJobExcuteStatus() {
        return jobExcuteStatus;
    }

    public void setJobExcuteStatus(long jobExcuteStatus) {
        this.jobExcuteStatus = jobExcuteStatus;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }

    public Date getLastEndDate() {
        return lastEndDate;
    }

    public void setLastEndDate(Date lastEndDate) {
        this.lastEndDate = lastEndDate;
    }


    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }

    public String getClazzPath_val() {
        return clazzPath_val;
    }

    public void setClazzPath_val(String clazzPath_val) {
        this.clazzPath_val = clazzPath_val;
    }

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getClazzPath() {
        return clazzPath;
    }

    public void setClazzPath(String clazzPath) {
        this.clazzPath = clazzPath;
    }

    public String getJobDesc() {
        return jobDesc;
    }

    public void setJobDesc(String jobDesc) {
        this.jobDesc = jobDesc;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}