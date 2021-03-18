package com.cisco.services.api_automation.filters;

import com.cisco.services.api_automation.exception.ErrorResponse;
import com.cisco.services.api_automation.exception.RestResponseStatusExceptionResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class ExceptionHandlerFilter implements Filter {

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		try {
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			Throwable cause = (e instanceof ServletException && e.getCause() != null) ? e.getCause() : e;
			ErrorResponse errorResponse = RestResponseStatusExceptionResolver.createErrorResponse(cause);

			response.setStatus(errorResponse.getStatus());
			response.setContentType("application/json");
			response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
		}
	}
}
