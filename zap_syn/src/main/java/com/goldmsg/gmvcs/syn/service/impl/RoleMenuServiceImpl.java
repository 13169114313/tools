package com.goldmsg.gmvcs.syn.service.impl;

import com.goldmsg.gmvcs.syn.core.base.BaseMapper;
import com.goldmsg.gmvcs.syn.core.base.impl.BaseServiceImpl;
import com.goldmsg.gmvcs.syn.entity.SysRoleMenu;
import com.goldmsg.gmvcs.syn.mapper.local.SysRoleMenuMapper;
import com.goldmsg.gmvcs.syn.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleMenuServiceImpl extends BaseServiceImpl<SysRoleMenu,String> implements
        RoleMenuService {
    @Autowired
    private SysRoleMenuMapper roleMenuMapper;
    @Override
    public BaseMapper<SysRoleMenu, String> getMappser() {
        return roleMenuMapper;
    }

    @Override
    public List<SysRoleMenu> selectByCondition(SysRoleMenu sysRoleMenu) {
        return roleMenuMapper.selectByCondition(sysRoleMenu);
    }

    @Override
    public int selectCountByCondition(SysRoleMenu sysRoleMenu) {
        return roleMenuMapper.selectCountByCondition(sysRoleMenu);
    }

    @Override
    public int deleteByPrimaryKey(SysRoleMenu sysRoleMenu) {
        return roleMenuMapper.deleteByPrimaryKey(sysRoleMenu);
    }
}
