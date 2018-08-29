package org.cuber.ids;

public abstract class AbstractIdGenerator implements IdGenerator{
    @Override
    public String nextId(IdPattern idPattern) throws Exception {
        String seqTemplates = "%0" + idPattern.getSeqLength() + "d";
        return getPrefix(idPattern) + String.format(seqTemplates, nextVal(idPattern));
    }
}
