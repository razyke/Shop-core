package com.niceprice.shop.services;

import com.niceprice.shop.models.Account;
import java.util.List;

/**
 * CRUD operation with Account.
 */
public interface AccountService {

  List<Account> getAll();

  Account getById(Long accountId);

  Account create(Account account);

  Account update(Account account);

  void delete(Long id);

}
