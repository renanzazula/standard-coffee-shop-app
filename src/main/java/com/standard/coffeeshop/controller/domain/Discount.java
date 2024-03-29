package com.standard.coffeeshop.controller.domain;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public @Data
class Discount extends Base {

    private String id;
    private String status;
    private Date fromDate;
    private Date toDate;
    private Double amount;
    private List<Product> products = new ArrayList<>();

}
