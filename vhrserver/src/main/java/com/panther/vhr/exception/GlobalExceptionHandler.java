package com.panther.vhr.exception;

import com.panther.vhr.response.Result;
import org.apache.ibatis.jdbc.Null;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * @author Gin 琴酒
 * @data 2023/02/21 21:46
 **/
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SQLException.class)
    public Result sqlException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return Result.error("该数据有关联数据，操作失败!");
        }
        return Result.error("数据库异常，操作失败!");
    }

    @ExceptionHandler(NullPointerException.class)
    public Result nullPointerException(NullPointerException e) {
        if (e != null) {
            e.printStackTrace();
            return Result.error("空数据，操作失败!");
        }
        return Result.error("请登录，操作失败!");
    }
}
