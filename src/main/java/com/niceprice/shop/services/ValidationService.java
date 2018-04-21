package com.niceprice.shop.services;

import com.niceprice.shop.utils.Action;

/**
 * Validation before CRUD operations
 */
public interface ValidationService {

  <T> void check (T type);

  <T,R> void checkForAction(T type, R repository, Action action);

}
