package com.goldmsg.gmvcs.syn.quartz.CustomQuartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 定时
 */
public class JobDemo1 implements JobTask{


  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    System.out.println("JobDemo1：执行完毕=======================");
  }

  public void run(){
//    ApplicationContext applicationContext=SpringUtil.getApplicationContext();
//    //可以 获取
//    //SysUserService sys=SpringUtil.getBean(SysUserServiceImpl.class);
//    List<SysUser> userList=sys.selectListByPage(new SysUser());
//    System.out.println(userList.get(0).getUsername());;
    System.out.println("JobDemo1：执行完毕=======================");

  }
}
