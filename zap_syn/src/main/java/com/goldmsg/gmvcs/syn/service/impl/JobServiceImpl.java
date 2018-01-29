package com.goldmsg.gmvcs.syn.service.impl;

import com.goldmsg.gmvcs.syn.core.base.BaseMapper;
import com.goldmsg.gmvcs.syn.core.base.impl.BaseServiceImpl;
import com.goldmsg.gmvcs.syn.entity.SysJob;
import com.goldmsg.gmvcs.syn.mapper.local.SysJobMapper;
import com.goldmsg.gmvcs.syn.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl  extends BaseServiceImpl<SysJob,String> implements JobService {

  @Autowired
  SysJobMapper jobMapper;
  @Override
  public BaseMapper<SysJob, String> getMappser() {
    return jobMapper;
  }

  @Override
  public int checkClass(String classPath) {
    return jobMapper.checkClass(classPath);
  }
}
