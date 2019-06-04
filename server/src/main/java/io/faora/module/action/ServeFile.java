package io.faora.module.action;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ServeFile extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(ServeFile.class);

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		if (req.getDispatcherType() == DispatcherType.REQUEST) {
			return serveFile(req, res);
		}
		return true;
	}

	private boolean serveFile(HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.info("serveFile");
		return true;
	}

}