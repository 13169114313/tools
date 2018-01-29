package com.goldmsg.gmvcs.syn.entity;

import java.io.Serializable;

public class SysRoleMenu implements Serializable {
    private String roleId;

    private String menuId;

    private static final long serialVersionUID = 1L;


    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }
}