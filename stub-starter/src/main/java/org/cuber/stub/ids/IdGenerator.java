package org.cuber.stub.ids;

import java.time.LocalDateTime;

public interface IdGenerator {
    /**
     *   下一个ID
     * @return 得到下一个ID
     */
    String nextId();

    /**
     *
     * @param id
     * @return
     */
    LocalDateTime findIdTime(String id);
}
