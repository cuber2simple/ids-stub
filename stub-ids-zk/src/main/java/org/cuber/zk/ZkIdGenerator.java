package org.cuber.zk;

import org.apache.commons.lang3.StringUtils;
import org.cuber.ids.IdGenerator;
import org.cuber.ids.IdGeneratorFactory;
import org.cuber.ids.IdPattern;
import org.cuber.ids.SnowflakeIdGenerator;

import java.time.LocalDateTime;

public class ZkIdGenerator<T> implements IdGenerator{
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

    public LocalDateTime findIdTime(String ids){
        long id = Long.parseLong(StringUtils.substringAfter(ids, prefix));
        return snowflakeIdGenerator.findIdTime(id);
    }

    public static void main(String[] args) {
        long id = Long.parseLong(StringUtils.substringAfter("157732102452330508", ""));
        System.out.println(id);
    }
}
