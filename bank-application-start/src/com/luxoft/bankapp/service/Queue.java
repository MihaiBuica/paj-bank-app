package com.luxoft.bankapp.service;

import java.util.ArrayList;
import java.util.List;

public class Queue implements Runnable {
    private List<Email> queue;
    private boolean shouldStop = false;

    Queue(){
        queue = new ArrayList<>();
    }

    void add(Email email){
        if (shouldStop) {
            ;
        }
        else {
            queue.add(email);
            synchronized (this){
                this.notify();
            }
        }
    }

    void stopThread(){
        shouldStop = true;
        synchronized (this){
            this.notify();
        }
    }

    private void sendEmail() {
        Email email = queue.remove(0);
        System.out.println("[EmailQ: " + Thread.currentThread().getId() + "] Sending email: " + email.toString());
    }
    private synchronized void checkEmailsQueue(){
        while(!queue.isEmpty()){
            sendEmail();
        }
        try {
            this.wait();
        } catch (InterruptedException e) {
            // exit
        }
    }

    @Override
    public void run() {
        while(!shouldStop){
            checkEmailsQueue();
        }
        System.out.println("EmailsQueue thread has stopped.");
    }
}
