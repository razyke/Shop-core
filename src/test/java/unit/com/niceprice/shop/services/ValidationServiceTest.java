package unit.com.niceprice.shop.services;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.niceprice.shop.models.Account;
import com.niceprice.shop.models.Basked;
import com.niceprice.shop.models.Product;
import com.niceprice.shop.repositories.AccountRepository;
import com.niceprice.shop.services.ValidationService;
import com.niceprice.shop.services.impl.ValidationServiceImpl;
import com.niceprice.shop.utils.Action;
import org.junit.Before;
import org.junit.Test;

public class ValidationServiceTest {

  private final ValidationService validationService = new ValidationServiceImpl();
  private Account account;
  private AccountRepository repository;

  @Before
  public void prepare() {
    account = Account.builder()
        .firstName("Fred")
        .lastName("Broxon")
        .email("fredbr@gmail.com")
        .basked(new Basked())
        .login("brox")
        .password("sha256asfewfqg4q4ger2r3fdsf")
        .build();

    repository = mock(AccountRepository.class);

    when(repository.existsAccountByEmail(account.getEmail())).thenReturn(false);
    when(repository.existsAccountByLogin(account.getLogin())).thenReturn(false);
  }

  @Test
  public void validationFineOnCheckMethodTest() {
    Long id = 5L;
    Account account = new Account();
    Product product = new Product();

    validationService.check(id);
    validationService.check(account);
    validationService.check(product);
  }

  @Test(expected = IllegalArgumentException.class)
  public void expectExceptionOnCheckMethodTest() {
    validationService.check(null);
  }

  @Test
  public void confidentObjectCheckForActionCreateTest() {
    validationService.checkForAction(account, repository, Action.CREATE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void noFirstNameAccountCheckForActionCreateTest() {
    account.setFirstName("    ");
    validationService.checkForAction(account, repository, Action.CREATE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void firstNameAccountIsNullCheckForActionCreateTest() {
    account.setFirstName(null);
    validationService.checkForAction(account, repository, Action.CREATE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void noLastNameAccountCheckForActionCreateTest() {
    account.setLastName("");
    validationService.checkForAction(account, repository, Action.CREATE);
  }
  @Test(expected = IllegalArgumentException.class)
  public void lastNameAccountInNullCheckForActionCreateTest() {
    account.setLastName(null);
    validationService.checkForAction(account, repository, Action.CREATE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badEmailAccountCheckForActionCreateTest() {
    account.setEmail("wdddef@.eeeeeeeeeee");
    validationService.checkForAction(account, repository, Action.CREATE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullEmailAccountCheckForActionCreateTest() {
    account.setEmail(null);
    validationService.checkForAction(account, repository, Action.CREATE);
  }
  @Test(expected = IllegalArgumentException.class)
  public void emptyEmailAccountCheckForActionCreateTest() {
    account.setEmail("      ");
    validationService.checkForAction(account, repository, Action.CREATE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void loginAlreadyExistCheckForActionCreateTest() {
    when(repository.existsAccountByLogin(account.getLogin())).thenReturn(true);
    validationService.checkForAction(account, repository, Action.CREATE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void loginAccountNullCheckForActionCreateTest() {
    account.setLogin(null);
    validationService.checkForAction(account, repository,Action.CREATE);
  }
  //TODO make tests for all situations.




}