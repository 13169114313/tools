package com.goldmsg.gmvcs.syn.quartz.listener;

import com.goldmsg.gmvcs.syn.common.SpringUtil;
import com.goldmsg.gmvcs.syn.entity.SysJob;
import com.goldmsg.gmvcs.syn.quartz.JobContext;
import com.goldmsg.gmvcs.syn.service.JobService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.Date;

public class MyJobListener implements JobListener {

    @Autowired
    public JobService jobservice;
    public SysJob sysJob;


    public MyJobListener(SysJob sysJob){
        this.sysJob = sysJob;
    }

    @Override
    public String getName() {
        return "11111";
    }
    /**
     * (1)
     * 任务执行之前执行
     * Called by the Scheduler when a JobDetail is about to be executed (an associated Trigger has occurred).
     */
    @Override
    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {
        ServletContext scontext = JobContext.getInstance().getContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(scontext);
        jobservice = (JobService)ctx.getBean("jobServiceImpl");
        if (null != jobservice && null != sysJob){
            SysJob job = new SysJob();
            job.setId(sysJob.getId());
            job.setJobExcuteStatus(2);
            job.setLastDate(new Date());
            jobservice.updateByPrimaryKeySelective(job);

            System.out.println("jobToBeExecuted:");
        }
    }

    /**
     * (2)
     * 这个方法正常情况下不执行,但是如果当TriggerListener中的vetoJobExecution方法返回true时,那么执行这个方法.
     * 需要注意的是 如果方法(2)执行 那么(1),(3)这个俩个方法不会执行,因为任务被终止了嘛.
     * Called by the Scheduler when a JobDetail was about to be executed (an associated Trigger has occurred),
     * but a TriggerListener vetoed it's execution.
     */
    @Override
    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {
        if (null != jobservice && null != sysJob){
            SysJob job = new SysJob();
            job.setId(sysJob.getId());
            job.setJobExcuteStatus(3);
            job.setLastEndDate(new Date());
            job.setExecuteFailedNum(1);
            jobservice.updateByPrimaryKeySelective(job);

            System.out.println("jobWasExecuted:");
        }
    }

    /**
     * (3)
     * 任务执行完成后执行,jobException如果它不为空则说明任务在执行过程中出现了异常
     * Called by the Scheduler after a JobDetail has been executed, and be for the associated Trigger's triggered(xx) method has been called.
     */
    @Override
    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        if (null != jobservice && null != sysJob){
            SysJob job = new SysJob();
            job.setId(sysJob.getId());
            job.setLastEndDate(new Date());
            job.setExecuteSuccNum(1);
            job.setJobExcuteStatus(1);
            jobservice.updateByPrimaryKeySelective(job);

            System.out.println("jobWasExecuted:");
        }

    }
}
