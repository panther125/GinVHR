package com.panther.vhr.controller;

import com.panther.vhr.model.entity.Salary;
import com.panther.vhr.model.job.RespPageBean;
import com.panther.vhr.response.Result;
import com.panther.vhr.serviec.EmployeeService;
import com.panther.vhr.serviec.SalaryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Gin 琴酒
 * @data 2023/02/24 18:22
 **/
@RestController
@RequestMapping("/salary")
public class SalaryController {

    @Resource
    private SalaryService salaryService;

    @Resource
    private EmployeeService employeeService;

    @GetMapping("/sob")
    public List<Salary> getAllSalary(){
        return salaryService.list();
    }

    @PostMapping("/sob")
    public Result addSalary(@RequestBody Salary salary) {
        if (salaryService.save(salary) ) {
            return Result.ok("添加成功!");
        }
        return Result.error("添加失败!");
    }

    @DeleteMapping("/sob/{id}")
    public Result deleteSalaryById(@PathVariable Integer id) {
        if (salaryService.removeById(id)) {
            return Result.ok("删除成功！");
        }
        return Result.error("删除失败！");
    }

    @PutMapping("/")
    public Result updateSalaryById(@RequestBody Salary salary) {
        if (salaryService.updateById(salary)) {
            return Result.ok("更新成功!");
        }
        return Result.error("更新失败!");
    }

    @GetMapping("/sobcfg")
    public RespPageBean getEmployeeByPageWithSalary(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        return employeeService.getEmployeeByPageWithSalary(page, size);
    }

    @GetMapping("/sobcfg/salaries")
    public List<Salary> getAllSalaries() {
        return salaryService.list();
    }

    @PutMapping("/sobcfg")
    public Result updateEmployeeSalaryById(Integer eid, Integer sid) {
        Integer result = employeeService.updateEmployeeSalaryById(eid, sid);
        if (result == 1 || result == 2) {
            return Result.ok("更新成功");
        }
        return Result.error("更新失败");
    }

}
