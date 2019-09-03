package com.ovit.jcw.controller.electronicfile;

import com.ovit.jcw.controller.*;
import com.ovit.jcw.service.*;
import com.ovit.jcw.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.apache.logging.log4j.*;
import javax.servlet.http.*;
import org.springframework.web.bind.annotation.*;
import java.text.*;
import java.math.*;
import com.ovit.jcw.common.*;
import com.ovit.jcw.utils.*;
import java.util.*;
import com.github.pagehelper.*;

@RestController
@RequestMapping({ "/baseInfo" })
public class DZDAGRDAController extends BaseController
{
    private Logger logger;
    @Autowired
    private DZDAJBXXMapper dzdajbxxMapper;
    @Autowired
    private AuditLogService auditLogService;
    @Autowired
    private JTZYCYXXMapper jTZYCYXXMapper;
    @Autowired
    private FCQKMapper fcqkMapper;
    @Autowired
    private SYYHDMapper sYYHDMapper;
    @Autowired
    private CLQKMapper clqkMapper;
    @Autowired
    private QGMHLGMapper qGMHLGMapper;
    @Autowired
    private QGTLSPMapper qgtlspMapper;
    @Autowired
    private DZDACXGJZSMapper dzdacxgjzsMapper;
    @Value("${encrypt}")
    private String encrypt;
    
    public DZDAGRDAController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @RequestMapping({ "/query" })
    public Result query(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("电子档案-基本信息接口调用");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ElectronicFile.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String idNumber = descPretty = params.get("idNumber").toString();
                final Map<String, Object> resultMap = new HashMap<String, Object>();
                final Map<String, Object> baseInfo = (Map<String, Object>)this.dzdajbxxMapper.queryUserByIDNumber(idNumber);
                final List<Map<String, Object>> hyxxList = (List<Map<String, Object>>)this.jTZYCYXXMapper.queryForHYXXByMZJ(idNumber);
                if (hyxxList != null && hyxxList.size() > 0) {
                    final String XB = baseInfo.get("XB").toString();
                    if (XB.equals("男")) {
                        for (final Map<String, Object> item : hyxxList) {
                            final String FZJH = item.get("FZJH").toString();
                            final Map<String, Object> map = (Map<String, Object>)this.jTZYCYXXMapper.queryForHYXXByJGBZ(FZJH);
                            String ZWMC = "";
                            String JGJC = "";
                            if (map != null) {
                                ZWMC = map.get("ZWMC").toString();
                                JGJC = map.get("JGJC").toString();
                            }
                            item.put("ZWMC", ZWMC);
                            item.put("JGJC", JGJC);
                        }
                    }
                    else if (XB.equals("女")) {
                        for (final Map<String, Object> item : hyxxList) {
                            final String MZJH = item.get("MZJH").toString();
                            final Map<String, Object> map = (Map<String, Object>)this.jTZYCYXXMapper.queryForHYXXByJGBZ(MZJH);
                            String ZWMC = "";
                            String JGJC = "";
                            if (map != null) {
                                ZWMC = map.get("ZWMC").toString();
                                JGJC = map.get("JGJC").toString();
                            }
                            item.put("ZWMC", ZWMC);
                            item.put("JGJC", JGJC);
                        }
                    }
                }
                final List<Map<String, Object>> hjZnxxList = (List<Map<String, Object>>)this.jTZYCYXXMapper.queryForHJZNXX(idNumber);
                final List<Map<String, Object>> ldZnxxList = (List<Map<String, Object>>)this.jTZYCYXXMapper.queryForLDZNXX(idNumber);
                final List<Map<String, Object>> znxxList = new ArrayList<Map<String, Object>>();
                znxxList.addAll(hjZnxxList);
                znxxList.addAll(ldZnxxList);
                final List<Map<String, Object>> fcxxList = (List<Map<String, Object>>)this.fcqkMapper.queryForFCBAXX(idNumber);
                resultMap.put("baseInfo", baseInfo);
                resultMap.put("hyxxList", hyxxList);
                resultMap.put("znxxList", znxxList);
                resultMap.put("fcxxList", fcxxList);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(resultMap);
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForBaseInfo.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForIdentifier" })
    public Result queryForIdentifier(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("电子档案-重要识别号查询");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ElectronicFile.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String idNumber = descPretty = params.get("idNumber").toString();
                final Map<String, Object> resultMap = new HashMap<String, Object>();
                final List<String> telList = (List<String>)this.sYYHDMapper.selectForBFHM(idNumber);
                final List<String> carList = (List<String>)this.clqkMapper.selectForCPHM(idNumber);
                resultMap.put("telList", telList);
                resultMap.put("carList", carList);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(resultMap);
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForIdentifier.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/getIdentifierDetail" })
    public Result getIdentifierDetail(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("电子档案-重要识别号详情查询");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ElectronicFile.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final Integer opt = Integer.parseInt(params.get("opt").toString());
                final String idNumber = descPretty = params.get("idNumber").toString();
                final int page = Integer.parseInt(params.get("page").toString());
                final int size = Integer.parseInt(params.get("size").toString());
                List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
                PageHelper.startPage(page, size);
                if (opt == NormalEnum.IdentifierType.Tel.getCode()) {
                    resultMap = (List<Map<String, Object>>)this.sYYHDMapper.queryByIdNumber(idNumber);
                }
                else if (opt == NormalEnum.IdentifierType.Car.getCode()) {
                    resultMap = (List<Map<String, Object>>)this.clqkMapper.queryByIdNumber(idNumber);
                }
                else if (opt == NormalEnum.IdentifierType.Bank.getCode()) {}
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(new PageInfo((List)resultMap));
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.getIdentifierDetail.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/getFCXXDetail" })
    public Result getFCXXDetail(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("电子档案-房屋详情接口调用");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ElectronicFile.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                descPretty = params.get("idNumber").toString();
                final Map<String, Object> resultMap = new HashMap<String, Object>();
                final List<Map<String, Object>> fcdyList = (List<Map<String, Object>>)this.fcqkMapper.queryForFCDYXX((Map)params);
                final List<Map<String, Object>> fcqsList = (List<Map<String, Object>>)this.fcqkMapper.queryForFCQSXX((Map)params);
                params.remove("idNumber");
                final List<Map<String, Object>> fccfList = (List<Map<String, Object>>)this.fcqkMapper.queryForFCCFXX((Map)params);
                resultMap.put("fcdyList", fcdyList);
                resultMap.put("fcqsList", fcqsList);
                resultMap.put("fccfList", fccfList);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(resultMap);
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.getFCXXDetail.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForLastActivitySituation" })
    public Result queryForLastActivitySituation(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("电子档案-最后一次活动情况接口调用");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ElectronicFile.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String idNumber = descPretty = params.get("idNumber").toString();
                final List<Map<String, Object>> hbList = (List<Map<String, Object>>)this.qGMHLGMapper.selectLastData(idNumber);
                final List<Map<String, Object>> hcList = (List<Map<String, Object>>)this.qgtlspMapper.selectLastData(idNumber);
                final List<Map<String, Object>> zsList = (List<Map<String, Object>>)this.dzdacxgjzsMapper.selectLastData(idNumber);
                String lgsj = "";
                String ccrq = "";
                String rzsj = "";
                if (hbList != null && hbList.size() > 0) {
                    for (final Map<String, Object> item : hbList) {
                        lgsj = item.get("LGSJ").toString();
                    }
                }
                if (hcList != null && hcList.size() > 0) {
                    for (final Map<String, Object> item : hcList) {
                        ccrq = item.get("CCRQ").toString();
                    }
                }
                if (zsList != null && zsList.size() > 0) {
                    for (final Map<String, Object> item : zsList) {
                        rzsj = item.get("RZSJ").toString();
                    }
                }
                if (!ccrq.equals("")) {
                    final Date hcDate = new SimpleDateFormat("yyyyMMdd").parse(ccrq);
                    ccrq = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(hcDate);
                }
                if (!lgsj.equals("")) {
                    final Date hbDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(lgsj);
                    lgsj = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(hbDate);
                }
                params.clear();
                if (lgsj.compareTo(ccrq) > 0) {
                    if (lgsj.compareTo(rzsj) > 0) {
                        params.put("HB", hbList);
                    }
                    else {
                        params.put("ZS", zsList);
                    }
                }
                else if (ccrq.compareTo(rzsj) > 0) {
                    params.put("HC", hcList);
                }
                else {
                    params.put("ZS", zsList);
                }
                result.setData(params);
                this.logger.info("数据查询成功：{}", (Object)result);
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForLastActivitySituation.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForActivitySituation" })
    public Result queryForActivitySituation(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("电子档案-活动情况接口调用");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ElectronicFile.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String idNumber = descPretty = params.get("idNumber").toString();
                final Map<String, Object> resultMap = new HashMap<String, Object>();
                int countHb7 = 0;
                int countHb8 = 0;
                int countHc7 = 0;
                int countHc8 = 0;
                int countZs7 = 0;
                int countZs8 = 0;
                resultMap.put("idNumber", idNumber);
                final String lastHb = this.qGMHLGMapper.selectLastDate(idNumber);
                if (lastHb != null) {
                    resultMap.put("day", 7);
                    resultMap.put("lastDate", lastHb);
                    countHb7 = this.qGMHLGMapper.queryByIdNumber((Map)resultMap);
                    resultMap.put("day", 30);
                    countHb8 = this.qGMHLGMapper.queryByIdNumber((Map)resultMap);
                }
                final String lastHc = this.qgtlspMapper.selectLastDate(idNumber);
                if (lastHc != null) {
                    resultMap.put("day", 7);
                    resultMap.put("lastDate", lastHc);
                    countHc7 = this.qgtlspMapper.queryByIdNumber((Map)resultMap);
                    resultMap.put("day", 30);
                    countHc8 = this.qgtlspMapper.queryByIdNumber((Map)resultMap);
                }
                final String lastZs = this.dzdacxgjzsMapper.selectLastDate(idNumber);
                if (lastZs != null) {
                    resultMap.put("day", 7);
                    resultMap.put("lastDate", lastZs);
                    countZs7 = this.dzdacxgjzsMapper.queryByIdNumber((Map)resultMap);
                    resultMap.put("day", 30);
                    countZs8 = this.dzdacxgjzsMapper.queryByIdNumber((Map)resultMap);
                }
                resultMap.clear();
                resultMap.put("countHb7", countHb7);
                resultMap.put("countHb30", countHb8);
                resultMap.put("countHc7", countHc7);
                resultMap.put("countHc30", countHc8);
                resultMap.put("countZs7", countZs7);
                resultMap.put("countZs30", countZs8);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(resultMap);
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForActivitySituation.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    @RequestMapping({ "/queryForActiveArea" })
    public Result queryForActiveArea(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("电子档案-活动区域接口查询");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ElectronicFile.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String idNumber = descPretty = params.get("idNumber").toString();
                List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
                final List<Map<String, Object>> resultHBMap = new ArrayList<Map<String, Object>>();
                final List<Map<String, Object>> resultTLMap = new ArrayList<Map<String, Object>>();
                final List<Map<String, Object>> siteHBMap = (List<Map<String, Object>>)this.qGMHLGMapper.selectSite(idNumber);
                if (siteHBMap != null && siteHBMap.size() > 0) {
                    for (final Map<String, Object> item : siteHBMap) {
                        item.put("idNumber", idNumber);
                        final long count = this.qGMHLGMapper.selectCounts((Map)item);
                        final Map<String, Object> map = new HashMap<String, Object>();
                        map.put(item.get("QFGZ") + "-" + item.get("DDGZ"), count);
                        resultHBMap.add(map);
                    }
                }
                final List<Map<String, Object>> siteTLMap = (List<Map<String, Object>>)this.qgtlspMapper.selectSite(idNumber);
                if (siteTLMap != null && siteTLMap.size() > 0) {
                    for (final Map<String, Object> item2 : siteTLMap) {
                        item2.put("idNumber", idNumber);
                        final long count2 = this.qgtlspMapper.selectCounts((Map)item2);
                        final Map<String, Object> map2 = new HashMap<String, Object>();
                        map2.put(item2.get("FZ") + "-" + item2.get("DZ"), count2);
                        resultTLMap.add(map2);
                    }
                }
                if (resultHBMap != null && resultHBMap.size() > 0 && (resultTLMap == null || resultTLMap.size() == 0)) {
                    resultMap.addAll(resultHBMap);
                }
                else if (resultTLMap != null && resultTLMap.size() > 0 && (resultHBMap == null || resultHBMap.size() == 0)) {
                    resultMap.addAll(resultTLMap);
                }
                else if (resultHBMap != null && resultHBMap.size() > 0 && resultTLMap != null && resultTLMap.size() > 0) {
                    if (resultHBMap.size() >= resultTLMap.size()) {
                        resultMap = this.mergeData(resultTLMap, resultHBMap);
                    }
                    else {
                        resultMap = this.mergeData(resultHBMap, resultTLMap);
                    }
                }
                final Map<String, Object> map3 = new HashMap<String, Object>();
                map3.put("HBTL", resultMap);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(map3);
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForActiveArea.getCode(), descPretty, dataUsage, queryType);
            }
        }
        else {
            result.setMsg(this.message);
            result.setCode(ResultCode.UNAUTHORIZED);
        }
        request.removeAttribute("flag");
        return result;
    }
    
    private List<Map<String, Object>> mergeData(final List<Map<String, Object>> resultSmallMap, final List<Map<String, Object>> resultLargeMap) {
        boolean flag = true;
        for (final Map<String, Object> item : resultSmallMap) {
            String keyT = "";
            String valueT = "";
            final Iterator<String> iterator2 = item.keySet().iterator();
            while (iterator2.hasNext()) {
                final String key = keyT = iterator2.next();
                valueT = item.get(key).toString();
            }
            for (final Map<String, Object> itemHB : resultLargeMap) {
                String keyH = "";
                String valueH = "";
                final Iterator<String> iterator4 = itemHB.keySet().iterator();
                while (iterator4.hasNext()) {
                    final String keyHB = keyH = iterator4.next();
                    valueH = itemHB.get(keyHB).toString();
                }
                if (keyT.equals(keyH)) {
                    itemHB.put(keyH, Long.valueOf(valueT) + Long.valueOf(valueH));
                    flag = false;
                    break;
                }
                flag = true;
            }
            if (flag) {
                resultLargeMap.add(item);
            }
        }
        return resultLargeMap;
    }
    
    @RequestMapping({ "/queryForTimingDiagram" })
    public Result queryForTimingDiagram(final HttpServletRequest request, @RequestBody final Map<String, Object> params) {
        this.logger.info("电子档案-活动时序图");
        final Result result = new Result();
        final Boolean flag = (Boolean)request.getAttribute("flag");
        if (flag) {
            final String loginTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            BigInteger dataUsage = BigInteger.valueOf(0L);
            final Integer queryType = NormalEnum.QueryType.ElectronicFile.getCode();
            String descPretty = "";
            try {
                this.logger.info("查询条件：{}", (Object)params);
                final String idNumber = descPretty = params.get("idNumber").toString();
                final List<Map<String, Object>> resultHBMap = (List<Map<String, Object>>)this.qGMHLGMapper.queryForTimingDiagram(idNumber);
                final List<Map<String, Object>> resultTLMap = (List<Map<String, Object>>)this.qgtlspMapper.queryForTimingDiagram(idNumber);
                if (resultTLMap != null && resultTLMap.size() > 0) {
                    for (final Map<String, Object> item : resultTLMap) {
                        final Date hcDate = new SimpleDateFormat("yyyyMMdd").parse(item.get("CCRQ").toString());
                        final String ccrq = new SimpleDateFormat("yyyy/MM/dd").format(hcDate);
                        item.put("CCRQ", ccrq);
                    }
                }
                final Map<String, Object> resultMap = new HashMap<String, Object>();
                resultMap.put("HB", resultHBMap);
                resultMap.put("TL", resultTLMap);
                this.logger.info("数据查询成功：{}", (Object)resultMap);
                result.setData(resultMap);
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
                this.auditLogService.insertLogRecordsForNormal(request, loginTime, NormalEnum.InterfaceName.queryForTimingDiagram.getCode(), descPretty, dataUsage, queryType);
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
