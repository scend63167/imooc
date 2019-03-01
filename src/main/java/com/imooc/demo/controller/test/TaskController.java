package com.imooc.demo.controller.test;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * Created by echisan on 2018/6/23
 */
@RestController
@RequestMapping("/tasks")
public class TaskController {

    @GetMapping
    public String listTasks(){
        return "任务列表";
    }

    @GetMapping("/update")
    public String updateTasks(Integer id){
        return "更新了一下id为:"+id+"的任务";
    }

    @GetMapping("delete")
    public String deleteTasks(Integer id){
        return "删除了id为:"+id+"的任务";
    }
}
