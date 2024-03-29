package com.standard.coffeeshop.service.dto;

import lombok.Data;

import java.util.List;

public @Data
class PrintReceiptDto {

    private double total;
    List<PrintReceiptItemDto> receiptItems;

}
