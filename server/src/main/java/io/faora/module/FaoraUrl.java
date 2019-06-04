package io.faora.module;

import org.springframework.util.MultiValueMap;

/**
 * If the URL is /@scope/name@version/file.js?main=browser:
 * 
 * @author t229545
 */
public class FaoraUrl {

	/**
	 * /@scope/name@version/path.js
	 */
	private String pathname;

	/**
	 * ?main=browser
	 */
	private String search;

	/**
	 * { main: 'browser' }
	 */
	private MultiValueMap<String, String> query;

	/**
	 * @scope/name
	 */
	private String packageName;

	/**
	 * version
	 */
	private String packageVersion;

	/**
	 * /file.js
	 */
	private String filename;

	public FaoraUrl(String pathname, String search, MultiValueMap<String, String> query, String packageName,
			String packageVersion, String filename) {
		super();
		this.pathname = pathname;
		this.search = search;
		this.query = query;
		this.packageName = packageName;
		this.packageVersion = packageVersion;
		this.filename = filename;
	}

	public String getPathname() {
		return pathname;
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public MultiValueMap<String, String> getQuery() {
		return query;
	}

	public void setQuery(MultiValueMap<String, String> query) {
		this.query = query;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public String getPackageVersion() {
		return packageVersion;
	}

	public void setPackageVersion(String packageVersion) {
		this.packageVersion = packageVersion;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

}