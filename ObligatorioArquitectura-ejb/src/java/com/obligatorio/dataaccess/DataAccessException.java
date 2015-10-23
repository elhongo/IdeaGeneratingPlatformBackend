package com.obligatorio.dataaccess;

import com.obligatorio.obligatorioexceptions.ObligatorioException;

/**
 *
 * @author juanmartincorallo
 */
public class DataAccessException extends ObligatorioException {

    public DataAccessException(Throwable innerException, String message) {
        super(innerException, message);
    }
    
}
