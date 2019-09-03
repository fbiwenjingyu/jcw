package com.ovit.jcw.service;

import com.ovit.jcw.model.Es2Oracle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public abstract interface EsQueryService {
	public abstract Page<Es2Oracle> findByDesc(String paramString, Pageable paramPageable);
}