package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.Account;
import com.luxoft.bankapp.domain.AccountFactory;
import com.luxoft.bankapp.domain.CheckingAccount;
import com.luxoft.bankapp.domain.SavingAccount;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Test10_AccountFactory {
    @Test
    public void testSavingAccount() throws NotEnoughFundsException {
        Account savingAccount = AccountFactory.newAccount("SAVING", 1);
        savingAccount.deposit(100.0);
        savingAccount.withdraw(50.0);
        assertEquals("SavingAccount", savingAccount.getClass().getSimpleName());
        assertEquals(1, savingAccount.getId());
        assertEquals(50, savingAccount.getBalance(), 0);
        assertEquals(50, savingAccount.maximumAmountToWithdraw(), 0);
    }

    @Test
    public void testCheckingAccount() throws OverdraftLimitExceededException {
        CheckingAccount checkingAccount = (CheckingAccount) AccountFactory.newAccount("CHECKING", 2);
        checkingAccount.deposit(1100.0);
        checkingAccount.setOverdraft(100);
        checkingAccount.withdraw(1150.0);
        assertEquals("CheckingAccount", checkingAccount.getClass().getSimpleName());
        assertEquals(2, checkingAccount.getId());
        assertEquals(-50, checkingAccount.getBalance(), 0);
        assertEquals(100, checkingAccount.getOverdraft(), 0);
        assertEquals(50, checkingAccount.maximumAmountToWithdraw(), 0);
    }
}
