package com.panther.vhr.controller;

import com.panther.vhr.model.DTO.EmployeeDTO;
import com.panther.vhr.model.entity.*;
import com.panther.vhr.model.job.RespEmployee;
import com.panther.vhr.model.job.RespMenu;
import com.panther.vhr.model.job.RespPageBean;
import com.panther.vhr.response.Result;
import com.panther.vhr.serviec.*;
import com.panther.vhr.utils.POIUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/22 22:40
 **/
@RestController
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Resource
    private EmployeeService employeeService;

    @Resource
    private NationService nationService;

    @Resource
    private PoliticsstatusService politicsstatusService;

    @Resource
    private JobLevelService jobLevelService;

    @Resource
    private PositionsService positionsService;

    @Resource
    private DepartmentService departmentService;


//    @Resource
//    private RedisTemplate<String,List<RespEmployee>> redisTemplate;

    @GetMapping
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1") Integer page,
                                          @RequestParam(defaultValue = "10") Integer size,
                                          EmployeeDTO employee, Date[] beginDateScope)
    {
        //List<RespEmployee> data = redisTemplate.opsForValue().get("employees");
        //判断缓存数据
//        if(data != null){
//            Long total = employeeService.getTotal(employee,beginDateScope);
//            RespPageBean bean = new RespPageBean();
//            bean.setData(data);
//            bean.setTotal(total);
//            return bean;
//        }

        if (page != null && size != null) {
            page = (page - 1) * size;
        }
        List<RespEmployee> data = employeeService.getEmployeeByPage(page, size,employee,beginDateScope);
        // 数据加入缓存
        //redisTemplate.opsForValue().set("employees",data);
        Long total = employeeService.getTotal(employee,beginDateScope);
        RespPageBean bean = new RespPageBean();
        bean.setData(data);
        bean.setTotal(total);
        return bean;
    }

    @GetMapping("/nations")
    public List<Nation> getAllNations() {
        return nationService.list();
    }

    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticsstatus() {
        return politicsstatusService.list();
    }

    @GetMapping("/joblevels")
    public List<JobLevel> getAllJobLevels() {
        return jobLevelService.list();
    }

    @GetMapping("/positions")
    public List<Position> getAllPositions() {
        return positionsService.list();
    }

    @GetMapping("/deps")
    public List<Department> getAllDepartments() {
        return departmentService.list();
    }

    @GetMapping("/maxWorkID")
    public Result maxWorkID() {
        return Result.build().setStatus(200)
                .setData(String.format("%08d", employeeService.maxWorkID() + 1));
    }

    @DeleteMapping("/{id}")
    public Result deleteEmpByEid(@PathVariable Integer id) {
        if (employeeService.removeById(id)) {
            return Result.ok("删除成功!");
        }
        return Result.error("删除失败!");
    }

    @PutMapping
    public Result UpdateEmpByEid(@RequestBody RespEmployee employee) {
        if (employeeService.updateEmp(employee) == 1) {
            return Result.ok("更新成功!");
        }
        return Result.error("更新失败!");
    }

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportData() {
        List<RespEmployee> list = (List<RespEmployee>) employeeService.getEmployeeByPage(null, null, new EmployeeDTO(),null);
        return POIUtils.employee2Excel(list);
    }


}
