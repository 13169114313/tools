package com.goldmsg.gmvcs.syn.mapper.local;

import com.goldmsg.gmvcs.syn.entity.SysLog;

import java.util.List;

public interface SysLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysLog record);

    int insertSelective(SysLog record);

    SysLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysLog record);

    int updateByPrimaryKey(SysLog record);

    List<SysLog> selectListByPage(SysLog sysLog);
}