package com.panther.vhr.controller.baseController;

import com.panther.vhr.model.entity.JobLevel;
import com.panther.vhr.response.Result;
import com.panther.vhr.serviec.JobLevelService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/24 17:32
 **/
@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {
    @Resource
    private JobLevelService jobLevelService;

    @GetMapping
    public List<JobLevel> getAllDep(){
        return jobLevelService.list();
    }

    @PostMapping
    public Result addJobLevel(@RequestBody JobLevel jobLevel){
         if(jobLevelService.save(jobLevel)){
             return Result.ok("新职位添加成功!");
         }
         return Result.error("新职位添加失败，请检查数据!");
    }

    @PutMapping
    public Result UpdateJobLevel(@RequestBody JobLevel jobLevel){
        if(jobLevelService.updateById(jobLevel)){
            return Result.ok("职位修改成功!");
        }
        return Result.error("职位修改失败，请检查数据!");
    }

    @DeleteMapping("/{id}")
    public Result deleteJobLevel(@PathVariable int id){
        if(jobLevelService.removeById(id)){
            return Result.ok("职位删除成功!");
        }
        return Result.error("职位删除失败，请检查数据!");
    }
}
