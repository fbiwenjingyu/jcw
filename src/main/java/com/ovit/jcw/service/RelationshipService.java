package com.ovit.jcw.service;

import java.util.List;
import java.util.Map;

public abstract interface RelationshipService {
	public abstract Map<String, Object> query(List<Map<String, String>> paramList, int paramInt);
}