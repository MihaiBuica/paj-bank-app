package com.luxoft.bankapp.domain;

import java.util.*;

public class BankReportStreams {

    public static int getNumberOfClients(Bank bank) {
        return (int) bank.getClients().stream().count();
    }

    public static int getNumberOfAccounts(Bank bank){
        Set<Client> clients = bank.getClients();
        return clients.stream().mapToInt(client -> client.getAccounts().size()).sum();
    }

    public static SortedSet getClientsSorted(Bank bank){
        SortedSet<Client> sortedClients = new TreeSet<>();
        bank.getClients().stream().forEach(client -> sortedClients.add(client));
        return sortedClients;
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
        bank.getClients().stream().forEach(client -> sortedAccounts.addAll(client.getAccounts()));
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
        Set<Client> clients = bank.getClients();
        bank.getClients().stream().forEach(client -> customerAccounts.put(client, client.getAccounts()));
        return customerAccounts;
    }

    public static Map<String, List<Client>> getClientsByCity(Bank bank) {
        // All keys in "natural" order
        Map<String, List<Client>> clientsCity = new TreeMap<>();
        bank.getClients().stream().forEach(client -> {
            if (clientsCity.containsKey(client.getCity())) {
                clientsCity.get(client.getCity()).add(client);
            }
            else {
                List<Client> newCityList = new ArrayList<>();
                newCityList.add(client);
                clientsCity.put(client.getCity(), newCityList);
            }
        });
        return clientsCity;
    }

}
