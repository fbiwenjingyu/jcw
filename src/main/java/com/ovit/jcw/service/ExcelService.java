package com.ovit.jcw.service;

import java.io.*;
import com.ovit.jcw.common.*;
import java.util.*;

public interface ExcelService
{
    String exportByStream(final String library, final InputStream stream, final NormalEnum.ExcelTYPE type);
    
    Map<String, Object> query(final String card, final NormalEnum.ExcelTYPE type);
    
    void deleteByCard(final String card, final NormalEnum.ExcelTYPE type);
    
    void deleteList(final List<String> cardList, final NormalEnum.ExcelTYPE type);
    
    List<Map<String, Object>> queryByLibrary(final Map<String, Object> params, final NormalEnum.ExcelTYPE type);
}
