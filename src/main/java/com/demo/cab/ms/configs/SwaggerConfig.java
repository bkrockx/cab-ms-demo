package com.demo.cab.ms.configs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Parameter;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

  @Value("${swagger.host.url}")
  private String swaggerHost;

  @Bean
  public Docket api() {
    List<Parameter> aParameters = new ArrayList<>();
    return new Docket(DocumentationType.SWAGGER_2)
        .host(swaggerHost)
        .globalOperationParameters(aParameters)
        .securitySchemes(Arrays.asList(new ApiKey("Bearer", "user-jwt-token", "header")))
        .securityContexts(
            Collections.singletonList(SecurityContext.builder().securityReferences(defaultAuth()).forPaths(
                PathSelectors.regex("/.*")).build()))
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }

  private List<SecurityReference> defaultAuth() {
    final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    final AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
    return Collections.singletonList(new SecurityReference("Bearer", authorizationScopes));
  }
}

