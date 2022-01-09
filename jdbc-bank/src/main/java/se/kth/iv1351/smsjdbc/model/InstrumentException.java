package se.kth.iv1351.smsjdbc.model;

public class InstrumentException extends Exception {

    public InstrumentException(String reason) {
        super(reason);
    }

    public InstrumentException(String reason, Throwable rootCause) {
        super(reason, rootCause);
    }
}
