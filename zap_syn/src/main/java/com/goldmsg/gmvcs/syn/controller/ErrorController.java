package com.goldmsg.gmvcs.syn.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.goldmsg.gmvcs.syn.common.FileUtils;
import com.goldmsg.gmvcs.syn.common.ReType;
import com.goldmsg.gmvcs.syn.core.config.SystemConfig;
import com.goldmsg.gmvcs.syn.entity.LogFileInfo;
import com.goldmsg.gmvcs.syn.entity.SysLog;
import io.swagger.annotations.Api;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/error")
@Api(value = "error", description = "异常处理")
public class ErrorController {
    private Logger LOG = LoggerFactory.getLogger(ErrorController.class);
    @Autowired
    private SystemConfig config;

    @GetMapping(value = "404")
    public String pageNotFound(){
    return "error/404";
    }

    @GetMapping(value = "403")
    public String NotFound(String message,Model model){
        if(!StringUtils.isEmpty(message)){
            model.addAttribute("message",message);
        }

        return "error/403";
    }

    @GetMapping(value = "logdir")
    @ResponseBody
    public String logDir(String page, String limit){
        Page<SysLog> tPage= PageHelper.startPage(Integer.valueOf(page),Integer.valueOf(limit));
        List<LogFileInfo> tList = new ArrayList<>();
        int total=0;
        String fileLogPath = config.getLoggingFile();
        Path path = java.nio.file.Paths.get(fileLogPath);
        if (null != path) {
            Path parent = path.getParent();
            File file = new File(parent.toString());
            File[] files = file.listFiles();
            if (null != files){
                total = files.length;
                int start = (Integer.valueOf(page) - 1) * Integer.valueOf(limit);
                for (int i = start; i < total; ++i) {
                    if (tList.size() >= Integer.valueOf(limit)){
                        break;
                    }

                    LogFileInfo info = new LogFileInfo();
                    info.setName(files[i].getName());
                    info.setPath(files[i].getPath());
                    info.setSize(files[i].length());
                    tList.add(info);
                }
            }
        }

        ReType reType=new ReType(total,tList);
        return JSON.toJSONString(reType);
    }






    @GetMapping(value = "readLog")
    public String readLog(String logName, Long lastNLine, HttpServletRequest request, HttpServletResponse response){
        if (null == lastNLine || lastNLine == 0){
            lastNLine = 50L;
        }

        String fileLogPath = config.getLoggingFile();
        Path path = Paths.get(fileLogPath);
        Path parent = path.getParent();
        if (null != logName){
            fileLogPath = Paths.get(parent.toString(), logName).toString();
        }
        if (fileLogPath != null) {
            response.setContentType("text/plain;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            File file = new File(fileLogPath);
            OutputStream os = null;
            try {
                os = response.getOutputStream();
                FileUtils.readLastNLine(file,lastNLine,os);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (null != os){
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @GetMapping(value = "downloadLog")
    public String downloadLog(String logName, HttpServletRequest request, HttpServletResponse response){
        String fileLogPath = config.getLoggingFile();
        Path path = Paths.get(fileLogPath);
        Path parent = path.getParent();
        if (null != logName){
            fileLogPath = Paths.get(parent.toString(), logName).toString();
        }

        if (fileLogPath != null) {
          File file = new File(fileLogPath);
          if (file.exists()) {
              if (null == logName){
                  logName = file.getName();
              }

            // 设置强制下载不打开
            response.setContentType("application/force-download");
            // 设置文件名
            response.addHeader("Content-Disposition",
                    "attachment;fileName=" + logName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
              fis = new FileInputStream(file);
              bis = new BufferedInputStream(fis);
              OutputStream os = response.getOutputStream();
              int i = bis.read(buffer);
              while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
              }
              System.out.println("success");
            } catch (Exception e) {
              e.printStackTrace();
            } finally {
              if (bis != null) {
                try {
                  bis.close();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
              if (fis != null) {
                try {
                  fis.close();
                } catch (IOException e) {
                  e.printStackTrace();
                }
              }
            }
          }
        }

        return null;
    }
}
