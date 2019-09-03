package com.ovit.jcw.controller.smartquery;

import java.math.BigInteger;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequestBuilder;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.ovit.jcw.common.NormalEnum;
import com.ovit.jcw.controller.BaseController;
import com.ovit.jcw.model.DescPretty;
import com.ovit.jcw.model.EsSearch;
import com.ovit.jcw.model.Esindex;
import com.ovit.jcw.model.SystemRole;
import com.ovit.jcw.mysqlmapper.FLTJBQMapper;
import com.ovit.jcw.mysqlmapper.MysqlQueryMapper;
import com.ovit.jcw.repository.PostRepository;
import com.ovit.jcw.service.AuditLogService;
import com.ovit.jcw.service.BusService;
import com.ovit.jcw.utils.Result;
import com.ovit.jcw.utils.ResultCode;

@RestController
@RequestMapping({ "/esQuery" })
public class EsQueryController extends BaseController {
	private Logger logger;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private BusService busService;
	@Autowired
	private FLTJBQMapper fltjbqMapper;
	@Autowired
	private AuditLogService auditLogService;
	@Value("${es.library}")
	private String library;
	@Autowired
	private MysqlQueryMapper mysqlQueryMapper;

	public EsQueryController() {
		this.logger = LogManager.getLogger((Class) this.getClass());
	}
	@RequestMapping({ "/esTest" })
	public List<String> esTest() {
		this.logger.info("查询条件：{}", "测试");
		List<String> list = new ArrayList<String>();
		final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		final NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();

//		queryBuilder.must(QueryBuilders.matchAllQuery());
		queryBuilder.must(QueryBuilders.matchQuery("title","aaaa").operator(Operator.OR));
		//指定type
		queryBuilder.must(QueryBuilders.typeQuery("product"));
		queryBuilder.must(QueryBuilders.matchQuery("onSale",true).operator(Operator.OR));
//		queryBuilder.must(QueryBuilders.matchPhraseQuery("title","aa"));
		searchQueryBuilder.withQuery(queryBuilder);
		System.out.println(NormalEnum.ExcelTYPE.GABY.GetCode());
		System.out.println(NormalEnum.ExcelTYPE.GABY.GetDesc());
		final SearchQuery searchQuery = searchQueryBuilder.build();
		list=searchQuery.getTypes();
		 Result result = (Result) this.elasticsearchTemplate.query(searchQuery,
				(ResultsExtractor) new ResultsExtractor<Result>() {
					public Result extract(final SearchResponse response) {
						final Result innerResult = new Result();
						innerResult.setHitsTotal(response.getHits().getTotalHits());
						innerResult.setTookInSec(response.getTookInMillis() / 1000.0);
						final List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
						for (final SearchHit hit : response.getHits()) {
							System.out.println(hit.getSource());							
						}
						innerResult.setData(resultList);
						return innerResult;
					}
				});
		return list;
	}
	@RequestMapping({ "/esTestSave" })
	public void esTestSave() {
		this.logger.info("查询条件：{}", "保存");
		SystemRole sr =new SystemRole();
		sr.setId(1);
		sr.setRemark("111111");
//		IndexQuery indexQuery = new IndexQueryBuilder()				
//				.withObject(sr)
//				.build();
//elasticsearchTemplate.index(indexQuery);
elasticsearchTemplate.createIndex(Esindex.class);
//elasticsearchTemplate.putMapping(Esindex.class);

	}
	@RequestMapping({ "/bool" })
	public Result bool(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
		Result result = new Result();
		final Boolean flag = (Boolean) request.getAttribute("flag");
		if (flag) {
			this.logger.info("PC端es查询接口调用");
			final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			BigInteger dataUsage = BigInteger.valueOf(0L);
			final Integer queryType = Integer.parseInt(params.get("queryType").toString());
			String descPretty = "";
			try {
				this.logger.info("查询条件：{}", (Object) params);
				final String info = MapUtils.getString((Map) params, (Object) "desc");
				descPretty = ((info != null) ? info.replace(" ", ",") : null);
				final int page = MapUtils.getInteger((Map) params, (Object) "page");
				final int size = MapUtils.getInteger((Map) params, (Object) "size");
				final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
				final NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
				if (StringUtils.isNotEmpty((CharSequence) MapUtils.getString(params, "db_nick_name"))) {
					queryBuilder
							.must(QueryBuilders.matchQuery("db_nick_name", MapUtils.getString(params, "db_nick_name"))
									.operator(Operator.AND));
				}
				if (StringUtils.isNotEmpty((CharSequence) info)) {
					final String[] split;
					final String[] descArray = split = info.split(" ");
					for (final String desc : split) {
						queryBuilder.must((QueryBuilder) QueryBuilders.matchPhraseQuery("desc_pretty",
								(Object) desc.toLowerCase()));
					}
				}
				searchQueryBuilder.withQuery((QueryBuilder) queryBuilder);
				searchQueryBuilder.withPageable((Pageable) PageRequest.of(page, size));
				final SearchQuery searchQuery = (SearchQuery) searchQueryBuilder.build();
				result = (Result) this.elasticsearchTemplate.query(searchQuery,
						(ResultsExtractor) new ResultsExtractor<Result>() {
							public Result extract(final SearchResponse response) {
								final Result innerResult = new Result();
								innerResult.setHitsTotal(response.getHits().getTotalHits());
								innerResult.setTookInSec(response.getTookInMillis() / 1000.0);
								final List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
								for (final SearchHit hit : response.getHits()) {
									resultList.add(EsQueryController.this.busService
											.encryptionData(EsQueryController.this.token, info, hit.getSource()));
								}
								innerResult.setData(resultList);
								return innerResult;
							}
						});
				this.logger.info("数据查询成功：{}", (Object) result);
				result.setMsg("数据查询成功");
				dataUsage = BigInteger.valueOf(result.getHitsTotal());
			} catch (Exception e) {
				dataUsage = BigInteger.valueOf(0L);
				this.logger.error("数据查询异常：{}", (Throwable) e);
				result.setMsg("数据查询异常");
				result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			} finally {
				this.auditLogService.insertLogRecordsForNormal(request, loginTime,
						NormalEnum.InterfaceName.bool.getCode(), descPretty, dataUsage, queryType);
			}
		} else {
			result.setMsg(this.message);
			result.setCode(ResultCode.UNAUTHORIZED);
		}
		request.removeAttribute("flag");
		return result;
	}

	@RequestMapping({ "/byTag" })
	public Result byTag(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
		this.logger.info("PC端es标准库查询接口调用");
		Result result = new Result();
		final Boolean flag = (Boolean) request.getAttribute("flag");
		if (flag) {
			final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			BigInteger dataUsage = BigInteger.valueOf(0L);
			final Integer queryType = NormalEnum.QueryType.StandardLibrary.getCode();
			String descPretty = "";
			try {
				this.logger.info("查询条件：{}", (Object) params);
				final String info = MapUtils.getString((Map) params, (Object) "desc");
				descPretty = ((info != null) ? info.replace(" ", ",") : null);
				final int page = MapUtils.getInteger((Map) params, (Object) "page");
				final int size = MapUtils.getInteger((Map) params, (Object) "size");
				final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
				final NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
				if (StringUtils.isNotEmpty((CharSequence) MapUtils.getString((Map) params, (Object) "db_nick_name"))) {
					queryBuilder.must((QueryBuilder) QueryBuilders
							.matchQuery("db_nick_name",
									(Object) MapUtils.getString((Map) params, (Object) "db_nick_name"))
							.operator(Operator.AND));
				}
				if (StringUtils.isNotEmpty((CharSequence) MapUtils.getString((Map) params, (Object) "tag"))) {
					queryBuilder.must((QueryBuilder) QueryBuilders.matchPhrasePrefixQuery("db_tag_name",
							(Object) MapUtils.getString((Map) params, (Object) "tag").toLowerCase()));
				}
				if (StringUtils.isNotEmpty((CharSequence) info)) {
					final String[] split;
					final String[] descArray = split = info.split(" ");
					for (final String desc : split) {
						queryBuilder.must((QueryBuilder) QueryBuilders.matchPhraseQuery("desc_pretty",
								(Object) desc.toLowerCase()));
					}
				}
				searchQueryBuilder.withQuery((QueryBuilder) queryBuilder);
				searchQueryBuilder.withPageable((Pageable) PageRequest.of(page, size));
				final SearchQuery searchQuery = (SearchQuery) searchQueryBuilder.build();
				result = (Result) this.elasticsearchTemplate.query(searchQuery,
						(ResultsExtractor) new ResultsExtractor<Result>() {
							public Result extract(final SearchResponse response) {
								final Result innerResult = new Result();
								innerResult.setHitsTotal(response.getHits().getTotalHits());
								innerResult.setTookInSec(response.getTookInMillis() / 1000.0);
								final List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
								for (final SearchHit hit : response.getHits()) {
									resultList.add(EsQueryController.this.busService
											.encryptionData(EsQueryController.this.token, info, hit.getSource()));
								}
								innerResult.setData(resultList);
								return innerResult;
							}
						});
				this.logger.info("数据查询成功：{}", (Object) result);
				result.setMsg("数据查询成功");
				dataUsage = BigInteger.valueOf(result.getHitsTotal());
			} catch (Exception e) {
				dataUsage = BigInteger.valueOf(0L);
				this.logger.error("数据查询异常：{}", (Throwable) e);
				result.setMsg("数据查询异常");
				result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			} finally {
				this.auditLogService.insertLogRecordsForNormal(request, loginTime,
						NormalEnum.InterfaceName.byTag.getCode(), descPretty, dataUsage, queryType);
			}
		} else {
			result.setMsg(this.message);
			result.setCode(ResultCode.UNAUTHORIZED);
		}
		request.removeAttribute("flag");
		return result;
	}

	@RequestMapping({ "/queryForUser" })
	public Result queryForUser(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
		final Result result = new Result();
		final Boolean flag = (Boolean) request.getAttribute("flag");
		if (flag) {
			this.logger.info("PC端es查询统计人员信息接口调用");
			final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			BigInteger dataUsage = BigInteger.valueOf(0L);
			final Integer queryType = Integer.parseInt(params.get("queryType").toString());
			String descPretty = "";
			try {
				this.logger.info("查询条件：{}", (Object) params);
				final String info = params.get("desc").toString();
				descPretty = info.replace(" ", ",");
				final int page = MapUtils.getInteger((Map) params, (Object) "page");
				final int size = MapUtils.getInteger((Map) params, (Object) "size");
				final List<Map<String, Object>> tableList = (List<Map<String, Object>>) this.mysqlQueryMapper
						.selectTableWithUserInfo();
				final List<String> dbTableNameList = new ArrayList<String>();
				if (tableList != null && tableList.size() > 0) {
					for (final Map<String, Object> item : tableList) {
						final String dbTableName = item.get("YWBM").toString();
						dbTableNameList.add(dbTableName);
					}
				}
				final List<EsSearch> esSearchList = new ArrayList<EsSearch>();
				if (tableList != null && tableList.size() > 0) {
					for (final Object item2 : dbTableNameList) {
						EsSearch esSearch = new EsSearch();
						final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
						if (StringUtils
								.isNotEmpty((CharSequence) MapUtils.getString((Map) params, (Object) "db_nick_name"))) {
							queryBuilder.must((QueryBuilder) QueryBuilders
									.matchQuery("db_nick_name",
											(Object) MapUtils.getString((Map) params, (Object) "db_nick_name"))
									.operator(Operator.AND));
						}
						queryBuilder.must((QueryBuilder) QueryBuilders
								.matchQuery("table_name", (Object) item2.toString()).operator(Operator.AND));
						if (StringUtils.isNotEmpty((CharSequence) MapUtils.getString((Map) params, (Object) "desc"))) {
							final String[] split;
							final String[] descArray = split = MapUtils.getString((Map) params, (Object) "desc")
									.split(" ");
							for (final String desc : split) {
								queryBuilder.must((QueryBuilder) QueryBuilders.matchPhraseQuery("desc_pretty",
										(Object) desc.toLowerCase()));
							}
						}
						final NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
						searchQueryBuilder.withQuery((QueryBuilder) queryBuilder);
						searchQueryBuilder.withPageable((Pageable) PageRequest.of(page, size));
						final SearchQuery searchQuery = (SearchQuery) searchQueryBuilder.build();
						esSearch = (EsSearch) this.elasticsearchTemplate.query(searchQuery,
								(ResultsExtractor) new ResultsExtractor<EsSearch>() {
									public EsSearch extract(final SearchResponse response) {
										final EsSearch esSearch = new EsSearch();
										esSearch.setHitsTotal(response.getHits().getTotalHits());
										esSearch.setTookInSec(response.getTookInMillis() / 1000.0);
										final List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
										for (final SearchHit hit : response.getHits()) {
											resultList.add(EsQueryController.this.IntegrationData(hit.getSource(),
													tableList, info, EsQueryController.this.token));
										}
										esSearch.setResultMap((List) resultList);
										return esSearch;
									}
								});
						esSearchList.add(esSearch);
					}
				}
				result.setData(esSearchList);
				this.logger.info("数据查询成功：{}", (Object) esSearchList);
				result.setMsg("数据查询成功");
				dataUsage = BigInteger.valueOf(result.getHitsTotal());
			} catch (Exception e) {
				dataUsage = BigInteger.valueOf(0L);
				this.logger.error("数据查询异常：{}", (Throwable) e);
				result.setMsg("数据查询异常");
				result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			} finally {
				this.auditLogService.insertLogRecordsForNormal(request, loginTime,
						NormalEnum.InterfaceName.bool.getCode(), descPretty, dataUsage, queryType);
			}
		} else {
			result.setMsg(this.message);
			result.setCode(ResultCode.UNAUTHORIZED);
		}
		request.removeAttribute("flag");
		return result;
	}

	private Map<String, Object> IntegrationData(final Map<String, Object> map,
			final List<Map<String, Object>> tableList, final String info, final String token) {
		final Map<String, Object> resultMap = new HashMap<String, Object>();
		if (map != null && map.size() > 0) {
			final String descPretty = map.get("desc_pretty").toString();
			final List<DescPretty> descPrettyList = (List<DescPretty>) JSONArray.parseArray(descPretty,
					(Class) DescPretty.class);
			final String tableName = map.get("table_name").toString();
			if (descPrettyList != null && descPrettyList.size() > 0) {
				for (final Map<String, Object> table : tableList) {
					if (table.get("YWBM").equals(tableName)) {
						final String keyIdNumber = "IdNumber";
						String valueIdNumber = "";
						final String keyName = "userName";
						String valueName = "";
						String SFZHZD = "";
						for (final DescPretty item : descPrettyList) {
							if (item.getCol_name().equals(table.get("SFZHZD"))) {
								valueIdNumber = item.getCol_data();
								SFZHZD = table.get("SFZHZD").toString();
							}
						}
						for (final DescPretty item : descPrettyList) {
							if (item.getCol_name().equals(table.get("XMZD"))) {
								valueName = item.getCol_data();
							}
						}
						resultMap.put(keyIdNumber, valueIdNumber);
						resultMap.put(keyName, valueName);
						this.busService.encryptionDataForQueryUser(token, info, SFZHZD, tableName, resultMap);
					}
				}
			}
		}
		return resultMap;
	}

	@RequestMapping({ "/queryForUserTest" })
	public Result queryForUserTest(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
		Result result = new Result();
		final Boolean flag = (Boolean) request.getAttribute("flag");
		if (flag) {
			this.logger.info("PC端es查询统计人员信息接口调用");
			final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			BigInteger dataUsage = BigInteger.valueOf(0L);
			final Integer queryType = Integer.parseInt(params.get("queryType").toString());
			String descPretty = "";
			try {
				this.logger.info("查询条件：{}", (Object) params);
				descPretty = params.get("desc").toString().replace(" ", ",");
				final int page = MapUtils.getInteger((Map) params, (Object) "page");
				final int size = MapUtils.getInteger((Map) params, (Object) "size");
				final List<Map<String, Object>> tableList = (List<Map<String, Object>>) this.mysqlQueryMapper
						.selectTableWithUserInfo();
				final List<String> dbTableNameList = new ArrayList<String>();
				if (tableList != null && tableList.size() > 0) {
					for (final Map<String, Object> item : tableList) {
						final String dbTableName = item.get("YWBM").toString();
						dbTableNameList.add(dbTableName);
					}
				}
				if (dbTableNameList != null && dbTableNameList.size() > 0) {
					final String dbTable = String.join(",", dbTableNameList);
					final String[] list = dbTable.split(",");
					final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
					if (StringUtils
							.isNotEmpty((CharSequence) MapUtils.getString((Map) params, (Object) "db_nick_name"))) {
						queryBuilder.must((QueryBuilder) QueryBuilders
								.matchQuery("db_nick_name",
										(Object) MapUtils.getString((Map) params, (Object) "db_nick_name"))
								.operator(Operator.AND));
					}
					queryBuilder.must((QueryBuilder) QueryBuilders.termsQuery("table_name", list));
					if (StringUtils.isNotEmpty((CharSequence) MapUtils.getString((Map) params, (Object) "desc"))) {
						final String[] split;
						final String[] descArray = split = MapUtils.getString((Map) params, (Object) "desc").split(" ");
						for (final String desc : split) {
							queryBuilder.must((QueryBuilder) QueryBuilders.matchPhraseQuery("desc_pretty",
									(Object) desc.toLowerCase()));
						}
					}
					final NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
					searchQueryBuilder.withQuery((QueryBuilder) queryBuilder);
					searchQueryBuilder.withPageable((Pageable) PageRequest.of(page, size));
					final SearchQuery searchQuery = (SearchQuery) searchQueryBuilder.build();
					result = (Result) this.elasticsearchTemplate.query(searchQuery,
							(ResultsExtractor) new ResultsExtractor<Result>() {
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
				}
				this.logger.info("数据查询成功：{}", (Object) result);
				result.setMsg("数据查询成功");
				dataUsage = BigInteger.valueOf(result.getHitsTotal());
			} catch (Exception e) {
				dataUsage = BigInteger.valueOf(0L);
				this.logger.error("数据查询异常：{}", (Throwable) e);
				result.setMsg("数据查询异常");
				result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			} finally {
				this.auditLogService.insertLogRecordsForNormal(request, loginTime,
						NormalEnum.InterfaceName.bool.getCode(), descPretty, dataUsage, queryType);
			}
		} else {
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
			this.logger.info("查询条件：{}", (Object) params);
			final int page = 0;
			final int size = 30;
			final Integer tag = params.containsKey("tag") ? Integer.parseInt(params.get("tag").toString())
					: NormalEnum.FLType.BZK_BS.GetCode();
			final List<Map<String, Object>> firstTagList = (List<Map<String, Object>>) this.fltjbqMapper
					.findTagsByBQID(tag, Integer.valueOf(NormalEnum.BQJB.First.getCode()));
			final List<Map<String, Object>> secondTagList = (List<Map<String, Object>>) this.fltjbqMapper
					.findTagsByBQID(tag, Integer.valueOf(NormalEnum.BQJB.Second.getCode()));
			final List<Map<String, Object>> thirdTagList = (List<Map<String, Object>>) this.fltjbqMapper
					.findTagsByBQID(tag, Integer.valueOf(NormalEnum.BQJB.Third.getCode()));
			final Map<String, Object> resultMap = new HashMap<String, Object>();
			final List<Map<String, Object>> firstTagMap = this.selectTagCounts(firstTagList, params, page, size);
			final List<Map<String, Object>> secondTagMap = this.selectTagCounts(secondTagList, params, page, size);
			if (thirdTagList != null && thirdTagList.size() > 0) {
				final List<Map<String, Object>> thirdTagMap = this.selectTagCounts(thirdTagList, params, page, size);
				resultMap.put("thirdTagMap", thirdTagMap);
			}
			resultMap.put("firstTagList", firstTagMap);
			resultMap.put("secondTagMap", secondTagMap);
			this.logger.info("数据查询成功：{}", (Object) resultMap);
			result.setData(resultMap);
			result.setMsg("数据查询成功");
		} catch (Exception e) {
			this.logger.error("数据查询异常：{}", (Throwable) e);
			result.setMsg("数据查询异常");
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	private List<Map<String, Object>> selectTagCounts(final List<Map<String, Object>> tagList,
			final Map<String, Object> params, final int page, final int size) {
		final List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
		if (tagList != null && tagList.size() > 0) {
			for (final Map<String, Object> item : tagList) {
				final String BQID = item.get("BQID").toString();
				final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
				if (StringUtils.isNotEmpty((CharSequence) BQID)) {
					queryBuilder.must((QueryBuilder) QueryBuilders.matchPhrasePrefixQuery("db_tag_name",
							(Object) BQID.toLowerCase()));
				}
				if (StringUtils.isNotEmpty((CharSequence) MapUtils.getString((Map) params, (Object) "desc"))) {
					final String[] split;
					final String[] descArray = split = MapUtils.getString((Map) params, (Object) "desc").split(" ");
					for (final String desc : split) {
						queryBuilder.must((QueryBuilder) QueryBuilders.matchPhraseQuery("desc_pretty",
								(Object) desc.toLowerCase()));
					}
				}
				final SearchQuery searchQuery = (SearchQuery) new NativeSearchQueryBuilder()
						.withQuery((QueryBuilder) queryBuilder).build();
				final long countNum = (long) this.elasticsearchTemplate.query(searchQuery,
						(ResultsExtractor) new ResultsExtractor<Long>() {
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

	@RequestMapping({ "/byLibraryForCounts" })
	public Result byLibraryForCounts(final Map<String, Object> params) {
		this.logger.info("专题库，标准库，关联库和基础库下的数据量查询");
		final Result result = new Result();
		try {
			this.logger.info("查询条件：{}", (Object) params);
			final String[] libraries = this.library.split(",");
			final List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
			if (libraries != null && libraries.length > 0) {
				for (final String item : libraries) {
					final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
					queryBuilder.must((QueryBuilder) QueryBuilders.matchQuery("db_nick_name", (Object) item)
							.operator(Operator.AND));
					final SearchQuery searchQuery = (SearchQuery) new NativeSearchQueryBuilder()
							.withQuery((QueryBuilder) queryBuilder).build();
					final long countNum = (long) this.elasticsearchTemplate.query(searchQuery,
							(ResultsExtractor) new ResultsExtractor<Long>() {
								public Long extract(final SearchResponse response) {
									final long countNum = response.getHits().getTotalHits();
									return countNum;
								}
							});
					final Map<String, Object> map = new HashMap<String, Object>();
					map.put("library", item);
					map.put("count", countNum);
					resultMap.add(map);
				}
			}
			this.logger.info("数据查询成功：{}", (Object) resultMap);
			result.setData(resultMap);
			result.setMsg("数据查询成功");
		} catch (Exception e) {
			this.logger.error("数据查询异常：{}", (Throwable) e);
			result.setMsg("数据查询异常");
			result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@RequestMapping({ "/boolForApp" })
	public Result boolForApp(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
		Result result = new Result();
		final Boolean flag = (Boolean) request.getAttribute("flag");
		if (flag) {
			this.logger.info("APP端es查询接口调用");
			final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
			BigInteger dataUsage = BigInteger.valueOf(0L);
			final Integer queryType = Integer.parseInt(params.get("queryType").toString());
			String descPretty = "";
			try {
				this.logger.info("查询条件：{}", (Object) params);
				final String info = MapUtils.getString((Map) params, (Object) "desc");
				descPretty = ((info != null) ? info.replace(" ", ",") : null);
				final int page = MapUtils.getInteger((Map) params, (Object) "page");
				final int size = MapUtils.getInteger((Map) params, (Object) "size");
				final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
				final NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder();
				if (StringUtils.isNotEmpty((CharSequence) MapUtils.getString((Map) params, (Object) "table_name"))) {
					queryBuilder.must((QueryBuilder) QueryBuilders
							.matchQuery("table_name", (Object) MapUtils.getString((Map) params, (Object) "table_name"))
							.operator(Operator.AND));
				}
				if (StringUtils.isNotEmpty((CharSequence) info)) {
					final String[] split;
					final String[] descArray = split = info.split(" ");
					for (final String desc : split) {
						queryBuilder.must((QueryBuilder) QueryBuilders.matchPhraseQuery("desc_pretty",
								(Object) desc.toLowerCase()));
					}
				}
				searchQueryBuilder.withQuery((QueryBuilder) queryBuilder);
				searchQueryBuilder.withPageable((Pageable) PageRequest.of(page, size));
				final SearchQuery searchQuery = (SearchQuery) searchQueryBuilder.build();
				result = (Result) this.elasticsearchTemplate.query(searchQuery,
						(ResultsExtractor) new ResultsExtractor<Result>() {
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
				this.logger.info("数据查询成功：{}", (Object) result);
				result.setMsg("数据查询成功");
				dataUsage = BigInteger.valueOf(result.getHitsTotal());
			} catch (Exception e) {
				dataUsage = BigInteger.valueOf(0L);
				this.logger.error("数据查询异常：{}", (Throwable) e);
				result.setMsg("数据查询异常");
				result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
			} finally {
				this.auditLogService.insertLogRecordsForNormal(request, loginTime,
						NormalEnum.InterfaceName.boolForApp.getCode(), descPretty, dataUsage, queryType);
			}
		} else {
			result.setMsg(this.message);
			result.setCode(ResultCode.UNAUTHORIZED);
		}
		request.removeAttribute("flag");
		return result;
	}

	@RequestMapping({ "/init" })
	public void init() {
//        final Es2Oracle es2Oracle = new Es2Oracle();
//        es2Oracle.setDb_nick_name("标准库");
//        es2Oracle.setTable_name("test03");
//        es2Oracle.setTable_nick_name("测试库03");
//        es2Oracle.setPk_col_name("id");
//        es2Oracle.setPk_col_data("2");
//        es2Oracle.setDb_user("oracle_es");
//        es2Oracle.setDesc("测试天气不错");
//        es2Oracle.setData_update_time("2018-10-23");
//        this.postRepository.save((Object)es2Oracle);
	}

	@RequestMapping({ "/byTagTest" })
	public List<Object> byTagTest(@RequestBody final Map<String, Object> params) {
		final List<Object> result = new ArrayList<Object>();
		try {
			final long startTime = System.currentTimeMillis();
			final Settings settings = Settings.builder().put("cluster.name", "elasticsearch")
					.put("client.transport.sniff", true).build();
			final TransportClient client = new PreBuiltTransportClient(settings, new Class[0]).addTransportAddress(
					(TransportAddress) new InetSocketTransportAddress(InetAddress.getByName("192.168.7.36"), 9300));
			String scrollId = "";
			final SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
			final BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
			if (StringUtils.isNotEmpty((CharSequence) MapUtils.getString((Map) params, (Object) "db_nick_name"))) {
				queryBuilder.must((QueryBuilder) QueryBuilders
						.matchQuery("db_nick_name", (Object) MapUtils.getString((Map) params, (Object) "db_nick_name"))
						.operator(Operator.AND));
			}
			if (StringUtils.isNotEmpty((CharSequence) MapUtils.getString((Map) params, (Object) "tag"))) {
				queryBuilder.must((QueryBuilder) QueryBuilders
						.matchQuery("db_tag_name",
								(Object) MapUtils.getString((Map) params, (Object) "tag").toLowerCase())
						.operator(Operator.AND));
			}
			if (StringUtils.isNotEmpty((CharSequence) MapUtils.getString((Map) params, (Object) "desc"))) {
				queryBuilder.must((QueryBuilder) QueryBuilders
						.matchQuery("desc", (Object) MapUtils.getString((Map) params, (Object) "desc").toLowerCase())
						.operator(Operator.AND));
			}
			sourceBuilder.query((QueryBuilder) queryBuilder).size(Integer.parseInt(params.get("size").toString()))
					.from(Integer.parseInt(params.get("page").toString()));
			final SearchRequest request = Requests.searchRequest(new String[0]);
			request.scroll(new TimeValue(60000L));
			request.source(sourceBuilder);
			final SearchResponse searchResponse = (SearchResponse) client.search(request).actionGet();
			final SearchHits hits = searchResponse.getHits();
			for (int i = 0; i < hits.getHits().length; ++i) {
				result.add(hits.getHits()[i].getSourceAsString());
			}
			scrollId = searchResponse.getScrollId();
			while (true) {
				final SearchScrollRequestBuilder searchScrollRequestBuilder = client.prepareSearchScroll(scrollId);
				final TimeValue timeValue = new TimeValue(60000L);
				searchScrollRequestBuilder.setScroll(timeValue);
				final SearchResponse response1 = (SearchResponse) searchScrollRequestBuilder.get();
				final SearchHits hits2 = response1.getHits();
				if (hits2.getHits().length == 0) {
					break;
				}
				for (int j = 0; j < hits2.getHits().length; ++j) {
					result.add(hits2.getHits()[j].getSourceAsString());
				}
				scrollId = response1.getScrollId();
			}
			System.out.println(result.size());
			System.out.println(result);
			final long endTime = System.currentTimeMillis();
			System.out.println("Java程序运行时间:" + (endTime - startTime) + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	
}
