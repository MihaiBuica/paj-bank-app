package com.luxoft.bankapp.service;

import com.luxoft.bankapp.domain.Client;

public class Email {
    private Client client;
    private String to;
    private String from;
    private String subject;
    private String body;

    public Email(Client client, String to) {
        this.client = client;
        this.to = to;
        this.from = "paj@bankapp.com";
        this.subject = "BankApp: Welcome " + client.getClientGreeting() + "!";
        this.body = "Welcome " + client.getClientGreeting();
    }

    @Override
    public String toString() {
        return "Email{" + "client=" + client + ", to='" + to + '\'' + ", from='" + from + '\'' +
                ", subject='" + subject + '\'' + ", body='" + body + '\'' + '}';
    }

}
