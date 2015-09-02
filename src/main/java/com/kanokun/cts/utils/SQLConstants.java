package com.kanokun.cts.utils;


public class SQLConstants {

	public static final String FX_QUERY =  "SELECT * FROM   "
			+ " crosstab('select f.data_date, c.name, f.rate from fxrates f, currency c"
			+ " where f.currency_id = c.currency_id order by 1,2') "
			+ "AS ct ( Data_date  date, EUR numeric, GBP numeric)";

	public static final String ASSET_PRICE_QUERY = "SELECT * FROM   crosstab("
			+ " 'select p.data_date, a.asset_code, p.closing_price from asset a, assetprices p "
			+ " where a.asset_id = p. asset_id order by 1,2') "
			+ "AS ct (data_date date, ABC numeric, DEF numeric, XYZ numeric)";

}
