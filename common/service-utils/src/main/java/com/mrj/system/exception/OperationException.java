package com.mrj.system.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationException extends RuntimeException{

    private Integer code;
    private String msg;

}
