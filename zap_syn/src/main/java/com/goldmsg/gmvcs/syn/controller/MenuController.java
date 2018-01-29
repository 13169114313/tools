package com.goldmsg.gmvcs.syn.controller;

import com.alibaba.fastjson.JSONArray;
import com.goldmsg.gmvcs.syn.common.JsonUtil;
import com.goldmsg.gmvcs.syn.core.base.BaseController;
import com.goldmsg.gmvcs.syn.core.exception.MyException;
import com.goldmsg.gmvcs.syn.entity.SysMenu;
import com.goldmsg.gmvcs.syn.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 菜单
 */
@Controller
@RequestMapping(value = "/menu")
@Api(value = "menu", description = "系统菜单")
public class MenuController extends BaseController {

  @Autowired
  private MenuService menuService;

  /**
   * 展示tree
   * @param model
   * @return
   */
  @ApiOperation(value = "/showMenu", httpMethod = "GET", notes = "展示菜单")
  @GetMapping(value = "showMenu")
  @ApiIgnore
  public String showMenu(Model model){
    JSONArray ja=menuService.getMenuJsonList();
    model.addAttribute("menus", ja.toJSONString());
    return "/system/menu/menuList";
  }

  @GetMapping(value = "showAddMenu")
  @ApiIgnore
  public String addMenu(Model model){
    JSONArray ja=menuService.getMenuJsonList();
    System.out.print(ja.toJSONString());
    model.addAttribute("menus", ja.toJSONString());
    return "/system/menu/add-menu";
  }

  @ApiOperation(value = "/addMenu", httpMethod = "POST", notes = "添加菜单")
  @PostMapping(value = "addMenu")
  @ResponseBody
  public JsonUtil addMenu(SysMenu sysMenu, Model model){
    if(StringUtils.isEmpty(sysMenu.getpId())){
      sysMenu.setpId(null);
    }
    if(StringUtils.isEmpty(sysMenu.getUrl())){
      sysMenu.setUrl(null);
    }
    if(StringUtils.isEmpty(sysMenu.getPermission())){
      sysMenu.setPermission(null);
    }
    JsonUtil jsonUtil=new JsonUtil();
    jsonUtil.setFlag(false);
    if(sysMenu==null){
      jsonUtil.setMsg("获取数据失败");
      return jsonUtil;
    }
    try{
      if(sysMenu.getMenuType()==2){
        sysMenu.setMenuType((byte)0);
      }
      menuService.insertSelective(sysMenu);
      jsonUtil.setMsg("添加成功");
    }catch (MyException e){
      e.printStackTrace();
      jsonUtil.setMsg("添加失败");
    }
    return jsonUtil;
  }

}
