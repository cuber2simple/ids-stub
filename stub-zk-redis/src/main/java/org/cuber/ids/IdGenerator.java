package org.cuber.ids;

public interface IdGenerator {

    long nextVal(IdPattern idPattern) throws Exception;

    long curVal(IdPattern idPattern) throws Exception;

    String getPrefix(IdPattern idPattern);

    String nextId(IdPattern idPattern) throws Exception;
}
