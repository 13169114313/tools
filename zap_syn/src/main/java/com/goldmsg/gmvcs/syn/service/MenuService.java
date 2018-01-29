package com.goldmsg.gmvcs.syn.service;

import com.alibaba.fastjson.JSONArray;
import com.goldmsg.gmvcs.syn.core.base.BaseService;
import com.goldmsg.gmvcs.syn.entity.SysMenu;

import java.util.List;

public interface MenuService extends BaseService<SysMenu,String> {

  List<SysMenu> getMenuNotSuper();

  @Override
  int insert(SysMenu menu);

  @Override
  SysMenu selectByPrimaryKey(String id);

  List<SysMenu> getMenuChildren(String id);

  public JSONArray getMenuJson(String roleId);

  public JSONArray getMenuJsonList();

  List<SysMenu> getMenuChildrenAll(String id);

  public JSONArray getTreeUtil(String roleId);

  List<SysMenu> getUserMenu(String id);

  public JSONArray getMenuJsonByUser(List<SysMenu> menuList);
}
