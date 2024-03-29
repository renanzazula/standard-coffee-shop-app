package com.standard.coffeeshop.repository.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "discount_item")
public @Data class DiscountItemEntity implements Serializable {

    private static final long serialVersionUID = 7167425684167438414L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "discount_item_id")
    private String id;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discount_id", referencedColumnName = "id", nullable = false ,updatable = false)
    private DiscountEntity discount;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false ,updatable = false)
    private ProductEntity product;

}
