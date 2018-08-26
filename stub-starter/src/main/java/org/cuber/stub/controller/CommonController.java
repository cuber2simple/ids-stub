package org.cuber.stub.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class CommonController {

    @Value("org.cuber.front.nginx:http://127.0.0.1")
    private String env;

    @Autowired(required = false)
    private ServletContext servletContext;

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
                      HttpServletResponse resp) throws Exception{
        String template = domain + "/login";
        WebContext ctx = new WebContext(req, resp, servletContext, req.getLocale());
        templateEngine.process(template, ctx, resp.getWriter());
    }
}
