package com.luxoft.bankapp.exceptions;

public class AccountTypeInvalid extends BankException{
    private static final long serialVersionUID = 3214520997410884218L;

    public AccountTypeInvalid(String message) {
        super(message);
    }
}
