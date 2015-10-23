package com.obligatorio.obligatorioexceptions;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanmartincorallo
 */
public abstract class ObligatorioException extends Exception {

    private final String message;
    private final Throwable innerException;

    public ObligatorioException(Throwable innerException, String message) {
        super();
        this.message = message;
        this.innerException = innerException;
        Logger.getLogger(getClass().getName()).log(Level.WARNING, "exception caught", this);
    }

    public String getStackTraceString() {
        String ret = "";
        if (innerException != null) {
            StringWriter sw = new StringWriter();
            innerException.printStackTrace(new PrintWriter(sw));
            ret = sw.toString();
        }
        return ret;
    }

    public Throwable getInnerException() {
        return innerException;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
