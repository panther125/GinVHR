package com.panther.vhr.controller.baseController;

import com.panther.vhr.model.entity.Position;
import com.panther.vhr.response.Result;
import com.panther.vhr.serviec.PositionsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/24 17:30
 **/
@RestController
@RequestMapping("/system/basic/pos")
public class PosController {

    @Resource
    private PositionsService posService;

    @GetMapping
    public List<Position> getAllDep(){
        return posService.list();
    }

    @PostMapping
    public Result addDepartment(@RequestBody Position department){
        if(posService.save(department)){
            return Result.ok("新职称添加成功!");
        }
        return Result.error("新职称添加失败，请检查数据!");
    }

    @PutMapping
    public Result UpdateDepartment(@RequestBody Position department){
        if(posService.updateById(department)){
            return Result.ok("职称修改成功!");
        }
        return Result.error("职称修改失败，请检查数据!");
    }

    @DeleteMapping("/{id}")
    public Result deleteDepartment(@PathVariable int id){
        if(posService.removeById(id)){
            return Result.ok("职称删除成功!");
        }
        return Result.error("职称删除失败，请检查数据!");
    }
}
