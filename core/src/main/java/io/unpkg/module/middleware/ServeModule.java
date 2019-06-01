package io.unpkg.module.middleware;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ServeModule extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(VaidateQuery.class);

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		if (req.getDispatcherType() == DispatcherType.REQUEST) {
			return serveModule(req, res);
		}
		return true;
	}

	private boolean serveModule(HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.info("serveModule");
		return true;
	}

}