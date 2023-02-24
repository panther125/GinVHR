package com.panther.vhr.controller.baseController;

import com.panther.vhr.model.entity.Department;
import com.panther.vhr.response.Result;
import com.panther.vhr.serviec.DepartmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/24 17:29
 **/
@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Resource
    private DepartmentService departmentService;

    @GetMapping
    public List<Department> getAllDep(){
        return departmentService.list();
    }

    @PostMapping
    public Result addDepartment(@RequestBody Department department){
        if(departmentService.save(department)){
            return Result.ok("新部门添加成功!");
        }
        return Result.error("新部门添加失败，请检查数据!");
    }

    @PutMapping
    public Result UpdateDepartment(@RequestBody Department department){
        if(departmentService.updateById(department)){
            return Result.ok("部门修改成功!");
        }
        return Result.error("部门修改失败，请检查数据!");
    }

    @DeleteMapping("/{id}")
    public Result deleteDepartment(@PathVariable int id){
        if(departmentService.removeById(id)){
            return Result.ok("部门删除成功!");
        }
        return Result.error("部门删除失败，请检查数据!");
    }

}
