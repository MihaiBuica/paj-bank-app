package com.luxoft.bankapp.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.luxoft.bankapp.domain.*;
import org.junit.jupiter.api.Test;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.service.BankService;

public class Test4 {
    @Test
    public void testGetClientsByCity() throws ClientExistsException {
        // Bank statistics test
        Bank bank = new Bank();
        Client client1 = new Client("Smith John", Gender.MALE, "BCity");
        Client client2 = new Client("Client 2", Gender.FEMALE, "ACity");
        Client client3 = new Client("Client 3", Gender.FEMALE, "ACity");
        Client client4 = new Client("Client 4", Gender.FEMALE, "BCity");
        BankService.addClient(bank, client1);
        BankService.addClient(bank, client2);
        BankService.addClient(bank, client3);
        BankService.addClient(bank, client4);
        assertEquals(4, bank.getClients().size());

        System.out.println("==========================================================");
        System.out.println("Bank statistics:");
        System.out.println(" Number of clients: " + BankReport.getNumberOfClients(bank));
        System.out.println(" Number of accounts: " + BankReport.getNumberOfAccounts(bank));
        System.out.println(" Total sum in accounts: " + BankReport.getTotalSumInAccounts(bank));
        System.out.println(" Total sum of bank credits: " + BankReport.getBankCreditSum(bank));
        System.out.println(" Clients by city: " + BankReport.getClientsByCity(bank));
        System.out.println("==========================================================");
    }

}
