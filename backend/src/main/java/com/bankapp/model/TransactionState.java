package com.bankapp.model;

public enum TransactionState {
	STARTED("STARTED"),
	FINISHED("FINISHED");
	
	private String state;
	
	TransactionState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return this.state;
	}
}
