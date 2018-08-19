package org.cuber.gen.service;

import org.beetl.core.Template;

import java.nio.file.Path;

public interface BeetlService {

    void genFile(Template template, Path path, String fileName);

    Template getTemplate(String template);
}
