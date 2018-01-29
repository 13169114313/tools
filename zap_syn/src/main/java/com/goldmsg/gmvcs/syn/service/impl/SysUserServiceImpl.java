package com.goldmsg.gmvcs.syn.service.impl;


import com.goldmsg.gmvcs.syn.common.Checkbox;
import com.goldmsg.gmvcs.syn.common.Md5Util;
import com.goldmsg.gmvcs.syn.core.base.BaseMapper;
import com.goldmsg.gmvcs.syn.core.base.impl.BaseServiceImpl;
import com.goldmsg.gmvcs.syn.entity.SysRole;
import com.goldmsg.gmvcs.syn.entity.SysRoleUser;
import com.goldmsg.gmvcs.syn.entity.SysUser;
import com.goldmsg.gmvcs.syn.mapper.local.SysRoleUserMapper;
import com.goldmsg.gmvcs.syn.mapper.local.SysUserMapper;
import com.goldmsg.gmvcs.syn.service.RoleService;
import com.goldmsg.gmvcs.syn.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SysUserServiceImpl extends BaseServiceImpl<SysUser,String> implements SysUserService {

  @Autowired
  SysUserMapper sysUserMapper;

  @Autowired
  SysRoleUserMapper sysRoleUserMapper;
  @Autowired
  RoleService roleService;
  @Override
  public BaseMapper<SysUser, String> getMappser() {
    return sysUserMapper;
  }

  @Override
  public SysUser login(String username) {
    return sysUserMapper.login(username);
  }


  @Override
  public int deleteByPrimaryKey(String id) {
    return sysUserMapper.deleteByPrimaryKey(id);
  }

  @Override
  public int insert(SysUser record) {
    return sysUserMapper.insert(record);
  }

  @Override
  public int insertSelective(SysUser record) {

    String pwd= Md5Util.getMD5(record.getPassword().trim(),record.getUsername().trim());
    record.setPassword(pwd);
    return super.insertSelective(record);
  }

  @Override
  public SysUser selectByPrimaryKey(String id) {
    return sysUserMapper.selectByPrimaryKey(id);
  }

  @Override
  public int updateByPrimaryKeySelective(SysUser record) {
    return super.updateByPrimaryKeySelective(record);
  }

  @Override
  public int updateByPrimaryKey(SysUser record) {
    return sysUserMapper.updateByPrimaryKey(record);
  }

  @Override
  public List<SysRoleUser> selectByCondition(SysRoleUser sysRoleUser) {
    return sysRoleUserMapper.selectByCondition(sysRoleUser);
  }

  /**
   * 分页查询
   * @param
   * @return
   */
  @Override
  public List<SysUser> selectListByPage(SysUser sysUser) {
    return sysUserMapper.selectListByPage(sysUser);
  }

  @Override
  public int count() {
    return sysUserMapper.count();
  }

  @Override
  public int add(SysUser user) {
    //密码加密
  String pwd= Md5Util.getMD5(user.getPassword().trim(),user.getUsername().trim());
  user.setPassword(pwd);
    return sysUserMapper.add(user);
  }

  @Override
  public int delById(String id) {
    return sysUserMapper.delById(id);

  }

  @Override
  public int checkUser(String username) {
    return sysUserMapper.checkUser(username);
  }

  @Override
  public List<Checkbox> getUserRoleByJson(String id){
    List<SysRole> roleList=roleService.selectListByPage(new SysRole());
    SysRoleUser sysRoleUser =new SysRoleUser();
    sysRoleUser.setUserId(id);
    List<SysRoleUser>  kList= selectByCondition(sysRoleUser);
    System.out.println(kList.size());
    List<Checkbox> checkboxList=new ArrayList<>();
    Checkbox checkbox=null;
    for(SysRole sysRole:roleList){
      checkbox=new Checkbox();
      checkbox.setId(sysRole.getId());
      checkbox.setName(sysRole.getRoleName());
      for(SysRoleUser sysRoleUser1 :kList){
        if(sysRoleUser1.getRoleId().equals(sysRole.getId())){
          checkbox.setCheck(true);
        }
      }
      checkboxList.add(checkbox);
    }
    return checkboxList;
  }

  @Override
  public int rePass(SysUser user) {
    return sysUserMapper.rePass(user);
  }
}
