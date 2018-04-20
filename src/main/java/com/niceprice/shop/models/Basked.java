package com.niceprice.shop.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Basked {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column
  private Long count;

}
