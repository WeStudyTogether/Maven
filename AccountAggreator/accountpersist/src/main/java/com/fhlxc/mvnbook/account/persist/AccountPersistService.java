package com.fhlxc.mvnbook.account.persist;

/**
* @author Xingchao Long
* @date 2020/42/08 00:42:04
* @ClassName AccountPersistService
* @Description 类描述
*/

public interface AccountPersistService {
    Account createAccount(Account account);
    Account readAccount(String id);
    Account updateAccount(Account account);
    void deleteAccount(String id);
}
