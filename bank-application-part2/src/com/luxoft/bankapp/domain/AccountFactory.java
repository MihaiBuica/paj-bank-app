package com.luxoft.bankapp.domain;

public class AccountFactory {
    public static AbstractAccount newAccount(String accountType, int id) {
        if (accountType == null) {
            return null;
        }
        switch (accountType.toUpperCase()) {
            case "CHECKING":
                return new CheckingAccount(id, 0.0, 0.0);
            case "SAVING":
                return new SavingAccount(id, 0.0);
            default:
                return null;
        }
    }
}
