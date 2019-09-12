package com.ovit.jcw.service.impl;

import org.springframework.stereotype.*;
import com.ovit.jcw.mapper.*;
import com.ovit.jcw.mysqlmapper.*;
import com.ovit.jcw.service.*;
import org.springframework.beans.factory.annotation.*;
import org.apache.logging.log4j.*;
import java.util.*;
import com.ovit.jcw.model.*;
import com.ovit.jcw.common.*;
import org.apache.commons.lang3.*;
import com.github.pagehelper.*;
import java.io.*;
import com.alibaba.fastjson.*;

@Service
public class BusServiceImpl implements BusService
{
    private Logger logger;
    @Autowired(required = false)
    private BJGMapper bjgMapper;
    @Autowired(required = false)
    private BusMapper busMapper;
    @Autowired(required = false)
    private FLTJBQMapper fLTJBQMapper;
    @Autowired
    private AuthorityService authorityService;
    @Value("${config.path}")
    private String configPath;
    @Value("${encrypt}")
    private String encrypt;
    @Value("${encryptField}")
    private String encryptField;
    
    public BusServiceImpl() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @Override
    public void createTable(final Integer tag, final Integer type) {
        try {
            final List<Map<String, Object>> list = (List<Map<String, Object>>)this.busMapper.queryByCreateTag(tag);
            if (list.size() > 0) {
                for (final Map<String, Object> item : list) {
                    final String library = item.get("library").toString().toUpperCase();
                    final String table = item.get("table").toString();
                    final List<Map<String, Object>> filedList = (List<Map<String, Object>>)this.busMapper.queryFiledByTable(table);
                    //------------------------盛齐星2019-9-修改
                    final List<Map<String, Object>> filedList1=new ArrayList();
                    //------------------------盛齐星2019-9-修改
                    if (filedList != null && filedList.size() > 0) {

                        final Map<String, String> map1 = new HashMap<String, String>();
                        final Map<String, String> map2 = new HashMap<String, String>();
                        for (int i = 0; i < filedList.size(); ++i) {
                            final Map<String, Object> map3 = filedList.get(i);
                            //------------------------盛齐星2019-9-修改
                            Object sizeValue = map3.get("size");
                            map3.remove("size");
                            map3.put("size1",sizeValue);
                            filedList1.add(map3);
                            //------------------------盛齐星2019-9-修改
                            if (type == 0) {
                                map1.put(map3.get("field").toString(), map3.get("size").toString());
                                map2.put(map3.get("field").toString(), map3.get("name").toString());
                            }
                        }
                        if (type == 0) {
                            this.bjgMapper.createOracleTable(library, table, (Map)map1, (Map)map2);
                        }
                        else {
                            //this.bjgMapper.createTable(library, table.toLowerCase(), (List)filedList);
                            //------------------------盛齐星2019-9-修改
                            this.bjgMapper.createTable(library, table.toLowerCase(), (List)filedList1);
                            //------------------------盛齐星2019-9-修改
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
    }
    
    @Override
    public Map<String, String> createTableField(final Integer tag) {
        final Map<String, String> map = new HashMap<String, String>();
        try {
            final List<Map<String, Object>> tableList = (List<Map<String, Object>>)this.busMapper.queryByCreateTag(tag);
            if (tableList.size() > 0) {
                for (final Map<String, Object> item : tableList) {
                    final String table = item.get("table").toString();
                    final List<Map<String, Object>> filedList = (List<Map<String, Object>>)this.busMapper.queryFiledByTable(table);
                    if (filedList != null && filedList.size() > 0) {
                        final List<Map<String, String>> list = new ArrayList<Map<String, String>>();
                        for (final Map<String, Object> item2 : filedList) {
                            final Map<String, String> map2 = new HashMap<String, String>();
                            map2.put("cold", item2.get("field").toString());
                            map2.put("coln", item2.get("name").toString());
                            list.add(map2);
                        }
                        map.put(table, JSON.toJSONString((Object)list));
                    }
                }
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
        return map;
    }
    
    @Override
    public Map<String, Object> createTableExcelField(final Integer tag) {
        final Map<String, Object> map = new HashMap<String, Object>();
        try {
            final List<Map<String, Object>> tableList = (List<Map<String, Object>>)this.busMapper.queryByCreateTag(tag);
            if (tableList.size() > 0) {
                for (final Map<String, Object> item : tableList) {
                    final String table = item.get("table").toString();
                    final List<Map<String, Object>> filedList = (List<Map<String, Object>>)this.busMapper.queryFiledByTable(table);
                    if (filedList != null && filedList.size() > 0) {
                        final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
                        for (final Map<String, Object> item2 : filedList) {
                            final Map<String, Object> map2 = new HashMap<String, Object>();
                            map2.put("cold", item2.get("field").toString());
                            map2.put("coln", item2.get("name").toString());
                            map2.put("colrr", 0);
                            map2.put("colr", 0);
                            map2.put("colc", 0);
                            list.add(map2);
                        }
                        map.put(table, JSON.toJSONString((Object)list));
                    }
                }
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
        return map;
    }
    
    @Override
    public void createConfigFile(final Integer tag) {
        final Map<String, Object> config = (Map<String, Object>)this.busMapper.queryBaseConfig();
        if (config != null && config.size() > 0) {
            final String jdbcInfo = config.get("jdbcInfo").toString();
            final String statementInfo = config.get("statementInfo").toString();
            String esIndex = config.get("es_index").toString();
            final String esHosts = config.get("es_hosts").toString();
            String documentType = config.get("document_type").toString();
            final Integer isDefault = Integer.parseInt(config.get("is_default").toString());
            final List<Map<String, Object>> list = (List<Map<String, Object>>)this.busMapper.queryByConfigTag(tag);
            if (list.size() > 0) {
                for (final Map<String, Object> item : list) {
                    final String library = item.get("library").toString();
                    final String table = item.get("table").toString();
                    if (isDefault == 1) {
                        esIndex = library.toLowerCase();
                        documentType = library.toLowerCase() + "_" + table.toLowerCase();
                    }
                    final List<Map<String, Object>> filedList = (List<Map<String, Object>>)this.busMapper.queryFiledByTable(table);
                    this.writeConfig(jdbcInfo, statementInfo, esIndex, esHosts, documentType, library, table, item, filedList);
                }
            }
        }
    }
    
    @Override
    public Map<String, Object> encryptionData(final String token, final String info, final Map<String, Object> map) {
        if (map != null && map.size() > 0) {
            List<String> list = new ArrayList<String>();
            final String[] str = this.encryptField.split(",");
            list = Arrays.asList(str);
            if (list != null && list.size() > 0) {
                for (final Map.Entry<String, Object> t : map.entrySet()) {
                    if (!list.contains(t.getKey())) {
                        t.setValue(this.encrypt);
                    }
                }
            }
            if (map.containsKey("desc_pretty")) {
                final String descPretty = map.get("desc_pretty").toString();
                final List<DescPretty> descPrettyList = (List<DescPretty>)JSONArray.parseArray(descPretty, (Class)DescPretty.class);
                final String tableName = map.get("table_name").toString();
                List<String> secretList = null;
                if (this.authorityService.chargeTableSecret(token, tableName, info)) {
                    secretList = (List<String>)this.busMapper.queryChineseSecretFiledByTable(tableName);
                }
                final List<String> signList = (List<String>)this.busMapper.querySignNameByTable(tableName);
                final Map<String, Map<String, String>> keyList = this.authorityService.getTableFieldKey(tableName, 1);
                boolean flag = true;
                if (signList != null && signList.size() > 0) {
                    final List<String> redList = this.authorityService.getRedListByUser(null);
                    final List<String> redAuthorityList = this.authorityService.getRedListByUser(token);
                    for (final String sign : signList) {
                        final DescPretty pretty = descPrettyList.stream().filter(usr -> sign.equals(usr.getCol_name())).findFirst().orElse(null);
                        if (pretty != null) {
                            final String value = pretty.getCol_data();
                            if (!redList.contains(value)) {
                                continue;
                            }
                            flag = false;
                            if (redAuthorityList == null || !redAuthorityList.contains(value)) {
                                for (final DescPretty t2 : descPrettyList) {
                                    t2.setCol_data(this.encrypt);
                                }
                                map.put("pk_col_data", this.encrypt);
                                descPrettyList.add(new DescPretty("flag", NormalEnum.FlagEnum.Red.GetCode().toString()));
                                break;
                            }
                            for (final DescPretty item : descPrettyList) {
                                this.authorityService.setEsFiledMapByKey(item, true, keyList);
                            }
                            descPrettyList.add(new DescPretty("flag", NormalEnum.FlagEnum.RedAuthority.GetCode().toString()));
                        }
                    }
                }
                if (flag && this.authorityService.encryptionEsMapBySecret(descPrettyList, secretList, keyList)) {
                    map.put("pk_col_data", this.encrypt);
                }
                map.put("desc_pretty", JSON.toJSONString((Object)descPrettyList));
            }
        }
        return map;
    }
    
    @Override
    public void encryptionDataForQueryUser(final String token, final String info, final String SFZHZD, final String tableName, final Map<String, Object> map) {
        if (map != null && map.size() > 0) {
            List<String> secretList = null;
            if (this.authorityService.chargeTableSecret(token, tableName, info)) {
                secretList = (List<String>)this.busMapper.queryChineseSecretFiledByTable(tableName);
            }
            final List<String> signList = (List<String>)this.busMapper.querySignNameByTable(tableName);
            boolean flag = true;
            if (signList != null && signList.size() > 0) {
                final List<String> redList = this.authorityService.getRedListByUser(null);
                final List<String> redAuthorityList = this.authorityService.getRedListByUser(token);
                final String idNumber = map.get("IdNumber").toString();
                if (signList.contains(SFZHZD) && redList.contains(idNumber)) {
                    flag = false;
                    if (redAuthorityList == null || (redAuthorityList != null && !redAuthorityList.contains(idNumber))) {
                        map.put("IdNumber", this.encrypt);
                        map.put("userName", this.encrypt);
                    }
                }
            }
            if (flag && secretList != null && secretList.size() > 0 && secretList.contains(SFZHZD)) {
                map.put("IdNumber", this.encrypt);
                map.put("userName", this.encrypt);
            }
        }
    }
    
    @Override
    public void encryptionDataForDZDA(final String token, final Integer flagType, final String info, final String tableName, final Map<String, Object> map) {
        if (map != null && flagType == NormalEnum.FlagEnum.Secret.GetCode()) {
            final List<String> tables = this.queryUserSecretByCondition(info, token);
            if (tables != null && tables.size() > 0) {
                if (!tables.contains(tableName)) {
                    this.authorityService.encryptionMap(tableName, map);
                }
            }
            else {
                for (final Map.Entry<String, Object> t : map.entrySet()) {
                    t.setValue(this.encrypt);
                }
            }
        }
    }
    
    @Override
    public void encryptionDataForDZDAList(final String token, final Integer flagType, final String info, final String tableName, final List<Map<String, Object>> list) {
        if (list != null && list.size() > 0 && flagType == NormalEnum.FlagEnum.Secret.GetCode()) {
            final List<String> tables = this.queryUserSecretByCondition(info, token);
            if (tables != null && tables.size() > 0) {
                if (!tables.contains(tableName)) {
                    this.authorityService.encryptionList(tableName, list);
                }
            }
            else {
                for (final Map<String, Object> map : list) {
                    for (final Map.Entry<String, Object> t : map.entrySet()) {
                        t.setValue(this.encrypt);
                    }
                }
            }
        }
    }
    
    @Override
    public Map<String, Object> encryptionDataForSpecialLibrary(final String token, final String info, final String tableName, final Map<String, Object> map) {
        if (map != null && map.size() > 0 && map != null && map.size() > 0) {
            List<String> secretList = null;
            if (this.authorityService.chargeTableSecret(token, tableName, info)) {
                secretList = (List<String>)this.busMapper.queryChineseSecretFiledByTable(tableName);
            }
            final List<String> signList = (List<String>)this.busMapper.querySignFiledByTable(tableName);
            final Map<String, Map<String, String>> keyList = this.authorityService.getTableFieldKey(tableName, 0);
            boolean flag = true;
            if (signList != null && signList.size() > 0) {
                final List<String> redList = this.authorityService.getRedListByUser(null);
                final List<String> redAuthorityList = this.authorityService.getRedListByUser(token);
                for (final String sign : signList) {
                    if (map.containsKey(sign)) {
                        final String value = map.get(sign).toString();
                        if (!redList.contains(value)) {
                            continue;
                        }
                        flag = false;
                        if (redAuthorityList == null || !redAuthorityList.contains(value)) {
                            for (final Map.Entry<String, Object> t : map.entrySet()) {
                                t.setValue(this.encrypt);
                            }
                            map.put("flag", NormalEnum.FlagEnum.Red.GetCode().toString());
                            break;
                        }
                        map.put("flag", NormalEnum.FlagEnum.RedAuthority.GetCode().toString());
                    }
                }
                if (flag) {
                    this.authorityService.encryptionMapBySecret(map, secretList, keyList);
                }
            }
        }
        return map;
    }
    
    @Override
    public Map<String, Object> query(final String token, final String table, final String info, final Integer page, final Integer size) {
        final Map<String, Object> map = new HashMap<String, Object>();
        try {
            final Map<String, Object> tableInfo = (Map<String, Object>)this.busMapper.queryInfoByTable(table);
            if (tableInfo != null && tableInfo.size() > 0) {
                final String library = tableInfo.get("library").toString().toUpperCase();
                final Map<String, String> filedMap = new HashMap<String, String>();
                final List<Map<String, Object>> filedList = (List<Map<String, Object>>)this.busMapper.queryFiledByTable(table);
                if (filedList != null && filedList.size() > 0) {
                    for (final Map<String, Object> item : filedList) {
                        filedMap.put(item.get("field").toString(), item.get("name").toString());
                    }
                }
                map.put("field", filedMap);
                final List<String> infoList = (List<String>)(StringUtils.isNotEmpty((CharSequence)info) ? this.busMapper.queryShowFiledByTable(table) : null);
                PageHelper.startPage((int)page, (int)size);
                final List<Map<String, Object>> dataList = (List<Map<String, Object>>)this.bjgMapper.queryByTableInfo(library, table, info, (List)infoList);
                if (dataList != null) {
                    if (dataList.size() > 0) {
                        List<String> secretList = null;
                        if (this.authorityService.chargeTableSecret(token, table, info)) {
                            secretList = (List<String>)this.busMapper.queryEnglishSecretFiledByTable(table);
                        }
                        final List<String> signList = (List<String>)this.busMapper.querySignFiledByTable(table);
                        final Map<String, Map<String, String>> keyList = this.authorityService.getTableFieldKey(table, 0);
                        List<String> redList = null;
                        List<String> redAuthorityList = null;
                        if (signList != null && signList.size() > 0) {
                            redList = this.authorityService.getRedListByUser(null);
                            redAuthorityList = this.authorityService.getRedListByUser(token);
                            this.authorityService.redSignList(secretList, signList, redList, redAuthorityList, keyList, dataList);
                        }
                        else {
                            this.authorityService.encryptionListBySecret(dataList, secretList, keyList);
                        }
                    }
                    map.put("data", new PageInfo((List)dataList));
                }
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
        return map;
    }
    
    @Override
    public List<String> queryUserSecretByCondition(final String condition, final String user) {
        return (List<String>)this.busMapper.queryUserSecretByCondition(condition, user);
    }
    
    @Override
    public List<String> queryUserSecretByConditionForExact(final String condition, final String user) {
        return (List<String>)this.busMapper.queryUserSecretByConditionForExact(condition, user);
    }
    
    @Override
    public Integer selectAllTables() {
        int count = 0;
        count = this.busMapper.selectAllTables();
        return count;
    }
    
    @Override
    public List<String> queryEnglishSecretFiledByTable(final String table) {
        return (List<String>)this.busMapper.queryEnglishSecretFiledByTable(table);
    }
    
    @Override
    public List<String> querySignFiledByTable(final String table) {
        return (List<String>)this.busMapper.querySignFiledByTable(table);
    }
    
    private void writeConfig(final String jdbcInfo, final String statementInfo, final String esIndex, final String esHosts, final String documentType, final String library, final String table, final Map<String, Object> item, final List<Map<String, Object>> filedList) {
        try {
            final String documentId = item.get("document_id").toString();
            final String statement = item.get("statement_info").toString();
            final String otherInfo = item.get("other_info").toString();
            final String path = this.configPath + library + "_" + table.toLowerCase() + ".conf";
            final BufferedWriter writer = new BufferedWriter(new FileWriter(new File(path)));
            writer.write("input{\nstdin{\n\n        }\n\n        jdbc{");
            writer.write("\r\n");
            writer.write("\r\n");
            this.writeJdbcInfo(writer, library, jdbcInfo);
            writer.write("\r\n");
            this.writeStatementInfo(writer, statementInfo, statement, library, table, filedList);
            writer.write("\r\n");
            writer.write("                schedule => \"* * * * *\"");
            writer.write("\r\n");
            writer.write("\r\n");
            this.writeAddFieldInfo(writer, otherInfo, library, table, item, filedList);
            writer.write("\r\n");
            writer.write("        }");
            writer.write("\r\n");
            writer.write("\r\n");
            writer.write("}");
            writer.write("\r\n");
            writer.write("\r\n");
            writer.write("\r\n");
            writer.write("filter{\n\n        json {\n\nsource => \"message\"\n\nremove_field => [\"message\",\"new_add_metadata\"]\n\n#remove_field => \"new_add_metadata\"\n\n        }\n\n}\n \n\noutput{\n\n         elasticsearch{\n\n                hosts => \"" + esHosts + "\"\n" + "\n" + "                index => \"" + esIndex + "\"\n" + "\n" + "                document_id => \"%{" + documentId + "}\"\n" + "\n" + "                document_type => \"" + documentType + "\"\n" + "\n" + "\t\t\t\t}\n" + "\n" + "}\n");
            writer.write("\r\n");
            writer.close();
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
    }
    
    private void writeJdbcInfo(final BufferedWriter writer, final String library, final String json) throws Exception {
        final JSONArray arrayData = JSONObject.parseArray(json);
        if (arrayData != null && arrayData.size() > 0) {
            for (int i = 0; i < arrayData.size(); ++i) {
                final JSONObject o = arrayData.getJSONObject(i);
                writer.write("                " + o.get((Object)"coln").toString() + "=>\"" + o.get((Object)"cold").toString().replace("replayValue", library.toUpperCase()) + "\"" + "\r\n");
                writer.write("\r\n");
            }
        }
    }
    
    private void writeStatementInfo(final BufferedWriter writer, final String statement, String desc, final String libraryValue, final String tableValue, final List<Map<String, Object>> filedList) throws Exception {
        if (StringUtils.isEmpty((CharSequence)desc)) {
            final StringBuilder sb = new StringBuilder();
            if (filedList != null && filedList.size() > 0) {
                for (int i = 0; i < filedList.size(); ++i) {
                    final Map<String, Object> fieldItem = filedList.get(i);
                    final Integer important = Integer.parseInt(fieldItem.get("important").toString());
                    if (important == 1) {
                        sb.append(fieldItem.get("field").toString().toUpperCase());
                        sb.append(",");
                    }
                }
            }
            desc = sb.toString();
            desc = desc.substring(0, desc.length() - 1);
        }
        final String dataValue = statement.replace("filedValue", desc).replace("libraryValue", libraryValue.toUpperCase()).replace("tableValue", tableValue);
        writer.write("                statement =>\"" + dataValue + "\"" + "\r\n");
        writer.write("\r\n");
    }
    
    private void writeAddFieldInfo(final BufferedWriter writer, final String otherInfo, final String library, final String tableValue, final Map<String, Object> item, final List<Map<String, Object>> filedList) throws Exception {
        final String db_user = library.toUpperCase();
        final String table_name = tableValue.toUpperCase();
        final String table_nick_name = item.get("tableName").toString();
        String db_tag_name = this.getDbTagName(table_name);
        if (db_tag_name == null || db_tag_name.length() == 0) {
            db_tag_name = "0";
        }
        final String db_nick_name = item.get("db_nick_name").toString();
        final String pk_col_name = item.get("pk_col_name").toString();
        final String pk_col_data = item.get("pk_col_data").toString();
        final StringBuilder sbDesc = new StringBuilder("{");
        final StringBuilder sbPretty = new StringBuilder("[");
        final String pretty = "{col_name:'colValue',col_data:'%{dataValue}'}";
        if (filedList != null && filedList.size() > 0) {
            for (int i = 0; i < filedList.size(); ++i) {
                final Map<String, Object> fieldItem = filedList.get(i);
                final String field = fieldItem.get("field").toString();
                final String name = fieldItem.get("name").toString();
                final Integer important = Integer.parseInt(fieldItem.get("important").toString());
                final Integer show = Integer.parseInt(fieldItem.get("show").toString());
                if (important == 1) {
                    sbDesc.append(field + ":'%{" + field + "}'");
                    sbDesc.append(",");
                }
                if (show == 1) {
                    sbPretty.append(pretty.replace("colValue", name).replace("dataValue", field.toLowerCase()));
                    sbPretty.append(",");
                }
            }
        }
        String desc = sbDesc.toString();
        desc = desc.substring(0, desc.length() - 1);
        String desc_pretty = sbPretty.toString();
        if (desc_pretty.length() > 1) {
            desc_pretty = desc_pretty.substring(0, desc_pretty.length() - 1);
        }
        desc += "}";
        desc_pretty += "]";
        writer.write("                add_field => {\"db_user\" =>\"" + db_user + "\"}" + "\r\n");
        writer.write("\r\n");
        writer.write("                add_field => {\"table_name\" =>\"" + table_name + "\"}" + "\r\n");
        writer.write("\r\n");
        writer.write("                add_field => {\"table_nick_name\" =>\"" + table_nick_name + "\"}" + "\r\n");
        writer.write("\r\n");
        writer.write("                add_field => {\"db_nick_name\" =>\"" + db_nick_name + "\"}" + "\r\n");
        writer.write("\r\n");
        writer.write("                add_field => {\"db_tag_name\" =>\"" + db_tag_name + "\"}" + "\r\n");
        writer.write("\r\n");
        writer.write("                add_field => {\"pk_col_name\" =>\"" + pk_col_name + "\"}" + "\r\n");
        writer.write("\r\n");
        writer.write("                add_field => {\"pk_col_data\" =>\"%{" + pk_col_data + "}\"}" + "\r\n");
        writer.write("\r\n");
        writer.write("                add_field => {\"desc\" =>\"" + desc + "\"}" + "\r\n");
        writer.write("\r\n");
        writer.write("                add_field => {\"desc_pretty\" =>\"" + desc_pretty + "\"}" + "\r\n");
        writer.write("\r\n");
        if (StringUtils.isNotEmpty((CharSequence)otherInfo)) {
            final JSONArray arrayData = JSONObject.parseArray(otherInfo);
            if (arrayData != null && arrayData.size() > 0) {
                for (int j = 0; j < arrayData.size(); ++j) {
                    final JSONObject o = arrayData.getJSONObject(j);
                    writer.write("                add_field => {\"" + o.get((Object)"coln").toString() + "\" =>\"" + o.get((Object)"cold").toString() + "\"}" + "\r\n");
                    writer.write("\r\n");
                }
            }
        }
    }
    
    private String getDbTagName(final String table) {
        String tag = this.fLTJBQMapper.findTagByTable(Integer.valueOf(0), Integer.valueOf(2), table);
        final String tag2 = this.fLTJBQMapper.findTagByTable(Integer.valueOf(1), Integer.valueOf(1), table);
        if (tag != null) {
            if (tag2 != null) {
                tag = tag + "," + tag2;
            }
        }
        else {
            tag = tag2;
        }
        return tag;
    }
}
