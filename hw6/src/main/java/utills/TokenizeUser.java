package utills;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import models.User;

import java.util.Map;

public class TokenizeUser {
    private final String SECRET_KEY = "secret";

    public String createJWT(User user) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            String token = JWT.create()
                    .withClaim("id", user.getId())
                    .withClaim("email", user.getEmail())
                    .withClaim("role", user.getRole())
                    .sign(algorithm);

            return token;
        } catch (JWTCreationException exception) {
            return null;
        }
    }

    public User decodeJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            User user = new User();
            user.setId(claims.get("id").asInt());
            user.setEmail(claims.get("email").asString());
            user.setRole(claims.get("role").asString());
            return user;
        } catch (JWTVerificationException exception) {
            return null;
        }
    }

}