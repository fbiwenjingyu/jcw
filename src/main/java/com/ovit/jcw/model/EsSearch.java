package com.ovit.jcw.model;

import java.util.List;
import java.util.Map;

public class EsSearch {
	private long hitsTotal;
	private double tookInSec;
	private List<Map<String, Object>> resultMap;

	public long getHitsTotal() {
		return hitsTotal;
	}

	public void setHitsTotal(long hitsTotal) {
		this.hitsTotal = hitsTotal;
	}

	public double getTookInSec() {
		return tookInSec;
	}

	public void setTookInSec(double tookInSec) {
		this.tookInSec = tookInSec;
	}

	public List<Map<String, Object>> getResultMap() {
		return resultMap;
	}

	public void setResultMap(List<Map<String, Object>> resultMap) {
		this.resultMap = resultMap;
	}
}