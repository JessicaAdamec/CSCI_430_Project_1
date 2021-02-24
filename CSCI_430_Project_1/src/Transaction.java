/**
  * NAME: Eric Young
  * DATE: 2/24/2021
  */

import java.util.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;

public class Transaction implements Serializable {
	private Date date;
	private String description;
	private double amount;
	
	public Transaction(String description, double amount) {
		this.date = Calendar.getInstance().getTime();
		this.description = description;
		this.amount = amount;
	}
	
	public Date getDate() {
		return date;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("mm-dd-yyyy hh:mm:ss");
		String strDate = dateFormat.format(date);
		String strAmount = String.format("%.2f", amount);

		String string = "Transaction Date: " + strDate + " Description: " + description + " Amount: $" + strAmount;
		return string;
	}
}