package org.cuber.stub.foundation.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.cuber.stub.vo.StubVO;

@ApiModel("币种表")
public class DCurrency extends StubVO {

    private static final long serialVersionUID = -5018110413944600001L;

    @ApiModelProperty("币种字母码")
    private String alphaCode;
    @ApiModelProperty("币种数字码")
    private String numberCode;
    @ApiModelProperty("币种最小单位")
    private Short minorUnit;
    @ApiModelProperty("币种符号")
    private String symbolCurrency;
    @ApiModelProperty("币种I18N")
    private String i18nKey;
    @ApiModelProperty("币种中文名")
    private String name;
    @ApiModelProperty("币种使用实体")
    private String country;
    @ApiModelProperty("是否是交易币种Y/N")
    private String grant4Sale;
    @ApiModelProperty("是否是结算币种Y/N")
    private String grant4Settle;
    @ApiModelProperty("是否可用")
    private String status;
    @ApiModelProperty("更新操作员")
    private String updateUserId;
    @ApiModelProperty("创建操作员")
    private String createUserId;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getAlphaCode() {
        return alphaCode;
    }

    public void setAlphaCode(String alphaCode) {
        this.alphaCode = alphaCode;
    }

    public String getNumberCode() {
        return numberCode;
    }

    public void setNumberCode(String numberCode) {
        this.numberCode = numberCode;
    }

    public Short getMinorUnit() {
        return minorUnit;
    }

    public void setMinorUnit(Short minorUnit) {
        this.minorUnit = minorUnit;
    }

    public String getSymbolCurrency() {
        return symbolCurrency;
    }

    public void setSymbolCurrency(String symbolCurrency) {
        this.symbolCurrency = symbolCurrency;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getGrant4Sale() {
        return grant4Sale;
    }

    public void setGrant4Sale(String grant4Sale) {
        this.grant4Sale = grant4Sale;
    }

    public String getGrant4Settle() {
        return grant4Settle;
    }

    public void setGrant4Settle(String grant4Settle) {
        this.grant4Settle = grant4Settle;
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
