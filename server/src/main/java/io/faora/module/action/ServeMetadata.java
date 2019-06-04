package io.faora.module.action;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ServeMetadata extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(ServeMetadata.class);

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		if (req.getDispatcherType() == DispatcherType.REQUEST) {
			return serveMetadata(req, res);
		}
		return true;
	}

	private boolean serveMetadata(HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.info("serveMetadata");
		return true;
	}

}