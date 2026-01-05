package com.thegym.warehousemanagementsystem.exceptions;

public class DuplicateWarehouseException extends RuntimeException {
    public DuplicateWarehouseException(String message) {
        super(message);
    }
}
