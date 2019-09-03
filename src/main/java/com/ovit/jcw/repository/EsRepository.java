package com.ovit.jcw.repository;

import com.ovit.jcw.model.Es2Oracle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public abstract interface EsRepository extends ElasticsearchRepository<Es2Oracle, String> {
	public abstract Page<Es2Oracle> findByDesc(String paramString, Pageable paramPageable);
}