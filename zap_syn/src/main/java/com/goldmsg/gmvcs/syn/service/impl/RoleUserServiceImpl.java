package com.goldmsg.gmvcs.syn.service.impl;


import com.goldmsg.gmvcs.syn.core.base.BaseMapper;
import com.goldmsg.gmvcs.syn.core.base.impl.BaseServiceImpl;
import com.goldmsg.gmvcs.syn.entity.SysRoleUser;
import com.goldmsg.gmvcs.syn.mapper.local.SysRoleUserMapper;
import com.goldmsg.gmvcs.syn.service.RoleUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleUserServiceImpl extends BaseServiceImpl<SysRoleUser,String> implements
        RoleUserService {

  @Autowired
  private SysRoleUserMapper sysRoleUserMapper;

  @Override
  public BaseMapper<SysRoleUser, String> getMappser() {
    return sysRoleUserMapper;
  }

  @Override
  public int deleteByPrimaryKey(SysRoleUser sysRoleUser) {
    return sysRoleUserMapper.deleteByPrimaryKey(sysRoleUser);
  }

  @Override
  public int selectCountByCondition(SysRoleUser sysRoleUser) {
    return sysRoleUserMapper.selectCountByCondition(sysRoleUser);
  }
}
