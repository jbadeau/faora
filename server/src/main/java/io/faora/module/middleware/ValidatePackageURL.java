package io.faora.module.middleware;

import static io.faora.module.util.ParsePackageURL.parsePackageURL;
import static io.faora.module.util.RequestAttributeMask.setFilename;
import static io.faora.module.util.RequestAttributeMask.setPackageName;
import static io.faora.module.util.RequestAttributeMask.setPackageSpec;
import static io.faora.module.util.RequestAttributeMask.setPackageVersion;
import static io.faora.module.util.RequestAttributeMask.setPathname;
import static io.faora.module.util.RequestAttributeMask.setQuery;
import static io.faora.module.util.RequestAttributeMask.setSearch;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import io.faora.module.FaoraUrl;

/**
 * Parse the URL and add various properties to the request object to do with the
 * package/file being requested. Reject invalid URLs.
 */
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

		UriComponents uri = ServletUriComponentsBuilder.fromRequest(req).build();
		final FaoraUrl url = parsePackageURL(uri);

		if (url == null) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN, String.format("Invalid URL: %s", uri.toUriString()));
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