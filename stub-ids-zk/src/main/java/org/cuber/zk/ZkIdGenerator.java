package org.cuber.zk;

import org.apache.commons.lang3.StringUtils;
import org.cuber.ids.IdGenerator;
import org.cuber.ids.IdGeneratorFactory;
import org.cuber.ids.IdPattern;
import org.cuber.ids.SnowflakeIdGenerator;

public class ZkIdGenerator<T> implements IdGenerator<T> {
    private SnowflakeIdGenerator snowflakeIdGenerator;
    private IdPattern<T> idPattern;
    private String prefix;

    public ZkIdGenerator(IdPattern<T> idPattern) {
        this.idPattern = idPattern;
        prefix = StringUtils.trimToEmpty(idPattern.getPrefix());
        snowflakeIdGenerator = new SnowflakeIdGenerator(IdGeneratorFactory._localId(), idPattern.getBizCode());

    }

    @Override
    public String nextId(){
        long seq = snowflakeIdGenerator.nextId();
        return prefix + seq;
    }
}
