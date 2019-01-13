package com.capgemini.mmbank.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.capgemini.mmbank.account.CurrentAccount;
import com.capgemini.mmbank.account.SavingsAccount;
import com.capgemini.mmbank.exception.AccountNotFoundException;
import com.capgemini.mmbank.mapper.BankAccountMapper;
import com.capgemini.mmbank.mapper.BankCurrentAccountMapper;

@Repository
@Primary
public class CurrentAccountSJDAOImpl implements CurrentAccountDAO {

	private Logger logger = Logger.getLogger(CurrentAccountSJDAOImpl.class.getName());
	@Autowired
	private JdbcTemplate tempaTemplate;

	@Override
	public CurrentAccount createNewAccount(CurrentAccount account)  {
		logger.info("In jdbc  DaoIMP ");
		tempaTemplate.update("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)",
				new Object[] { account.getBankAccount().getAccountNumber(),
						account.getBankAccount().getAccountHolderName(), account.getBankAccount().getAccountBalance(),
						null, account.getOdLimit(), "CA" });
		return account;

	}

	@Override
	public CurrentAccount getAccountById(int accountNumber)
			throws AccountNotFoundException {
		CurrentAccount currentAccount = (CurrentAccount) tempaTemplate.queryForObject(
				"SELECT * FROM account where account_number=?", new Object[] { accountNumber },
				new BankCurrentAccountMapper());
		return currentAccount;
	}

	@Override
	public boolean deleteAccount(int accountNumber)
			throws  AccountNotFoundException {
		tempaTemplate.update("DELETE From account  WHERE account_number = ?", accountNumber);
		return true;

	}

	@Override
	public List<CurrentAccount> getAllCurrentAccount(){
		return tempaTemplate.query("SELECT * FROM ACCOUNT", new BankCurrentAccountMapper());

	}

	@Override
	public void updateBalance(int accountNumber, double currentBalance){
		tempaTemplate.update("UPDATE ACCOUNT SET account_balance=? where account_number=?",
				new Object[] { currentBalance, accountNumber });

	}

	@Override
	public double getAccountBalance(int accountNumber)
			throws  AccountNotFoundException{
		double accountBalance = getAccountById(accountNumber).getBankAccount().getAccountBalance();
		return accountBalance;
	}

	@Override
	public CurrentAccount getAccountByName(String accountHolderName)
			throws AccountNotFoundException{
		return (CurrentAccount) tempaTemplate.queryForObject("SELECT * FROM account where account_hn =?",
				new Object[] { accountHolderName }, new BankCurrentAccountMapper());
	}

	@Override
	public CurrentAccount updateAccountInfo(CurrentAccount currentAccount)
			throws AccountNotFoundException {
		tempaTemplate.update("UPDATE account SET account_hn=? , od_limit=? WHERE account_number=?",
				new Object[] { currentAccount.getBankAccount().getAccountHolderName(), currentAccount.getOdLimit(),
						currentAccount.getBankAccount().getAccountNumber()});
		return currentAccount;
	}

	@Override
	public List<CurrentAccount> getAccountByBalRange(double minimumBalance, double maxBalance) {
		return tempaTemplate.query("SELECT * FROM account WHERE account_balance BETWEEN  ? AND ? ",
				new Object[] { minimumBalance, maxBalance }, new BankCurrentAccountMapper());
	}

}
