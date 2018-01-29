package com.goldmsg.gmvcs.syn.service;


import com.goldmsg.gmvcs.syn.common.Checkbox;
import com.goldmsg.gmvcs.syn.core.base.BaseService;
import com.goldmsg.gmvcs.syn.entity.SysRoleUser;
import com.goldmsg.gmvcs.syn.entity.SysUser;

import java.util.List;

public interface SysUserService extends BaseService<SysUser,String> {

  SysUser login(String username);

  @Override
  SysUser selectByPrimaryKey(String id);

  /**
   * 分页查询
   * @param
   * @return
   */
  List<SysUser> selectListByPage(SysUser sysUser);

  int count();

  /**
   * 新增
   * @param user
   * @return
   */
  int add(SysUser user);

  /**
   * 删除
   * @param id
   * @return
   */
  int delById(String id);

  int checkUser(String username);


  int updateByPrimaryKey(SysUser sysUser);

  List<SysRoleUser> selectByCondition(SysRoleUser sysRoleUser);

  public List<Checkbox> getUserRoleByJson(String id);

  /**
   * 更新密码
   * @param user
   * @return
   */
  int rePass(SysUser user);

}
