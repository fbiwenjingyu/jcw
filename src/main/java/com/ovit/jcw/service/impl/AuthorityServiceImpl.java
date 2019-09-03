package com.ovit.jcw.service.impl;

import com.ovit.jcw.service.*;
import org.springframework.stereotype.*;
import com.ovit.jcw.mysqlmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.apache.logging.log4j.*;
import com.ovit.jcw.model.*;
import com.alibaba.fastjson.*;
import com.ovit.jcw.common.*;
import com.ovit.jcw.utils.*;
import org.apache.commons.lang3.*;
import java.util.*;

@Service
public class AuthorityServiceImpl implements AuthorityService
{
    private Logger logger;
    @Autowired
    private BusMapper busMapper;
    @Value("${encrypt}")
    private String encrypt;
    @Value("${encryptField}")
    private String encryptField;
    
    public AuthorityServiceImpl() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    public Map<String, Object> encryptionMap(final String tableName, final Map<String, Object> map) {
        if (map != null && map.size() > 0) {
            final List<String> encryptList = (List<String>)this.busMapper.queryEnglishSecretFiledByTable(tableName);
            if (encryptList != null && encryptList.size() > 0) {
                for (final String item : encryptList) {
                    if (map.containsKey(item)) {
                        map.put(item, this.encrypt);
                    }
                }
            }
        }
        return map;
    }
    
    public List<Map<String, Object>> encryptionList(final String table, final List<Map<String, Object>> list) {
        if (list != null && list.size() > 0) {
            final List<String> secretList = (List<String>)this.busMapper.queryEnglishSecretFiledByTable(table);
            final Map<String, Map<String, String>> keyList = this.getTableFieldKey(table, 0);
            this.encryptionListBySecret(list, secretList, keyList);
        }
        return list;
    }
    
    public void encrypt(final List<String> secretList, final List<DescPretty> descPrettyList, final Map<String, Object> map) {
        if (secretList != null && secretList.size() > 0) {
            for (final DescPretty item : descPrettyList) {
                if (secretList.contains(item.getCol_name())) {
                    item.setCol_data(this.encrypt);
                }
            }
        }
        map.put("desc_pretty", JSON.toJSONString((Object)descPrettyList));
    }
    
    public List<Map<String, Object>> encryptionList1(final String token, final String table, final String info, final List<Map<String, Object>> list) {
        if (list != null && list.size() > 0) {
            List<String> secretList = null;
            if (this.chargeTableSecret(token, table, info)) {
                secretList = (List<String>)this.busMapper.queryEnglishSecretFiledByTable(table);
            }
            final List<String> signList = (List<String>)this.busMapper.querySignFiledByTable(table);
            final Map<String, Map<String, String>> keyList = this.getTableFieldKey(table, 0);
            List<String> redList = null;
            List<String> redAuthorityList = null;
            if (signList != null && signList.size() > 0) {
                redList = this.getRedListByUser(null);
                redAuthorityList = this.getRedListByUser(token);
                this.redSignList(secretList, signList, redList, redAuthorityList, keyList, list);
            }
            else {
                this.encryptionListBySecret(list, secretList, keyList);
            }
        }
        return list;
    }
    
    public void encryptionListBySecret(final List<Map<String, Object>> list, final List<String> secretList, final Map<String, Map<String, String>> keyList) {
        for (final Map<String, Object> item : list) {
            this.setFiledMapByKey(item, secretList, keyList);
            if (secretList != null && secretList.size() > 0) {
                boolean flag = false;
                for (final String secret : secretList) {
                    if (item.containsKey(secret)) {
                        item.put(secret, this.encrypt);
                        flag = true;
                    }
                }
                if (flag) {
                    item.put("flag", NormalEnum.FlagEnum.Secret.GetCode().toString());
                }
                else {
                    item.put("flag", NormalEnum.FlagEnum.SecretAuthority.GetCode().toString());
                }
            }
        }
    }
    
    public void encryptionMapBySecret(final Map<String, Object> map, final List<String> secretList, final Map<String, Map<String, String>> keyList) {
        boolean flag = false;
        if (secretList != null && secretList.size() > 0) {
            for (final String secret : secretList) {
                if (map.containsKey(secret)) {
                    map.put(secret, this.encrypt);
                    flag = true;
                }
            }
        }
        this.setFiledMapByKey(map, secretList, keyList);
        if (flag) {
            map.put("flag", NormalEnum.FlagEnum.Secret.GetCode().toString());
        }
        else {
            map.put("flag", NormalEnum.FlagEnum.SecretAuthority.GetCode().toString());
        }
    }
    
    public void setFiledMapByKey(final Map<String, Object> map, final List<String> secretList, final Map<String, Map<String, String>> keyList) {
        for (final Map.Entry<String, Map<String, String>> entry : keyList.entrySet()) {
            final String key = entry.getKey();
            if (keyList.size() > 0 && map.containsKey(key) && (secretList == null || !secretList.contains(key))) {
                final Map<String, String> keyMap = entry.getValue();
                final String value = map.get(key).toString();
                if (keyMap.containsKey(value)) {
                    map.put(key, keyMap.get(value));
                }
                else {
                    map.put(key, keyMap.get(Utils.DefaultValue));
                }
            }
        }
    }
    
    public void setEsFiledMapByKey(final DescPretty item, final Boolean tag, final Map<String, Map<String, String>> keyList) {
        if (keyList.size() > 0 && tag && keyList.containsKey(item.getCol_name())) {
            final Map<String, String> keyMap = keyList.get(item.getCol_name());
            final String value = item.getCol_data();
            if (keyMap.containsKey(value)) {
                item.setCol_data((String)keyMap.get(value));
            }
            else {
                item.setCol_data((String)keyMap.get(Utils.DefaultValue));
            }
        }
    }
    
    public boolean encryptionEsMapBySecret(final List<DescPretty> descPrettyList, final List<String> secretList, final Map<String, Map<String, String>> keyList) {
        boolean flag = false;
        for (final DescPretty item : descPrettyList) {
            boolean tag = true;
            if (secretList != null && secretList.size() > 0 && secretList.contains(item.getCol_name())) {
                item.setCol_data(this.encrypt);
                flag = true;
                tag = false;
            }
            this.setEsFiledMapByKey(item, tag, keyList);
        }
        if (flag) {
            descPrettyList.add(new DescPretty("flag", NormalEnum.FlagEnum.Secret.GetCode().toString()));
        }
        else {
            descPrettyList.add(new DescPretty("flag", NormalEnum.FlagEnum.SecretAuthority.GetCode().toString()));
        }
        return flag;
    }
    
    public void redSignList(final List<String> secretList, final List<String> signList, final List<String> redList, final List<String> redAuthorityList, final Map<String, Map<String, String>> keyList, final List<Map<String, Object>> list) {
        if (redList != null && redList.size() > 0) {
            for (final Map<String, Object> item : list) {
                boolean flag = true;
                for (final String sign : signList) {
                    final String value = item.get(sign).toString();
                    if (redList.contains(value)) {
                        flag = false;
                        if (redAuthorityList == null || !redAuthorityList.contains(value)) {
                            for (final Map.Entry<String, Object> t : item.entrySet()) {
                                t.setValue(this.encrypt);
                            }
                            item.put("flag", NormalEnum.FlagEnum.Red.GetCode().toString());
                            break;
                        }
                        item.put("flag", NormalEnum.FlagEnum.RedAuthority.GetCode().toString());
                        this.setFiledMapByKey(item, null, keyList);
                    }
                }
                if (flag) {
                    this.encryptionMapBySecret(item, secretList, keyList);
                }
            }
        }
        else {
            this.encryptionListBySecret(list, secretList, keyList);
        }
    }
    
    public List<String> getRedListByUser(final String user) {
        List<String> redList = null;
        if (StringUtils.isNoneEmpty(new CharSequence[] { user })) {
            redList = (List<String>)this.busMapper.queryRedListByUser(user);
        }
        else {
            redList = (List<String>)this.busMapper.queryRedList();
        }
        return redList;
    }
    
    public Map<String, String> getTableFieldInfo(final String table, final Integer type) {
        final Map<String, String> filedMap = new HashMap<String, String>();
        final List<Map<String, Object>> filedList = (List<Map<String, Object>>)this.busMapper.queryFiledByTable(table);
        if (filedList != null && filedList.size() > 0) {
            for (final Map<String, Object> item : filedList) {
                if (type == 0) {
                    filedMap.put(item.get("field").toString(), item.get("name").toString());
                }
                else {
                    filedMap.put(item.get("name").toString(), item.get("field").toString());
                }
            }
        }
        return filedMap;
    }
    
    public Map<String, Map<String, String>> getTableFieldKey(final String table, final Integer type) {
        final Map<String, Map<String, String>> map = new HashMap<String, Map<String, String>>();
        final List<Map<String, Object>> list = (List<Map<String, Object>>)((type == 0) ? this.busMapper.queryEnglishFiledKeyByTable(table) : this.busMapper.queryChineseFiledKeyByTable(table));
        if (list != null && list.size() > 0) {
            String oldKey = "";
            Map<String, String> sub = null;
            for (final Map<String, Object> m : list) {
                final String key = m.get("field").toString();
                if (!key.equals(oldKey)) {
                    oldKey = key;
                    sub = new HashMap<String, String>();
                    sub.put(Utils.DefaultValue, m.get("remark").toString());
                    map.put(key, sub);
                }
                final String keyValue = m.get("key").toString();
                if (StringUtils.isNotEmpty((CharSequence)keyValue)) {
                    sub.put(keyValue, m.get("data").toString());
                }
            }
        }
        return map;
    }
    
    public String setFiledMapValueByKey(final String key, final String value, final List<String> secretList, final Map<String, Map<String, String>> keyList) {
        String newValue = value;
        if (secretList != null && secretList.contains(key)) {
            newValue = this.encrypt;
        }
        else if (keyList != null && keyList.size() > 0 && keyList.containsKey(key)) {
            final Map<String, String> keyMap = keyList.get(key);
            if (keyMap.containsKey(value)) {
                newValue = keyMap.get(value);
            }
            else {
                newValue = keyMap.get(Utils.DefaultValue);
            }
        }
        return newValue;
    }
    
    public boolean chargeTableSecret(final String user, final String table, final String condition) {
        boolean flag = true;
        try {
            final Integer count = this.busMapper.queryUserSecretByUser(user, condition);
            if (count > 0) {
                flag = false;
            }
            else {
                final List<String> list = (List<String>)this.busMapper.queryUserSecretByTable(table, user);
                if (list != null && list.size() > 0) {
                    for (final String item : list) {
                        if (item.equals(condition)) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
        return flag;
    }
    
    public boolean chargeLibrarySecret(final String user, final String library, final String condition) {
        boolean flag = true;
        try {
            final List<String> list = (List<String>)this.busMapper.queryUserSecretByLibrary(library, user);
            if (list != null && list.size() > 0) {
                if (StringUtils.isEmpty((CharSequence)condition)) {
                    flag = false;
                }
                else {
                    for (final String item : list) {
                        if (item.equals(condition)) {
                            flag = false;
                            break;
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
        return flag;
    }
}
