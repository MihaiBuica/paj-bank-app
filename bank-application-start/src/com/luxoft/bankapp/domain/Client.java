package com.luxoft.bankapp.domain;

import java.util.*;

public class Client implements Comparable{
	
	private String name;
	private Gender gender;
	private String city;
	private Set<Account> accounts = new HashSet<>();

	public Client(String name, Gender gender) {
		this.name = name;
		this.gender = gender;
		this.city = "N/A";
	}

	public Client(String name, Gender gender, String city) {
		this.name = name;
		this.gender = gender;
		this.city = city.toLowerCase();
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

	public String getCity(){
		return city;
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
		return (Objects.equals(this.getName(), client.getName()) && (this.getGender() == client.getGender()) &&
				(this.getCity() == client.getCity()) );
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.getName(), this.getGender());
	}
	@Override
	public String toString() {
		return getClientGreeting();
	}

	@Override
	public int compareTo(Object o) {
		int ret = 0;

		if (!(o instanceof Client)) {
			ret = 1;
		}

		Client client2 = (Client) o;

		if (this.equals(client2)) {
			ret = 0;
		}
		else if (this.name.compareTo(client2.getName()) == 0) {
			if (this.gender.compareTo(client2.getGender()) == 0) {
				ret = this.city.compareTo(client2.getCity());
			}
			else {
				ret = this.gender.compareTo(client2.getGender());
			}
		}
		else
		{
			ret = this.name.compareTo(client2.getName());
		}

		return ret;
	}
}
