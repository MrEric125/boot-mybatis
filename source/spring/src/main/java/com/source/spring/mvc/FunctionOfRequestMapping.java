package com.source.spring.mvc;

import com.louis.common.common.HttpResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jun.liu
 * @since 2020/11/14 14:21
 * 主要问题：
 *  如何解析 httpUrl
 *  如何拼接各种url
 *  @see #requestBody()  如何解析 {@link org.springframework.web.bind.annotation.RequestBody}
 *
 *
 *  @see #responseBody() 如何解析 {@link org.springframework.web.bind.annotation.ResponseBody}
 *
 *
 */
@RestController
public class FunctionOfRequestMapping {

    private static final String SUCCESS = "success";


    /**
     * @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
     *
     *
     * @return
     */
    @RequestMapping("/testRequestMapping")
    public HttpResult testRequestMapping() {
        return HttpResult.ok();
    }



    public void requestBody() {

    }
    public void responseBody() {

    }

}
