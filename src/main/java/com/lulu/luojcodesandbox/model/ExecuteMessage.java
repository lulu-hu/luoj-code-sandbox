package com.lulu.luojcodesandbox.model;

import lombok.Data;

/**
 * @author lulu
 * @version 1.0
 * @description 进程执行信息
 * @date 2024/7/2 16:43
 */
@Data
public class ExecuteMessage {

    private Integer exitValue;

    private String message;

    private String errorMessage;

    private Long time;

}
