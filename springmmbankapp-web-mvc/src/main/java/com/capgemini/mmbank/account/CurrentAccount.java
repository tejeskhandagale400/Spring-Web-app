package com.capgemini.mmbank.account;

public class CurrentAccount {
	private double odLimit;
	private BankAccount bankAccount;

	public CurrentAccount(String accountHolderName, double accountBalance,  double odLimit) {
		bankAccount = new BankAccount(accountHolderName, accountBalance);
		this.odLimit = odLimit;
	}
	public CurrentAccount(String accountHolderName, double odLimit) {
		bankAccount = new BankAccount(accountHolderName);
		this.odLimit = odLimit;
	}

	public CurrentAccount(int accountNumber, String accountHolderName, double accountBalance,double odLimit) {
		bankAccount = new BankAccount(accountNumber, accountHolderName, accountBalance);
		this.odLimit = odLimit;
	}
	public CurrentAccount(int accountNumber, String accountHolderName, double accountBalance, double odLimit, String type) {
		bankAccount = new BankAccount(accountNumber, accountBalance, accountHolderName, type);
		this.odLimit = odLimit;

 	}
	
	public double getOdLimit() {
		return odLimit;
	}

	public void setOdlimit(double odlimit) {
		this.odLimit = odLimit;
	}
	
	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	public int getTypeOfAccount() {
		return 2;
	}
	@Override
	public String toString() {
		return "CurrentAccount [salary=" + odLimit + "," + bankAccount + "\n";
	}
}
