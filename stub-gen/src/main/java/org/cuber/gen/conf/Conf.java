package org.cuber.gen.conf;

import org.cuber.gen.define.GenDefine;
import org.cuber.gen.define.TableDefine;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties("org.cuber.gen.conf")
public class Conf {

    private GenDefine dto;

    private GenDefine mapper;

    private GenDefine xml;

    private GenDefine vo;

    private GenDefine service;

    private GenDefine ctrl;

    private GenDefine view;

    private List<TableDefine> tables;

    private String catalog;

    private String schema;


    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public GenDefine getDto() {
        return dto;
    }

    public void setDto(GenDefine dto) {
        this.dto = dto;
    }

    public GenDefine getMapper() {
        return mapper;
    }

    public void setMapper(GenDefine mapper) {
        this.mapper = mapper;
    }

    public GenDefine getXml() {
        return xml;
    }

    public void setXml(GenDefine xml) {
        this.xml = xml;
    }

    public GenDefine getVo() {
        return vo;
    }

    public void setVo(GenDefine vo) {
        this.vo = vo;
    }

    public GenDefine getService() {
        return service;
    }

    public void setService(GenDefine service) {
        this.service = service;
    }

    public GenDefine getCtrl() {
        return ctrl;
    }

    public void setCtrl(GenDefine ctrl) {
        this.ctrl = ctrl;
    }

    public GenDefine getView() {
        return view;
    }

    public void setView(GenDefine view) {
        this.view = view;
    }

    public List<TableDefine> getTables() {
        return tables;
    }

    public void setTables(List<TableDefine> tables) {
        this.tables = tables;
    }
}
