package com.capgemini.mmbank.validation;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.capgemini.mmbank.account.SavingsAccount;
import com.capgemini.mmbank.exception.InvalidInputException;

@Component
@Aspect
public class MMBankappCurrentAccValidation {


	private Logger logger = Logger.getLogger(MMBankappValidation.class.getName());

	@Around("execution(* com.capgemini.mmbank.service.CurrentAccountServiceImpl.deposit(..))")
	public void logAroundDeposite(ProceedingJoinPoint joinPoint) {

		Object parametrs[] = joinPoint.getArgs();

		if ((Double) parametrs[1] > 0) {
			try {
				logger.info("inside deposit");
				joinPoint.proceed();
			} catch (Throwable e) {
				logger.info(" Invalid account");
			}
		}

		else {
			logger.info("Invalid Input Amount!"); // throw new Exception(); throw
			new InvalidInputException("Invalid Input Amount!");
		}

	}

	@Around("execution(* com.capgemini.mmbank.service.CurrentAccountServiceImpl.withdraw(..))")
	public void logAroundWithdraw(ProceedingJoinPoint joinPoint) throws Throwable {

		Object parametrs[] = joinPoint.getArgs();
		SavingsAccount account = (SavingsAccount) parametrs[0];

		if ((Double) parametrs[1] > 0 && account.getBankAccount().getAccountBalance() >= (Double) parametrs[1]) {
 				logger.info("inside withdraw");
				joinPoint.proceed();
				logger.info("Withdraw Successful");
			 
		}

		else {
			logger.info("Invalid Input or Insufficient Funds!"); // throw new
			throw new InvalidInputException("Invalid Input Amount!");
		}

	}
	
	
	
	@Around("execution(* com.capgemini.mmbank.service.CurrentAccountServiceImpl.fundTransfer(..))")
	public void logAroundFundTransfer(ProceedingJoinPoint joinPoint) throws Throwable {

		Object parametrs[] = joinPoint.getArgs();
		SavingsAccount senderAccount = (SavingsAccount) parametrs[0];
		SavingsAccount recieverAccount = (SavingsAccount) parametrs[1];
		double amount = (Double)parametrs[2];
		if (amount > 0 && senderAccount.getBankAccount().getAccountBalance() >= amount ) {
	 		logger.info("inside Transfer");
			joinPoint.proceed();
				logger.info("Transfer Successful");
		 
	}
		else {
			logger.info("Invalid Input or Insufficient Funds!"); // throw new
 		}

	}


	@AfterThrowing(pointcut = "execution(* com.capgemini.mmbank.service.CurrentAccountServiceImpl.fundTransfer(..))", throwing = "ex")
	public void exceptions(JoinPoint jp, Throwable ex) {

		logger.info("Exception is   " + ex);
	}

}
