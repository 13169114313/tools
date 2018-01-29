package com.goldmsg.gmvcs.syn.mapper.local;

import com.goldmsg.gmvcs.syn.core.base.BaseMapper;
import com.goldmsg.gmvcs.syn.entity.SysJob;

public interface SysJobMapper extends BaseMapper<SysJob,String> {
    int deleteByPrimaryKey(String id);

    int insert(SysJob record);

    int insertSelective(SysJob record);

    SysJob selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysJob record);

    int updateByPrimaryKey(SysJob record);

    int checkClass(String classPath);

}