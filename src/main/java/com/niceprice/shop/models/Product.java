package com.niceprice.shop.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Product implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

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

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "product_basked",
  joinColumns = @JoinColumn(name = "basked_id"),
  inverseJoinColumns = @JoinColumn(name = "product_id"))
  private List<Basked> baskets = new ArrayList<>();

}
