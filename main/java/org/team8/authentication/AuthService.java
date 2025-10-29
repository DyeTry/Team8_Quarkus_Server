package org.team8.authentication;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import org.mindrot.jbcrypt.BCrypt;

@ApplicationScoped
public class AuthService {

    public String authenticate(String username, String password) {
        AppUser user = AppUser.find("username", username).firstResult();
        if (user == null) return null;

        if (!BCrypt.checkpw(password, user.passwordHash)) {
            return null;
        }

        Set<String> roles = new HashSet<>();
        roles.add(user.role);

        return Jwt.issuer("asset-manager")
                .subject(user.username)
                .groups(roles)
                .expiresIn(Duration.ofHours(1))
                .sign();
    }
}
