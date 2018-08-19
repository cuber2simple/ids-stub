package org.cuber.gen.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.cuber.gen.conf.Table;
import org.cuber.gen.define.GenDefine;
import org.cuber.gen.service.BeetlService;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

@Service
public class BeetlServiceImpl implements BeetlService {

    private GroupTemplate gt;

    @PostConstruct
    public void init() throws Exception{
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("/template");
        Configuration cfg = Configuration.defaultConfiguration();
        gt = new GroupTemplate(resourceLoader, cfg);
    }

    @Override
    public void genFile(GenDefine define, Table table, FullyQualifiedJavaType javaType) throws Exception{
        Template template = gt.getTemplate(define.getTemplate());
        template.binding("tale",table);
        template.binding("curJava",javaType);
        String fileName = javaType.getShortName() + ".java";
        if(Objects.isNull(javaType)){
            fileName = table.getMapperJavaType().getShortName() + ".xml";
        }
        String packages = StringUtils.join(StringUtils.split(define.getDestPackage(),"."),File.separator);
        String packageString = define.getDestProject() + File.separator + packages;
        Path packagePath = Paths.get(packageString);
        if (!Files.exists(packagePath)) {
            Files.createDirectories(packagePath);
        }

        String file = packageString + File.separator + fileName;
        Path filePath = Paths.get(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                Files.newOutputStream(filePath,
                        StandardOpenOption.TRUNCATE_EXISTING,StandardOpenOption.CREATE));
        template.renderTo(outputStreamWriter);
    }
}
