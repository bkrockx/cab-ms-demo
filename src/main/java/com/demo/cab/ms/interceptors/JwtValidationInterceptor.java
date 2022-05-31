package com.demo.cab.ms.interceptors;

import com.demo.cab.ms.interceptors.annotations.JwtSecure;
import com.demo.cab.ms.utils.CommonUtil;
import java.io.IOException;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class JwtValidationInterceptor implements HandlerInterceptor {

  private static final String ID = "id";
  private static final String HS_512_ALGORITHM = "HS512";

  @Autowired
  private CommonUtil commonUtil;

  @Value("${jwt.secret}")
  private String secret;

  @Value("${jwt.token}")
  private String resellerTokenHeader;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
    if (handler instanceof HandlerMethod) {
      HandlerMethod handlerMethod = (HandlerMethod) handler;
      if (!handlerMethod.getMethod().isAnnotationPresent(JwtSecure.class)) {
        return true;
      }

      LOGGER.info("Headers passed in the request are : {}", Collections.list(request.getHeaderNames()));
      String jwtToken = commonUtil.getTokenString(request, resellerTokenHeader);
      LOGGER.info("Received jwt token {} from headers", jwtToken);
      if (StringUtils.isEmpty(jwtToken)) {
        LOGGER.info("no jwt token passed in the headers and the header name is {}", resellerTokenHeader);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No JWT token in the headers");
        return false;
      }
      return commonUtil.verifyJwtToken(response, jwtToken);
    }
    return true;

  }
}

