package io.unpkg.module.middleware;

import static io.unpkg.module.util.ParsePackageURL.parsePackageURL;
import static io.unpkg.module.util.RequestAttributeMask.setFilename;
import static io.unpkg.module.util.RequestAttributeMask.setPackageName;
import static io.unpkg.module.util.RequestAttributeMask.setPackageSpec;
import static io.unpkg.module.util.RequestAttributeMask.setPackageVersion;
import static io.unpkg.module.util.RequestAttributeMask.setPathname;
import static io.unpkg.module.util.RequestAttributeMask.setQuery;
import static io.unpkg.module.util.RequestAttributeMask.setSearch;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.unpkg.module.UnpkgUrl;

public class ValidatePackageURL extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(ValidatePackageURL.class);

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		if (req.getDispatcherType() == DispatcherType.REQUEST) {
			return validatePackageURL(req, res);
		}
		return true;
	}

	private boolean validatePackageURL(HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.info("validatePackageURL");

		UnpkgUrl url = parsePackageURL(req);

		if (url == null) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN, String.format("Invalid URL: %s", url));
			return false;
		}

		setPackageName(req, url.getPackageName());
		setPackageVersion(req, url.getPackageVersion());
		setPackageSpec(req, String.format("%s@$%s", url, url.getPackageName(), url.getPackageVersion()));
		setPathname(req, url.getPathname());
		setFilename(req, url.getFilename());
		setSearch(req, url.getSearch());
		setQuery(req, url.getQuery());

		return true;
	}

}