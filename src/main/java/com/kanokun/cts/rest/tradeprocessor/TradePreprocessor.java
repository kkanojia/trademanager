package com.kanokun.cts.rest.tradeprocessor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanokun.cts.dao.TradeEntryDao;
import com.kanokun.cts.entity.TradeEntry;
import com.kanokun.cts.utils.CachedData;

public class TradePreprocessor {

	private static final Logger logger = LoggerFactory.getLogger(TradePreprocessor.class);

	@Autowired
	private TradeEntryDao tradeEntryDao;

	@Autowired
	private CachedData cacheData;

	public TradeEntry processAndSaveTrade(TradeEntry entry){

		entry.setPriceUSD(getUsdPrice(entry));

		if(entry.getBuySell().equalsIgnoreCase("Buy")){
			entry.setOpenQuantity(entry.getQuantity());
			return tradeEntryDao.save(entry);
		}

		List<TradeEntry> openTrades = tradeEntryDao.getAllNonSettledEntry(entry.getAssetId(), entry.getTradeDateObject());

		//No Open trades to sell
		if(isShortSell(entry, openTrades)){
			logger.info("Short Selling detected. Will not save the trade.");
			return null;
		}

		double totalProfit = settleTradesAndGetProfit(entry, openTrades);

		entry.setProfit(totalProfit);

		return tradeEntryDao.save(entry);
	}

	private double settleTradesAndGetProfit(TradeEntry entry, List<TradeEntry> openTrades) {
		int quantityToSell = entry.getQuantity();
		double totalProfit = 0;
		for (int i = 0; i < openTrades.size() &&  quantityToSell > 0 ; i++) {
			TradeEntry buyTrade = openTrades.get(i);
			if(buyTrade.getOpenQuantity() > quantityToSell){
				buyTrade.setOpenQuantity(buyTrade.getOpenQuantity() - quantityToSell);
				totalProfit += quantityToSell * ( entry.getPriceUSD() - buyTrade.getPriceUSD());
				quantityToSell = 0;
			}else{
				totalProfit += buyTrade.getOpenQuantity() * ( entry.getPriceUSD() - buyTrade.getPriceUSD() );
				quantityToSell -= buyTrade.getOpenQuantity();
				buyTrade.setOpenQuantity(0);
				buyTrade.setSettled(true);
			}
			tradeEntryDao.save(buyTrade);
		}
		return totalProfit;
	}

	private boolean isShortSell(TradeEntry entry, List<TradeEntry> openTrades) {
		if(openTrades.size() < 1){
			return true;
		}

		Integer totalQuantity = 0;
		for (TradeEntry tradeEntry : openTrades) {
			totalQuantity += tradeEntry.getOpenQuantity();
		}

		//Cannot sell more than you already have
		if(totalQuantity < entry.getQuantity()){
			return true;
		}
		return false;
	}

	private double getUsdPrice(TradeEntry entry) {
		Integer assetCurrency = cacheData.getAssetcurrency(entry.getAssetId());
		logger.info("Date : " + entry.getTradeDateObject());
		logger.info("Asset Price: " + cacheData.getAssetPrice(entry.getTradeDateObject(), entry.getAssetId()) );
		logger.info("FX: " + cacheData.getFXRates(entry.getTradeDateObject(), assetCurrency) );
		double priceUSD = cacheData.getAssetPrice(entry.getTradeDateObject(), entry.getAssetId())
				* cacheData.getFXRates(entry.getTradeDateObject(), assetCurrency);
		return priceUSD;
	}

	public void setTradeEntryDao(TradeEntryDao tradeEntryDao) {
		this.tradeEntryDao = tradeEntryDao;
	}

	public void setCacheData(CachedData cacheData) {
		this.cacheData = cacheData;
	}

}
