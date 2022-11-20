package com.luxoft.bankapp.domain;

import java.util.*;

public class Client {
	
	private String name;
	private Gender gender;
	private Set<Account> accounts = new HashSet<>();

	public Client(String name, Gender gender) {
		this.name = name;
		this.gender = gender;
	}
	
	public void addAccount(final Account account) {
		accounts.add(account);
	}
	
	public String getName() {
		return name;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public Set<Account> getAccounts() {
		return Collections.unmodifiableSet(accounts);
	}
	
	public String getClientGreeting() {
		if (gender != null) {
			return gender.getGreeting() + " " + name;
		} else {
			return name;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Client)) return false;
		Client client = (Client) obj;
		return (Objects.equals(this.getName(), client.getName()) && (this.getGender() == client.getGender()));
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getName(), this.getGender());
	}
	@Override
	public String toString() {
		return getClientGreeting();
	}

}
