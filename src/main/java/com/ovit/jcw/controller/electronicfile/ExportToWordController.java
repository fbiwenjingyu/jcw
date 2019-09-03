package com.ovit.jcw.controller.electronicfile;

import com.ovit.jcw.mapper.*;
import org.springframework.beans.factory.annotation.*;
import org.apache.logging.log4j.*;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.*;
import java.net.*;
import freemarker.template.*;
import java.io.*;
import java.util.*;

@RestController
@RequestMapping({ "/export" })
public class ExportToWordController
{
    private Logger logger;
    private Configuration configuration;
    @Autowired
    private DZDAJBXXMapper dzdajbxxMapper;
    @Autowired
    private JTZYCYXXMapper jTZYCYXXMapper;
    @Autowired
    private FCQKMapper fcqkMapper;
    @Autowired
    private SYYHDMapper sYYHDMapper;
    @Autowired
    private CLQKMapper clqkMapper;
    @Value("${word.path}")
    private String path;
    
    public ExportToWordController() {
        this.logger = LogManager.getLogger((Class)this.getClass());
        this.configuration = null;
    }
    
    @RequestMapping({ "/word" })
    public void word(@RequestBody final Map<String, Object> params, final HttpServletResponse response) {
        this.logger.info("电子档案-导出word");
        this.logger.info("传递的参数：{}", (Object)params);
        (this.configuration = new Configuration()).setDefaultEncoding("utf-8");
        final Map<String, Object> dataMap = this.getData(params);
        Template template = null;
        try {
            this.logger.info("文件所在路径：{}", (Object)this.path);
            this.configuration.setDirectoryForTemplateLoading(new File(this.path));
            template = this.configuration.getTemplate("DZDA_GRDA.ftl");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        final File outFile = new File(this.path + "/" + "DZDA_GRDA" + ".doc");
        Writer out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile)));
        }
        catch (FileNotFoundException e2) {
            this.logger.error("接口调用异常：{}", (Throwable)e2);
            e2.printStackTrace();
        }
        try {
            template.process((Object)dataMap, out);
        }
        catch (TemplateException e3) {
            this.logger.error("接口调用异常：{}", (Throwable)e3);
            e3.printStackTrace();
        }
        catch (IOException e4) {
            this.logger.error("接口调用异常：{}", (Throwable)e4);
            e4.printStackTrace();
        }
        try {
            out.flush();
            out.close();
        }
        catch (IOException e4) {
            this.logger.error("接口调用异常：{}", (Throwable)e4);
            e4.printStackTrace();
        }
        InputStream fis = null;
        OutputStream toClient = null;
        try {
            fis = new FileInputStream(this.path + "/" + "DZDA_GRDA" + ".doc");
            final String filename = URLEncoder.encode("DZDA_GRDA.doc", "utf-8");
            response.addHeader("Content-Disposition", "attachment;filename=" + filename + "");
            response.addHeader("Content-Length", "" + outFile.length());
            response.setContentType("application/octet-stream");
            toClient = (OutputStream)response.getOutputStream();
            int len = 0;
            final byte[] b = new byte[2048];
            while ((len = fis.read(b)) != -1) {
                toClient.write(b, 0, len);
            }
            toClient.flush();
            fis.close();
            this.logger.info("导出成功");
        }
        catch (Exception e5) {
            this.logger.error("接口调用异常：{}", (Throwable)e5);
            e5.printStackTrace();
            try {
                if (fis != null) {
                    fis.close();
                }
            }
            catch (IOException e6) {
                this.logger.error("接口调用异常：{}", (Throwable)e6);
                e6.printStackTrace();
            }
            try {
                if (toClient != null) {
                    toClient.close();
                }
            }
            catch (Exception e) {
                this.logger.error("接口调用异常：{}", (Throwable)e5);
                e.printStackTrace();
            }
        }
        finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            }
            catch (IOException e7) {
                this.logger.error("接口调用异常：{}", (Throwable)e7);
                e7.printStackTrace();
            }
            try {
                if (toClient != null) {
                    toClient.close();
                }
            }
            catch (Exception e8) {
                this.logger.error("接口调用异常：{}", (Throwable)e8);
                e8.printStackTrace();
            }
        }
    }
    
    private Map<String, Object> getData(final Map<String, Object> params) {
        final Map<String, Object> dataMap = new HashMap<String, Object>();
        final String idNumber = params.get("idNumber").toString();
        final Map<String, Object> baseInfo = (Map<String, Object>)this.dzdajbxxMapper.queryUser(idNumber);
        dataMap.put("baseInfo", baseInfo);
        final List<Map<String, Object>> hyxxList = (List<Map<String, Object>>)this.jTZYCYXXMapper.queryForHYXXByMZJ(idNumber);
        final List<Map<String, Object>> hjZnxxList = (List<Map<String, Object>>)this.jTZYCYXXMapper.queryForHJZNXX(idNumber);
        final List<Map<String, Object>> ldZnxxList = (List<Map<String, Object>>)this.jTZYCYXXMapper.queryForLDZNXX(idNumber);
        final List<Map<String, Object>> znxxList = new ArrayList<Map<String, Object>>();
        znxxList.addAll(hjZnxxList);
        znxxList.addAll(ldZnxxList);
        final List<Map<String, Object>> family = new ArrayList<Map<String, Object>>();
        final String xb = baseInfo.get("XB").toString();
        if (xb.equals("男")) {
            if (hyxxList != null && hyxxList.size() > 0) {
                for (final Map<String, Object> item : hyxxList) {
                    final Map<String, Object> map = new HashMap<String, Object>();
                    map.put("XM", item.get("FMNAME"));
                    if (item.get("YWLB").equals("结婚")) {
                        map.put("GX", "夫妻");
                    }
                    else if (item.get("YWLB").equals("离婚")) {
                        map.put("GX", "前妻");
                    }
                    else {
                        map.put("GX", "");
                    }
                    map.put("XB", "女");
                    map.put("SFZH", item.get("FZJH"));
                    family.add(map);
                }
            }
            if (znxxList != null && znxxList.size() > 0) {
                for (final Map<String, Object> item : znxxList) {
                    final Map<String, Object> map = new HashMap<String, Object>();
                    map.put("XM", item.get("XM"));
                    if (item.get("XB").equals("男")) {
                        map.put("GX", "父子");
                    }
                    else if (item.get("XB").equals("女")) {
                        map.put("GX", "父女");
                    }
                    else {
                        map.put("GX", "");
                    }
                    map.put("XB", item.get("XB"));
                    map.put("SFZH", "");
                    family.add(map);
                }
            }
        }
        else if (xb.equals("女")) {
            if (hyxxList != null && hyxxList.size() > 0) {
                for (final Map<String, Object> item : hyxxList) {
                    final Map<String, Object> map = new HashMap<String, Object>();
                    map.put("XM", item.get("MNAME"));
                    if (item.get("YWLB").equals("结婚")) {
                        map.put("GX", "夫妻");
                    }
                    else if (item.get("YWLB").equals("离婚")) {
                        map.put("GX", "前夫");
                    }
                    else {
                        map.put("GX", "");
                    }
                    map.put("XB", "男");
                    map.put("SFZH", item.get("MZJH"));
                    family.add(map);
                }
            }
            if (znxxList != null && znxxList.size() > 0) {
                for (final Map<String, Object> item : znxxList) {
                    final Map<String, Object> map = new HashMap<String, Object>();
                    map.put("XM", item.get("XM"));
                    if (item.get("XB").equals("男")) {
                        map.put("GX", "母子");
                    }
                    else if (item.get("XB").equals("女")) {
                        map.put("GX", "母女");
                    }
                    else {
                        map.put("GX", "");
                    }
                    map.put("XB", item.get("XB"));
                    map.put("SFZH", "");
                    family.add(map);
                }
            }
        }
        dataMap.put("family", family);
        final List<Map<String, Object>> fcqkList = (List<Map<String, Object>>)this.fcqkMapper.queryForFCBAXX(idNumber);
        dataMap.put("fcqkList", fcqkList);
        final List<String> telList = (List<String>)this.sYYHDMapper.selectForBFHM(idNumber);
        final List<String> carList = (List<String>)this.clqkMapper.selectForCPHM(idNumber);
        final List<Map<String, Object>> tels = new ArrayList<Map<String, Object>>();
        final List<Map<String, Object>> cars = new ArrayList<Map<String, Object>>();
        if (telList != null && telList.size() > 0) {
            for (final String str : telList) {
                final Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("tel", str);
                tels.add(map2);
            }
        }
        else {
            final Map<String, Object> map3 = new HashMap<String, Object>();
            map3.put("tel", "");
            tels.add(map3);
        }
        if (carList != null && carList.size() > 0) {
            for (final String str : carList) {
                final Map<String, Object> map2 = new HashMap<String, Object>();
                map2.put("car", str);
                cars.add(map2);
            }
        }
        else {
            final Map<String, Object> map3 = new HashMap<String, Object>();
            map3.put("car", "");
            cars.add(map3);
        }
        dataMap.put("telList", tels);
        dataMap.put("carList", cars);
        final String lastActivitySituation = params.get("LastActivitySituation").toString();
        dataMap.put("lastActivitySituation", lastActivitySituation);
        int countHb7 = 0;
        int countHb8 = 0;
        int countHc7 = 0;
        int countHc8 = 0;
        int countZs7 = 0;
        int countZs8 = 0;
        countHb7 = Integer.parseInt(params.get("countHb7").toString());
        countHb8 = Integer.parseInt(params.get("countHb30").toString());
        countHc7 = Integer.parseInt(params.get("countHc7").toString());
        countHc8 = Integer.parseInt(params.get("countHc30").toString());
        countZs7 = Integer.parseInt(params.get("countZs7").toString());
        countZs8 = Integer.parseInt(params.get("countZs30").toString());
        dataMap.put("countHb7", countHb7);
        dataMap.put("countHb30", countHb8);
        dataMap.put("countHc7", countHc7);
        dataMap.put("countHc30", countHc8);
        dataMap.put("countZs7", countZs7);
        dataMap.put("countZs30", countZs8);
        final String activityTop = params.get("activityTop").toString();
        dataMap.put("activityTop", activityTop);
        return dataMap;
    }
}
