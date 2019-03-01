package com.imooc.demo.dao;

import com.imooc.demo.entity.Manager;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 管理员信息表 Mapper 接口
 * </p>
 *
 * @author zhachengwei
 * @since 2019-02-25
 */
public interface ManagerDao extends BaseMapper<Manager> {

    Manager findByUserName(String username);
}
