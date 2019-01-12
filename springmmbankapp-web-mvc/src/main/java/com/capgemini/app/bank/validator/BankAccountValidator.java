package com.capgemini.app.bank.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.capgemini.app.bank.pojo.Employee;
import com.capgemini.mmbank.account.SavingsAccount;

@Component
public class BankAccountValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {

		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		/*
		 * SavingsAccount savingAccount = (SavingsAccount) target;
		 * if(savingAccount.getBankAccount().getAccountBalance() <) {
		 * errors.rejectValue("empName", "empname.length",
		 * "Employee Name must have 2 or more than 2 characters"); }
		 * if(employee.getSalary()<100000) { errors.rejectValue("salary",
		 * "salary.length", "salary must greater than 100000");
		 * 
		 * }
		 */
		double amount =(double)target;
		if(amount <0 ) {
			errors.rejectValue("amount", "amount.length", "amount must be greater than 100000 ");
		}
	}

}
