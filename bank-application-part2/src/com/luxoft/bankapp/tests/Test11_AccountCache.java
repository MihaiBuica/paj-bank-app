package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.*;
import com.luxoft.bankapp.exceptions.AccountTypeInvalid;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;
import org.junit.Test;

import static org.junit.Assert.*;

public class Test11_AccountCache {
    @Test
    public void testSavingAccount() throws NotEnoughFundsException, AccountTypeInvalid, CloneNotSupportedException {
        AccountCache accounts = AccountCache.getInstance();
        Account savingAccount = accounts.getNewAccount("SAVING");
        savingAccount.deposit(100.0);
        savingAccount.withdraw(50.0);
        assertEquals("SavingAccount", savingAccount.getClass().getSimpleName());
        assertEquals(0, savingAccount.getId());
        assertEquals(50, savingAccount.getBalance(), 0);
        assertEquals(50, savingAccount.maximumAmountToWithdraw(), 0);
    }

    @Test
    public void testCheckingAccount() throws OverdraftLimitExceededException, AccountTypeInvalid, CloneNotSupportedException {
        AccountCache accounts = AccountCache.getInstance();
        CheckingAccount checkingAccount = (CheckingAccount) accounts.getNewAccount("CHECKING");

        checkingAccount.deposit(1100.0);
        checkingAccount.setOverdraft(100);
        checkingAccount.withdraw(1150.0);
        assertEquals("CheckingAccount", checkingAccount.getClass().getSimpleName());
        assertEquals(0, checkingAccount.getId());
        assertEquals(-50, checkingAccount.balance, 0);
        assertEquals(100, checkingAccount.overdraft, 0);
        assertEquals(50, checkingAccount.maximumAmountToWithdraw(), 0);
    }

    @Test(expected=AccountTypeInvalid.class)
    public void testInvalidAccount() throws AccountTypeInvalid, CloneNotSupportedException {
        AccountCache accounts = AccountCache.getInstance();
        Account invalidAccount = accounts.getNewAccount("ASD");
    }

    @Test
    public void testMultipleAccounts() throws NotEnoughFundsException, AccountTypeInvalid, CloneNotSupportedException {
        AccountCache accounts = AccountCache.getInstance();
        Account savingAccount1 = accounts.getNewAccount("SAVING");
        Account savingAccount2 = accounts.getNewAccount("SAVING");

        assertTrue(savingAccount1.equals(savingAccount2));
        assertEquals("SavingAccount", savingAccount1.getClass().getSimpleName());
        assertEquals("SavingAccount", savingAccount2.getClass().getSimpleName());
        assertEquals(0, savingAccount1.getId());
        assertEquals(0, savingAccount2.getId());

        savingAccount1.deposit(100.0);
        savingAccount1.withdraw(50.0);
        savingAccount2.deposit(200.0);

        // Same id
        assertFalse(savingAccount1.equals(savingAccount2));

        assertEquals(50.0, savingAccount1.getBalance(), 0);
        assertEquals(200.0, savingAccount2.getBalance(), 0);

    }

}
