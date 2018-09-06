package org.cuber.ids;

import java.time.LocalDateTime;

public interface IdGenerator {
    String nextId();
    LocalDateTime findIdTime(String ids);
}
