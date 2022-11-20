package com.luxoft.bankapp.domain;

import java.util.*;

public class BankReport {

    public static int getNumberOfClients(Bank bank) {
        return bank.getClients().size();
    }

    public static int getNumberOfAccounts(Bank bank){
        Set<Client> clients = bank.getClients();
        return clients.stream().mapToInt(client -> client.getAccounts().size()).sum();
    }

    public static SortedSet getClientsSorted(Bank bank){
        return new TreeSet<>(bank.getClients());
    }

    public static double getTotalSumInAccounts(Bank bank) {
        Set<Client> clients = bank.getClients();
        return clients
                .stream()
                .mapToDouble(client -> client.getAccounts()
                        .stream()
                        .mapToDouble(acc -> acc.getBalance())
                        .sum())
                .sum();
    }

    public static SortedSet getAccountsSortedBySum(Bank bank) {
        Comparator<Account> comparator = (a, b) -> (int)(a.getBalance() - b.getBalance());
        SortedSet<Account> sortedAccounts = new TreeSet<>(comparator);
        bank.getClients().forEach(client -> sortedAccounts.addAll(client.getAccounts()));
        return sortedAccounts;
    }

    public static double getBankCreditSum(Bank bank) {
        return bank.getClients()
                .stream()
                .flatMap(client -> client.getAccounts().stream())
                .map(account -> (account.getBalance() >= 0 ? 0 : -account.getBalance()))
                .mapToDouble(Double::new)
                .sum();
    }

    public static Map<Client, Collection<Account>> getCustomerAccounts(Bank bank) {
        Map<Client, Collection<Account>> customerAccounts = new HashMap<>();
        bank.getClients().forEach(client -> customerAccounts.put(client, client.getAccounts()));
        return customerAccounts;
    }

    // TODO: getClientsByCity(...)
}