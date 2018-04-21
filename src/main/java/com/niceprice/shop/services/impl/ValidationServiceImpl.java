package com.niceprice.shop.services.impl;

import com.niceprice.shop.models.Account;
import com.niceprice.shop.models.Basked;
import com.niceprice.shop.models.Category;
import com.niceprice.shop.models.Product;
import com.niceprice.shop.repositories.AccountRepository;
import com.niceprice.shop.repositories.BaskedRepository;
import com.niceprice.shop.repositories.CategoryRepository;
import com.niceprice.shop.repositories.ProductRepository;
import com.niceprice.shop.services.ValidationService;
import com.niceprice.shop.utils.Action;
import java.util.regex.Pattern;
import org.springframework.stereotype.Service;

/**
 * Verification before make some operation on DB.
 */
@Service
public class ValidationServiceImpl implements ValidationService {

  private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
      Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

  /**
   *
   * @param type simple checking on {@code null}.
   */
  @Override
  public <T> void check(T type) {
    if (type == null) {
      throw new IllegalArgumentException("Is null!");
    }
  }

  /**
   * @param type Classes from {@link com.niceprice.shop.models} or {@code Long}.
   * @param repository Models JPA repositories.
   * @param action Trigger for using one of validation methods.
   */
  @Override
  public <T, R> void checkForAction(T type, R repository, Action action) {

    check(type);
    check(repository);
    check(action);

    switch (action) {
      case CREATE:
        if (type instanceof Account && repository instanceof AccountRepository) {
          Account account = (Account) type;
          AccountRepository accountRepository = (AccountRepository) repository;
          checkAccount(account);
          checkAccountInRepository(account, accountRepository, action, null);
        } else if (type instanceof Product && repository instanceof ProductRepository) {
          Product product = (Product) type;
          ProductRepository productRepository = (ProductRepository) repository;
          checkProduct(product);
          checkProductInRepository(product, productRepository, action, null);
          // *** BASKED CREATED WHEN USER CREATED BY DEFAULT ***
/*        } else if (type instanceof Basked && repository instanceof BaskedRepository) {
          Basked basked = (Basked) type;
          // Nothing check in repository, for now.
          BaskedRepository baskedRepository = (BaskedRepository) repository;
          checkBasked(basked);*/
        } else if (type instanceof Category && repository instanceof CategoryRepository) {
          Category category = (Category) type;
          CategoryRepository categoryRepository = (CategoryRepository) repository;
          checkCategory(category);
          checkCategoryInRepository(category, categoryRepository, action, null);
        }
        break;
      case UPDATE:
        if (type instanceof Account && repository instanceof AccountRepository) {
          Account account = (Account) type;
          AccountRepository accountRepository = (AccountRepository) repository;
          checkAccount(account);
          checkAccountInRepository(account, accountRepository, action, null);
        } else if (type instanceof Product && repository instanceof ProductRepository) {
          Product product = (Product) type;
          ProductRepository productRepository = (ProductRepository) repository;
          checkProduct(product);
          checkProductInRepository(product, productRepository, action, null);
        } else if (type instanceof Basked && repository instanceof BaskedRepository) {
          Basked basked = (Basked) type;
          BaskedRepository baskedRepository = (BaskedRepository) repository;
          checkBasked(basked);
          checkBaskedInRepository(basked, baskedRepository, action);
        } else if (type instanceof Category && repository instanceof CategoryRepository) {
          Category category = (Category) type;
          CategoryRepository categoryRepository = (CategoryRepository) repository;
          checkCategory(category);
          checkCategoryInRepository(category, categoryRepository, action, null);
        }
        break;
      case DELETE:
        Long id = (Long) type;
        if (repository instanceof AccountRepository) {
          AccountRepository accountRepository = (AccountRepository) repository;
          checkAccountInRepository(null, accountRepository, action, id);
        } else if (repository instanceof ProductRepository) {
          ProductRepository productRepository = (ProductRepository) repository;
          checkProductInRepository(null, productRepository, action, id);
        } else if (repository instanceof CategoryRepository) {
          CategoryRepository categoryRepository = (CategoryRepository) repository;
          checkCategoryInRepository(null, categoryRepository, action, id);

        }
        break;

      default:
        throw new IllegalArgumentException("Can't verify no such ACTION!");
    }

  }

  private void checkAccount(Account account) {
    if (account.getEmail() == null || account.getEmail().trim().isEmpty()) {
      throw new IllegalArgumentException("Account email can't be empty");
    } else if (account.getFirstName() == null || account.getFirstName().trim().isEmpty()) {
      throw new IllegalArgumentException("Account first name empty");
    } else if (account.getLastName() == null || account.getLastName().trim().isEmpty()) {
      throw new IllegalArgumentException("Account last name empty");
    } else if (account.getLogin() == null || account.getLogin().trim().isEmpty()) {
      throw new IllegalArgumentException("Account login name empty");
    } else if (account.getEmail() == null || account.getEmail().trim().isEmpty()
        || !VALID_EMAIL_ADDRESS_REGEX.matcher(account.getEmail()).find()) {
      throw new IllegalArgumentException("Account email empty or not valid");
    } else if (account.getPassword() == null || account.getPassword().isEmpty()) {
      throw new IllegalArgumentException("Account password can't be null");
    } else if (account.getBasked() == null) {
      throw new IllegalArgumentException("Account basked in null");
    }
  }

  private void checkProduct(Product product) {
    if (product.getName() == null || product.getName().trim().isEmpty()) {
      throw new IllegalArgumentException("Name product is empty");
    } else if (product.getCategory() == null || product.getCategory().getId() <= 0) {
      throw new IllegalArgumentException("Category empty or ID lower than 1");
    } else if (product.getPrice() == null || product.getPrice() <= 0) {
      throw new IllegalArgumentException("Price can't be empty or lower than 1");
    }
  }

  private void checkBasked(Basked basked) {
    if (basked.getAccount() == null) {
      throw new IllegalArgumentException("Basked have empty User");
    }
  }

  private void checkCategory(Category category) {
    if (category.getType() == null || category.getType().trim().isEmpty()) {
      throw new IllegalArgumentException("Type is empty");
    }
  }

  private void checkAccountInRepository(Account account, AccountRepository accountRepository,
      Action action, Long id) {
    switch (action) {
      case CREATE:
        if (accountRepository.existsAccountByEmail(account.getEmail())) {
          throw new IllegalArgumentException("Email already in use!");
        } else if (accountRepository.existsAccountByLogin(account.getLogin())) {
          throw new IllegalArgumentException("Login already in use!");
        }
        break;
      case UPDATE:
        forUpdateOrDeleteAccount(account.getId(), accountRepository);
        break;
      case DELETE:
        forUpdateOrDeleteAccount(id, accountRepository);
        break;
    }
  }

  private void checkProductInRepository(Product product, ProductRepository productRepository,
      Action action, Long id) {
    switch (action) {
      case CREATE:
        if (productRepository.existsByName(product.getName())) {
          throw new IllegalArgumentException("Name of product already in use");
        }
        break;
      case UPDATE:
        forUpdateOrDeleteProduct(product.getId(), productRepository);
        break;
      case DELETE:
        forUpdateOrDeleteProduct(id, productRepository);
        break;
    }
  }

  private void checkBaskedInRepository(Basked basked, BaskedRepository baskedRepository,
      Action action) {
    // Basked is creating and deleting when User do.
    switch (action) {
      case UPDATE:
        if (basked.getId() == null) {
          throw new IllegalArgumentException("Basked have empty ID");
        } else if (!baskedRepository.existsById(basked.getId())) {
          throw new IllegalArgumentException("Basked not founded with this ID");
        }
        break;
    }
  }

  private void checkCategoryInRepository(Category category, CategoryRepository categoryRepository,
      Action action, Long id) {
    switch (action) {
      case CREATE:
        if (categoryRepository.existsByType(category.getType())) {
          throw new IllegalArgumentException("Category already exist");
        }
        break;
      case UPDATE:
        forUpdateOrDeleteCategory(category.getId(), categoryRepository);
        break;
      case DELETE:
        forUpdateOrDeleteCategory(id, categoryRepository);
        break;
    }
  }

  private void forUpdateOrDeleteProduct(Long id, ProductRepository productRepository) {
    if (id == null) {
      throw new IllegalArgumentException("Product have empty ID");
    } else if (!productRepository.existsById(id)) {
      throw new IllegalArgumentException("Product not founded with this ID");
    }
  }

  private void forUpdateOrDeleteAccount(Long id, AccountRepository accountRepository) {
    if (id == null) {
      throw new IllegalArgumentException("Account have empty ID");
    } else if (!accountRepository.existsById(id)) {
      throw new IllegalArgumentException("Account not founded with this ID");
    }
  }

  private void forUpdateOrDeleteCategory(Long id, CategoryRepository categoryRepository) {
    if (id == null) {
      throw new IllegalArgumentException("Category have empty ID");
    } else if (!categoryRepository.existsById(id)) {
      throw new IllegalArgumentException("Category not founded with this ID");
    }
  }

}
