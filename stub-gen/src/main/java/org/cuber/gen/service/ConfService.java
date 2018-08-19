package org.cuber.gen.service;

import org.cuber.gen.conf.Conf;
import org.cuber.gen.conf.Table;
import org.cuber.gen.define.TableDefine;

import java.sql.DatabaseMetaData;
import java.util.List;

public interface ConfService {

    Table loadTable(Conf conf, TableDefine tableDefine, DatabaseMetaData databaseMetaData);

    List<Table> dispose(Conf conf);
}
