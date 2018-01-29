package com.goldmsg.gmvcs.syn.service;

import com.goldmsg.gmvcs.syn.core.base.BaseService;
import com.goldmsg.gmvcs.syn.entity.SysJob;

public interface JobService extends BaseService<SysJob,String> {
    int checkClass(String classPath);
}
