package com.kanokun.cts.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.kanokun.cts.utils.CustomDateDeserialize;

@javax.persistence.Entity
@Table(name = "TradeInformation")
@JsonIgnoreProperties(ignoreUnknown = true)
public class TradeEntry implements SerializableEntity {

	private static final long serialVersionUID = -5388202133784638034L;

	@Id
	@GeneratedValue(generator = "trade_seq")
	@SequenceGenerator(name = "trade_seq", sequenceName = "TRADE_SEQ", allocationSize = 1)
	private long id;

	@Column
	@JsonDeserialize(using = CustomDateDeserialize.class)
	private Date tradeDate;

	@Column
	private String buySell;

	@Column
	private Integer assetId;

	@Column
	private Integer quantity;

	@Column
	private boolean settled;

	@Column
	private Integer openQuantity;

	@Column
	private double profit;

	@Column
	private double priceUSD;

	private double cumulativeProfit;
	private String assetCode;
	private String currency;
	private double pricelocal;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTradeDate() {
		if (tradeDate == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		return sdf.format(tradeDate);
	}

	//hack fix later
	public Date getTradeDateObject() {
		return  tradeDate;
	}

	public void setTradeDate(Date tradeDate) {
		this.tradeDate = tradeDate;
	}

	public String getBuySell() {
		return buySell;
	}

	public void setBuySell(String buySell) {
		this.buySell = buySell;
	}

	public Integer getAssetId() {
		return assetId;
	}

	public void setAssetId(Integer assetId) {
		this.assetId = assetId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getOpenQuantity() {
		return openQuantity;
	}

	public void setOpenQuantity(Integer openQuantity) {
		this.openQuantity = openQuantity;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public boolean isSettled() {
		return settled;
	}

	public void setSettled(boolean settled) {
		this.settled = settled;
	}

	public double getPriceUSD() {
		return priceUSD;
	}

	public void setPriceUSD(double buyPriceUSD) {
		this.priceUSD = buyPriceUSD;
	}

	public double getCumulativeProfit() {
		return cumulativeProfit;
	}

	public void setCumulativeProfit(double cumulativeProfit) {
		this.cumulativeProfit = cumulativeProfit;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public double getPricelocal() {
		return pricelocal;
	}

	public void setPricelocal(double pricelocal) {
		this.pricelocal = pricelocal;
	}

}
