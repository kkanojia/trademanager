package com.kanokun.cts.dao.jpaimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

import com.kanokun.cts.entity.AssetPrices;
import com.kanokun.cts.entity.FxRates;
import com.kanokun.cts.utils.SQLConstants;

public class StaticDataDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional(readOnly = true)
	public List<FxRates> getFxRates() {
		return jdbcTemplate.query(SQLConstants.FX_QUERY, new Object[0] , new RowMapper<FxRates>(){
			@Override
			public FxRates mapRow(ResultSet rs, int rowNum) throws SQLException {
				FxRates fx = new FxRates();
				fx.setDataDate(rs.getDate(1));
				fx.setEurRate(rs.getDouble(2));
				fx.setGbpRate(rs.getDouble(3));
				return fx;
			}
		});
	}

	public List<AssetPrices> getAssetPrices() {
		return jdbcTemplate.query(SQLConstants.ASSET_PRICE_QUERY, new Object[0] , new RowMapper<AssetPrices>(){
			@Override
			public AssetPrices mapRow(ResultSet rs, int rowNum) throws SQLException {
				AssetPrices ap = new AssetPrices();
				ap.setDataDate(rs.getDate(1));
				ap.setAssetABC(rs.getDouble(2));
				ap.setAssetDEF(rs.getDouble(3));
				ap.setAssetXYZ(rs.getDouble(4));
				return ap;
			}
		});
	}

}
