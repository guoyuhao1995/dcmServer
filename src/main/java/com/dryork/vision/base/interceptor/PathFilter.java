package com.dryork.vision.base.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PathFilter implements Filter {

	private String homePage;

	@Override
	public void init(FilterConfig config) throws ServletException {
		homePage = config.getInitParameter("homePage");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String path = req.getContextPath();
		String ctx = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + path;
		request.setAttribute("ctx", ctx);
		String pathInfo = req.getRequestURI();

		if (pathInfo == null || pathInfo.equals(path + "/") || pathInfo.equals(path) || "".equals(pathInfo)) {
			resp.sendRedirect(ctx + homePage);
			return;
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}

}
