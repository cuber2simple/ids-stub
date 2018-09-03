package org.cuber.ids;

public interface IdGenerator {
    
    String nextId(IdPattern idPattern) throws Exception;
}
