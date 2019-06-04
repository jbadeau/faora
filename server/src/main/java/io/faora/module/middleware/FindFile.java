package io.faora.module.middleware;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class FindFile extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(FindFile.class);

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		if (req.getDispatcherType() == DispatcherType.REQUEST) {
			return findFile(req, res);
		}
		return true;
	}

	private boolean findFile(HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.info("findFile");
		return true;
	}

}