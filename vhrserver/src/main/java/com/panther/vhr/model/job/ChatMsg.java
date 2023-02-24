package com.panther.vhr.model.job;

import lombok.Data;

import java.util.Date;

/**
 * @author Gin 琴酒
 * @data 2023/02/24 11:51
 **/
@Data
public class ChatMsg {

    private String from;
    private String to;
    private String content;
    private Date date;
    private String fromNickname;
}
