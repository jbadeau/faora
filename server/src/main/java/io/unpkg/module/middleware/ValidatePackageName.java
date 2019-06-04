package io.unpkg.module.middleware;

import static io.unpkg.module.util.RequestAttributeMask.getPackageName;
import static io.unpkg.module.util.ValidateNpmPackageName.validateNpmPackageName;

import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ValidatePackageName extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(ValidatePackageName.class);

	private static final String hexValue = "(?)^[a-f0-9]+$";

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		if (req.getDispatcherType() == DispatcherType.REQUEST) {
			return validatePackageName(req, res);
		}
		return true;
	}

	protected boolean isHash(String value) {
		return value.length() == 32 && hexValue.matches(value);
	}

	/**
	 * Reject requests for invalid npm package names.
	 */
	private boolean validatePackageName(HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.info("validatePackageURL");

		if (isHash(getPackageName(req))) {
			res.sendError(HttpServletResponse.SC_FORBIDDEN,
					String.format("Invalid package name %s (cannot be a hash)", getPackageName(req)));
			return false;
		}

		List<String> errors = validateNpmPackageName(getPackageName(req)).getErrors();

		if (!errors.isEmpty()) {
			final String reason = String.join(", ", errors);

			res.sendError(HttpServletResponse.SC_FORBIDDEN,
					String.format("Invalid package name \"%s\" (%s)", getPackageName(req), reason));
			return false;
		}

		return true;
	}

}