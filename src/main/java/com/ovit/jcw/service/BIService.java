package com.ovit.jcw.service;

import java.util.*;
import com.ovit.jcw.common.*;

public interface BIService
{
    List<Map<String, Object>> selectForRHZXDZJZ();
    
    Map<String, Object> selectForRHZXSJSL();
    
    List<Map<String, Object>> selectForRHZXWBJ();
    
    List<Map<String, Object>> selectByHJZXSJZL();
    
    Map<String, List<Map<String, Object>>> selectByHJZXKSJZL();
    
    List<Map<String, Object>> selectByHJZXSJAL();
    
    Map<String, Object> selectForHJZX(final NormalEnum.HJZXType type);
    
    Map<String, List<Map<String, Object>>> selectForHJZXQST(final NormalEnum.HJZXType type);
    
    List<Map<String, Object>> selectByLSQSQK();
    
    Map<String, Object> selectBySJZLQK();
    
    List<Map<String, Object>> selectByFSLQSQK();
    
    List<Map<String, Object>> selectByWBJSJZB();
    
    List<Map<String, Object>> selectByWTSJFLZB();
    
    Map<String, Object> selectBySJZLQKFX();
    
    Map<String, Object> selectForJCXT(final NormalEnum.JCXTType type);
    
    List<Map<String, Object>> selectByJCXTQYAJFX();
    
    List<Map<String, Object>> selectByJCXTJCRYTJ();
}
