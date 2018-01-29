package com.goldmsg.gmvcs.syn.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import com.goldmsg.gmvcs.syn.common.VerifyCodeUtils;
import com.goldmsg.gmvcs.syn.entity.SysMenu;
import com.goldmsg.gmvcs.syn.entity.SysUser;
import com.goldmsg.gmvcs.syn.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 登录、退出页面
 */
@Controller
@Api(value = "login", description = "用户登录")
public class LoginController {

  @Autowired
  private MenuService menuService;

  @GetMapping(value = "")
  public String loginInit(){
    return loginCheck();
  }
  @GetMapping(value = "goLogin")
  @ApiIgnore
  public String goLogin(Model model,ServletRequest request){
      return "/main/main";
  }

  @GetMapping(value = "/login")
  @ApiIgnore
  public String loginCheck(){
    return "/login";
  }

  @GetMapping(value = "/")
  @ApiIgnore
  public String index(){
    return "/main/main";
  }

  /**
   * 登录动作
   * @param user
   * @param model
   * @param rememberMe
   * @return
   */
  @ApiOperation(value = "/login", httpMethod = "POST", notes = "登录method")
  @PostMapping(value = "/login")
  @ApiIgnore
  public String login(SysUser user, Model model, String rememberMe, HttpServletRequest request){

        return "/main/main";
  }

  @GetMapping(value = "/logout")
  @ApiIgnore
  public String logout(){
    return "/login";
  }

  public JSONArray getMenuJson(){
    List<SysMenu> mList=menuService.getMenuNotSuper();
    JSONArray jsonArr=new JSONArray();
    for(SysMenu sysMenu:mList){
      SysMenu menu=getChild(sysMenu.getId());
      jsonArr.add(menu);
    }
    return jsonArr;
  }

  public SysMenu getChild(String id){
    SysMenu sysMenu=menuService.selectByPrimaryKey(id);
    List<SysMenu> mList=menuService.getMenuChildren(id);
    for(SysMenu menu:mList){
      SysMenu m=getChild(menu.getId());
      sysMenu.addChild(m);
    }
    return sysMenu;
  }



  @GetMapping(value="/getCode")
  public void getYzm(HttpServletResponse response, HttpServletRequest request){
    try {
      response.setHeader("Pragma", "No-cache");
      response.setHeader("Cache-Control", "no-cache");
      response.setDateHeader("Expires", 0);
      response.setContentType("image/jpg");

      //生成随机字串
      String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
      //存入会话session
      HttpSession session = request.getSession(true);
      session.setAttribute("_code", verifyCode.toLowerCase());
      //生成图片
      int w = 146, h = 33;
      VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
