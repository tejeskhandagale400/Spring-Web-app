package com.capgemini.mmbank.account;

public class BankAccount {
	private  int accountNumber;
	private double accountBalance;
	private String accountHolderName;
	private String type;

	/*private static int accountId;
*/
	/*static {
		accountId = 100;
	}*/

	public BankAccount(String accountHolderName, double accountBalance) {
		//accountNumber = ++accountId;
		this.accountHolderName = accountHolderName;
		this.accountBalance = accountBalance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BankAccount(String accountHolderName) {
	//	accountNumber = ++accountId;
		this.accountHolderName = accountHolderName;
	}

	public BankAccount(int accountNumber, String accountHolderName, double accountBalance) {
		this.accountNumber = accountNumber;
		this.accountHolderName = accountHolderName;
		this.accountBalance = accountBalance;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public BankAccount(int accountNumber, double accountBalance, String accountHolderName, String type) {
		super();
		this.accountNumber = accountNumber;
		this.accountBalance = accountBalance;
		this.accountHolderName = accountHolderName;
		this.type = type;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public double getAccountBalance() {
		return accountBalance;
	}
	
	@Override
	public String toString() {
		return " accountNumber=" + accountNumber + ", accountBalance=" + accountBalance
				+ ", accountHolderName=" + accountHolderName + "]";
	}

}
