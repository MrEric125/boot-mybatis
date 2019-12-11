package com.oauth2.provider.security.provider.impl;

import com.oauth2.provider.security.provider.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author louis
 * <p>
 * Date: 2019/11/19
 * Description:
 */
@Component
public class AuthorizeConfigProviderImpl implements AuthorizeConfigProvider {


    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config
                .anyRequest()
                .access("@permissionService.hasPermission(authentication,request)");
        return true;
    }
}
