package com.goldmsg.gmvcs.syn.service;

import com.goldmsg.gmvcs.syn.core.base.BaseService;
import com.goldmsg.gmvcs.syn.entity.SysRole;

import java.util.List;

public interface RoleService extends BaseService<SysRole,String> {

  @Override
  int deleteByPrimaryKey(String id);

  @Override
  int insert(SysRole record);

  @Override
  int insertSelective(SysRole record);

  @Override
  SysRole selectByPrimaryKey(String id);

  @Override
  int updateByPrimaryKeySelective(SysRole record);

  @Override
  int updateByPrimaryKey(SysRole record);

  @Override
  List<SysRole> selectListByPage(SysRole sysRole);
}
