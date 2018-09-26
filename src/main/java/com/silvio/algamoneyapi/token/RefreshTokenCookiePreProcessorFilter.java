package com.silvio.algamoneyapi.token;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		if ("/oauth/token".equalsIgnoreCase(httpServletRequest.getRequestURI())
				&& "refresh_token".equals(httpServletRequest.getParameter("grant_type"))
				&& httpServletRequest.getCookies() != null) {
			for (Cookie cookie : httpServletRequest.getCookies()) {
				if (cookie.getName().equals("refreshToken")) {
					String refreshToken = cookie.getValue();
					httpServletRequest = new MyServletRequestWrapper(httpServletRequest, refreshToken);
				}
			}
		}
		chain.doFilter(httpServletRequest, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	static class MyServletRequestWrapper extends HttpServletRequestWrapper {

		private String refreshToken;

		public MyServletRequestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;

		}

		@Override
		public Map<String, String[]> getParameterMap() {
			ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
			map.put("refresh_token", new String[] { refreshToken });
			map.setLocked(true);
			return map;

		}

	}

}
