package com.goldmsg.gmvcs.syn.common;

import java.util.ArrayList;
import java.util.List;

public class TreeUtil {
    /**级数*/
    private int layer;
    private String id;
    private String name;
    private String pId;
    /**是否开启 默认开启*/
    private boolean open=true;
    /**是否选择 checkbox状态可用 默认未选中*/
    private boolean checked=false;
    private List<TreeUtil> children=new ArrayList<>();

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<TreeUtil> getChildren() {
        return children;
    }

    public void setChildren(List<TreeUtil> children) {
        this.children = children;
    }
}
