package io.faora.module.middleware;

import static io.faora.module.util.RequestAttributeMask.getFilename;
import static io.faora.module.util.RequestAttributeMask.getPackageConfig;
import static io.faora.module.util.RequestAttributeMask.getPackageInfo;
import static io.faora.module.util.RequestAttributeMask.getPackageName;
import static io.faora.module.util.RequestAttributeMask.getPackageVersion;
import static io.faora.module.util.RequestAttributeMask.setPackageConfig;
import static io.faora.module.util.RequestAttributeMask.setPackageInfo;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import io.faora.module.npm.NpmInfo;
import io.faora.module.util.GetNpmPackageInfo;

public class FetchPackage extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(FetchPackage.class);

	@Autowired
	GetNpmPackageInfo getNpmPackageInfo;

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		if (req.getDispatcherType() == DispatcherType.REQUEST) {
			return fetchPackage(req, res);
		}
		return true;
	}

	private boolean fetchPackage(HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.info("fetchPackage");

		String packageName = getPackageName(req);

		NpmInfo info = getNpmPackageInfo.getNpmPackageInfo(packageName);

		if (info == null || info.getVersions().isEmpty()) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND, String.format("Cannot find package %s", packageName));
			return false;
		}

		setPackageInfo(req, info);
		setPackageConfig(req, info.getVersion(getPackageVersion(req)));

		if (getPackageConfig(req) != null) {
			// Redirect to a fully-resolved version.
			if (getPackageInfo(req).containsDistTag(getPackageVersion(req))) {
				tagRedirect(req, res);
				return false;
			} else {
				semverRedirect(req, res);
				return false;
			}
		}

		if (getFilename(req) == null) {
			filenameRedirect(req, res);
			return false;
		}

		return true;

	}

	private void tagRedirect(HttpServletRequest req, HttpServletResponse res) {
		logger.info("tagRedirect");
	}

	private void semverRedirect(HttpServletRequest req, HttpServletResponse res) {
		logger.info("semverRedirect");
	}

	private void filenameRedirect(HttpServletRequest req, HttpServletResponse res) {
		logger.info("filenameRedirect");
	}

}