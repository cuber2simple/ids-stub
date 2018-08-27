package org.cuber.stub.controller;

import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.rpc.Resp;
import org.cuber.stub.utils.RpcUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
public class CommonController implements ErrorController {

    @Value("${org.cuber.front.nginx:http://127.0.0.1}")
    private String env;

    @Value("${org.cuber.product:false}")
    private boolean isProduct;

    @Autowired(required = false)
    private ServletContext servletContext;

    @Autowired
    private ErrorAttributes errorAttributes;

    private TemplateEngine templateEngine = new TemplateEngine();

    @EventListener
    public void ApplicationReady(ApplicationReadyEvent readyEvent) {
        ServletContextTemplateResolver templateResolver =
                new ServletContextTemplateResolver(servletContext);
        templateResolver.setTemplateMode(TemplateMode.HTML);
        String nginxTemplates = env + "/templates/";
        templateResolver.setPrefix(nginxTemplates);
        templateResolver.setSuffix(".html");
        templateResolver.setCacheTTLMs(Long.valueOf(3600000L));
        templateResolver.setCacheable(true);
        templateEngine.setTemplateResolver(templateResolver);
    }

    @RequestMapping("/login.htm")
    public void login(@RequestHeader("domain") String domain,
                      HttpServletRequest req,
                      HttpServletResponse resp) throws Exception {
        String template = domain + "/login";
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        templateEngine.process(template, ctx, resp.getWriter());
    }

    private Map<String, Object> getErrorAttributes(WebRequest request, boolean includeStackTrace) {
        return errorAttributes.getErrorAttributes(request, includeStackTrace);
    }

    @RequestMapping(value = "/error",  produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public Resp error(WebRequest webRequest){
        return buildBody(webRequest,!isProduct);
    }
    private Resp buildBody(WebRequest webRequest, boolean includeStackTrace) {
        Map<String, Object> errorAttributes = getErrorAttributes(webRequest, includeStackTrace);
        int status = (Integer) errorAttributes.get("status");
        String path = (String) errorAttributes.get("path");
        String messageFound = (String) errorAttributes.get("message");
        String message = "";
        String trace = "";
        if (!StringUtils.isEmpty(path)) {
            message = String.format("Requested path %s with result %s", path, messageFound);
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
