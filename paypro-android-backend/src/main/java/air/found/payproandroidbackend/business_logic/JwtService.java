package air.found.payproandroidbackend.business_logic;

import air.found.payproandroidbackend.core.models.JwtTokenInfo;
import air.found.payproandroidbackend.core.models.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private static final int EXPIRATION_IN_MINUTES = 30;
    private static final String SECRET_KEY = "X3hP5yB8rG0kS2dU7vC3wK5zN8pQ1tL9qY4mE2aV6bH1jM0fO8gJ3iR7sZ5xW2cT";

    public JwtTokenInfo getJwtToken(UserAccount userAccount) {
        Date issuedAt = new Date();

        JwtTokenInfo jwtToken = new JwtTokenInfo();
        jwtToken.setAccess_token(buildJws(userAccount, issuedAt));

        JwtTokenInfo.RefreshToken refreshToken = new JwtTokenInfo.RefreshToken();
        JwtTokenInfo.RefreshToken.Validity validity = new JwtTokenInfo.RefreshToken.Validity();

        validity.setTime_unit("minutes");
        validity.setTime_amount(EXPIRATION_IN_MINUTES + 90);
        refreshToken.setValid_for(validity);
        refreshToken.setToken(buildRefresh(userAccount, issuedAt));

        jwtToken.setRefresh_token(refreshToken);

        return jwtToken;
    }

    private String buildJws(UserAccount userAccount, Date issuedAt) {
        Date expirationJws = new Date(issuedAt.getTime() + (EXPIRATION_IN_MINUTES * 60 * 1000));

        return Jwts.builder()
                .header()
                .type("JWT")
                .and()

                .subject(userAccount.getEmailAddress())
                .expiration(expirationJws)
                .issuedAt(issuedAt)
                .claims(getDataFromUserAccount(userAccount))

                .signWith(generateKey(), Jwts.SIG.HS256)

                .compact();
    }

    private String buildRefresh(UserAccount userAccount, Date issuedAt) {
        Date expirationRefresh = new Date(issuedAt.getTime() + ((EXPIRATION_IN_MINUTES + 90) * 60 * 1000));

        return Jwts.builder()
                .header()
                .type("JWT")
                .and()

                .subject(userAccount.getEmailAddress())
                .expiration(expirationRefresh)
                .issuedAt(issuedAt)

                .signWith(generateKey(), Jwts.SIG.HS256)

                .compact();
    }

    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(generateKey()).build().parseUnsecuredClaims(token).getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token, Claims::getExpiration).before(new Date());
    }

    private static SecretKey generateKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    private Map<String, Object> getDataFromUserAccount(UserAccount userAccount){
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("sub", userAccount.getUserId().toString());
        extraClaims.put("email_address", userAccount.getEmailAddress());
        extraClaims.put("first_name", userAccount.getFirstName());
        extraClaims.put("last_name", userAccount.getLastName());
        extraClaims.put("is_admin", userAccount.getIsAdmin());

        return extraClaims;
    }
}
