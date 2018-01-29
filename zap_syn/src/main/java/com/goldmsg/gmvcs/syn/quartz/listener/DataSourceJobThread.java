package com.goldmsg.gmvcs.syn.quartz.listener;

import com.goldmsg.gmvcs.syn.common.SpringUtil;
import com.goldmsg.gmvcs.syn.entity.SysJob;
import com.goldmsg.gmvcs.syn.quartz.JobTask;
import com.goldmsg.gmvcs.syn.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 启动数据库中已经设定为 启动状态(status:true)的任务 项目启动时init
 */
@Configuration
public class DataSourceJobThread extends Thread {

    private static final Logger log = LoggerFactory.getLogger(DataSourceJobThread.class);

    @Autowired
    JobService jobService;

    @Override
    public void run() {
        System.out.println("1");
        try {
            Thread.sleep(1000);
            log.info("---------线程启动---------");
            JobTask jobTask = SpringUtil.getBean("jobTask");
            SysJob job = new SysJob();
            job.setStatus(true);
            List<SysJob> jobList = jobService.selectListByPage(job);
            //开启任务
            for (SysJob jobs : jobList) {
                log.info("---任务[" + jobs.getId() + "]系统 init--开始启动---------");
                jobTask.startJob(jobs);
            }

            if (jobList.size() == 0) {
                log.info("---数据库暂无启动的任务---------");
            } else {
                System.out.println("---任务启动完毕---------");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
