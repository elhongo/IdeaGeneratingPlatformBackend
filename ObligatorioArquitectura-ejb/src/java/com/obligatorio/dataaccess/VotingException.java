package com.obligatorio.dataaccess;

import com.obligatorio.obligatorioexceptions.ObligatorioException;

/**
 *
 * @author juanmartincorallo
 */
public class VotingException extends ObligatorioException {

    public VotingException(Throwable innerException, String message) {
        super(innerException, message);
    }
    
}
