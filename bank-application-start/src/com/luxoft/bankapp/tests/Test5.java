package com.luxoft.bankapp.tests;

import com.luxoft.bankapp.domain.*;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;
import com.luxoft.bankapp.service.BankService;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Map;
import java.util.SortedSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Test5 {
    @Test
    public void testBankReportStreams() {
        Bank bank = new Bank();
        Client client1 = new Client("John", Gender.MALE, "Bucharest");
        Client client2 = new Client("Johannes", Gender.MALE, "Munich");
        Account account1 = new SavingAccount(1, 100);
        Account account2 = new CheckingAccount(2, 100, 20);
        Account account3 = new CheckingAccount(3, 1000, 200);
        client1.addAccount(account1);
        client1.addAccount(account2);
        client2.addAccount(account3);
        try {
            BankService.addClient(bank, client1);
        } catch(ClientExistsException e) {
            System.out.format("Cannot add an already existing client: %s%n", client1.getName());
        }

        try {
            BankService.addClient(bank, client2);
        } catch(ClientExistsException e) {
            System.out.format("Cannot add an already existing client: %s%n", client2.getName());
        }

        account1.deposit(100);

        assertEquals(BankReport.getNumberOfClients(bank), BankReportStreams.getNumberOfClients(bank));

        assertEquals(BankReport.getNumberOfAccounts(bank), BankReportStreams.getNumberOfAccounts(bank));

        assertTrue(BankReport.getClientsSorted(bank).equals(BankReportStreams.getClientsSorted(bank)));

        assertEquals(BankReport.getTotalSumInAccounts(bank), BankReportStreams.getTotalSumInAccounts(bank));

        assertEquals(BankReport.getAccountsSortedBySum(bank), BankReportStreams.getAccountsSortedBySum(bank));

        Map<Client, Collection<Account>> map1 = BankReport.getCustomerAccounts(bank);
        Map<Client, Collection<Account>> map2 = BankReport.getCustomerAccounts(bank);
        assertEquals(map1.size(), map2.size());
        for(Map.Entry<Client, Collection<Account>> en1 : map1.entrySet()) {
            Client key = en1.getKey();
            Collection<Account> value = en1.getValue();
            assertTrue(map2.containsKey(key));
            assertEquals(value.size(), map2.get(key).size());
        }

        assertEquals(BankReport.getClientsByCity(bank), BankReportStreams.getClientsByCity(bank));

    }
}
