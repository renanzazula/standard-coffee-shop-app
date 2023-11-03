package com.standard.coffeeShop.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "config_param")
public @Data class ConfigParamEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false, length = 45)
    private String id;

    @Id
    @Column(name = "value", nullable = false, length = 150)
    private String value;


}