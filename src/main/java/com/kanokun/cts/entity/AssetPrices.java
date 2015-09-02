package com.kanokun.cts.entity;

import java.util.Date;

public class AssetPrices implements SerializableEntity {

	private static final long serialVersionUID = -6125612990747558869L;

	private Date dataDate;

	private double assetABC;

	private double assetDEF;

	private double assetXYZ;

	public Date getDataDate() {
		return dataDate;
	}

	public void setDataDate(Date dataDate) {
		this.dataDate = dataDate;
	}

	public double getAssetABC() {
		return assetABC;
	}

	public void setAssetABC(double assetABC) {
		this.assetABC = assetABC;
	}

	public double getAssetDEF() {
		return assetDEF;
	}

	public void setAssetDEF(double assetDEF) {
		this.assetDEF = assetDEF;
	}

	public double getAssetXYZ() {
		return assetXYZ;
	}

	public void setAssetXYZ(double assetXYZ) {
		this.assetXYZ = assetXYZ;
	}

}
