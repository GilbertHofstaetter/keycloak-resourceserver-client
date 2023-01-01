package com.gho.client.conf.api;

import com.gho.client.clientApi.api.CompanyControllerApi;
import com.gho.client.clientApi.api.EmployeeControllerApi;
import com.gho.client.clientApi.invoker.auth.OAuth;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class ApiConfig {

    @Value("${spring.security.oauth2.resourceserver.basePath}")
    private String resourceServerBasePath; //=client id

    @Bean
    @RequestScope
    public AccessToken accessToken(OAuth2AuthorizedClientService clientService) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();
        String accessToken = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getHeader("Authorization");
        if (!StringUtils.isEmpty(accessToken))
            accessToken = accessToken.replace("Bearer ", "");

        if (StringUtils.isEmpty(accessToken) && authentication.getClass()
                .isAssignableFrom(OAuth2AuthenticationToken.class)) {
            OAuth2AuthenticationToken oauthToken =
                    (OAuth2AuthenticationToken) authentication;
            String clientRegistrationId =
                    oauthToken.getAuthorizedClientRegistrationId();

            OAuth2AuthorizedClient client = clientService.loadAuthorizedClient(
                    clientRegistrationId, oauthToken.getName());
            accessToken = client.getAccessToken().getTokenValue();

        }
        return new AccessToken(accessToken);
    }

    @Bean
    @RequestScope
    public ApiClient apiClient(AccessToken accessToken) {
        return new ApiClient(accessToken.getAccessToken());
    }

    @Bean
    @Primary
    @RequestScope
    @Qualifier("openApiClient")
    public com.gho.client.clientApi.invoker.ApiClient openApiClient(AccessToken accessToken) {
        com.gho.client.clientApi.invoker.ApiClient apiClient = new com.gho.client.clientApi.invoker.ApiClient();
        apiClient.setBasePath(resourceServerBasePath);
        OAuth oAuth = (OAuth) apiClient.getAuthentication("keycloak");
        oAuth.setAccessToken(accessToken.getAccessToken());
        return apiClient;
    }

    @Bean
    @RequestScope
    public CompanyControllerApi companyControllerApi(com.gho.client.clientApi.invoker.ApiClient openApiClient) {
        return new CompanyControllerApi(openApiClient);
    }

    @Bean
    @RequestScope
    public EmployeeControllerApi employeeControllerApi(com.gho.client.clientApi.invoker.ApiClient openApiClient) {
        return new EmployeeControllerApi(openApiClient);
    }
}
