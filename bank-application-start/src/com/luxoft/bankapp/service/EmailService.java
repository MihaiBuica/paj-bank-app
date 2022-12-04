package com.luxoft.bankapp.service;

public class EmailService {

    private Queue emailQueue;
    private Thread thread;


    public EmailService(){
        emailQueue = new Queue();
        thread = new Thread(emailQueue);
        thread.start();
    }

    public void sendNotificationEmail(Email email) {
        emailQueue.add(email);
    }

    public void close(){
        System.out.println("EmailService closing..");
        emailQueue.stopThread();
    }
}
