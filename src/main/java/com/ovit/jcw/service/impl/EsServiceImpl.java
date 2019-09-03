package com.ovit.jcw.service.impl;

import com.ovit.jcw.service.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import org.apache.logging.log4j.*;
import com.ovit.jcw.utils.*;
import org.apache.commons.lang3.*;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.*;
import org.elasticsearch.action.search.*;
import org.elasticsearch.search.*;
import com.alibaba.fastjson.*;
import java.util.*;
import org.elasticsearch.index.query.*;
import org.springframework.data.elasticsearch.core.query.*;

@Service
public class EsServiceImpl implements EsService
{
    private Logger logger;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    
    public EsServiceImpl() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @Override
    public Result query(final String table, final String type, final String info, final Integer page, final Integer size) {
        final Result result = new Result();
        if (StringUtils.isNotEmpty((CharSequence)table)) {
            final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
            final NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
            queryBuilder.must((QueryBuilder)QueryBuilders.matchQuery("table_name", (Object)table).operator(Operator.AND));
            if (StringUtils.isNotEmpty((CharSequence)type)) {
                queryBuilder.must((QueryBuilder)QueryBuilders.matchQuery("row_tag", (Object)type).operator(Operator.AND));
            }
            if (StringUtils.isNotEmpty((CharSequence)info)) {
                final String[] split;
                final String[] descArray = split = info.split(" ");
                for (final String desc : split) {
                    queryBuilder.must((QueryBuilder)QueryBuilders.matchPhraseQuery("row_select", (Object)desc.toLowerCase()));
                }
            }
            searchQueryBuilder.withQuery((QueryBuilder)queryBuilder);
            searchQueryBuilder.withPageable((Pageable)PageRequest.of((int)page, (int)size));
            final SearchQuery searchQuery = (SearchQuery)searchQueryBuilder.build();
            return (Result)this.elasticsearchTemplate.query(searchQuery, (ResultsExtractor)new ResultsExtractor<Result>() {
                public Result extract(final SearchResponse response) {
                    final Result innerResult = new Result();
                    innerResult.setHitsTotal(response.getHits().getTotalHits());
                    innerResult.setTookInSec(response.getTookInMillis() / 1000.0);
                    final List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
                    for (final SearchHit hit : response.getHits()) {
                        final Map<String, Object> map = (Map<String, Object>)hit.getSource();
                        if (map.containsKey("desc")) {
                            final Map<String, Object> descMap = (Map<String, Object>)JSONObject.parseObject(map.get("desc").toString());
                            resultList.add(descMap);
                        }
                    }
                    EsServiceImpl.this.logger.info("数据查询成功：{}", (Object)resultList);
                    innerResult.setData(resultList);
                    return innerResult;
                }
            });
        }
        return result;
    }
}
