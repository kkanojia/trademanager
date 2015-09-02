package com.kanokun.cts.rest.resources;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.GET;
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
import com.kanokun.cts.dao.jpaimpl.StaticDataDao;
import com.kanokun.cts.entity.AssetPrices;
import com.kanokun.cts.entity.FxRates;
import com.kanokun.cts.entity.TradeEntry;

@Component
@Path("/staticdata")
public class StaticDataResource {

	private static final Logger logger = LoggerFactory.getLogger(StaticDataResource.class);

	@Autowired
	private StaticDataDao summarydao;

	@Autowired
	private ObjectMapper mapper;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/assetprices")
	public String getAssetPrices() throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("Retrieving all asset prices");

		ObjectWriter viewWriter;
		viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		List<AssetPrices> allEntries = this.summarydao.getAssetPrices();

		logger.info("Returning asset prices record count :" + allEntries.size());

		return viewWriter.writeValueAsString(allEntries);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/fxrates")
	public String getFxRates() throws JsonGenerationException, JsonMappingException, IOException {
		logger.info("Retrieving fx rates");

		ObjectWriter viewWriter;
		viewWriter = this.mapper.writerWithView(JsonViews.Admin.class);
		List<FxRates> allEntries = this.summarydao.getFxRates();

		logger.info("Returning fx record count :" + allEntries.size());

		return viewWriter.writeValueAsString(allEntries);
	}

}