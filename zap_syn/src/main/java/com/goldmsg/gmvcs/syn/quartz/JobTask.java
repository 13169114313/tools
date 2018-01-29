package com.goldmsg.gmvcs.syn.quartz;

import com.goldmsg.gmvcs.syn.core.exception.MyException;
import com.goldmsg.gmvcs.syn.entity.SysJob;
import com.goldmsg.gmvcs.syn.quartz.listener.MyJobListener;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;

/**
 *
 * 定时任务类 增删改 可参考api：http://www.quartz-scheduler.org/api/2.2.1/
 *
 * 任务名称 默认为 SysJob 类 id
 */
@Service
public class JobTask {

  private static final Logger log = LoggerFactory.getLogger(JobTask.class);

//  @Autowired()
//  SchedulerFactoryBean schedulerFactoryBean;
//  @Resource(name = "MySchedulerFactoryBean")
//  SchedulerFactoryBean schedulerFactoryBean;

  @Resource(name = "MyScheduler")
  private Scheduler scheduler;

  /**
   * true 存在 false 不存在
   * @param
   * @return
   */
  public boolean checkJob(SysJob job){
//    Scheduler scheduler = schedulerFactoryBean.getScheduler();
    TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
    try {
      if(scheduler.checkExists(triggerKey)){
        return true;
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * 开启
   */
  public boolean startJob(SysJob job) {
    try {
      scheduler.getListenerManager().addJobListener(new MyJobListener(job));
    } catch (SchedulerException e) {
      e.printStackTrace();
    }


//    Scheduler scheduler = schedulerFactoryBean.getScheduler();
    try {
      Class clazz = Class.forName(job.getClazzPath());
      JobDetail jobDetail = JobBuilder.newJob(clazz).build();
        // 触发器
      TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
      CronTrigger trigger = TriggerBuilder.newTrigger()
          .withIdentity(triggerKey)
          .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron())).build();
      scheduler.scheduleJob(jobDetail, trigger);
      // 启动
      if (!scheduler.isShutdown()) {
        scheduler.start();
        log.info("---任务[" + triggerKey.getName() + "]启动成功-------");
        return true;
      }else{
        log.info("---任务[" + triggerKey.getName() + "]已经运行，请勿再次启动-------");
      }
    } catch (Exception e) {
      if (e instanceof ClassNotFoundException){
        throw new MyException("找不到[" + e.getMessage()+"]任务类");
      }else {
        if (e.getMessage().contains("CronExpression")){
          throw new MyException("表达式错误:" + job.getCron());
        }else{
          throw new MyException(e.getMessage());
        }
      }
    }
    return false;
  }

  /**
   * 更新
   */
  public boolean updateJob(SysJob job) {
//    Scheduler scheduler = schedulerFactoryBean.getScheduler();
//    String createTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    String createTime = "";

    TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
    try {
      if (scheduler.checkExists(triggerKey)) {
        return false;
      }

      JobKey jobKey = JobKey.jobKey(job.getId(), Scheduler.DEFAULT_GROUP);

      CronScheduleBuilder schedBuilder = CronScheduleBuilder.cronSchedule(job.getCron())
          .withMisfireHandlingInstructionDoNothing();
      CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
          .withDescription(createTime).withSchedule(schedBuilder).build();
      Class clazz = null;
      JobDetail jobDetail = scheduler.getJobDetail(jobKey);
      HashSet<Trigger> triggerSet = new HashSet<>();
      triggerSet.add(trigger);
      scheduler.scheduleJob(jobDetail, triggerSet, true);
      log.info("---任务["+triggerKey.getName()+"]更新成功-------");
      return true;
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    return false;
  }

  /**
   * 删除
   */
  public boolean remove(SysJob job) {
//    Scheduler scheduler = schedulerFactoryBean.getScheduler();
    TriggerKey triggerKey = TriggerKey.triggerKey(job.getId(), Scheduler.DEFAULT_GROUP);
    try {
      if (checkJob(job)) {
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        scheduler.deleteJob(JobKey.jobKey(job.getId(), Scheduler.DEFAULT_GROUP));
        log.info("---任务[" + triggerKey.getName() + "]删除成功-------");
        return true;
      }
    } catch (SchedulerException e) {
      e.printStackTrace();
    }
    return false;
  }
}
