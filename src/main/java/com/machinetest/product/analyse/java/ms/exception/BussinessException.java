package com.machinetest.product.analyse.java.ms.exception;

import lombok.Data;

@Data
public class BussinessException extends RuntimeException
{
    String msg;

    public BussinessException(String message) {
        this.msg=message;
    }
}
