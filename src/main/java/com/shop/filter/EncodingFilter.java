package com.shop.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;


// Filter để set UTF-8 encoding cho tất cả request/response

@WebFilter(filterName = "EncodingFilter", urlPatterns = {"/*"})
public class EncodingFilter implements Filter {

    private static final String ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) {
        System.out.println(" EncodingFilter initialized - Encoding: " + ENCODING);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Set request encoding (cho form data, parameters)
        if (request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(ENCODING);
        }

        // Set response encoding (cho HTML, JSON output)
        response.setCharacterEncoding(ENCODING);

        // Continue filter chain
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println(" EncodingFilter destroyed");
    }
}