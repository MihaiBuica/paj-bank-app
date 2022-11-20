package com.luxoft.bankapp.main;

import com.luxoft.bankapp.domain.*;
import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.exceptions.OverdraftLimitExceededException;
import com.luxoft.bankapp.service.BankService;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class BankApplication {
	
	private static Bank bank;
	
	public static void main(String[] args) {
		bank = new Bank();
		modifyBank();
		printBalance();
		BankService.printMaximumAmountToWithdraw(bank);
		if(Arrays.asList(args).contains("-statistics")) {
			displayStatistics();
		}
	}
	
	private static void modifyBank() {
		Client client1 = new Client("John", Gender.MALE);
		Account account1 = new SavingAccount(1, 100);
		Account account2 = new CheckingAccount(2, 100, 20);
		client1.addAccount(account1);
		client1.addAccount(account2);
		
		try {
		   BankService.addClient(bank, client1);
		} catch(ClientExistsException e) {
			System.out.format("Cannot add an already existing client: %s%n", client1.getName());
	    } 

		account1.deposit(100);
		try {
		  account1.withdraw(10);
		} catch (OverdraftLimitExceededException e) {
	    	System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
	    } catch (NotEnoughFundsException e) {
	    	System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
	    }
		
		try {
		  account2.withdraw(90);
		} catch (OverdraftLimitExceededException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
	    } catch (NotEnoughFundsException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
	    }
		
		try {
		  account2.withdraw(100);
		} catch (OverdraftLimitExceededException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, overdraft: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getOverdraft(), e.getAmount());
	    } catch (NotEnoughFundsException e) {
	      System.out.format("Not enough funds for account %d, balance: %.2f, tried to extract amount: %.2f%n", e.getId(), e.getBalance(), e.getAmount());
	    }
		
		try {
		  BankService.addClient(bank, client1);
		} catch(ClientExistsException e) {
		  System.out.format("Cannot add an already existing client: %s%n", client1);
	    } 
	}
	
	private static void printBalance() {
		System.out.format("%nPrint balance for all clients%n");
		for(Client client : bank.getClients()) {
			System.out.println("Client: " + client);
			for (Account account : client.getAccounts()) {
				System.out.format("Account %d : %.2f%n", account.getId(), account.getBalance());
			}
		}
	}

	private static void displayStatistics(){
		System.out.println("==========================================================");
		System.out.println("Bank statistics:");
		System.out.println(" Number of clients: " + BankReport.getNumberOfClients(bank));
		System.out.println(" Number of accounts: " + BankReport.getNumberOfAccounts(bank));
		System.out.println(" Total sum in accounts: " + BankReport.getTotalSumInAccounts(bank));
		System.out.println(" Total sum of bank credits: " + BankReport.getBankCreditSum(bank));
		System.out.println(" Clients by city: N/A");
		System.out.println("==========================================================");
	}

}
