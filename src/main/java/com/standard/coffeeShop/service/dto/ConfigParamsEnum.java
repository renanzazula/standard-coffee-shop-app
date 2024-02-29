package com.standard.coffeeShop.service.dto;

public enum ConfigParamsEnum {

    GOOGLE_2FA(ConfigParamTypeEnum.STRING);

    private final ConfigParamTypeEnum type;

    ConfigParamsEnum(ConfigParamTypeEnum type) {
        this.type = type;


    }
    public ConfigParamTypeEnum getType() {
        return type;
    }

}


