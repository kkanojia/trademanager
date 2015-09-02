package com.kanokun.cts.dao;

import java.util.Date;
import java.util.List;

import com.kanokun.cts.entity.TradeEntry;

public interface TradeEntryDao extends Dao<TradeEntry, Long> {

	List<TradeEntry> getAllNonSettledEntry(Integer assetCode, Date date);

	int deleteAll();

}