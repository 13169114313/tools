package com.goldmsg.gmvcs.syn.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.goldmsg.gmvcs.syn.common.JsonUtil;
import com.goldmsg.gmvcs.syn.common.ReType;
import com.goldmsg.gmvcs.syn.core.base.BaseController;
import com.goldmsg.gmvcs.syn.core.exception.MyException;
import com.goldmsg.gmvcs.syn.entity.SysLog;
import com.goldmsg.gmvcs.syn.mapper.local.SysLogMapper;
import io.swagger.annotations.Api;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 日志监控
 */
@Controller
@RequestMapping(value = "/log")
@Api(value = "log", description = "日志处理")
public class LogController  extends BaseController {
    private static final Logger logger= Logger.getLogger(LogController.class);
    @Autowired
    private SysLogMapper logMapper;

    @GetMapping(value = "showLog")
    @ApiIgnore
    public String showMenu(Model model){
        return "/system/log/logList";
    }

    @GetMapping(value = "runLog")
    @ApiIgnore
    public String runLog(Model model){
        return "/system/log/runLog";
    }

    @GetMapping(value = "lookView")
    @ApiIgnore
    public String lookLog(Model model){
        return "/system/log/lookView";
    }


    /**
     * 日志监控
     * @param sysLog
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "showLogList")
    @ResponseBody
    public String showLog(SysLog sysLog, String page, String limit){
        List<SysLog> tList=null;
        Page<SysLog> tPage= PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(limit));
        try{
            tList=logMapper.selectListByPage(sysLog);
        }catch (MyException e){
            logger.error("class:LogController ->method:showLog->message:"+e.getMessage());
            e.printStackTrace();
        }
        ReType reType=new ReType(tPage.getTotal(),tList);
        return JSON.toJSONString(reType);
    }

    /**
     * 删除log
     * @param
     * @return
     */
    @PostMapping(value = "del")
    @ResponseBody
    public JsonUtil del(String[] ids){
        JsonUtil j=new JsonUtil();
        String msg="删除成功";
        try{
            for(String id:ids)
            logMapper.deleteByPrimaryKey(Integer.valueOf(id));
        }catch (MyException e){
            msg="删除失败";
            logger.error(msg+e.getMessage());
        }
        j.setMsg(msg);
        return j;
    }


}
