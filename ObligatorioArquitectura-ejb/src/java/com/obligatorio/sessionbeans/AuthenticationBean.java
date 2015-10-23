package com.obligatorio.sessionbeans;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;

@Singleton
@LocalBean
public class AuthenticationBean {

    private final Map<String, UUID> usersTokens = new HashMap<>();

    public Boolean authenticateUser(UUID token, String username) {
        Boolean sessionExists = false;
        if (usersTokens.containsKey(username) && usersTokens.containsValue(token)) {
            sessionExists = true;
        }
        return sessionExists;
    }

    /**
     *
     * @param username
     * @return UUID token
     * @throws LoginException
     */
    public UUID loginUser(String username) throws LoginException {
        if (!usersTokens.containsKey(username)) {
            UUID token = UUID.randomUUID();
            usersTokens.put(username, token);
            return token;
        } else {
            throw new LoginException(null, "User already logged in");
        }
    }

    public Boolean logout(UUID token, String username) {
        return usersTokens.remove(username, token);
    }

}
