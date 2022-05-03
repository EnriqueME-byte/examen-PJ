package saki.corp.demo.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.util.Date;

@Component
public class JWTUtils {

    @Value("${security.jwt.secret}")
    private String key;

    @Value("${security.jwt.issuer}")
    private String issuer;

    @Value("${security.jwt.ttMillis}")
    private long ttMillis;

    // Implementamos un logger para ver cual metodo da error en caso de falla
    private final static Logger logger = LoggerFactory.getLogger(JWTUtils.class);

    /*crer un nuevo token*/
    public String crear(String id, String subject, String nombre){
        Date actual = new Date();
        Date exp = new Date(actual.getTime() + ttMillis);
        final String jwt = Jwts.builder().setSubject(id)
                .claim("usuario", subject).claim("apellido",nombre)
                .setIssuedAt(new Date()).setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, TextCodec.BASE64.encode(key))
                .compact();
        return jwt;
    }

    public String decodifica(String token){
        Claims claims = (Claims) Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        return claims.getSubject();
    }

    public boolean validarToken(String token){
        try{
            Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return true;
        }catch(ExpiredJwtException e){
            logger.error("Token expirado");
        }catch (IllegalArgumentException e){
            logger.error("Token vacio");
        }catch (SignatureException e){
            logger.error("Fallo con la firma");
        }
        return false;
    }

}
