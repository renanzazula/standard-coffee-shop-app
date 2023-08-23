package com.inAtlas.coffeeShop.repository.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "product")
@EqualsAndHashCode
@ToString
public @Data
class ProductEntity extends BaseAuditEntity {

    private static final long serialVersionUID = -7008578354674160248L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 45)
    @Column(name = "name", length = 45)
    private String name;

    @Size(max = 100)
    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "status")
    private String status;

    @ColumnDefault(value = "0")
    @Column(name = "quantity")
    private Long quantity;

    @ColumnDefault(value = "0")
    @Column(name = "price_unit")
    private Double priceUnit;


}
