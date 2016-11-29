package com.weavey.msc;

/**
 * create by Weavey
 * on date 2016-01-22
 * TODO
 */
public class SemanticAnalysisResult {

    private String operation;

    private String service;

    private Semantic semantic;

    private Pk_fea pk_fea;

    private int rc;

    private String text;

    public void setOperation(String operation){
        this.operation = operation;
    }
    public String getOperation(){
        return this.operation;
    }
    public void setService(String service){
        this.service = service;
    }
    public String getService(){
        return this.service;
    }
    public void setSemantic(Semantic semantic){
        this.semantic = semantic;
    }
    public Semantic getSemantic(){
        return this.semantic;
    }
    public void setPk_fea(Pk_fea pk_fea){
        this.pk_fea = pk_fea;
    }
    public Pk_fea getPk_fea(){
        return this.pk_fea;
    }
    public void setRc(int rc){
        this.rc = rc;
    }
    public int getRc(){
        return this.rc;
    }
    public void setText(String text){
        this.text = text;
    }
    public String getText(){
        return this.text;
    }

}
