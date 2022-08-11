package com.demo.cab.ms.utils;

import static java.util.Collections.emptyList;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TimeZone;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CommonUtil {

  private static final String ID = "id";
  private static final String HS_512_ALGORITHM = "HS512";

  @Value("${jwt.secret}")
  private String secret;

  private SimpleDateFormat sdf ;
  @PostConstruct
  public void init(){
    sdf =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

  }

  public boolean verifyJwtToken(HttpServletResponse response, String jwtToken) throws IOException {
    try {
      Algorithm algorithm = Algorithm.HMAC512(secret);

      DecodedJWT decodedToken = JWT.decode(jwtToken);
      if (decodedToken.getAlgorithm().equals(HS_512_ALGORITHM) && decodedToken.getClaims() != null && decodedToken.getClaims().containsKey(ID)) {

        Claim idClaim = decodedToken.getClaim(ID);
        if (idClaim.isNull()) {
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Id claim is null value");
          return false;
        }

        JWTVerifier verifier = getJwtVerifier(algorithm, idClaim.asInt());

        DecodedJWT verifiedJwt = verifier.verify(jwtToken);

        if (verifiedJwt == null) {
          response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
          return false;
        }
        Map<String, Claim> claimMap = decodedToken.getClaims();
        Authentication authentication = new UsernamePasswordAuthenticationToken(new JWTPrincipal(claimMap), null, emptyList());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return true;

      } else {
        LOGGER.debug("jwt token is not passed with correct algorithm or it does not have id claim");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
        return false;
      }

    } catch (JWTVerificationException e) {
      LOGGER.error("Error while JWT validation ", e);
      LOGGER.error("Using jwt secret key {} for decoding ", secret);
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT");
      return false;
    }
  }

  private JWTVerifier getJwtVerifier(Algorithm algorithm, int id) {
    return JWT.require(algorithm).
        withClaim(ID, id).
        build();
  }

  public String getTokenString(HttpServletRequest request, String tokenHeader){
    return request.getHeader(tokenHeader);
  }

  public boolean verifyJwtToken(String jwtToken, long validHours) throws Exception {
    try {
      Algorithm algorithm = Algorithm.HMAC512(secret);

      DecodedJWT decodedToken = JWT.decode(jwtToken);
      if (decodedToken.getAlgorithm().equals(HS_512_ALGORITHM) && decodedToken.getClaims() != null && decodedToken.getClaims().containsKey(ID)) {

        Claim idClaim = decodedToken.getClaim(ID);
        if (idClaim.isNull()) {
          throw new Exception("Invalid token");
        }

        JWTVerifier verifier = getJwtVerifier(algorithm, idClaim.asInt());

        DecodedJWT verifiedJwt = verifier.verify(jwtToken);

        if (verifiedJwt == null) {
          throw new Exception("Invalid token");
        }
        if(( sdf.parse(decodedToken.getClaim("time_stamp").asString()).getTime() +validHours)< new Date().getTime()){
          throw new Exception("Token expired");
        }

        return true;

      } else {
        LOGGER.debug("jwt token is not passed with correct algorithm or it does not have id claim");
        throw new Exception("Invalid token");
      }

    } catch (JWTVerificationException e) {
      LOGGER.error("Error while JWT validation ", e);
      LOGGER.error("Using jwt secret key {} for decoding ", secret);
      throw new Exception("Invalid token");
    }
  }
}