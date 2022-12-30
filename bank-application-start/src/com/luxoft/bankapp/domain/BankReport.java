package com.luxoft.bankapp.domain;

import java.util.*;

public class BankReport {

    public static int getNumberOfClients(Bank bank) {
        return bank.getClients().size();
    }

    public static int getNumberOfAccounts(Bank bank){
        int numOfAccounts = 0;
        Set<Client> clients = bank.getClients();
        for(Client client:clients) {
            numOfAccounts += client.getAccounts().size();
        }
        return numOfAccounts;
    }

    public static SortedSet getClientsSorted(Bank bank){
        return new TreeSet<>(bank.getClients());
    }

    public static double getTotalSumInAccounts(Bank bank) {
        double generalSum = 0;
        Set<Client> clients = bank.getClients();
        for(Client client : clients) {
            Set<Account> accounts = client.getAccounts();
            for(Account account : accounts) {
                generalSum += account.getBalance();
            }
        }
        return generalSum;
    }

    public static SortedSet getAccountsSortedBySum(Bank bank) {
        Comparator<Account> comparator = (a, b) -> (int)(a.getBalance() - b.getBalance());
        SortedSet<Account> sortedAccounts = new TreeSet<>(comparator);
        for (Client client : bank.getClients()) {
            sortedAccounts.addAll(client.getAccounts());
        }
        return sortedAccounts;
    }

    public static double getBankCreditSum(Bank bank) {
        double generalSum = 0;
        Set<Client> clients = bank.getClients();
        for(Client client : clients) {
            Set<Account> accounts = client.getAccounts();
            for(Account account : accounts) {
                generalSum += (account.getBalance() >= 0 ? 0 : -account.getBalance());
            }
        }
        return generalSum;
    }

    public static Map<Client, Collection<Account>> getCustomerAccounts(Bank bank) {
        Map<Client, Collection<Account>> customerAccounts = new HashMap<>();
        Set<Client> clients = bank.getClients();

        for (Client client : bank.getClients()) {
            customerAccounts.put(client, client.getAccounts());
        }

        return customerAccounts;
    }

    public static Map<String, List<Client>> getClientsByCity(Bank bank) {
        // All keys in "natural" order
        Map<String, List<Client>> clientsCity = new TreeMap<>();
        bank.getClients().forEach(client -> {
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
