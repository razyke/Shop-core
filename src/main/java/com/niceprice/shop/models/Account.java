package com.niceprice.shop.models;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Account {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, targetEntity = Basked.class)
  @JoinColumn(name = "id_basked")
  private Basked basked;

  @Column(nullable = false)
  private String firstName;

  private String middleName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false, unique = true)
  private String login;

  private String password;

  @Column(nullable = false, unique = true)
  private String email;

  private Integer phoneNumber;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private Role role;
}
