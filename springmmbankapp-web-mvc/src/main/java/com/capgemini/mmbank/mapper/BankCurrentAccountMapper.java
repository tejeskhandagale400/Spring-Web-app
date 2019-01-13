package com.capgemini.mmbank.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.capgemini.mmbank.account.CurrentAccount;
import com.capgemini.mmbank.account.SavingsAccount;

public class BankCurrentAccountMapper implements RowMapper {

	public CurrentAccount mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		CurrentAccount currentAccount = new CurrentAccount(resultSet.getInt("account_number"),
				resultSet.getString("account_hn"), resultSet.getDouble("account_balance"),
				resultSet.getDouble("od_limit"),resultSet.getString("type"));
		return currentAccount;
	}
}
