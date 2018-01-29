package com.goldmsg.gmvcs.syn.controller;

import com.alibaba.fastjson.JSON;
import com.goldmsg.gmvcs.syn.common.BeanUtil;
import com.goldmsg.gmvcs.syn.common.ClassUtil;
import com.goldmsg.gmvcs.syn.common.JsonUtil;
import com.goldmsg.gmvcs.syn.core.annotation.Log;
import com.goldmsg.gmvcs.syn.core.base.BaseController;
import com.goldmsg.gmvcs.syn.core.exception.MyException;
import com.goldmsg.gmvcs.syn.entity.SysJob;
import com.goldmsg.gmvcs.syn.entity.SysJobClass;
import com.goldmsg.gmvcs.syn.mapper.remote.TestMapper;
import com.goldmsg.gmvcs.syn.quartz.JobTask;
import com.goldmsg.gmvcs.syn.service.JobService;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 定时任务 controller
 */
@Controller
@RequestMapping("/job")
@Api(value = "JOB", description = "工作任务")
public class JobController extends BaseController<SysJob> {

    @Autowired
    JobService jobService;

    @Autowired
    JobTask jobTask;

    @Autowired
    TestMapper testMapper;

    @GetMapping(value = "showClass")
    @ResponseBody
    public String showClass(String id, Model model, boolean detail) {
        List<Class> classes = null;
        try {
            classes = ClassUtil.getAllClassByInterface(Class.forName("com.goldmsg.gmvcs.syn.quartz.CustomQuartz.JobTask"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        List<SysJobClass> retList = new ArrayList<>();
        for (int i = 0;i<classes.size();i++) {
            Class cl = classes.get(i);
            String classPath = cl.getName();
            int count = jobService.checkClass(classPath);
            if (1 == count){
                continue;
            }

            SysJobClass sysJobClass = new SysJobClass();
            sysJobClass.setId(i+1);
            sysJobClass.setName(cl.getSimpleName());
            sysJobClass.setClassPath(cl.getName());
            retList.add(sysJobClass);
        }

        return JSON.toJSONString(retList);
    }

    @GetMapping(value = "showJob")
    @ApiIgnore
    public String showUser(Model model) {
        return "/system/job/jobList";
    }

    @GetMapping(value = "showJobList")
    @ResponseBody
    public String showUser(Model model, SysJob job, String page, String limit) {
//        long count = testMapper.selectCount();
//        System.out.println(count);

        return jobService.show(job, Integer.valueOf(page), Integer.valueOf(limit));
    }

    @GetMapping(value = "showAddJob")
    @ApiIgnore
    public String addJob(Model model) {
        return "/system/job/add-job";
    }

    @ApiOperation(value = "/添加任务", httpMethod = "POST", notes = "添加任务类")
    @PostMapping(value = "addJob")
    @Log(desc = "添加任务")
    @ResponseBody
    public JsonUtil addJob(SysJob job) {
        JsonUtil j = new JsonUtil();
        String msg = "保存成功";
        job.setStatus(false);
        try {
            jobService.insertSelective(job);
        } catch (Exception e) {
            msg = "保存失败";
            j.setFlag(false);
            e.printStackTrace();
        }
        j.setMsg(msg);
        return j;
    }

    @GetMapping(value = "updateJob")
    @ApiIgnore
    public String updateJob(String id, Model model, boolean detail) {
        if (!id.isEmpty()) {
            SysJob job = jobService.selectByPrimaryKey(id);
            model.addAttribute("job", job);
        }
        model.addAttribute("detail", detail);
        return "system/job/update-job";
    }

    @ApiOperation(value = "/updateJob", httpMethod = "POST", notes = "更新任务")
    @Log(desc = "更新任务",type = Log.LOG_TYPE.UPDATE)
    @PostMapping(value = "updateJob")
    @ResponseBody
    public JsonUtil updateJob(SysJob job) {
        JsonUtil j = new JsonUtil();
        j.setFlag(false);
        if (job == null) {
            j.setMsg("获取数据失败");
            return j;
        }
        if (jobTask.checkJob(job)) {
            j.setMsg("已经启动任务无法更新,请停止后更新");
            return j;
        }
        try {
            SysJob oldJob = jobService.selectByPrimaryKey(job.getId());
            BeanUtil.copyNotNullBean(job, oldJob);
            jobService.updateByPrimaryKey(oldJob);
            j.setFlag(true);
            j.setMsg("修改成功");
        } catch (Exception e) {
            j.setMsg("更新失败");
            e.printStackTrace();
        }
        return j;
    }

    @ApiOperation(value = "/del", httpMethod = "POST", notes = "删除任务")
    @PostMapping(value = "del")
    @ResponseBody
    public JsonUtil del(String id) {
        JsonUtil j = new JsonUtil();
        j.setFlag(false);
        if (StringUtils.isEmpty(id)) {
            j.setMsg("获取数据失败");
            return j;
        }
        try {
            SysJob job = jobService.selectByPrimaryKey(id);
            boolean flag = jobTask.checkJob(job);
            if ((flag && !job.getStatus()) || !flag && job.getStatus()) {
                j.setMsg("您任务表状态和web任务状态不一致,无法删除");
                return j;
            }
            if (flag) {
                j.setMsg("该任务处于启动中，无法删除");
                return j;
            }
            jobService.deleteByPrimaryKey(id);
            j.setFlag(true);
            j.setMsg("任务删除成功");
        } catch (Exception e) {
            j.setMsg("任务删除异常");
            e.printStackTrace();
        }
        return j;
    }


    @PostMapping(value = "startJob")
    @ResponseBody
    public JsonUtil startJob(String id) {
        JsonUtil j = new JsonUtil();
        String msg = null;
        if (StringUtils.isEmpty(id)) {
            j.setMsg("获取数据失败");
            j.setFlag(false);
            return j;
        }
        try {
            SysJob job = jobService.selectByPrimaryKey(id);
            jobTask.startJob(job);
            job.setStatus(true);
            jobService.updateByPrimaryKey(job);
            msg = "启动成功";
        } catch (MyException e) {
            e.printStackTrace();
            j.setFlag(false);
            msg = e.getMessage();
        }
        j.setMsg(msg);
        return j;
    }

    @PostMapping(value = "endJob")
    @ResponseBody
    public JsonUtil endJob(String id) {
        JsonUtil j = new JsonUtil();
        String msg = null;
        if (StringUtils.isEmpty(id)) {
            j.setMsg("获取数据失败");
            j.setFlag(false);
            return j;
        }
        try {
            SysJob job = jobService.selectByPrimaryKey(id);
            jobTask.remove(job);
            job.setStatus(false);
            jobService.updateByPrimaryKey(job);
            msg = "停止成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        j.setMsg(msg);
        return j;
    }

}
