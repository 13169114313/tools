package com.goldmsg.gmvcs.syn.service;


import com.goldmsg.gmvcs.syn.core.base.BaseService;
import com.goldmsg.gmvcs.syn.entity.SysRoleMenu;

import java.util.List;

public interface RoleMenuService extends BaseService<SysRoleMenu,String> {

    List<SysRoleMenu> selectByCondition(SysRoleMenu sysRoleMenu);

    int  selectCountByCondition(SysRoleMenu sysRoleMenu);

    int deleteByPrimaryKey(SysRoleMenu sysRoleMenu);
}
