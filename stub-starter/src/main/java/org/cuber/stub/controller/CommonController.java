package org.cuber.stub.controller;

import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.StubConstant;
import org.cuber.stub.rpc.Resp;
import org.cuber.stub.utils.RpcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@Controller
public class CommonController extends BaseController implements ErrorController {

    @Autowired
    private ErrorAttributes errorAttributes;

    @RequestMapping("/")
    public String login() {
        return StubConstant.REDIRECT + this.env + "/sso/login.htm";
    }

    private Map<String, Object> getErrorAttributes(WebRequest request, boolean includeStackTrace) {
        return errorAttributes.getErrorAttributes(request, includeStackTrace);
    }

    @RequestMapping(value = "/error", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Resp error(WebRequest webRequest) {
        return buildBody(webRequest, !this.isProduct);
    }

    private Resp buildBody(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = getErrorAttributes(webRequest, includeStackTrace);
        int status = (Integer) errorAttributes.get("status");
        String path = (String) errorAttributes.get("path");
        String messageFound = (String) errorAttributes.get("message");
        String message = "";
        String trace = "";
        if (!StringUtils.isEmpty(path)) {
            message = String.format("Requested path [%s] with result [%s]", path, messageFound);
        }
        if (includeStackTrace) {
            trace = (String) errorAttributes.get("trace");
        }
        return RpcUtils.httpFailed(status, message, trace);
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
