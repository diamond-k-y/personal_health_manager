package com.example.controller;

import com.example.common.Result;
import com.example.entity.BodyRecords;
import com.example.service.BodyRecordsService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端请求接口
 */
@RestController
@RequestMapping("/bodyRecords")
public class BodyRecordsController {

    @Resource
    private BodyRecordsService bodyRecordsService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody BodyRecords bodyRecords) {
        bodyRecordsService.add(bodyRecords);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody BodyRecords bodyRecords) {
        bodyRecordsService.updateById(bodyRecords);
        return Result.success();
    }

    /**
     * 单个删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        bodyRecordsService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids) {
        bodyRecordsService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 单个查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        BodyRecords bodyRecords = bodyRecordsService.selectById(id);
        return Result.success(bodyRecords);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(BodyRecords bodyRecords) {
        List<BodyRecords> list = bodyRecordsService.selectAll(bodyRecords);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(BodyRecords bodyRecords,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<BodyRecords> pageInfo = bodyRecordsService.selectPage(bodyRecords, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
