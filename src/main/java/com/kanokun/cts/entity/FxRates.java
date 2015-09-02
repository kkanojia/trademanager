package com.kanokun.cts.entity;

import java.util.Date;

public class FxRates implements SerializableEntity {

	private static final long serialVersionUID = -8505241693884524073L;

	private Date dataDate;

	private double gbpRate;

	private double eurRate;

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public double getGbpRate() {
		return gbpRate;
	}

	public void setGbpRate(double gbpRate) {
		this.gbpRate = gbpRate;
	}

	public double getEurRate() {
		return eurRate;
	}

	public void setEurRate(double eurRate) {
		this.eurRate = eurRate;
	}

}
