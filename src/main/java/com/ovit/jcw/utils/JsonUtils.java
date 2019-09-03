package com.ovit.jcw.utils;

import com.fasterxml.jackson.core.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

public class JsonUtils
{
    private static final ObjectMapper JSON;
    
    public static String toJson(final Object obj) {
        try {
            return JsonUtils.JSON.writeValueAsString(obj);
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static <T> T parseObject(final String json, final Class<T> clazz) {
        return (T)com.alibaba.fastjson.JSON.parseObject(json, (Class)clazz);
    }
    
    static {
        (JSON = new ObjectMapper()).setSerializationInclusion(JsonInclude.Include.NON_NULL);
        JsonUtils.JSON.configure(SerializationFeature.INDENT_OUTPUT, (boolean)Boolean.TRUE);
    }
}
