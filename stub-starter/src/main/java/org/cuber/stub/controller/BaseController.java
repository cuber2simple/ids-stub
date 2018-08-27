package org.cuber.stub.controller;

import org.cuber.stub.StubConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
    @Value("${org.cuber.front.nginx:http://127.0.0.1}")
    protected String env;

    @Value("${org.cuber.product:false}")
    protected boolean isProduct;

    @ModelAttribute
    public void addBaseModel(ModelMap modelMap) {
        modelMap.put(StubConstant.ENV, env);
        modelMap.put(StubConstant.PRODUCT, isProduct);
    }
}
