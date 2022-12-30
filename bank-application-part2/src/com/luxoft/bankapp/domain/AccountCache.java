package com.luxoft.bankapp.domain;

import com.luxoft.bankapp.exceptions.AccountTypeInvalid;

import java.util.HashMap;
import java.util.Map;

import static com.luxoft.bankapp.domain.AccountFactory.newAccount;

public class AccountCache {
    private static AccountCache INSTANCE;
    private Map<String, AbstractAccount> accountCache;

    private long savingAccountId = 0;
    private long checkingAccountId = 0;

    private AccountCache() {
        accountCache = new HashMap<>();
        accountCache.put("SAVING", AccountFactory.newAccount("SAVING", 0));
        accountCache.put("CHECKING", AccountFactory.newAccount("CHECKING", 0));
    }

    public static AccountCache getInstance()
    {
        if (INSTANCE == null) {
            INSTANCE = new AccountCache();
        }

        return INSTANCE;
    }

    public AbstractAccount getNewAccount(String type) throws AccountTypeInvalid, CloneNotSupportedException {
        AbstractAccount newAccount;

        switch (type.toUpperCase()){
            case "SAVING":
                newAccount = (AbstractAccount) accountCache.get("SAVING").clone();
                break;
            case "CHECKING":
                newAccount = (AbstractAccount) accountCache.get("CHECKING").clone();
                break;
            default:
                throw new AccountTypeInvalid("Invalid account type");
        }

        return newAccount;
    }



}
