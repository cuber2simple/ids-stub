package org.cuber.stub.basic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.cuber.stub.json.GsonHolder;
import org.cuber.stub.json.JacksonHolder;
import org.cuber.stub.vo.StubConfVO;

@ApiModel("国家对象")
public class Country extends StubConfVO {

    private static final long serialVersionUID = 1313879296002292643L;

    @ApiModelProperty("国家2字码")
    private String alphaCode2;

    @ApiModelProperty("国家3字码")
    private String alphaCode3;

    @ApiModelProperty("国家数字码")
    private String numberCode;

    @ApiModelProperty("国家iso31662数字码")
    private String iso31662;

    @ApiModelProperty("i18nKey资源")
    private String i18nKey;

    @ApiModelProperty("国家名字")
    private String name;

    @ApiModelProperty("国家地区")
    private String region;

    @ApiModelProperty("国家子地区")
    private String subRegion;

    @ApiModelProperty("国家中间地区")
    private String intermediateRegion;

    @ApiModelProperty("国家地区编码")
    private String regionCode;

    @ApiModelProperty("国家子地区编码")
    private String subRegionCode;

    @ApiModelProperty("国家中间地区编码")
    private String intermediateRegionCode;

    public String getAlphaCode2() {
        return alphaCode2;
    }

    public void setAlphaCode2(String alphaCode2) {
        this.alphaCode2 = alphaCode2;
    }

    public String getAlphaCode3() {
        return alphaCode3;
    }

    public void setAlphaCode3(String alphaCode3) {
        this.alphaCode3 = alphaCode3;
    }

    public String getNumberCode() {
        return numberCode;
    }

    public void setNumberCode(String numberCode) {
        this.numberCode = numberCode;
    }

    public String getIso31662() {
        return iso31662;
    }

    public void setIso31662(String iso31662) {
        this.iso31662 = iso31662;
    }

    public String getI18nKey() {
        return i18nKey;
    }

    public void setI18nKey(String i18nKey) {
        this.i18nKey = i18nKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubRegion() {
        return subRegion;
    }

    public void setSubRegion(String subRegion) {
        this.subRegion = subRegion;
    }

    public String getIntermediateRegion() {
        return intermediateRegion;
    }

    public void setIntermediateRegion(String intermediateRegion) {
        this.intermediateRegion = intermediateRegion;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getSubRegionCode() {
        return subRegionCode;
    }

    public void setSubRegionCode(String subRegionCode) {
        this.subRegionCode = subRegionCode;
    }

    public String getIntermediateRegionCode() {
        return intermediateRegionCode;
    }

    public void setIntermediateRegionCode(String intermediateRegionCode) {
        this.intermediateRegionCode = intermediateRegionCode;
    }

    @Override
    public String toString() {
        return JacksonHolder.toJackson(this);
    }
}
