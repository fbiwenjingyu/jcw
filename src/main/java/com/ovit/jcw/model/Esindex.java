package com.ovit.jcw.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "esindex",type="testtype")
public class Esindex {
	@Id
    @Field(store=true,type=FieldType.Integer)
    private Integer id;
    @Field(analyzer="ik",store=true,searchAnalyzer="ik",type=FieldType.text)
    private String title;
    @Field(analyzer="ik",store=true,searchAnalyzer="ik",type=FieldType.text)
    private String content;

}
