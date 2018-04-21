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
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import lombok.Data;

@Entity
@Data
public class Basked implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne(fetch = FetchType.LAZY)
  @PrimaryKeyJoinColumn
  private Account account;

  @Column
  private Long count;

  @ManyToMany(fetch = FetchType.LAZY, mappedBy = "baskets")
  private List<Product> products = new ArrayList<>();

}
