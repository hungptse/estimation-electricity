package hungpt.services;

import hungpt.entities.AccountEntity;
import hungpt.repositories.AccountRepository;

public class AccountService {
    private static AccountRepository accountRepository = new AccountRepository();

    public AccountEntity checkLogin(String username, String password){
        return accountRepository.checkLogin(username, password);
    }
}
