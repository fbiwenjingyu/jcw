package com.ovit.jcw.service.impl;

import com.ovit.jcw.service.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.*;
import com.ovit.jcw.mapper.*;
import com.ovit.jcw.mysqlmapper.*;
import org.apache.logging.log4j.*;
import java.util.*;
import com.alibaba.fastjson.*;
import org.apache.poi.poifs.filesystem.*;
import com.ovit.jcw.common.*;
import org.apache.poi.hssf.usermodel.*;
import java.io.*;
import com.ovit.jcw.utils.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

@Service
public class ExcelServiceImpl implements ExcelService
{
    private Logger logger;
    @Value("${gaby.path}")
    private String gabyPath;
    @Value("${gaby.url}")
    private String gabyUrl;
    @Autowired
    private DCBMapper dcbMapper;
    @Autowired
    private GABYMapper gabyMapper;
    @Autowired
    private BusMapper busMapper;
    
    public ExcelServiceImpl() {
        this.logger = LogManager.getLogger((Class)this.getClass());
    }
    
    @Override
    public List<Map<String, Object>> queryByLibrary(final Map<String, Object> params, final NormalEnum.ExcelTYPE type) {
        final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        return list;
    }
    
    @Override
    public Map<String, Object> query(final String card, final NormalEnum.ExcelTYPE type) {
        final Map<String, Object> map = new HashMap<String, Object>();
        try {
            final int tag = type.GetCode();
            if (type == NormalEnum.ExcelTYPE.GABY) {
                for (int i = 0; i < 4; ++i) {
                    final NormalEnum.ExcelGABY gaby = NormalEnum.ExcelGABY.values()[i];
                    this.getDataListByType(gaby.GetCode(), card, gaby.GetDesc(), map, type);
                }
            }
            else if (type == NormalEnum.ExcelTYPE.DCB) {
                this.getDataListByType(tag, card, type.GetDesc(), map, type);
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
        return map;
    }
    
    @Override
    public void deleteByCard(final String card, final NormalEnum.ExcelTYPE type) {
        try {
            final List<Map<String, Object>> vlist = (List<Map<String, Object>>)this.busMapper.queryByLibrary(Integer.valueOf(type.GetCode()));
            for (final Map<String, Object> item : vlist) {
                if (type == NormalEnum.ExcelTYPE.GABY) {
                    this.gabyMapper.deleteDataByTable(item.get("table").toString(), card);
                }
                else {
                    if (type != NormalEnum.ExcelTYPE.DCB) {
                        continue;
                    }
                    this.dcbMapper.deleteDataByTable(item.get("table").toString(), card);
                }
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
    }
    
    @Override
    public void deleteList(final List<String> cardList, final NormalEnum.ExcelTYPE type) {
        try {
            final List<Map<String, Object>> vlist = (List<Map<String, Object>>)this.busMapper.queryByLibrary(Integer.valueOf(type.GetCode()));
            for (final Map<String, Object> item : vlist) {
                if (type == NormalEnum.ExcelTYPE.GABY) {
                    this.gabyMapper.deleteList(item.get("table").toString(), (List)cardList);
                }
                else {
                    if (type != NormalEnum.ExcelTYPE.DCB) {
                        continue;
                    }
                    this.dcbMapper.deleteList(item.get("table").toString(), (List)cardList);
                }
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
    }
    
    private void getDataListByType(final Integer tag, final String card, final String desc, final Map<String, Object> map, final NormalEnum.ExcelTYPE type) {
        final List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        List<Map<String, Object>> vlist = null;
        if (type == NormalEnum.ExcelTYPE.GABY) {
            vlist = (List<Map<String, Object>>)this.busMapper.queryEXByType(tag);
        }
        else if (type == NormalEnum.ExcelTYPE.DCB) {
            vlist = (List<Map<String, Object>>)this.busMapper.queryByLibrary(tag);
        }
        if (vlist.size() > 0) {
            for (final Map<String, Object> item : vlist) {
                List<Map<String, Object>> dataList = null;
                if (type == NormalEnum.ExcelTYPE.GABY) {
                    dataList = (List<Map<String, Object>>)this.gabyMapper.queryDataByTable(item.get("table").toString(), card);
                }
                else if (type == NormalEnum.ExcelTYPE.DCB) {
                    dataList = (List<Map<String, Object>>)this.dcbMapper.queryDataByTable(item.get("table").toString(), card);
                }
                if (dataList != null && dataList.size() > 0) {
                    final Map<String, Object> vvMap = new HashMap<String, Object>();
                    vvMap.put("name", item.get("ex_name").toString());
                    vvMap.put("field", this.getFiledMapByTable(item.get("table").toString()));
                    vvMap.put("data", dataList);
                    list.add(vvMap);
                }
            }
        }
        map.put(desc, list);
    }
    
    private Map<String, String> getFiledMapByTable(final String table) {
        final Map<String, String> filedMap = new HashMap<String, String>();
        final List<Map<String, Object>> filedList = (List<Map<String, Object>>)this.busMapper.queryFiledByTable(table);
        if (filedList != null && filedList.size() > 0) {
            for (final Map<String, Object> item : filedList) {
                filedMap.put(item.get("field").toString(), item.get("name").toString());
            }
        }
        return filedMap;
    }
    
    private void InsertData(final NormalEnum.ExcelTYPE type, final String card, final String table, final Map<String, String> map) {
        try {
            if (type == NormalEnum.ExcelTYPE.GABY) {
                this.gabyMapper.deleteDataByTable(table, card);
                this.gabyMapper.insertData(table, (Map)map);
            }
            else if (type == NormalEnum.ExcelTYPE.DCB) {
                this.dcbMapper.deleteDataByTable(table, card);
                this.dcbMapper.insertData(table, (Map)map);
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
    }
    
    private void InsertList(final NormalEnum.ExcelTYPE type, final String card, final String table, final List<Map<String, String>> list) {
        try {
            if (type == NormalEnum.ExcelTYPE.GABY) {
                this.gabyMapper.deleteDataByTable(table, card);
                for (final Map<String, String> item : list) {
                    this.gabyMapper.insertData(table, (Map)item);
                }
            }
            else if (type == NormalEnum.ExcelTYPE.DCB) {
                this.dcbMapper.deleteDataByTable(table, card);
                for (final Map<String, String> item : list) {
                    this.dcbMapper.insertData(table, (Map)item);
                }
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
    }
    
    private String getCellKey(final JSONArray array, final String bt) {
        String value = null;
        if (array != null) {
            for (int i = 0; i < array.size(); ++i) {
                final JSONObject obj = array.getJSONObject(i);
                if (obj.get((Object)"coln").toString().equals(bt)) {
                    value = obj.get((Object)"cold").toString();
                    break;
                }
            }
        }
        return value;
    }
    
    @Override
    public String exportByStream(final String library, final InputStream stream, final NormalEnum.ExcelTYPE type) {
        String sfzh = null;
        try {
            if (type == NormalEnum.ExcelTYPE.GABY) {
                sfzh = this.JXGABY(stream);
            }
            else if (type == NormalEnum.ExcelTYPE.DCB) {
                sfzh = this.JXDCB(library, stream);
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
        return sfzh;
    }
    
    private String JXGABY(final InputStream stream) {
        HSSFWorkbook workbook = null;
        POIFSFileSystem fs = null;
        String card = "";
        try {
            fs = new POIFSFileSystem(stream);
            workbook = new HSSFWorkbook(fs);
            for (int i = 0; i < 4; ++i) {
                final HSSFSheet sheet = workbook.getSheetAt(i);
                if (sheet.getSheetName().equals(NormalEnum.ExcelGABY.JBXX.GetDesc())) {
                    card = this.getJBXX(workbook, NormalEnum.ExcelGABY.JBXX.GetCode(), sheet);
                }
                else if (sheet.getSheetName().equals(NormalEnum.ExcelGABY.JCXX.GetDesc())) {
                    this.getJCXX(sheet, NormalEnum.ExcelGABY.JCXX.GetCode(), card);
                }
                else if (sheet.getSheetName().equals(NormalEnum.ExcelGABY.BJXX.GetDesc())) {
                    this.getJCXX(sheet, NormalEnum.ExcelGABY.BJXX.GetCode(), card);
                }
                else if (sheet.getSheetName().equals(NormalEnum.ExcelGABY.HDXX.GetDesc())) {
                    this.getJCXX(sheet, NormalEnum.ExcelGABY.HDXX.GetCode(), card);
                }
            }
            workbook.close();
            fs.close();
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
        return card;
    }
    
    private String getJBXX(final HSSFWorkbook workbook, final Integer tag, final HSSFSheet sheet) {
        String card = "";
        try {
            final List<Map<String, Object>> list = (List<Map<String, Object>>)this.busMapper.queryEXByType(tag);
            if (list.size() > 0) {
                final int rowCount = sheet.getLastRowNum();
                final Map<String, Object> curMap = list.get(0);
                final Map<String, String> dataList = new HashMap<String, String>();
                final Map<String, String> titleMap = new HashMap<String, String>();
                final JSONArray array = JSONObject.parseArray(curMap.get("field").toString());
                if (array != null) {
                    for (int i = 0; i < rowCount + 1; ++i) {
                        final HSSFRow row = sheet.getRow(i);
                        titleMap.put(row.getCell(1).getStringCellValue().replace(":", ""), row.getCell(2).getStringCellValue());
                        if (i != rowCount) {
                            titleMap.put(row.getCell(4).getStringCellValue().replace(":", ""), row.getCell(5).getStringCellValue());
                        }
                    }
                    for (int i = 0; i < array.size(); ++i) {
                        final JSONObject obj = array.getJSONObject(i);
                        final String colName = obj.get((Object)"coln").toString();
                        if (titleMap.containsKey(colName)) {
                            dataList.put(obj.get((Object)"cold").toString(), titleMap.get(colName));
                        }
                    }
                }
                if (dataList.size() > 0) {
                    card = dataList.get(Constant.SFZH);
                    final PictureData data = this.getSheetPictrues(sheet, workbook);
                    if (data != null) {
                        final String path = this.saveSheetImg(card, data);
                        dataList.put(Constant.GRZP, path);
                    }
                    this.InsertData(NormalEnum.ExcelTYPE.GABY, card, curMap.get("table").toString(), dataList);
                }
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
        return card;
    }
    
    private void getJCXX(final HSSFSheet sheet, final int tag, final String card) {
        try {
            final List<Map<String, Object>> list = (List<Map<String, Object>>)this.busMapper.queryEXByType(Integer.valueOf(tag));
            if (list.size() > 0) {
                final Map<String, Object> map = new HashMap<String, Object>();
                for (final Map<String, Object> item : list) {
                    map.put(item.get("ex_name").toString(), item);
                }
                final int rowCount = sheet.getLastRowNum();
                Map<String, Object> curMap = null;
                List<Map<String, String>> dataList = null;
                Map<Integer, String> titleMap = null;
                JSONArray jsonArr = null;
                for (int i = 0; i < rowCount + 1; ++i) {
                    final HSSFRow row = sheet.getRow(i);
                    if (row == null) {
                        if (dataList != null && curMap != null && dataList.size() > 0) {
                            this.InsertList(NormalEnum.ExcelTYPE.GABY, card, curMap.get("table").toString(), dataList);
                        }
                        curMap = null;
                        dataList = null;
                        titleMap = null;
                    }
                    else {
                        final int cellCount = row.getLastCellNum();
                        HSSFCell cell = row.getCell(0);
                        if (cellCount == 1) {
                            final String key = cell.getRichStringCellValue().getString();
//                            System.out.println(key);
                            if (map.containsKey(key)) {
                                curMap = (Map<String, Object>) map.get(key);
                                jsonArr = JSONObject.parseArray(curMap.get("field").toString());
                                dataList = new ArrayList<Map<String, String>>();
                                titleMap = new HashMap<Integer, String>();
                            }
                        }
                        else if (titleMap != null && dataList != null) {
                            if (titleMap.size() == 0) {
                                for (int j = 0; j < cellCount; ++j) {
                                    titleMap.put(j, row.getCell(j).getStringCellValue());
                                }
                            }
                            else {
                                final Map<String, String> dataMap = new HashMap<String, String>();
                                dataList.add(dataMap);
                                for (int k = 0; k < cellCount; ++k) {
                                    cell = row.getCell(k);
                                    final String cellKey = this.getCellKey(jsonArr, titleMap.get(k));
                                    if (cellKey != null) {
                                        dataMap.put(cellKey, cell.getStringCellValue());
                                    }
                                }
                                if (Integer.parseInt(curMap.get("has_sfzh").toString()) > 0) {
                                    dataMap.put(Constant.SFZH, card);
                                }
                            }
                        }
                    }
                }
                if (dataList != null && curMap != null && dataList.size() > 0) {
                    this.InsertList(NormalEnum.ExcelTYPE.GABY, card, curMap.get("table").toString(), dataList);
                }
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
    }
    
    public PictureData getSheetPictrues(final HSSFSheet sheet, final HSSFWorkbook workbook) {
        HSSFPictureData picData = null;
        final List<HSSFPictureData> pictures = (List<HSSFPictureData>)workbook.getAllPictures();
        if (pictures.size() != 0) {
            for (final HSSFShape shape : sheet.getDrawingPatriarch().getChildren()) {
                if (shape instanceof HSSFPicture) {
                    final HSSFPicture pic = (HSSFPicture)shape;
                    final int pictureIndex = pic.getPictureIndex() - 1;
                    picData = pictures.get(pictureIndex);
                    break;
                }
            }
        }
        return (PictureData)picData;
    }
    
    public String saveSheetImg(final String picName, final PictureData pic) {
        String path = "";
        try {
            final String ext = pic.suggestFileExtension();
            final byte[] data = pic.getData();
            final FileOutputStream out = new FileOutputStream(this.gabyPath + picName + "." + ext);
            out.write(data);
            out.close();
            path = this.gabyUrl + picName + "." + ext;
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
        return path;
    }
    
    public void saveExcelFile(final InputStream is, final String fileName, final String ext, final String type) {
        try {
            final String path = this.gabyPath + type + "//" + fileName + "." + ext;
            BufferedInputStream in = null;
            BufferedOutputStream out = null;
            in = new BufferedInputStream(is);
            out = new BufferedOutputStream(new FileOutputStream(path));
            int len = -1;
            final byte[] b = new byte[1024];
            while ((len = in.read(b)) != -1) {
                out.write(b, 0, len);
            }
            in.close();
            out.close();
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
    }
    
    private String JXDCB(final String library, final InputStream stream) {
        XSSFWorkbook workbook = null;
        String card = "";
        try {
            workbook = new XSSFWorkbook(stream);
            final XSSFSheet sheet = workbook.getSheetAt(1);
            card = this.JXTABLE(NormalEnum.ExcelTYPE.DCB.GetCode(), library, sheet);
            workbook.close();
            stream.close();
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
        return card;
    }
    
    private String JXTABLE(final Integer tag, final String library, final XSSFSheet sheet) {
        String card = "";
        try {
            final List<Map<String, Object>> list = (List<Map<String, Object>>)this.busMapper.queryByLibrary(tag);
            if (list.size() > 0) {
                final Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
                for (final Map<String, Object> item : list) {
                    map.put(item.get("ex_name").toString(), item);
                }
                final int rowCount = sheet.getLastRowNum();
                int lastRowCount = 1;
                for (int i = 0; i < rowCount + 1; ++i) {
                    if (i == lastRowCount) {
                        final int cellReginRow = ExcelUtils.getMergerCellRegionRow((Sheet)sheet, i, 0);
                        if (cellReginRow > 0) {
                            lastRowCount += cellReginRow;
                            final XSSFRow row = sheet.getRow(i);
                            final String title = row.getCell(0).getRichStringCellValue().getString();
                            if (map.containsKey(title)) {
                                final Map<String, Object> curMap = map.get(title);
                                final Integer exType = Integer.parseInt(curMap.get("ex_type").toString());
                                if (exType == NormalEnum.ExcelDCB.QJJX.GetCode()) {
                                    card = this.JXData(sheet, library, card, i, lastRowCount, curMap);
                                }
                                else if (exType == NormalEnum.ExcelDCB.LBJX.GetCode()) {
                                    card = this.JXList(sheet, card, i, lastRowCount, curMap);
                                }
                                else if (exType == NormalEnum.ExcelDCB.QJLBJX.GetCode()) {
                                    card = this.JXDataList(sheet, card, i, lastRowCount, curMap);
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
        return card;
    }
    
    private String JXData(final XSSFSheet sheet, final String library, String card, final int firstRow, final int lastRow, final Map<String, Object> curMap) {
        try {
            final JSONArray array = JSONObject.parseArray(curMap.get("field").toString());
            if (array != null) {
                final Map<String, String> dataList = new HashMap<String, String>();
                for (int i = 0; i < array.size(); ++i) {
                    final JSONObject obj = array.getJSONObject(i);
                    final Integer colr = (Integer)obj.get((Object)"colr");
                    final Integer colc = (Integer)obj.get((Object)"colc");
                    final XSSFRow row = sheet.getRow(firstRow + colr);
                    this.setDataMap(dataList, row, obj, colc);
                }
                if (dataList.size() > 0) {
                    if (Utils.chargeMapBlack(dataList)) {
                        if (dataList.containsKey(Constant.SFZH)) {
                            card = dataList.get(Constant.SFZH);
                            dataList.put("LIBRARY", library);
                        }
                        else {
                            dataList.put(Constant.SFZH, card);
                        }
                    }
                    this.InsertData(NormalEnum.ExcelTYPE.DCB, card, curMap.get("table").toString(), dataList);
                }
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
        return card;
    }
    
    private String JXList(final XSSFSheet sheet, final String card, final int firstRow, final int lastRow, final Map<String, Object> curMap) {
        try {
            final JSONArray array = JSONObject.parseArray(curMap.get("field").toString());
            if (array != null) {
                final List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
                JSONObject obj = array.getJSONObject(0);
                final Integer startRow = firstRow + (int)obj.get((Object)"colr");
                final Integer endRow = lastRow - (int)obj.get((Object)"colrr");
                for (int rowCount = startRow; rowCount < endRow; ++rowCount) {
                    final Map<String, String> dataMap = new HashMap<String, String>();
                    for (int i = 0; i < array.size(); ++i) {
                        obj = array.getJSONObject(i);
                        final Integer colc = (Integer)obj.get((Object)"colc");
                        final XSSFRow row = sheet.getRow(rowCount);
                        this.setDataMap(dataMap, row, obj, colc);
                    }
                    if (Utils.chargeMapBlack(dataMap)) {
                        if (!dataMap.containsKey(Constant.SFZH)) {
                            dataMap.put(Constant.SFZH, card);
                        }
                        dataList.add(dataMap);
                    }
                }
                if (dataList.size() > 0) {
                    this.InsertList(NormalEnum.ExcelTYPE.DCB, card, curMap.get("table").toString(), dataList);
                }
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
        return card;
    }
    
    private String JXDataList(final XSSFSheet sheet, final String card, final int firstRow, final int lastRow, final Map<String, Object> curMap) {
        try {
            final JSONArray array = JSONObject.parseArray(curMap.get("field").toString());
            if (array != null) {
                final List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
                JSONObject obj = array.getJSONObject(0);
                final Integer colrr = (Integer)obj.get((Object)"colrr");
                final Integer length = (lastRow - firstRow) / colrr;
                for (int rowCount = 0; rowCount < length; ++rowCount) {
                    final Map<String, String> dataMap = new HashMap<String, String>();
                    for (int i = 0; i < array.size(); ++i) {
                        obj = array.getJSONObject(i);
                        final Integer colr = (Integer)obj.get((Object)"colr");
                        final Integer colc = (Integer)obj.get((Object)"colc");
                        final XSSFRow row = sheet.getRow(firstRow + colrr * rowCount + colr);
                        this.setDataMap(dataMap, row, obj, colc);
                    }
                    if (Utils.chargeMapBlack(dataMap)) {
                        if (!dataMap.containsKey(Constant.SFZH)) {
                            dataMap.put(Constant.SFZH, card);
                        }
                        dataList.add(dataMap);
                    }
                }
                if (dataList.size() > 0) {
                    this.InsertList(NormalEnum.ExcelTYPE.DCB, card, curMap.get("table").toString(), dataList);
                }
            }
        }
        catch (Exception ex) {
            this.logger.error((Object)ex);
        }
        return card;
    }
    
    private void setDataMap(final Map<String, String> dataMap, final XSSFRow row, final JSONObject obj, final Integer colc) {
        if (row != null) {
            final XSSFCell cell = row.getCell((int)colc);
            if (cell != null) {
                final String title = obj.get((Object)"cold").toString();
                final String value = ExcelUtils.getCellValue((Cell)cell);
                dataMap.put(title, value);
            }
        }
    }
}
