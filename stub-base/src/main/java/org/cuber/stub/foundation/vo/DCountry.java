package org.cuber.stub.foundation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.cuber.stub.vo.StubVO;

@ApiModel("国家表")
public class DCountry extends StubVO {
    private static final long serialVersionUID = 7073964929554500977L;

    @ApiModelProperty("国家2字码")
    private String alphaCode2;
    @ApiModelProperty("国家3字码")
    private String alphaCode3;
    @ApiModelProperty("国家数字码")
    private String numberCode;
    @ApiModelProperty("国家3166-2字码")
    private String iso31662;
    @ApiModelProperty("国家I18N")
    private String i18nKey;
    @ApiModelProperty("国家英文名")
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
    @ApiModelProperty("是否可用")
    private String status;
    @ApiModelProperty("更新操作员")
    private String updateUserId;
    @ApiModelProperty("创建操作员")
    private String createUserId;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }
}
