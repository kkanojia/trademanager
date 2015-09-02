package com.kanokun.cts.rest.tradeprocessor;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.*;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.kanokun.cts.dao.TradeEntryDao;
import com.kanokun.cts.entity.AssetPrices;
import com.kanokun.cts.entity.TradeEntry;
import com.kanokun.cts.utils.CachedData;

public class TradePreprocessorTest {

	TradePreprocessor preprocessor;

	TradeEntryDao tradeEntryDao;

	CachedData cacheData;

	@Before
	public void setup(){
		preprocessor = new TradePreprocessor();
		tradeEntryDao = mock(TradeEntryDao.class);
		cacheData = mock(CachedData.class);
		preprocessor.setCacheData(cacheData);
		preprocessor.setTradeEntryDao(tradeEntryDao);

	}

	@Test
	public void testSimpleBuyTrade() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = df.parse("2015-08-01");
		when(cacheData.getAssetcurrency(anyInt())).thenReturn(1);
		when(cacheData.getAssetPrice(any(Date.class),anyInt())).thenReturn(12.0);
		when(cacheData.getFXRates(any(Date.class),anyInt())).thenReturn(1.52);
		TradeEntry trade = new TradeEntry();
		trade.setTradeDate(dt);
		trade.setAssetId(1);
		trade.setId(23);
		trade.setBuySell("Buy");
		when(tradeEntryDao.save(any(TradeEntry.class))).thenReturn(trade);
		TradeEntry response = preprocessor.processAndSaveTrade(trade);
		assertEquals(trade.getId(), response.getId());
	}

	@Test
	public void testSimpleSellTrade() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = df.parse("2015-08-01");
		when(cacheData.getAssetcurrency(anyInt())).thenReturn(1);
		when(cacheData.getAssetPrice(any(Date.class),anyInt())).thenReturn(10.0);
		when(cacheData.getFXRates(any(Date.class),anyInt())).thenReturn(1.0);
		TradeEntry trade = new TradeEntry();
		trade.setTradeDate(dt);
		trade.setAssetId(1);
		trade.setId(23);
		trade.setQuantity(100);
		trade.setBuySell("Sell");
		when(tradeEntryDao.save(any(TradeEntry.class))).thenReturn(trade);
		List<TradeEntry> list = new ArrayList<>();
		TradeEntry buyTrade = new TradeEntry();
		buyTrade.setTradeDate(dt);
		buyTrade.setAssetId(1);
		buyTrade.setId(22);
		buyTrade.setBuySell("Buy");
		buyTrade.setSettled(false);
		buyTrade.setOpenQuantity(100);
		buyTrade.setPriceUSD(9);

		list.add(buyTrade);
		when(tradeEntryDao.getAllNonSettledEntry(anyInt(), any(Date.class))).thenReturn(list );

		TradeEntry response = preprocessor.processAndSaveTrade(trade);
		assertEquals(trade.getId(), response.getId());
		assertEquals(100.0, response.getProfit(),0.005);
	}

	@Test
	public void testShortSellTrade() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = df.parse("2015-08-01");
		when(cacheData.getAssetcurrency(anyInt())).thenReturn(1);
		when(cacheData.getAssetPrice(any(Date.class),anyInt())).thenReturn(12.0);
		when(cacheData.getFXRates(any(Date.class),anyInt())).thenReturn(1.52);
		TradeEntry trade = new TradeEntry();
		trade.setTradeDate(dt);
		trade.setAssetId(1);
		trade.setId(23);
		trade.setQuantity(100);
		trade.setBuySell("Sell");
		when(tradeEntryDao.save(any(TradeEntry.class))).thenReturn(trade);
		List<TradeEntry> list = new ArrayList<>();
		when(tradeEntryDao.getAllNonSettledEntry(anyInt(), any(Date.class))).thenReturn(list );

		TradeEntry response = preprocessor.processAndSaveTrade(trade);
		assertNull(response);
	}


	@Test
	public void testPartialSettlementSellTrade() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = df.parse("2015-08-01");
		when(cacheData.getAssetcurrency(anyInt())).thenReturn(1);
		when(cacheData.getAssetPrice(any(Date.class),anyInt())).thenReturn(10.0);
		when(cacheData.getFXRates(any(Date.class),anyInt())).thenReturn(1.0);
		TradeEntry trade = new TradeEntry();
		trade.setTradeDate(dt);
		trade.setAssetId(1);
		trade.setId(23);
		trade.setQuantity(50);
		trade.setBuySell("Sell");
		when(tradeEntryDao.save(any(TradeEntry.class))).thenReturn(trade);
		List<TradeEntry> list = new ArrayList<>();
		TradeEntry buyTrade = new TradeEntry();
		buyTrade.setTradeDate(dt);
		buyTrade.setAssetId(1);
		buyTrade.setId(22);
		buyTrade.setBuySell("Buy");
		buyTrade.setSettled(false);
		buyTrade.setOpenQuantity(100);
		buyTrade.setPriceUSD(9);

		list.add(buyTrade);
		when(tradeEntryDao.getAllNonSettledEntry(anyInt(), any(Date.class))).thenReturn(list );

		TradeEntry response = preprocessor.processAndSaveTrade(trade);
		assertEquals(trade.getId(), response.getId());
		assertEquals(50.0, response.getProfit(),0.005);
	}

	@Test
	public void testFullSettlementMultipleBuyTrade() throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date dt = df.parse("2015-08-01");
		when(cacheData.getAssetcurrency(anyInt())).thenReturn(1);
		when(cacheData.getAssetPrice(any(Date.class),anyInt())).thenReturn(10.0);
		when(cacheData.getFXRates(any(Date.class),anyInt())).thenReturn(1.0);
		TradeEntry trade = new TradeEntry();
		trade.setTradeDate(dt);
		trade.setAssetId(1);
		trade.setId(23);
		trade.setQuantity(150);
		trade.setBuySell("Sell");
		when(tradeEntryDao.save(any(TradeEntry.class))).thenReturn(trade);
		List<TradeEntry> list = new ArrayList<>();
		TradeEntry buyTrade = new TradeEntry();
		buyTrade.setTradeDate(dt);
		buyTrade.setAssetId(1);
		buyTrade.setId(21);
		buyTrade.setBuySell("Buy");
		buyTrade.setSettled(false);
		buyTrade.setOpenQuantity(50);
		buyTrade.setPriceUSD(9);

		TradeEntry buyTrade2 = new TradeEntry();
		buyTrade2.setTradeDate(dt);
		buyTrade2.setAssetId(1);
		buyTrade2.setId(22);
		buyTrade2.setBuySell("Buy");
		buyTrade2.setSettled(false);
		buyTrade2.setOpenQuantity(100);
		buyTrade2.setPriceUSD(9);

		list.add(buyTrade);
		list.add(buyTrade2);
		when(tradeEntryDao.getAllNonSettledEntry(anyInt(), any(Date.class))).thenReturn(list );

		TradeEntry response = preprocessor.processAndSaveTrade(trade);

		assertEquals(trade.getId(), response.getId());
		assertEquals(150.0, response.getProfit(),0.005);
	}

}
