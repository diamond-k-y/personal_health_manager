package com.example.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.example.entity.Forum;
import com.example.mapper.ForumMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 业务层方法
 */
@Service
public class ForumService {

    @Resource
    private ForumMapper forumMapper;

    public void add(Forum forum) {
        forum.setTime(DateUtil.now());
        forum.setReadCount(0);
        forumMapper.insert(forum);
    }

    public void updateById(Forum forum) {
        forumMapper.updateById(forum);
    }

    public void deleteById(Integer id) {
        forumMapper.deleteById(id);
    }

    public void deleteBatch(List<Integer> ids) {
        for (Integer id : ids) {
            forumMapper.deleteById(id);
        }
    }

    public Forum selectById(Integer id) {
        return forumMapper.selectById(id);
    }

    public List<Forum> selectAll(Forum forum) {
        return forumMapper.selectAll(forum);
    }

    public PageInfo<Forum> selectPage(Forum forum, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Forum> list = forumMapper.selectAll(forum);
        return PageInfo.of(list);
    }

}
