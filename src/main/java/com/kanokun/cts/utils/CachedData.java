package com.kanokun.cts.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.kanokun.cts.dao.jpaimpl.StaticDataDao;
import com.kanokun.cts.entity.AssetPrices;
import com.kanokun.cts.entity.FxRates;
import com.kanokun.cts.rest.tradeprocessor.TradePreprocessor;

public class CachedData {

	private static final Logger logger = LoggerFactory.getLogger(TradePreprocessor.class);

	@Autowired
	private StaticDataDao staticDatadao;

	//date -> (assetid -> value)
	public static Map<Long,Map<Integer,Double>> assetPriceCache;

	//date -> (ccy -> value)
	public static Map<Long,Map<Integer,Double>> fxratecache;

	//Get below 3 from DB. Done here for convenience
	public static Map<Integer, String> currencyCache = new HashMap<>();

	public static Map<Integer, String> assetCache = new HashMap<>();

	public static Map<Integer, Integer> assetCurrencyCache = new HashMap<>();

	static{
		currencyCache.put(1, "EUR");
		currencyCache.put(2, "GBP");
		currencyCache.put(3, "USD");

		assetCache.put(1, "ABC");
		assetCache.put(2, "DEF");
		assetCache.put(3, "XYZ");

		assetCurrencyCache.put(1, 3);
		assetCurrencyCache.put(2, 2);
		assetCurrencyCache.put(3, 1);
	}

	public Integer getAssetcurrency(Integer assetId){
		return assetCurrencyCache.get(assetId);
	}


	public Double getAssetPrice(Date dataDate, Integer assetId){
		if(assetPriceCache == null){
			initializePriceCache();
		}
		return assetPriceCache.get(dataDate.getTime()) != null ? assetPriceCache.get(dataDate.getTime()).get(assetId) : 0.0;
	}

	public  Double getFXRates(Date dataDate, Integer currencyId){
		if(fxratecache == null){
			initializeFXCache();
		}
		return fxratecache.get(dataDate.getTime()) != null ? fxratecache.get(dataDate.getTime()).get(currencyId) : 0.0;
	}

	private  void initializeFXCache() {
		List<FxRates> fxRatesList = staticDatadao.getFxRates();

		fxratecache = new HashMap<>();
		for (FxRates rates : fxRatesList) {
			Map<Integer,Double> ratesMap = new HashMap<>();
			ratesMap.put(1, rates.getEurRate());
			ratesMap.put(2, rates.getGbpRate());
			ratesMap.put(3, 1.0);
			fxratecache.put(rates.getDataDate().getTime(), ratesMap);
		}

		logger.info("Initialized FX  Cache");

	}

	private  void initializePriceCache() {
		List<AssetPrices> assetPricesList = staticDatadao.getAssetPrices();

		assetPriceCache = new HashMap<>();
		for (AssetPrices assetPrices : assetPricesList) {
			Map<Integer,Double> assetmap = new HashMap<>();
			assetmap.put(1, assetPrices.getAssetABC());
			assetmap.put(2, assetPrices.getAssetDEF());
			assetmap.put(3, assetPrices.getAssetXYZ());
			assetPriceCache.put(assetPrices.getDataDate().getTime(), assetmap);
		}

		logger.info("Initialized Asset Price Cache");

	}


	public String getAssetCode(Integer assetId) {
		return assetCache.get(assetId);
	}


	public String getCurrencyCode(Integer ccy) {
		return currencyCache.get(ccy);
	}

}
