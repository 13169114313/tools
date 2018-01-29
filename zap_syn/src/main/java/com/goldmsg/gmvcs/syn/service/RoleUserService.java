package com.goldmsg.gmvcs.syn.service;

import com.goldmsg.gmvcs.syn.core.base.BaseService;
import com.goldmsg.gmvcs.syn.entity.SysRoleUser;

public interface RoleUserService  extends BaseService<SysRoleUser,String> {

  int deleteByPrimaryKey(SysRoleUser sysRoleUser);

  int insert(SysRoleUser sysRoleUser);

  int selectCountByCondition(SysRoleUser sysRoleUser);

}
