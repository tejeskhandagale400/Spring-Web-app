package com.capgemini.mmbank.dao;
/*
 * package com.capgemini.mmbank.dao;
 * 
 * import java.sql.Connection; import java.sql.PreparedStatement; import
 * java.sql.ResultSet; import java.sql.SQLException; import java.sql.Statement;
 * import java.util.ArrayList; import java.util.List;
 * 
 * import org.springframework.stereotype.Repository;
 * 
 * import com.capgemini.mmbank.account.SavingsAccount; import
 * com.capgemini.mmbank.exception.AccountNotFoundException; import
 * com.capgemini.mmbank.util.DBUtil;
 * 
 * @Repository public class SavingsAccountDAOImpl implements SavingsAccountDAO {
 * 
 * public SavingsAccount createNewAccount(SavingsAccount account) throws
 * ClassNotFoundException, SQLException { Connection connection =
 * DBUtil.getConnection(); PreparedStatement preparedStatement =
 * connection.prepareStatement
 * ("INSERT INTO ACCOUNT VALUES(?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
 * preparedStatement.setInt(1, account.getBankAccount().getAccountNumber());
 * preparedStatement.setString(2,
 * account.getBankAccount().getAccountHolderName());
 * preparedStatement.setDouble(3, account.getBankAccount().getAccountBalance());
 * preparedStatement.setBoolean(4, account.isSalary());
 * preparedStatement.setObject(5, null); preparedStatement.setString(6, "SA");
 * int i = preparedStatement.executeUpdate(); int accountId=0; if(i==1) { try
 * (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) { if
 * (generatedKeys.next()) { accountId=(int) (generatedKeys.getInt(1));
 * System.out.println(accountId); } else { throw new
 * SQLException("Creating user failed, no ID obtained."); } } }
 * //System.out.println(i); preparedStatement.close(); DBUtil.commit(); return
 * account; }
 * 
 * public List<SavingsAccount> getAllSavingsAccount() throws
 * ClassNotFoundException, SQLException { List<SavingsAccount> savingsAccounts =
 * new ArrayList<>(); Connection connection = DBUtil.getConnection(); Statement
 * statement = connection.createStatement(); ResultSet resultSet =
 * statement.executeQuery("SELECT * FROM ACCOUNT"); while (resultSet.next()) {//
 * Check if row(s) is present in table int accountNumber = resultSet.getInt(1);
 * String accountHolderName = resultSet.getString("account_hn"); double
 * accountBalance = resultSet.getDouble(3); boolean salary =
 * resultSet.getBoolean("salary"); SavingsAccount savingsAccount = new
 * SavingsAccount(accountNumber, accountHolderName, accountBalance, salary);
 * savingsAccounts.add(savingsAccount); } DBUtil.commit(); return
 * savingsAccounts; }
 * 
 * @Override public void updateBalance(int accountNumber, double currentBalance)
 * throws ClassNotFoundException, SQLException { Connection connection =
 * DBUtil.getConnection(); connection.setAutoCommit(false); PreparedStatement
 * preparedStatement = connection.prepareStatement
 * ("UPDATE ACCOUNT SET account_balance=? where account_number=?");
 * preparedStatement.setDouble(1, currentBalance); preparedStatement.setInt(2,
 * accountNumber); preparedStatement.executeUpdate(); }
 * 
 * @Override public SavingsAccount getAccountById(int accountNumber) throws
 * ClassNotFoundException, SQLException, AccountNotFoundException { Connection
 * connection = DBUtil.getConnection(); PreparedStatement preparedStatement =
 * connection.prepareStatement ("SELECT * FROM account where account_number=?");
 * preparedStatement.setInt(1, accountNumber); ResultSet resultSet =
 * preparedStatement.executeQuery(); SavingsAccount savingsAccount = null;
 * if(resultSet.next()) { String accountHolderName =
 * resultSet.getString("account_hn"); double accountBalance =
 * resultSet.getDouble(3); boolean salary = resultSet.getBoolean("salary");
 * savingsAccount = new SavingsAccount(accountNumber, accountHolderName,
 * accountBalance, salary); return savingsAccount; } throw new
 * AccountNotFoundException("Account with account number "
 * +accountNumber+" does not exist."); }
 * 
 * @Override public SavingsAccount getAccountByName(String accountHolderName)
 * throws AccountNotFoundException, SQLException, ClassNotFoundException {
 * Connection connection = DBUtil.getConnection(); PreparedStatement
 * preparedStatement = connection.prepareStatement
 * ("SELECT * FROM account where account_hn like ?");
 * preparedStatement.setString(1, accountHolderName); ResultSet resultSet =
 * preparedStatement.executeQuery(); SavingsAccount savingsAccount = null;
 * if(resultSet.next()) { int accountNumber = resultSet.getInt(1); double
 * accountBalance = resultSet.getDouble(3); boolean salary =
 * resultSet.getBoolean("salary"); savingsAccount = new
 * SavingsAccount(accountNumber, accountHolderName, accountBalance, salary);
 * return savingsAccount; } throw new
 * AccountNotFoundException("Account with account holder name "
 * +accountHolderName+" does not exist."); }
 * 
 * 
 * 
 * 
 * 
 * 
 * @Override public boolean deleteAccount(int accountNumber) throws
 * SQLException, ClassNotFoundException, AccountNotFoundException { Connection
 * connection = DBUtil.getConnection(); connection.setAutoCommit(false); if(
 * getAccountById(accountNumber).getBankAccount().getAccountNumber() ==
 * accountNumber) { PreparedStatement preparedStatement =
 * connection.prepareStatement("DELETE From account  WHERE account_number = ?");
 * preparedStatement.setInt(1, accountNumber);
 * preparedStatement.executeUpdate(); DBUtil.commit(); return true; } else
 * return false; }
 * 
 * 
 * @Override public double getAccountBalance(int accountNumber) throws
 * SQLException, AccountNotFoundException, ClassNotFoundException { Connection
 * connection = DBUtil.getConnection(); PreparedStatement preparedStatement =
 * connection.prepareStatement ("SELECT * FROM account where account_number=?");
 * preparedStatement.setInt(1, accountNumber); ResultSet resultSet =
 * preparedStatement.executeQuery();
 * 
 * if(resultSet.next()) { double accountBalance = resultSet.getDouble(3); return
 * accountBalance; } throw new
 * AccountNotFoundException("Account with account number "
 * +accountNumber+" does not exist.");
 * 
 * }
 * 
 * 
 * 
 * @Override public SavingsAccount updateAccountInfo(SavingsAccount
 * savingsAccount) throws ClassNotFoundException, SQLException,
 * AccountNotFoundException {
 * 
 * Connection connection = DBUtil.getConnection(); PreparedStatement
 * preparedStatement = connection.prepareStatement
 * ("UPDATE account SET account_hn=? , salary=? WHERE account_number=?");
 * preparedStatement.setString(1,
 * savingsAccount.getBankAccount().getAccountHolderName());
 * preparedStatement.setBoolean(2, savingsAccount.isSalary());
 * preparedStatement.setInt(3,
 * savingsAccount.getBankAccount().getAccountNumber());
 * preparedStatement.executeUpdate(); DBUtil.commit(); return
 * getAccountById(savingsAccount.getBankAccount().getAccountNumber()); }
 * 
 * @Override public List<SavingsAccount> getAccountByBalRange(double
 * minimumBalance, double maxBalance) throws ClassNotFoundException,
 * SQLException { List<SavingsAccount> savingsAccounts = new ArrayList<>();
 * Connection connection = DBUtil.getConnection(); PreparedStatement
 * preparedStatement = connection.prepareStatement
 * ("SELECT * FROM account WHERE account_balance BETWEEN  ? AND ? ");
 * preparedStatement.setDouble(1,minimumBalance); preparedStatement.setDouble(2,
 * maxBalance); ResultSet resultSet = preparedStatement.executeQuery(); while
 * (resultSet.next()) {// Check if row(s) is present in table int accountNumber
 * = resultSet.getInt(1); String accountHolderName =
 * resultSet.getString("account_hn"); double accountBalance =
 * resultSet.getDouble(3); boolean salary = resultSet.getBoolean("salary");
 * SavingsAccount savingsAccount = new SavingsAccount(accountNumber,
 * accountHolderName, accountBalance, salary);
 * savingsAccounts.add(savingsAccount); }
 * 
 * return savingsAccounts; }
 * 
 * 
 * 
 * 
 * }
 */