package com.ovit.jcw.controller;

import com.ovit.jcw.service.*;
import org.springframework.beans.factory.annotation.*;
import org.apache.logging.log4j.*;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import java.text.*;
import java.math.*;
import com.ovit.jcw.common.*;
import com.ovit.jcw.utils.*;
import org.apache.commons.collections.*;
import org.apache.commons.lang3.*;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.*;
import org.elasticsearch.action.search.*;
import org.elasticsearch.search.*;
import java.util.*;
import org.elasticsearch.index.query.*;
import org.springframework.data.elasticsearch.core.query.*;

@RestController
@RequestMapping({ "/apiQuery" })
public class ApiQueryController extends BaseController
{
    private Logger logger;
    @Autowired
    private BusService busService;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Value("${encrypt}")
    private String encrypt;
    
    public ApiQueryController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping({ "/query" })
    public Result queryForTable(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("API接口-根据表名查询数据");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.APILibrary.getCode();
            String info = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final int size = Integer.parseInt(params.get("size").toString());
                final int page = Integer.parseInt(params.get("page").toString());
                final String table = params.get("table").toString();
                info = params.get("info").toString();
                final Map<String, Object> resultMap = (Map<String, Object>)this.busService.query(this.token, table, info, Integer.valueOf(page), Integer.valueOf(size));
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData((Object)resultMap);
                result.setMsg("数据查询成功");
                dataUsage = BigInteger.valueOf(1L);
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForAPITable.getCode(), info, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryEs" })
    public Result queryForEs(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("API接口-根据表名查询ES数据");
        Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.APILibrary.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final int size = Integer.parseInt(params.get("size").toString());
                final int page = Integer.parseInt(params.get("page").toString());
                final String table = MapUtils.getString((Map)params, (Object)"table");
                final String info;
                descPretty = (info = MapUtils.getString((Map)params, (Object)"info"));
                if (StringUtils.isNotEmpty((CharSequence)table)) {
                    final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
                    final NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
                    queryBuilder.must((QueryBuilder)QueryBuilders.matchQuery("table_name", (Object)table).operator(Operator.AND));
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
                                resultList.add(ApiQueryController.this.busService.encryptionData(ApiQueryController.this.token, info, hit.getSource()));
                            }
                            innerResult.setData((Object)resultList);
                            return innerResult;
                        }
                    });
                    this.logger.info("数据查询成功：{}", (Object)result);
                    result.setMsg("数据查询成功");
                    dataUsage = BigInteger.valueOf(result.getHitsTotal());
                }
                else {
                    result.setMsg("参数异常");
                    result.setCode(ResultCode.UNAUTHORIZED);
                }
            }
            catch (Exception e) {
                this.logger.error("数据查询异常：{}", (Throwable)e);
                result.setMsg("数据查询异常");
                result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
                dataUsage = BigInteger.valueOf(0L);
            }
            finally {
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForAPITable.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
}
