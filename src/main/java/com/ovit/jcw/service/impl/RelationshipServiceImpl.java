package com.ovit.jcw.service.impl;

import com.ovit.jcw.service.*;
import org.springframework.stereotype.*;
import com.ovit.jcw.mysqlmapper.*;
import org.springframework.beans.factory.annotation.*;
import com.ovit.jcw.mapper.*;
import org.apache.logging.log4j.*;
import java.util.stream.*;
import com.ovit.jcw.model.*;
import org.apache.commons.lang3.*;
import java.util.*;
import com.alibaba.fastjson.*;

@Service
public class RelationshipServiceImpl implements RelationshipService
{
    private Logger logger;
    @Autowired
    private BusMapper busMapper;
    @Autowired
    private BJGMapper bjgMapper;
    
    public RelationshipServiceImpl() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @Override
    public Map<String, Object> query(final List<Map<String, String>> userList, final int tag) {
        final Map<String, Object> map = new HashMap<String, Object>();
        //final List<String> cardList = userList.stream().map(t -> t.get("card")).distinct().collect((Collector<? super Object, ?, List<String>>)Collectors.toList());2019-9-2
        final List<String> cardList = userList.stream().map(t -> t.get("card")).distinct().collect(Collectors.toList());
        final List<Map<String, Object>> contrastList = (List<Map<String, Object>>)this.busMapper.selectRelationshipContrast();
        for (final Map<String, Object> item : contrastList) {
            final Integer type = Integer.parseInt(item.get("type").toString());
            final String name = item.get("name").toString();
            this.getTypeInfo(map, tag, type, name, userList, cardList);
        }
        return map;
    }
    
    private void getTypeInfo(final Map<String, Object> map, final int tag, final int type, final String name, final List<Map<String, String>> userList, final List<String> cardList) {
        final List<Map<String, Object>> relationshipContrastInfoList = (List<Map<String, Object>>)this.busMapper.selectRelationshipContrastInfo(Integer.valueOf(type));
        final List<Map<String, Object>> infoValueList = new ArrayList<Map<String, Object>>();
        for (final Map<String, Object> item : relationshipContrastInfoList) {
            final Integer mod = Integer.parseInt(item.get("mod").toString());
            final String infoName = item.get("name").toString();
            final String queryInfo = item.get("query_info").toString();
            Map<String, Object> result = null;
            final List<Map<String, Object>> relationshipMap = this.getTableInfo(mod, queryInfo, cardList);
            if (tag == 0) {
                result = new HashMap<String, Object>();
                result.put("name", infoName);
                result.put("mod", mod);
                result.put("data", relationshipMap);
            }
            else {
                final String remark = item.get("remark").toString();
                this.getTypeResult(result, relationshipMap, mod, infoName, remark, userList);
            }
            if (result != null) {
                infoValueList.add(result);
            }
        }
        map.put(name, infoValueList);
    }
    
    private void getTypeResult(Map<String, Object> result, final List<Map<String, Object>> relationshipMap, final int mod, final String infoName, final String remark, final List<Map<String, String>> userList) {
        if (relationshipMap.size() > 0) {
            for (final Map<String, Object> relationship : relationshipMap) {
                final String card = relationship.get("card").toString();
                //final List<RelationshipContrast> relationshipContrastList = relationship.get("data");2019-9-2
                final List<RelationshipContrast> relationshipContrastList = (List<RelationshipContrast>) relationship.get("data");
                if (relationshipContrastList != null && relationshipContrastList.size() > 0) {
                    final StringBuffer sb = new StringBuffer();
                    final String curUser = userList.stream().filter(t -> t.get("card").equals(card)).findFirst().orElse(null).get("name");
                    for (final RelationshipContrast relationshipContrast : relationshipContrastList) {
                        final String oneUser = userList.stream().filter(t -> t.get("card").equals(relationshipContrast.getTag())).findFirst().orElse(null).get("name");
                        String info = remark.replace("user1", curUser).replace("user2", oneUser);
                        if (mod == 1) {
                            info = info.replace("count", relationshipContrast.getValue().toString());
                        }
                        else if (mod == 0 || mod == 10) {
                            final List<String> infoList = (List<String>)relationshipContrast.getValue();
                            final String dataInfo = "[" + StringUtils.join(infoList.toArray(), "],[") + "]";
                            info = info.replace("info", dataInfo).replace("count", String.valueOf(infoList.size()));
                        }
                        else if (mod == 11) {
                            final Map<String, Long> infoList2 = (Map<String, Long>)relationshipContrast.getValue();
                            String dataInfo = "";
                            if (infoList2 != null && infoList2.size() > 0) {
                                for (final Map.Entry<String, Long> item : infoList2.entrySet()) {
                                    dataInfo = dataInfo + "[" + item.getKey() + "] ";
                                }
                            }
                            info = info.replace("info", dataInfo.trim()).replace("count", String.valueOf(infoList2.size()));
                        }
                        sb.append(info);
                    }
                    result = new HashMap<String, Object>();
                    result.put("name", infoName);
                    result.put("data", sb.toString());
                }
            }
        }
    }
    
    private List<Map<String, Object>> getTableInfo(final int mod, final String queryInfo, final List<String> cardList) {
        List<Map<String, Object>> relationshipMap = new ArrayList<Map<String, Object>>();
        if (StringUtils.isNotEmpty((CharSequence)queryInfo)) {
            final JSONArray arrayData = JSONObject.parseArray(queryInfo);
            if (arrayData != null && arrayData.size() > 0) {
                final Map<String, List<Map<String, Object>>> userMap = new HashMap<String, List<Map<String, Object>>>();
                for (int i = 0; i < arrayData.size(); ++i) {
                    final JSONObject o = arrayData.getJSONObject(i);
                    final String table = o.getString("table");
                    final String keyFiled = o.getString("key_filed");
                    //final Map<String, String> inMap = (Map<String, String>)o.getJSONObject("in_filed");2019-9-2
                    final Map<String, Object> inMap = (Map<String, Object>)o.getJSONObject("in_filed");
                    for (final String item : cardList) {
                        final List<Map<String, Object>> list = (List<Map<String, Object>>)this.bjgMapper.queryBaisicByInfo(table, keyFiled, item, (Map)inMap);
                        if (userMap.containsKey(item)) {
                            final List<Map<String, Object>> oldList = userMap.get(item);
                            oldList.addAll(list);
                            userMap.put(item, oldList);
                        }
                        else {
                            userMap.put(item, list);
                        }
                    }
                }
                relationshipMap = this.getContrastByMod(mod, userMap, cardList);
            }
        }
        return relationshipMap;
    }
    
    private List<Map<String, Object>> getContrastByMod(final Integer mode, final Map<String, List<Map<String, Object>>> userMap, final List<String> cardList) {
        final Integer lenght = cardList.size();
        final List<Map<String, Object>> relationshipContrastList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < lenght; ++i) {
            final String card = cardList.get(i);
            final List<Map<String, Object>> list = userMap.get(card);
            final List<RelationshipContrast> relationshipList = new ArrayList<RelationshipContrast>();
            int tag = i;
            if (mode == 10) {
                tag = 0;
            }
            for (int j = tag; j < lenght; ++j) {
                if (i != j) {
                    final String tagCard = cardList.get(j);
                    final List<Map<String, Object>> tagList = userMap.get(tagCard);
                    final RelationshipContrast contrastValue = this.getContrastValueByMod(mode, card, tagCard, list, tagList);
                    if (contrastValue != null) {
                        relationshipList.add(contrastValue);
                    }
                }
            }
            if (relationshipList.size() > 0) {
                final Map<String, Object> relationshipMap = new HashMap<String, Object>();
                relationshipMap.put("card", card);
                relationshipMap.put("data", relationshipList);
                relationshipContrastList.add(relationshipMap);
            }
        }
        return relationshipContrastList;
    }
    
    private RelationshipContrast getContrastValueByMod(final Integer mode, final String card, final String tagCard, final List<Map<String, Object>> list, final List<Map<String, Object>> tagList) {
        RelationshipContrast contrastValue = null;
        Object value = null;
        if (list != null && list.size() > 0 && tagList != null && tagList.size() > 0) {
//            final List<String> outList = list.stream().map(map -> map.get("OUTFILED").toString()).distinct().collect((Collector<? super Object, ?, List<String>>)Collectors.toList());2019-9-2
//            final List<String> tagInList = tagList.stream().map(map -> map.get("INLETFILED").toString()).distinct().collect((Collector<? super Object, ?, List<String>>)Collectors.toList());2019-9-2
            final List<String> outList = list.stream().map(map -> map.get("OUTFILED").toString()).distinct().collect(Collectors.toList());
            final List<String> tagInList = tagList.stream().map(map -> map.get("INLETFILED").toString()).distinct().collect(Collectors.toList());
            for (final String item : tagInList) {
                if (outList.contains(item)) {
                    if (mode == 0 || mode == 10) {
                        value = ((value == null) ? new ArrayList<String>() : ((List)value)).add(item);
                    }
                    else if (mode == 1) {
                        final Long tagCount = tagList.stream().filter(usr -> item.equals(usr.get("OUTFILED"))).count();
                        if (tagCount <= 0L) {
                            continue;
                        }
                        value = ((value == null) ? 0L : (Long.parseLong(value.toString()) + tagCount));
                    }
                    else {
                        if (mode != 11) {
                            continue;
                        }
                        final Long tagCount = tagList.stream().filter(usr -> item.equals(usr.get("OUTFILED"))).count();
                        if (tagCount <= 0L) {
                            continue;
                        }
                        String newInfo = item;
                        if (item.contains("*")) {
                            newInfo = item.substring(item.indexOf(42));
                        }
                        final Map<String, Long> map2 = (value == null) ? new HashMap<String, Long>() : ((Map)value);
                        if (map2.containsKey(newInfo)) {
                            map2.put(newInfo, tagCount + map2.get(newInfo));
                        }
                        else {
                            map2.put(newInfo, tagCount);
                        }
                        value = map2;
                    }
                }
            }
        }
        if (value != null) {
            contrastValue = new RelationshipContrast();
            contrastValue.setTag(tagCard);
            contrastValue.setValue(value);
        }
        return contrastValue;
    }
}
