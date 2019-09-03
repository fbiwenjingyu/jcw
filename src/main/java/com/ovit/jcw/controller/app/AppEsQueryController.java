package com.ovit.jcw.controller.app;

import com.ovit.jcw.controller.*;
import com.ovit.jcw.mysqlmapper.*;
import com.ovit.jcw.service.*;
import org.springframework.beans.factory.annotation.*;
import org.apache.logging.log4j.*;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import java.text.*;
import java.math.*;
import org.apache.commons.collections.*;
import org.apache.commons.lang3.*;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.*;
import org.elasticsearch.action.search.*;
import org.elasticsearch.search.*;
import com.ovit.jcw.utils.*;
import com.ovit.jcw.common.*;
import org.elasticsearch.index.query.*;
import org.springframework.data.elasticsearch.core.query.*;
import java.util.*;

@RestController
@RequestMapping({ "/app/v1.0/esQuery" })
public class AppEsQueryController extends BaseController
{
    private Logger logger;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private BusService busService;
    @Autowired
    private FLTJBQMapper fltjbqMapper;
    @Autowired
    private AuditLogService auditLogService;
    @Value("${es.library}")
    private String library;
    
    public AppEsQueryController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping({ "/bool" })
    public Result bool(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            this.logger.info("APP端es查询接口调用");
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = Integer.parseInt(params.get("queryType").toString());
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String info = MapUtils.getString((Map)params, (Object)"desc");
                descPretty = ((info != null) ? info.replace(" ", ",") : null);
                final int page = MapUtils.getInteger((Map)params, (Object)"page");
                final int size = MapUtils.getInteger((Map)params, (Object)"size");
                final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
                final NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
                if (StringUtils.isNotEmpty((CharSequence)MapUtils.getString((Map)params, (Object)"db_nick_name"))) {
                    queryBuilder.must((QueryBuilder)QueryBuilders.matchQuery("db_nick_name", (Object)MapUtils.getString((Map)params, (Object)"db_nick_name")).operator(Operator.AND));
                }
                if (StringUtils.isNotEmpty((CharSequence)info)) {
                    final String[] split;
                    final String[] descArray = split = info.split(" ");
                    for (final String desc : split) {
                        queryBuilder.must((QueryBuilder)QueryBuilders.matchPhraseQuery("desc_pretty", (Object)desc.toLowerCase()));
                    }
                }
                searchQueryBuilder.withQuery((QueryBuilder)queryBuilder);
                searchQueryBuilder.withPageable((Pageable)PageRequest.of(page, size));
                final SearchQuery searchQuery = (SearchQuery)searchQueryBuilder.build();
                result = (Result)this.elasticsearchTemplate.query(searchQuery, (ResultsExtractor)new ResultsExtractor<Result>() {
                    public Result extract(final SearchResponse response) {
                        final Result innerResult = new Result();
                        innerResult.setHitsTotal(response.getHits().getTotalHits());
                        innerResult.setTookInSec(response.getTookInMillis() / 1000.0);
                        final List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
                        for (final SearchHit hit : response.getHits()) {
                            resultList.add(AppEsQueryController.this.busService.encryptionData(AppEsQueryController.this.token, info, hit.getSource()));
                        }
                        innerResult.setData(resultList);
                        return innerResult;
                    }
                });
                this.logger.info("数据查询成功：{}", (Object)result);
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(result.getHitsTotal());
            }
            catch (Exception e) {
                dataUsage = BigInteger.valueOf(0L);
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.boolForApp.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/byTag" })
    public Result byTag(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("APP端es标准库查询接口调用");
        Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.StandardLibrary.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String info = MapUtils.getString((Map)params, (Object)"desc");
                descPretty = ((info != null) ? info.replace(" ", ",") : null);
                final int page = MapUtils.getInteger((Map)params, (Object)"page");
                final int size = MapUtils.getInteger((Map)params, (Object)"size");
                final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
                final NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
                if (StringUtils.isNotEmpty((CharSequence)MapUtils.getString((Map)params, (Object)"db_nick_name"))) {
                    queryBuilder.must((QueryBuilder)QueryBuilders.matchQuery("db_nick_name", (Object)MapUtils.getString((Map)params, (Object)"db_nick_name")).operator(Operator.AND));
                }
                if (StringUtils.isNotEmpty((CharSequence)MapUtils.getString((Map)params, (Object)"tag"))) {
                    queryBuilder.must((QueryBuilder)QueryBuilders.matchPhrasePrefixQuery("db_tag_name", (Object)MapUtils.getString((Map)params, (Object)"tag").toLowerCase()));
                }
                if (StringUtils.isNotEmpty((CharSequence)info)) {
                    final String[] split;
                    final String[] descArray = split = info.split(" ");
                    for (final String desc : split) {
                        queryBuilder.must((QueryBuilder)QueryBuilders.matchPhraseQuery("desc_pretty", (Object)desc.toLowerCase()));
                    }
                }
                searchQueryBuilder.withQuery((QueryBuilder)queryBuilder);
                searchQueryBuilder.withPageable((Pageable)PageRequest.of(page, size));
                final SearchQuery searchQuery = (SearchQuery)searchQueryBuilder.build();
                result = (Result)this.elasticsearchTemplate.query(searchQuery, (ResultsExtractor)new ResultsExtractor<Result>() {
                    public Result extract(final SearchResponse response) {
                        final Result innerResult = new Result();
                        innerResult.setHitsTotal(response.getHits().getTotalHits());
                        innerResult.setTookInSec(response.getTookInMillis() / 1000.0);
                        final List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
                        for (final SearchHit hit : response.getHits()) {
                            resultList.add(AppEsQueryController.this.busService.encryptionData(AppEsQueryController.this.token, info, hit.getSource()));
                        }
                        innerResult.setData(resultList);
                        return innerResult;
                    }
                });
                this.logger.info("数据查询成功：{}", (Object)result);
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(result.getHitsTotal());
            }
            catch (Exception e) {
                dataUsage = BigInteger.valueOf(0L);
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.byTagForApp.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/integratedQuery" })
    public Result integratedQuery(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            this.logger.info("综合查询接口调用");
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = Integer.parseInt(params.get("queryType").toString());
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String info = MapUtils.getString((Map)params, (Object)"desc");
                descPretty = ((info != null) ? info.replace(" ", ",") : null);
                final int page = MapUtils.getInteger((Map)params, (Object)"page");
                final int size = MapUtils.getInteger((Map)params, (Object)"size");
                final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
                final NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
                if (StringUtils.isNotEmpty((CharSequence)MapUtils.getString((Map)params, (Object)"table_name"))) {
                    queryBuilder.must((QueryBuilder)QueryBuilders.matchQuery("table_name", (Object)MapUtils.getString((Map)params, (Object)"table_name")).operator(Operator.AND));
                }
                if (StringUtils.isNotEmpty((CharSequence)info)) {
                    final String[] split;
                    final String[] descArray = split = info.split(" ");
                    for (final String desc : split) {
                        queryBuilder.must((QueryBuilder)QueryBuilders.matchPhraseQuery("desc_pretty", (Object)desc.toLowerCase()));
                    }
                }
                searchQueryBuilder.withQuery((QueryBuilder)queryBuilder);
                searchQueryBuilder.withPageable((Pageable)PageRequest.of(page, size));
                final SearchQuery searchQuery = (SearchQuery)searchQueryBuilder.build();
                result = (Result)this.elasticsearchTemplate.query(searchQuery, (ResultsExtractor)new ResultsExtractor<Result>() {
                    public Result extract(final SearchResponse response) {
                        final Result innerResult = new Result();
                        innerResult.setHitsTotal(response.getHits().getTotalHits());
                        innerResult.setTookInSec(response.getTookInMillis() / 1000.0);
                        final List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
                        for (final SearchHit hit : response.getHits()) {
                            resultList.add(hit.getSource());
                        }
                        innerResult.setData(resultList);
                        return innerResult;
                    }
                });
                this.logger.info("数据查询成功：{}", (Object)result);
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(result.getHitsTotal());
            }
            catch (Exception e) {
                dataUsage = BigInteger.valueOf(0L);
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.boolForApp.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/byTagForCounts" })
    public Result byTagForCounts(@RequestBody final Map<String, Object> params) {
        this.logger.info("标签下的数据量查询");
        final Result result = new Result();
        try {
            this.logger.info("查询条件：{}", (Object)params);
            final int page = 0;
            final int size = 30;
            final Integer tag = params.containsKey("tag") ? Integer.parseInt(params.get("tag").toString()) : NormalEnum.FLType.BZK_BS.GetCode();
            final List<Map<String, Object>> firstTagList = (List<Map<String, Object>>)this.fltjbqMapper.findTagsByBQID(tag, Integer.valueOf(NormalEnum.BQJB.First.getCode()));
            final List<Map<String, Object>> secondTagList = (List<Map<String, Object>>)this.fltjbqMapper.findTagsByBQID(tag, Integer.valueOf(NormalEnum.BQJB.Second.getCode()));
            final List<Map<String, Object>> thirdTagList = (List<Map<String, Object>>)this.fltjbqMapper.findTagsByBQID(tag, Integer.valueOf(NormalEnum.BQJB.Third.getCode()));
            final Map<String, Object> resultMap = new HashMap<String, Object>();
            final List<Map<String, Object>> firstTagMap = this.selectTagCounts(firstTagList, params, page, size);
            final List<Map<String, Object>> secondTagMap = this.selectTagCounts(secondTagList, params, page, size);
            if (thirdTagList != null && thirdTagList.size() > 0) {
                final List<Map<String, Object>> thirdTagMap = this.selectTagCounts(thirdTagList, params, page, size);
                resultMap.put("thirdTagMap", thirdTagMap);
            }
            resultMap.put("firstTagList", firstTagMap);
            resultMap.put("secondTagMap", secondTagMap);
            this.logger.info("数据查询成功：{}", (Object)resultMap);
            result.setData(resultMap);
            result.setMsg("数据查询成功");
        }
        catch (Exception e) {
            this.logger.error("数据查询异常：{}", (Throwable)e);
            result.setMsg("数据查询异常");
            result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        }
        return result;
    }
    
    private List<Map<String, Object>> selectTagCounts(final List<Map<String, Object>> tagList, final Map<String, Object> params, final int page, final int size) {
        final List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
        if (tagList != null && tagList.size() > 0) {
            for (final Map<String, Object> item : tagList) {
                final String BQID = item.get("BQID").toString();
                final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
                if (StringUtils.isNotEmpty((CharSequence)BQID)) {
                    queryBuilder.must((QueryBuilder)QueryBuilders.matchPhrasePrefixQuery("db_tag_name", (Object)BQID.toLowerCase()));
                }
                if (StringUtils.isNotEmpty((CharSequence)MapUtils.getString((Map)params, (Object)"desc"))) {
                    final String[] split;
                    final String[] descArray = split = MapUtils.getString((Map)params, (Object)"desc").split(" ");
                    for (final String desc : split) {
                        queryBuilder.must((QueryBuilder)QueryBuilders.matchPhraseQuery("desc_pretty", (Object)desc.toLowerCase()));
                    }
                }
                final SearchQuery searchQuery = (SearchQuery)new NativeSearchQueryBuilder().withQuery((QueryBuilder)queryBuilder).build();
                final long countNum = (long)this.elasticsearchTemplate.query(searchQuery, (ResultsExtractor)new ResultsExtractor<Long>() {
                    public Long extract(final SearchResponse response) {
                        final long countNum = response.getHits().getTotalHits();
                        return countNum;
                    }
                });
                final Map<String, Object> map = new HashMap<String, Object>();
                map.put("BQMC", item.get("BQMC").toString());
                map.put("count", countNum);
                resultMap.add(map);
            }
        }
        return resultMap;
    }
}
