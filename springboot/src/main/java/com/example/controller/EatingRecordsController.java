package com.example.controller;

import com.example.common.Result;
import com.example.entity.EatingRecords;
import com.example.service.EatingRecordsService;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 前端请求接口
 */
@RestController
@RequestMapping("/eatingRecords")
public class EatingRecordsController {

    @Resource
    private EatingRecordsService eatingRecordsService;

    /**
     * 新增
     */
    @PostMapping("/add")
    public Result add(@RequestBody EatingRecords eatingRecords) {
        eatingRecordsService.add(eatingRecords);
        return Result.success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public Result update(@RequestBody EatingRecords eatingRecords) {
        eatingRecordsService.updateById(eatingRecords);
        return Result.success();
    }

    /**
     * 单个删除
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id) {
        eatingRecordsService.deleteById(id);
        return Result.success();
    }

    /**
     * 批量删除
     */
    @DeleteMapping("/delete/batch")
    public Result delete(@RequestBody List<Integer> ids) {
        eatingRecordsService.deleteBatch(ids);
        return Result.success();
    }

    /**
     * 单个查询
     */
    @GetMapping("/selectById/{id}")
    public Result selectById(@PathVariable Integer id) {
        EatingRecords eatingRecords = eatingRecordsService.selectById(id);
        return Result.success(eatingRecords);
    }

    /**
     * 查询所有
     */
    @GetMapping("/selectAll")
    public Result selectAll(EatingRecords eatingRecords) {
        List<EatingRecords> list = eatingRecordsService.selectAll(eatingRecords);
        return Result.success(list);
    }

    /**
     * 分页查询
     */
    @GetMapping("/selectPage")
    public Result selectPage(EatingRecords eatingRecords,
                             @RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "10") Integer pageSize) {
        PageInfo<EatingRecords> pageInfo = eatingRecordsService.selectPage(eatingRecords, pageNum, pageSize);
        return Result.success(pageInfo);
    }

}
