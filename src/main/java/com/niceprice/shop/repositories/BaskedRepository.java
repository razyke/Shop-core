package com.niceprice.shop.repositories;

import com.niceprice.shop.models.Basked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BaskedRepository extends JpaRepository<Basked, Long> {

}
