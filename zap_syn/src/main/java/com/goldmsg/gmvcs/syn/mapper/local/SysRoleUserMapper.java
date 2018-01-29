package com.goldmsg.gmvcs.syn.mapper.local;

import com.goldmsg.gmvcs.syn.core.base.BaseMapper;
import com.goldmsg.gmvcs.syn.entity.SysRoleUser;

import java.util.List;

public interface SysRoleUserMapper extends BaseMapper<SysRoleUser,String> {

    int deleteByPrimaryKey(SysRoleUser key);

    int insert(SysRoleUser record);

    int insertSelective(SysRoleUser record);

    List<SysRoleUser> selectByCondition(SysRoleUser sysRoleUser);

    int selectCountByCondition(SysRoleUser sysRoleUser);
}