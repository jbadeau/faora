package io.unpkg.module.util;

import javax.servlet.ServletRequest;

import org.springframework.util.MultiValueMap;

import io.unpkg.module.npm.NpmInfo;
import io.unpkg.module.npm.NpmVersion;

public class RequestAttributeMask {

	private static String PACKAGE_NAME = "packageName";
	private static String PACKAGE_VERSION = "packageVersion";
	private static String PACKAGE_SPEC = "packageSpec";
	private static String PATH_NAME = "pathname";
	private static String FILE_NAME = "filename";
	private static String SEARCH = "search";
	private static String QUERY = "query";
	private static String PACKAGE_INFO = "packageInfo ";
	private static String PACKAGE_CONFIG = "packageConfig ";

	private RequestAttributeMask() {
	}

	public static String getPackageName(ServletRequest req) {
		return getStringAttribute(req, PACKAGE_NAME);
	}

	public static void setPackageName(ServletRequest req, String value) {
		req.setAttribute(PACKAGE_NAME, value);
	}

	public static String getPackageVersion(ServletRequest req) {
		return getStringAttribute(req, PACKAGE_VERSION);
	}

	public static void setPackageVersion(ServletRequest req, String value) {
		req.setAttribute(PACKAGE_VERSION, value);
	}

	public static String getPackageSpec(ServletRequest req) {
		return getStringAttribute(req, PACKAGE_SPEC);
	}

	public static void setPackageSpec(ServletRequest req, String value) {
		req.setAttribute(PACKAGE_SPEC, value);
	}

	public static String getPathname(ServletRequest req) {
		return getStringAttribute(req, PATH_NAME);
	}

	public static void setPathname(ServletRequest req, String value) {
		req.setAttribute(PATH_NAME, value);
	}

	public static String getFilename(ServletRequest req) {
		return getStringAttribute(req, FILE_NAME);
	}

	public static void setFilename(ServletRequest req, String value) {
		req.setAttribute(FILE_NAME, value);
	}

	public static String getSearch(ServletRequest req) {
		return getStringAttribute(req, SEARCH);
	}

	public static void setSearch(ServletRequest req, String value) {
		req.setAttribute(SEARCH, value);
	}

	public static MultiValueMap<String, String> getQuery(ServletRequest req) {
		return (MultiValueMap<String, String>) getObjectAttsribute(req, QUERY);
	}

	public static void setQuery(ServletRequest req, MultiValueMap<String, String> value) {
		req.setAttribute(QUERY, value);
	}

	protected static String getStringAttribute(ServletRequest req, String name) {
		return (String) req.getAttribute(name);
	}

	public static NpmInfo getPackageInfo(ServletRequest req) {
		return (NpmInfo) getObjectAttsribute(req, PACKAGE_INFO);
	}

	public static void setPackageInfo(ServletRequest req, NpmInfo value) {
		req.setAttribute(PACKAGE_INFO, value);
	}

	public static NpmVersion getPackageConfig(ServletRequest req) {
		return (NpmVersion) getObjectAttsribute(req, PACKAGE_CONFIG);
	}

	public static void setPackageConfig(ServletRequest req, NpmVersion value) {
		req.setAttribute(PACKAGE_CONFIG, value);
	}

	protected static Object getObjectAttsribute(ServletRequest req, String name) {
		return req.getAttribute(name);
	}
	

}
