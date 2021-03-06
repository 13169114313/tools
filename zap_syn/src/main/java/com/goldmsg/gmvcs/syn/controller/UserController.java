package com.goldmsg.gmvcs.syn.controller;

import com.goldmsg.gmvcs.syn.common.BeanUtil;
import com.goldmsg.gmvcs.syn.common.Checkbox;
import com.goldmsg.gmvcs.syn.common.JsonUtil;
import com.goldmsg.gmvcs.syn.common.Md5Util;
import com.goldmsg.gmvcs.syn.core.base.BaseController;
import com.goldmsg.gmvcs.syn.core.exception.MyException;
import com.goldmsg.gmvcs.syn.entity.SysRoleUser;
import com.goldmsg.gmvcs.syn.entity.SysUser;
import com.goldmsg.gmvcs.syn.quartz.JobTask;
import com.goldmsg.gmvcs.syn.service.RoleUserService;
import com.goldmsg.gmvcs.syn.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户管理
 */
//@Api(value="user")
@Controller
@RequestMapping(value = "/user")
@Api(value = "user", description = "用户管理")
public class UserController  extends BaseController {

  //private static final Logger

  @Autowired
  SysUserService userService;

  @Autowired
  RoleUserService roleUserService;

  @Autowired
  JobTask task;

  @GetMapping(value = "mainTest")
  @ApiIgnore
  public String showTest() {
    return "system/user/mainTest";
  }

  @GetMapping(value = "showUser")
  @ApiIgnore
  public String showUser(Model model) {
    return "/system/user/userList";
  }

  @GetMapping(value = "showUserList")
  @ResponseBody
  public String showUser(Model model, SysUser user, String page, String limit) {
    return userService.show(user,Integer.valueOf(page),Integer.valueOf(limit));
  }

  @GetMapping(value = "showAddUser")
  @ApiIgnore
  public String addUser(Model model) {
    List<Checkbox> checkboxList=userService.getUserRoleByJson(null);
    model.addAttribute("boxJson",checkboxList);
    return "/system/user/add-user";
  }

  @ApiOperation(value = "/addUser", httpMethod = "POST", notes = "添加用户")
  @PostMapping(value = "addUser")
  @ResponseBody
  public String addUser(SysUser user,String[] role) {
    if (user == null) {
      return "获取数据失败";
    }
//    if (StringUtils.isBlank(user.getUsername())) {
//
//      return "用户名不能为空";
//    }
//    if (StringUtils.isBlank(user.getPassword())) {
//      return "密码不能为空";
//    }
    if(role==null){
      return "请选择角色";
    }
    int result = userService.checkUser(user.getUsername());
    if (result > 0) {
      return "用户名已存在";
    }
    try {
      userService.insertSelective(user);
      SysRoleUser sysRoleUser=new SysRoleUser();
      sysRoleUser.setUserId(user.getId());
      for(String r:role){
        sysRoleUser.setRoleId(r);
        roleUserService.insertSelective(sysRoleUser);
      }
    } catch (MyException e) {
      e.printStackTrace();
    }
    return "保存成功";
  }

  @GetMapping(value = "updateUser")
  @ApiIgnore
  public String updateUser(String id, Model model, boolean detail) {
    if (StringUtils.isEmpty(id)) {
      //用户-角色
     List<Checkbox> checkboxList=userService.getUserRoleByJson(id);
      SysUser user = userService.selectByPrimaryKey(id);
      model.addAttribute("user", user);
      model.addAttribute("boxJson", checkboxList);
    }
    model.addAttribute("detail", detail);
    return "system/user/update-user";
  }

  @ApiOperation(value = "/updateUser", httpMethod = "POST", notes = "更新用户")
  @PostMapping(value = "updateUser")
  @ResponseBody
  public JsonUtil updateUser(SysUser user, String role[]) {
    JsonUtil jsonUtil = new JsonUtil();
    jsonUtil.setFlag(false);
    if (user == null) {
      jsonUtil.setMsg("获取数据失败");
      return jsonUtil;
    }
    try {
      SysUser oldUser = userService.selectByPrimaryKey(user.getId());
      BeanUtil.copyNotNullBean(user, oldUser);
      userService.updateByPrimaryKeySelective(oldUser);

      SysRoleUser sysRoleUser =new SysRoleUser();
      sysRoleUser.setUserId(oldUser.getId());
      List<SysRoleUser> keyList=userService.selectByCondition(sysRoleUser);
      for(SysRoleUser sysRoleUser1 :keyList){
        roleUserService.deleteByPrimaryKey(sysRoleUser1);
      }
      if(role!=null){
        for(String r:role){
          sysRoleUser.setRoleId(r);
          roleUserService.insert(sysRoleUser);
        }
      }
      jsonUtil.setFlag(true);
      jsonUtil.setMsg("修改成功");
    } catch (MyException e) {
      e.printStackTrace();
    }
    return jsonUtil;
  }

  @ApiOperation(value = "/del", httpMethod = "POST", notes = "删除用户")
  @PostMapping(value = "/del")
  @ResponseBody
  public String del(String id, boolean flag) {
    if (StringUtils.isEmpty(id)) {
      return "获取数据失败";
    }

    try {
      SysUser sysUser = userService.selectByPrimaryKey(id);
      if("admin".equals(sysUser.getUsername())){
        return "超管无法删除";
      }
      SysRoleUser roleUser=new SysRoleUser();
      roleUser.setUserId(id);
      int count=roleUserService.selectCountByCondition(roleUser);
      if(count>0){
        return "账户已经绑定角色，无法删除";
      }
      if (flag) {
        //逻辑
        sysUser.setDelFlag(Byte.parseByte("1"));
        userService.updateByPrimaryKeySelective(sysUser);
      } else {
        //物理
        userService.delById(id);
      }
    } catch (MyException e) {
      e.printStackTrace();
    }
    return "删除成功";
  }

  @GetMapping(value = "goRePass")
  @ApiIgnore
  public String goRePass(String id,Model model){
    if(StringUtils.isEmpty(id)){
      return "获取账户信息失败";
    }
    SysUser user=userService.selectByPrimaryKey(id);
    model.addAttribute("user",user);
    return "/system/user/re-pass";
  }

  /**
   * 修改密码
   * @param id
   * @param
   * @param newPwd
   * @return
   */
  @PostMapping(value = "rePass")
  @ResponseBody
  public  JsonUtil rePass(String id,String pass,String newPwd){
    boolean flag=StringUtils.isEmpty(id)||StringUtils.isEmpty(pass)||StringUtils.isEmpty(newPwd);
    JsonUtil j=new JsonUtil();
    j.setFlag(false);
    if(flag){
      j.setMsg("获取数据失败，修改失败");
      return j;
    }
    SysUser user=userService.selectByPrimaryKey(id);
    newPwd= Md5Util.getMD5(newPwd,user.getUsername());
    pass=Md5Util.getMD5(pass,user.getUsername());
    if(!pass.equals(user.getPassword())){
        j.setMsg("密码不正确");
        return j;
    }
    if(newPwd.equals(user.getPassword())){
      j.setMsg("新密码不能与旧密码相同");

      return j;
    }
    user.setPassword(newPwd);
    try {
      userService.rePass(user);
      j.setMsg("修改成功");
      j.setFlag(true);
    }catch (MyException e){
      e.printStackTrace();
    }
    return j;
  }
  /**
   * 头像上传 目前首先相对路径
   */
  @PostMapping(value = "upload")
  @ResponseBody
  public JsonUtil imgUpload(HttpServletRequest req, @RequestParam("file") MultipartFile file,
                            ModelMap model) {
    JsonUtil j = new JsonUtil();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdf1 = new SimpleDateFormat("hhmmss");

    String fileName = sdf1.format(new Date()) + file.getOriginalFilename();
    String objPath =
        req.getSession().getServletContext().getRealPath("image/") + sdf.format(new Date())
            .toString();
    File targetFile1 = new File(objPath, fileName);
    File file2 = new File(objPath);
    if (!file2.exists()) {
      file2.mkdirs();
    }
    if (!targetFile1.exists()) {
      targetFile1.mkdirs();
    }

    try {
      file.transferTo(targetFile1);
    } catch (Exception e) {
      j.setFlag(false);
      j.setMsg("上传失败");
      e.printStackTrace();
    }
    j.setMsg("image/" + sdf.format(new Date()).toString() + "/" + req.getContextPath() + fileName);
    return j;
  }

  /**
   * 验证用户名是否存在
   */
  @GetMapping(value = "checkUser")
  @ResponseBody
  public JsonUtil checkUser(String uname, HttpServletRequest req) {
    JsonUtil j = new JsonUtil();
    j.setFlag(Boolean.FALSE);
    if (StringUtils.isEmpty(uname)) {
      j.setMsg("获取数据失败");
      return j;
    }
    int result = userService.checkUser(uname);
    if (result > 0) {
      j.setMsg("用户名已存在");
      return j;
    }
    j.setFlag(true);
    return j;

  }


}
