package com.panther.vhr.model.DTO;

import lombok.Data;

/**
 * @author Gin 琴酒
 * @data 2023/02/21 21:58
 **/
@Data
public class LoginDTO {

    private String username;
    private String password;
    private String code;
}
