package org.cuber.gen.runner;

import com.opencsv.CSVParser;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

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
        genMapper(table, conf);
        genXml(table, conf);
        genVO(table, conf);
        genService(table, conf);
        genController(table, conf);
    }

    private void genController(Table table, Conf conf) {
        GenDefine ctrl = conf.getCtrl();
        Template template = beetlService.getTemplate(ctrl.getTemplate());
        FullyQualifiedJavaType controller = table.getControllerJavaType();
        FullyQualifiedJavaType vo = table.getVoJavaType();
        FullyQualifiedJavaType serviceJava = table.getServiceJavaType();
        FullyQualifiedJavaType primary = table.getPrimaryJavType();
        template.binding("curJava", controller);
        template.binding("voJava", vo);
        template.binding("primary", primary);
        template.binding("serviceJava", serviceJava);
        Set<String> importJavas = new HashSet<>();
        template.binding("table", table);
        importJavas.add(serviceJava.getFullyQualifiedNameWithoutTypeParameters());
        importJavas.add(vo.getFullyQualifiedNameWithoutTypeParameters());
        importJavas.add(primary.getFullyQualifiedNameWithoutTypeParameters());
        importJavas.add("org.cuber.stub.rpc.PageResp");
        template.binding("importJavas", importJavas);
        Path path = getPath(ctrl);
        String fileName = controller.getShortName() + ".java";
        beetlService.genFile(template, path, fileName);
    }

    private void genService(Table table, Conf conf){
        try{
            GenDefine service = conf.getService();
            Template template = beetlService.getTemplate(service.getTemplate());
            FullyQualifiedJavaType java = table.getServiceJavaType();
            FullyQualifiedJavaType vo = table.getVoJavaType();
            Set<String> importJavas = new HashSet<>();
            importJavas.add(vo.getFullyQualifiedNameWithoutTypeParameters());
            importJavas.add("java.util.List");
            importJavas.add("org.cuber.stub.rpc.PageResp");
            template.binding("curJava", java);
            template.binding("voJava", vo);
            template.binding("importJavas", importJavas);
            FullyQualifiedJavaType primary = table.getPrimaryJavType();
            template.binding("primary", primary);
            Path path = getPath(service);
            String fileName = java.getShortName() + ".java";
            beetlService.genFile(template, path, fileName);
            Template templateImpl = beetlService.getTemplate(service.getTemplateImpl());
            FullyQualifiedJavaType javaImpl = table.getServiceImplJavaType();
            FullyQualifiedJavaType dtoJava = table.getDtoJavaType();
            FullyQualifiedJavaType mapperJava = table.getMapperJavaType();
            templateImpl.binding("importJavas",importJavas);

            importJavas.add(dtoJava.getFullyQualifiedNameWithoutTypeParameters());
            importJavas.add(mapperJava.getFullyQualifiedNameWithoutTypeParameters());
            importJavas.add("org.springframework.beans.BeanUtils");
            importJavas.add(java.getFullyQualifiedNameWithoutTypeParameters());
            template.binding("primary", primary);
            templateImpl.binding("curJava",javaImpl);
            templateImpl.binding("voJava", vo);
            templateImpl.binding("dtoJava", dtoJava);
            templateImpl.binding("serviceJava", java);
            templateImpl.binding("primary", table.getPrimaryJavType());
            templateImpl.binding("mapperJava", mapperJava);
            Path pathImpl = Paths.get(path + "/impl/");
            fileName = javaImpl.getShortName() + ".java";
            beetlService.genFile(templateImpl, pathImpl, fileName);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void genVO(Table table, Conf conf){
        try {
            GenDefine vo = conf.getVo();
            Template template = beetlService.getTemplate(vo.getTemplate());
            List<Column> showColumns = table.showColumns(vo.getRootClass());
            template.binding("table", table);
            template.binding("properties", showColumns);
            FullyQualifiedJavaType java = table.getVoJavaType();
            template.binding("curJava", java);
            java.getPackageName();
            Set<String> importJavas = getImportByColumn(showColumns);
            dealWithFather(vo, importJavas, template);
            importJavas.add("io.swagger.annotations.ApiModel");
            importJavas.add("io.swagger.annotations.ApiModelProperty");
            template.binding("importJavas", importJavas);
            Path path = getPath(conf.getVo());
            String fileName = java.getShortName() + ".java";
            beetlService.genFile(template, path, fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void genXml(Table table, Conf conf) {
        GenDefine xml = conf.getXml();
        Template template = beetlService.getTemplate(xml.getTemplate());
        FullyQualifiedJavaType mapperJava = table.getMapperJavaType();
        FullyQualifiedJavaType dtoJava = table.getDtoJavaType();
        List<Column> allColumns = table.allColumns();
        template.binding("dtoJava", dtoJava);
        template.binding("allColumns", allColumns);
        template.binding("mapperJava", mapperJava);
        template.binding("table", table);

        Path path = getPath(xml);
        String fileName = mapperJava.getShortName() + ".xml";
        beetlService.genFile(template, path, fileName);
    }

    private void genMapper(Table table, Conf conf) {
        GenDefine mapper = conf.getMapper();
        Template template = beetlService.getTemplate(mapper.getTemplate());
        FullyQualifiedJavaType mapperJava = table.getMapperJavaType();
        FullyQualifiedJavaType dtoJava = table.getDtoJavaType();
        FullyQualifiedJavaType primary = table.getPrimaryJavType();
        template.binding("curJava", mapperJava);
        template.binding("dtoJava", dtoJava);
        template.binding("primary", primary);
        Set<String> importJavas = new HashSet<>();
        dealWithFather(mapper, importJavas, template);
        importJavas.add(dtoJava.getFullyQualifiedNameWithoutTypeParameters());
        importJavas.add(primary.getFullyQualifiedNameWithoutTypeParameters());
        if (StringUtils.isEmpty(mapper.getRootClass())) {
            importJavas.add("com.github.pagehelper.Page");
            importJavas.add("java.util.List");
            importJavas.add("java.util.Map");
        }
        template.binding("importJavas", importJavas);
        Path path = getPath(mapper);
        String fileName = mapperJava.getShortName() + ".java";
        beetlService.genFile(template, path, fileName);
    }

    private void dealWithFather(GenDefine define, Set<String> importJavas, Template template) {
        template.binding("hasFather", false);
        if (StringUtils.isNotEmpty(define.getRootClass())) {
            importJavas.add(define.getRootClass());
            FullyQualifiedJavaType father = new FullyQualifiedJavaType(define.getRootClass());
            template.binding("hasFather", true);
            template.binding("fatherName", father.getShortName());
        }
    }

    private void genDTOFile(Table table, Conf conf) {
        try {
            GenDefine dto = conf.getDto();
            Template template = beetlService.getTemplate(dto.getTemplate());
            List<Column> showColumns = table.showColumns(dto.getRootClass());
            template.binding("table", table);
            template.binding("properties", showColumns);
            FullyQualifiedJavaType java = table.getDtoJavaType();
            template.binding("curJava", java);
            java.getPackageName();
            Set<String> importJavas = getImportByColumn(showColumns);
            dealWithFather(dto, importJavas, template);
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
    private static final CSVParser csvParser = new CSVParser();
    public static void main(String[] args) throws Exception{
        String csvFile = "/work/home_work/country.csv";
        String insertSql = "INSERT INTO t_country_3166(id,alpha_code_2,alpha_code_3,number_code,ISO3166_2,\n" +
                "i18n_key,name,region,sub_region,intermediate_region,region_code,sub_region_code,intermediate_region_code,\n" +
                "status,update_user_id,create_user_id,update_datetime,create_datetime) values(";
        FileInputStream infile = new FileInputStream(csvFile);
        final Reader decorator = new InputStreamReader(infile, "utf-8");
        BufferedReader reader = new BufferedReader(decorator);
        Stream<String> lines = reader.lines();
        lines.forEach(line ->{
            try {
                String[] data = csvParser.parseLine(line);
                if(data!= null && data.length >= 11){
                    StringBuilder stringBuilder = new StringBuilder(insertSql);
                    stringBuilder.append("nextval('SEQ_COUNTRY_3166'),'").append(data[1]).append("','")
                            .append(data[2]).append("','").append(data[3]).append("','")
                            .append(data[4]).append("',null,'").append(data[0]).append("','")
                            .append(data[5]).append("','").append(data[6]).append("','").append(data[7]).append("','")
                            .append(data[8]).append("','").append(data[9]).append("','").append(data[10])
                            .append("','1','1','1',current_timestamp,current_timestamp);");
                    System.out.println(stringBuilder);
                    System.out.println();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
}
