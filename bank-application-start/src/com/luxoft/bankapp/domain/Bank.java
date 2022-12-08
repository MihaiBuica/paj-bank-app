package com.luxoft.bankapp.domain;

import java.text.DateFormat;
import java.util.*;

import com.luxoft.bankapp.exceptions.ClientExistsException;
import com.luxoft.bankapp.service.Email;
import com.luxoft.bankapp.service.EmailService;
import com.luxoft.bankapp.utils.ClientRegistrationListener;

public class Bank {
	
	private final Set<Client> clients = new HashSet<>();
	private final List<ClientRegistrationListener> listeners = new ArrayList<ClientRegistrationListener>();
	private final EmailService emailService = new EmailService();

	private int printedClients = 0;
	private int emailedClients = 0;
	private int debuggedClients = 0;
	
	public Bank() {
		listeners.add(new PrintClientListener());
		listeners.add(new EmailNotificationListener());
		listeners.add(new DebugListener());
	}
	
	public int getPrintedClients() {
		return printedClients;
	}

	public int getEmailedClients() {
		return emailedClients;
	}

	public int getDebuggedClients() {
		return debuggedClients;
	}
	
	public void addClient(final Client client) throws ClientExistsException {
    	if (false == clients.add(client)) {
			throw new ClientExistsException("Client already exists into the bank");
    	} 

        notify(client);
	}
	
	private void notify(Client client) {
        for (ClientRegistrationListener listener: listeners) {
            listener.onClientAdded(client);
        }
    }
	
	public Set<Client> getClients() {
		// Requirement: the getClients() method must return unmodifiable client set for clients encapsulation.
		return Collections.unmodifiableSet(clients);
	}

	public void stopEmailService() {
		emailService.close();
	}

	class PrintClientListener implements ClientRegistrationListener {
		@Override 
		public void onClientAdded(Client client) {
	        System.out.println("Client added: " + client.getName());
	        printedClients++;
	    }

	}
	
	class EmailNotificationListener implements ClientRegistrationListener {
		@Override 
		public void onClientAdded(Client client) {
	        System.out.println("Notification email for client " + client.getName() + " to be sent");
	        emailedClients++;
			emailService.sendNotificationEmail(new Email(client, client.getName() + "@mail.com"));
		}
	}
	
	class DebugListener implements ClientRegistrationListener {
        @Override 
        public void onClientAdded(Client client) {
            System.out.println("Client " + client.getName() + " added on: " + DateFormat.getDateInstance(DateFormat.FULL).format(new Date()));
            debuggedClients++;
        }
    }

}




