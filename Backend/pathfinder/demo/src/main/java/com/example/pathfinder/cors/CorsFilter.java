package com.example.pathfinder.cors;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter
{
    String frontend_url = "*";
    String accessControlAllowHeaders[] = {
            "Content-Type",
            "Access-Control-Allow-Headers",
            "Authorization",
            "X-Requested-With",
            "Origen",
            "Access-Control-Allow-Origin"};

    String accessControlAllowMethods[] = {
            "POST",
            "GET",
            "DELETE",
            "POST",
            "PUT",
            "OPTIONS"
    };

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) {
        HttpServletRequest request   = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;

        response.setHeader("Access-Control-Allow-Origin", frontend_url);
        response.setHeader("Access-Control-Allow-Credentials", "true");
        AtomicReference<String> allowedHeaders = new AtomicReference<>("");
        AtomicReference<String> allowedMethods = new AtomicReference<>("");

        //concat all elements into a single string
        Arrays.stream(accessControlAllowHeaders).reduce((current, next) -> current + ',' + next)
            .ifPresent(allowedHeaders::set);

        Arrays.stream(accessControlAllowMethods).reduce((current, next) -> current + ',' + next)
            .ifPresent(allowedMethods::set);

        response.setHeader("Access-Control-Allow-Methods", allowedMethods.get());
        response.setHeader("Access-Control-Allow-Headers", allowedHeaders.get());
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setStatus(HttpServletResponse.SC_OK);

        if ("OPTIONS".equals(request.getMethod())) {
            //&& url.equals(request.getHeader("Origen")
            //response.setHeader("Access-Control-Max-Age", "3600");
            //response.setStatus(HttpServletResponse.SC_OK);
        } else {
            try {
                chain.doFilter(req, res);
            } catch (IOException e) {
            } catch (ServletException e) {
                e.printStackTrace();
            }
        }
    }

}