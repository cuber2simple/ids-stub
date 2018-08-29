package org.cuber.stub.rpc;

public class StubException extends Exception {

    private static final long serialVersionUID = -2582739697949842731L;
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public StubException() {
        super();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StubException(String code, Throwable t) {
        super(t);
        this.code = code;
        this.message = t.getMessage();
    }
}
