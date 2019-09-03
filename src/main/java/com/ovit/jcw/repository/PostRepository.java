package com.ovit.jcw.repository;

import com.ovit.jcw.model.Es2Oracle;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public abstract interface PostRepository extends ElasticsearchRepository<Es2Oracle, String> {
}