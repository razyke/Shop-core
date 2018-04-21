package com.niceprice.shop.repositories;

import com.niceprice.shop.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

  Boolean existsAccountByEmail(String email);

  Boolean existsAccountByLogin(String login);

}
