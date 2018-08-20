package org.cuber.gen.runner;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.beetl.core.Template;
import org.cuber.gen.conf.Column;
import org.cuber.gen.conf.Conf;
import org.cuber.gen.conf.Table;
import org.cuber.gen.define.GenDefine;
import org.cuber.gen.service.BeetlService;
import org.cuber.gen.service.ConfService;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class GenFileCommandRunner implements CommandLineRunner {

    @Autowired
    private Conf conf;

    @Autowired
    private ConfService confService;

    @Autowired
    private BeetlService beetlService;

    @Override
    public void run(String... args) throws Exception {
        List<Table> tables = confService.dispose(conf);
        if (CollectionUtils.isNotEmpty(tables)) {
            tables.parallelStream().forEach(table ->
                    genFilesByTable(table, conf)
            );
        }
    }

    private void genFilesByTable(Table table, Conf conf) {
        genDTOFile(table, conf);

    }

    private void genDTOFile(Table table, Conf conf) {
        try {
            GenDefine dto = conf.getDto();
            Template template = beetlService.getTemplate(dto.getTemplate());
            List<Column> showColumns = table.showColumns(dto.getRootClass());
            template.binding("columns", showColumns);
            template.binding("table", table);
            template.binding("properties", showColumns);
            FullyQualifiedJavaType java = table.getDtoJavaType();
            template.binding("curJava", java);
            template.binding("hasFather", false);
            java.getPackageName();
            Set<String> importJavas = getImportByColumn(showColumns);
            if (StringUtils.isNotEmpty(dto.getRootClass())) {
                importJavas.add(dto.getRootClass());
                FullyQualifiedJavaType father = new FullyQualifiedJavaType(dto.getRootClass());
                template.binding("hasFather", true);
                template.binding("fatherName",father.getShortName());
            }
            template.binding("importJavas", importJavas);
            Path path = getPath(conf.getDto());
            String fileName = java.getShortName() + ".java";
            beetlService.genFile(template, path, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Path getPath(GenDefine genDefine) {
        String packages = StringUtils.join(StringUtils.split(genDefine.getDestPackage(), "."), File.separator);
        String packageString = genDefine.getDestProject() + File.separator + packages;
        return Paths.get(packageString);
    }

    private Set<String> getImportByColumn(List<Column> showColumns) {
        Set<String> importJavas = new HashSet<>();
        if (CollectionUtils.isNotEmpty(showColumns)) {
            showColumns.stream().forEach(column -> {
                FullyQualifiedJavaType fullyQualifiedJavaType = column.getFullyQualifiedJavaType();
                if (!fullyQualifiedJavaType.isPrimitive() && !fullyQualifiedJavaType.isArray()) {
                    importJavas.add(fullyQualifiedJavaType.getFullyQualifiedNameWithoutTypeParameters());
                }
            });
        }
        return importJavas;
    }
}
