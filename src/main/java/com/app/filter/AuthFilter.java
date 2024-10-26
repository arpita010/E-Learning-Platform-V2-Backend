package com.app.filter;

import com.app.exceptions.AuthException;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthFilter implements Filter {
  private final AuthFilterService authFilterService;

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException, AuthException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    String requestURI = request.getRequestURI();
    String method = request.getMethod();
    //    log.info("Request Method : {}", method);
    String origin = request.getHeader("origin");
    //    log.info("Request Origin : {}", origin);
    log.info("Request [URI] : {}", requestURI);
    try {
      setRequiredHeaders(response);
      if (!method.equals("OPTIONS")) {
        validateToken(request, requestURI, method, origin);
      }
      filterChain.doFilter(servletRequest, servletResponse);
      //      log.info("Authorization Successful");
    } catch (Exception e) {
      //      log.info("UNAUTHORIZED request for URL {}", requestURI);
      //      log.error("Error : {}", e.getMessage());
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      setRequiredHeaders(response);
      throw new AuthException("Unauthorized");
    } finally {
      MDC.clear();
    }
  }

  private void setRequiredHeaders(HttpServletResponse response) {
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
    response.setHeader(
        "Access-Control-Allow-Headers",
        "Content-Type, Authorization, access-control-allow-origin, content-type, authorization, access-control-allow-headers");
    response.setHeader("Access-Control-Allow-Credentials", "true");
    response.setContentType("application/json");
  }

  /* token = authUserEmail|authUserId|userRole|randomAuthToken */

  private void validateToken(HttpServletRequest request, String url, String method, String origin)
      throws Exception {
    if (UnfilteredApi.hasPath(url)) return;
    Map<String, String> headers = getHeaders(request);
    if (!authorizationHeaderPresent(headers))
      throw new AuthException("Missing Authorization Header");
    String authorizationToken = getAuthorizationToken(headers);
    String[] parts = authFilterService.parseAuthToken(authorizationToken);
    String role = parts[2];
    //    authFilterService.doRoleBasedAuthentication(url, method, role);
    authFilterService.validateToken(parts, authorizationToken);
  }

  private String getAuthorizationToken(Map<String, String> headers) throws Exception {
    if (headers.containsKey("authorization")) return headers.get("authorization");
    if (headers.containsKey("Authorization")) return headers.get("Authorization");
    throw new AuthException("Missing Authorization Header");
  }

  private boolean authorizationHeaderPresent(Map<String, String> headers) {
    log.info("Auth Header : {}", headers.get("authorization"));
    return headers.containsKey("authorization") || headers.containsKey("Authorization");
  }

  private Map<String, String> getHeaders(HttpServletRequest request) {
    Map<String, String> headers = new HashMap<>();
    Enumeration<String> headerNames = request.getHeaderNames();
    headerNames.asIterator().forEachRemaining(h -> headers.put(h, request.getHeader(h)));
    //    log.info("Headers : {}", headers);
    return headers;
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    Filter.super.init(filterConfig);
  }

  @Override
  public void destroy() {
    Filter.super.destroy();
  }
}
