package com.ovit.jcw.service.impl;

import com.ovit.jcw.model.Es2Oracle;
import com.ovit.jcw.repository.EsRepository;
import com.ovit.jcw.service.EsQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EsQueryServiceImpl implements EsQueryService {
	private EsRepository esRepository;

	@Autowired
	public void setBookRepository(EsRepository esRepository) {
		this.esRepository = esRepository;
	}

	public Page<Es2Oracle> findByDesc(String desc, Pageable page) {
		return esRepository.findByDesc(desc, page);
	}
}