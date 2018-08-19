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
import java.util.Arrays;
import java.util.List;
import java.util.Set;
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

    }

    private void genDTOFile(Table table, Conf conf) {
        try {
            GenDefine dto = conf.getDto();
            Template template = beetlService.getTemplate(dto.getTemplate());
            List<Column> showColumns = table.showColumns(dto.getRootClass());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(Arrays.asList(BeanUtils.getPropertyDescriptors(Column.class)));
    }
}
