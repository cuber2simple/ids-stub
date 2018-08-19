package org.cuber.stub.rpc;

public class StubException extends Exception {

    private static final long serialVersionUID = -2582739697949842731L;
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public StubException() {
        super();
    }

    public StubException(String code, Throwable t) {
        super(t);
        this.code = code;
    }
}
