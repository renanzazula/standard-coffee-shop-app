package com.inAtlas.coffeeShop.controller.domain;

import lombok.Data;

public @Data
class PrintReceiptItem {

    private long amount;
    private String productName;
    private double unitPrice;
}
