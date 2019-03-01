package com.imooc.demo.service.impl;

import com.imooc.demo.entity.Manager;
import com.imooc.demo.dao.ManagerDao;
import com.imooc.demo.service.ManagerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员信息表 服务实现类
 * </p>
 *
 * @author zhachengwei
 * @since 2019-02-25
 */
@Service
public class ManagerServiceImpl extends ServiceImpl<ManagerDao, Manager> implements ManagerService {

}
