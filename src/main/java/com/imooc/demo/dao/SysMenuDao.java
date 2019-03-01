package com.imooc.demo.dao;

import com.imooc.demo.entity.SysMenu;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 菜单管理 Mapper 接口
 * </p>
 *
 * @author zhachengwei
 * @since 2019-02-25
 */
public interface SysMenuDao extends BaseMapper<SysMenu> {
    /**
     * 通过userId 查询当前用户所拥有的权限
     * @param userId
     * @return
     */
    List<SysMenu> findByAdminUserId(Integer userId);
    /**
     * 查询所有权限
     * @return
     */
    List<SysMenu> findAll();
}
