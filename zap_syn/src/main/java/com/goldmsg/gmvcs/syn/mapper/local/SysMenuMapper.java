package com.goldmsg.gmvcs.syn.mapper.local;

import com.goldmsg.gmvcs.syn.core.base.BaseMapper;
import com.goldmsg.gmvcs.syn.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu,String> {

    @Override
    int deleteByPrimaryKey(String id);

    @Override
    int insert(SysMenu record);

    @Override
    int insertSelective(SysMenu record);

    @Override
    SysMenu selectByPrimaryKey(String id);

    @Override
    int updateByPrimaryKeySelective(SysMenu record);

    @Override
    int updateByPrimaryKey(SysMenu record);
        /**获取元节点*/
    List<SysMenu> getMenuNotSuper();

    /**
     * 获取子节点
     * @return
     */
    List<SysMenu> getMenuChildren(String id);

    List<SysMenu> getMenuChildrenAll(String id);

    /**
     * 根据用户获取所有菜单
     * @param id
     * @return
     */
    List<SysMenu> getUserMenu(@Param("id") String id);

}