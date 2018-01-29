package com.goldmsg.gmvcs.syn.mapper.local;

import com.goldmsg.gmvcs.syn.core.base.BaseMapper;
import com.goldmsg.gmvcs.syn.entity.SysRoleMenu;

import java.util.List;

public interface SysRoleMenuMapper  extends BaseMapper<SysRoleMenu,String> {

    int deleteByPrimaryKey(SysRoleMenu key);

    int insert(SysRoleMenu record);

    int insertSelective(SysRoleMenu record);

    List<SysRoleMenu> selectByCondition(SysRoleMenu sysRoleMenu);

   int  selectCountByCondition(SysRoleMenu sysRoleMenu);
}