package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import java.util.Date;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
    private Date lastWithdrawDate;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

public void withdraw(double amount) {
    if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
    	if (accountType == MAXI_SAVINGS) {
			lastWithdrawDate = new Date();
		}
        transactions.add(new Transaction(-amount));
    }
}

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
            	if (lastWithdrawDate != null) {
    				long diff = (new Date().getTime()) - (lastWithdrawDate.getTime());
    				int numOfDays = (int) (diff / (1000 * 60 * 60 * 24));
    				if (amount > 0 && numOfDays > 10)
    					return amount * 0.05;
    			}
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }
   
    public double getTotalAmount() {
		double amount = 0.0;
		for (Transaction t : transactions)
			amount += t.amount;
		return amount;
	}
    
    public double dailyInterest() {
		double dailyInterest = (double) interestEarned() / 365;
		DecimalFormat df2 = new DecimalFormat("###.###");
		return Double.valueOf(df2.format(dailyInterest));
	}
	
	//This method not required but it added to MAXI SAVINGS with draw scenario
	public void setLastWithdrawDate(Date date){
		this.lastWithdrawDate = date;
		
	}

}
