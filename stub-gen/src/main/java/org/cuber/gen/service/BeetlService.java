package org.cuber.gen.service;

import org.cuber.gen.conf.Table;
import org.cuber.gen.define.GenDefine;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;

public interface BeetlService {

    void genFile(GenDefine define, Table table,FullyQualifiedJavaType javaType) throws Exception;
}
