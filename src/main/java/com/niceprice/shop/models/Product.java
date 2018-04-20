package com.niceprice.shop.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, targetEntity = Basked.class)
  @JoinColumn(name = "id_basked", nullable = false)
  private Basked basked;

  @ManyToOne(targetEntity = Category.class)
  @JoinColumn(name = "id_category", nullable = false)
  private Category category;

  @Column(unique = true, nullable = false)
  private String name;

  @Column(nullable = false)
  private Long price;

  @Column(length = 1000)
  private String description;

  private Double discount;
}
