package com.obligatorio.sessionbeans;

import com.obligatorio.obligatorioexceptions.ObligatorioException;

/**
 *
 * @author horaciotorrendell
 */
public class LoginException extends ObligatorioException {

    public LoginException(Throwable innerException, String message) {
        super(innerException, message);
    }
    
}
