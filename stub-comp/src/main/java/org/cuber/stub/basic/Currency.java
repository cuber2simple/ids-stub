package org.cuber.stub.basic;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.cuber.stub.json.GsonHolder;
import org.cuber.stub.json.JacksonHolder;
import org.cuber.stub.vo.StubConfVO;

@ApiModel("币种对象")
public class Currency extends StubConfVO {

    private static final long serialVersionUID = 1919986400896138533L;

    @ApiModelProperty("币种字母码")
    private String alphaCode;

    @ApiModelProperty("币种数字码")
    private String numberCode;

    @ApiModelProperty("币种最小单位")
    private int minorUnit;

    @ApiModelProperty("币种符号")
    private String symbolCurrency;

    @ApiModelProperty("币种I18N")
    private String i18nKey;

    @ApiModelProperty("币种中文名")
    private String name;

    @ApiModelProperty("币种使用实体")
    private String country;

    @ApiModelProperty("是否是交易币种Y/N")
    private boolean grant4Sale;

    @ApiModelProperty("是否是结算币种Y/N")
    private boolean grant4Settle;


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

    public int getMinorUnit() {
        return minorUnit;
    }

    public void setMinorUnit(int minorUnit) {
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

    public boolean isGrant4Sale() {
        return grant4Sale;
    }

    public void setGrant4Sale(boolean grant4Sale) {
        this.grant4Sale = grant4Sale;
    }

    public boolean isGrant4Settle() {
        return grant4Settle;
    }

    public void setGrant4Settle(boolean grant4Settle) {
        this.grant4Settle = grant4Settle;
    }

    @Override
    public String toString() {
        return JacksonHolder.toJackson(this);
    }
}
