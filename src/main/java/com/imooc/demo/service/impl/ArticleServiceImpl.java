package com.imooc.demo.service.impl;

import com.imooc.demo.entity.Article;
import com.imooc.demo.dao.ArticleDao;
import com.imooc.demo.service.ArticleService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 发布号作者表 服务实现类
 * </p>
 *
 * @author zhachengwei
 * @since 2019-02-25
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleDao, Article> implements ArticleService {

}
