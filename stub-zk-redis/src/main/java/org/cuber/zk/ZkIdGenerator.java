package org.cuber.zk;

import org.cuber.ids.IdGenerator;
import org.cuber.ids.IdPattern;

public class ZkIdGenerator implements IdGenerator {

    @Override
    public long nextVal(IdPattern idPattern) throws Exception {
        return 0;
    }

    @Override
    public long curVal(IdPattern idPattern) throws Exception {

        return 0;
    }

    @Override
    public String getPrefix(IdPattern idPattern) {
        return null;
    }

    @Override
    public String nextId(IdPattern idPattern) throws Exception {
        return null;
    }
}
