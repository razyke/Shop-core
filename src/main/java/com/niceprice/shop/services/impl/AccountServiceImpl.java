package com.niceprice.shop.services.impl;

import com.niceprice.shop.models.Account;
import com.niceprice.shop.repositories.AccountRepository;
import com.niceprice.shop.services.AccountService;
import com.niceprice.shop.services.ValidationService;
import com.niceprice.shop.utils.Action;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final ValidationService validationService;

  @Autowired
  public AccountServiceImpl(AccountRepository accountRepository,
      ValidationService validationService) {
    this.accountRepository = accountRepository;
    this.validationService = validationService;
  }

  @Override
  public List<Account> getAll() {
    return accountRepository.findAll();
  }

  @Override
  public Account getById(Long accountId) {
    validationService.check(accountId);
    return accountRepository.getOne(accountId);
  }

  @Override
  public Account create(Account account) {
    validationService.checkForAction(account, accountRepository, Action.CREATE);
    return accountRepository.save(account);
  }

  @Override
  public Account update(Account account) {
    validationService.checkForAction(account, accountRepository, Action.UPDATE);
     return accountRepository.save(account);
  }

  @Override
  public void delete(Long id) {
    validationService.checkForAction(id, accountRepository, Action.DELETE);
    accountRepository.deleteById(id);
  }

}
