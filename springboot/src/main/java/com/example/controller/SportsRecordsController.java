package com.example.controller;

import com.example.common.Result;
import com.example.entity.SportsRecords;
import com.example.service.SportsRecordsService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端请求接口
 */
@RestController
@RequestMapping("/sportsRecords")
public class SportsRecordsController {

    @Resource
    private SportsRecordsService sportsRecordsService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody SportsRecords sportsRecords) {
        sportsRecordsService.add(sportsRecords);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody SportsRecords sportsRecords) {
        sportsRecordsService.updateById(sportsRecords);
        return Result.success();
    }

    /**
     * 单个删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        sportsRecordsService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids) {
        sportsRecordsService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 单个查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        SportsRecords sportsRecords = sportsRecordsService.selectById(id);
        return Result.success(sportsRecords);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(SportsRecords sportsRecords) {
        List<SportsRecords> list = sportsRecordsService.selectAll(sportsRecords);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(SportsRecords sportsRecords,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<SportsRecords> pageInfo = sportsRecordsService.selectPage(sportsRecords, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
