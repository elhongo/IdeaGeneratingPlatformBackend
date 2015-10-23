package com.obligatorio.dataaccess;

import com.obligatorio.obligatorioexceptions.ObligatorioException;

/**
 *
 * @author juanmartincorallo
 */
public class PostNotFoundException extends ObligatorioException {

    public PostNotFoundException(Throwable innerException, String message) {
        super(innerException, message);
    }
    
}
