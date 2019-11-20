package com.louis.controller;

import com.google.common.collect.Maps;
import com.louis.common.common.WrapMapper;
import com.louis.common.common.Wrapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author louis
 * <p>
 * Date: 2019/11/20
 * Description:
 */
@RestController
@RequestMapping("/api")
public class ApiController {
    @RequestMapping("/authMessage")
    public Wrapper userMessage(Authentication user) {
        Map<String, Object> userInfo = Maps.newHashMap();
        userInfo.put("user", user.getPrincipal());
        userInfo.put("authorities", AuthorityUtils.authorityListToSet(user.getAuthorities()));
        return WrapMapper.wrap( userInfo);

    }

    @RequestMapping("/userMessage/{roleName}")
    public Wrapper request(@PathVariable String roleName, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> userInfo = Maps.newHashMap();
        userInfo.put("remoteUser", request.getRemoteUser());
        userInfo.put("principal", request.getUserPrincipal());
        userInfo.put("userInRole", request.isUserInRole(roleName));

        return WrapMapper.ok(userInfo);
    }

}