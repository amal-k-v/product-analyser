package com.machinetest.product.analyse.java.ms.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;

@Controller
public class SwaggerRedirectController {

    @GetMapping("/product/analyse/v1/swagger-ui/user")
    public void redirectUserSwagger(HttpServletResponse response) throws IOException {
        response.sendRedirect("/product/analyse/v1/swagger-ui/index.html?urls.primaryName=public-config");
    }

    @GetMapping("/product/analyse/v1/swagger-ui/admin")
    public void redirectAdminSwagger(HttpServletResponse response) throws IOException {
        response.sendRedirect("/product/analyse/v1/swagger-ui/index.html?urls.primaryName=admin-config");
    }
}
