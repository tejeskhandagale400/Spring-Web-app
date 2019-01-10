package com.capgemini.mmbank.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.capgemini.mmbank.account.SavingsAccount;

public class BankAccountMapper implements RowMapper {
	 
	@Override
	public SavingsAccount mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		System.out.println("in map");

		SavingsAccount savingsAccount = new SavingsAccount(resultSet.getInt("account_number"),
				resultSet.getString("account_hn"), resultSet.getDouble("account_balance"),
				 resultSet.getBoolean("salary"));
		return savingsAccount;
	}

}
