package com.machinetest.product.analyse.java.ms.config;

import com.machinetest.product.analyse.java.ms.config.properties.CustomProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class IpFilter extends OncePerRequestFilter {

    private final CustomProperties properties;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String remoteIp = request.getRemoteAddr();
        log.info("ip {} trying to access",remoteIp);

        if (!properties.getAllowedIps().contains(remoteIp)) {
            log.warn("access denied for ip {} ",remoteIp);
            response.sendError(HttpStatus.FORBIDDEN.value(), "Access denied from IP: " + remoteIp);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
