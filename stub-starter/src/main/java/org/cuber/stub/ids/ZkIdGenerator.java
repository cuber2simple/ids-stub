package org.cuber.stub.ids;

import org.apache.commons.lang3.StringUtils;
import org.cuber.stub.basic.BizTableDef;
import org.cuber.zk.XClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Objects;

public class ZkIdGenerator implements IdGenerator {

    private static Logger logger = LoggerFactory.getLogger(ZkIdGenerator.class);


    private SnowflakeIdGenerator snowflakeIdGenerator;
    private BizTableDef bizTableDef;
    private String prefix;

    public ZkIdGenerator(BizTableDef bizTableDef) {
        try {
            if (Objects.nonNull(bizTableDef)) {
                prefix = bizTableDef.getPrefix();
                int bizCode = bizTableDef.getBizCode();
                long centerId = XClient.getLocalId();
                this.bizTableDef = bizTableDef;
                this.snowflakeIdGenerator = new SnowflakeIdGenerator(bizCode, centerId);
                prefix = StringUtils.trimToEmpty(prefix);
            }
        } catch (Exception e) {
            logger.error("新建主键生成策略失败", e);
        }
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

    public String getSplitPattern() {
        return bizTableDef.getPattern();
    }
}
