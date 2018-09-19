package org.cuber.stub.ids;

import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.basic.BizTableDef;
import org.cuber.zk.XClient;

import java.time.LocalDateTime;
import java.util.Objects;

public class ZkIdGenerator implements IdGenerator {

    private SnowflakeIdGenerator snowflakeIdGenerator;
    private BizTableDef bizTableDef;
    private String prefix;

    public ZkIdGenerator(BizTableDef bizTableDef) throws Exception {
        int bizCode = bizTableDef.getBizCode();
        long centerId = XClient.getLocalId();
        this.bizTableDef = bizTableDef;
        this.snowflakeIdGenerator = new SnowflakeIdGenerator(bizCode, centerId);
        if (Objects.nonNull(bizTableDef)) {
            prefix = bizTableDef.getPrefix();
        }
        prefix = StringUtils.trimToEmpty(prefix);
    }

    @Override
    public String nextId() {
        return prefix + snowflakeIdGenerator.nextId();
    }

    @Override
    public LocalDateTime findIdTime(String id) {
        long during = Long.parseLong(StringUtils.substringAfter(id, prefix));
        return snowflakeIdGenerator.findIdTime(during);
    }


}
