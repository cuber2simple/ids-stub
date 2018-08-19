package org.cuber.gen.service.impl;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;
import org.cuber.gen.service.BeetlService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Service
public class BeetlServiceImpl implements BeetlService {

    private GroupTemplate gt;

    @PostConstruct
    public void init() throws Exception {
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader("/template");
        Configuration cfg = Configuration.defaultConfiguration();
        gt = new GroupTemplate(resourceLoader, cfg);
    }

    @Override
    public Template getTemplate(String template) {
        return gt.getTemplate(template);
    }

    @Override
    public void genFile(Template template, Path path, String fileName) {
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            String file = path.toString() + File.separator + fileName;
            Path filePath = Paths.get(file);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                    Files.newOutputStream(filePath,
                            StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE));
            template.renderTo(outputStreamWriter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
