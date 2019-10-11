package com.changyue.o2o.exceptions;

/**
 * @program: shopping
 * @description:
 * @author: ChangYue
 * @create: 2019-03-21 22:47
 */
public class ProductOperationException extends RuntimeException {
    public ProductOperationException(String msg) {
        super(msg);
    }
}
