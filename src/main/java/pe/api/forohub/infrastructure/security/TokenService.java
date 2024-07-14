package pe.api.forohub.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pe.api.forohub.domain.user.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.secret}")
    private String apiSecret;

    private final String issuer = "forohub pe";

    public String generateToken(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                .withIssuer(issuer)
                .withSubject(user.getName())
                .withClaim("id", user.getId())
                .withExpiresAt(generateExpirationTime())
                .sign(algorithm);
        } catch (JWTCreationException e) {
            throw new RuntimeException(e);
        }
    }

    private Instant generateExpirationTime() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-05:00"));
    }

    public String getSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer(issuer)
                .build()
                .verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException exception) {
            throw new RuntimeException(exception);
        }
    }
}
