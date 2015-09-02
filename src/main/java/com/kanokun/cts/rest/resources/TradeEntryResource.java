package com.kanokun.cts.rest.resources;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kanokun.cts.JsonViews;
import com.kanokun.cts.dao.TradeEntryDao;
import com.kanokun.cts.entity.TradeEntry;
import com.kanokun.cts.rest.tradeprocessor.TradePreprocessor;
import com.kanokun.cts.utils.CachedData;

@Component
@Path("/transaction")
public class TradeEntryResource {

	private static final Logger logger = LoggerFactory.getLogger(TradeEntryResource.class);

	@Autowired
	private TradeEntryDao tradeEntryDao;

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private TradePreprocessor processor;

	@Autowired
	private CachedData cache;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public TradeEntry  create(TradeEntry tradeEntry) {
		logger.info("create(): " + tradeEntry);
		return processor.processAndSaveTrade(tradeEntry);
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/deleteAll")
	public String delete() {
		return "{ \"count\" : " + this.tradeEntryDao.deleteAll() + " }";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/all")
	public String getAllTransactionsSummary() throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("Retrieving All trade entries");
		ObjectWriter viewWriter;
		viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		List<TradeEntry> allEntries = this.tradeEntryDao.findAll();

		List<TradeEntry> summaryList = new ArrayList<>();
		double cumulativeprofit = 0;
		//calculate cumulative sum - could have been done in a query too
		for (TradeEntry trade : allEntries) {
			cumulativeprofit += trade.getProfit();
			trade.setCumulativeProfit(cumulativeprofit);
			trade.setAssetCode(cache.getAssetCode(trade.getAssetId()));
			trade.setPricelocal(cache.getAssetPrice(trade.getTradeDateObject(), trade.getAssetId()));
			Integer ccy = cache.getAssetcurrency(trade.getAssetId());
			trade.setCurrency(cache.getCurrencyCode(ccy));
			summaryList.add(trade);
		}

		logger.info("Returning record count : {}", summaryList.size());
		return viewWriter.writeValueAsString(summaryList);
	}

}