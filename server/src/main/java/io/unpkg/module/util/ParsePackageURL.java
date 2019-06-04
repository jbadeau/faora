package io.unpkg.module.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriUtils;

import io.unpkg.module.UnpkgUrl;

public class ParsePackageURL {

	static final Pattern packageURLFormat = Pattern.compile("^/((?:@[^/@]+/)?[^/@]+)(?:@([^/]+))?(/.*)?$");

	private ParsePackageURL() {
	}

	public static UnpkgUrl parsePackageURL(UriComponents originalURL) {

		String pathname = originalURL.getPath();
		String search = originalURL.getQuery();
		MultiValueMap<String, String> query = originalURL.getQueryParams();

		try {
			pathname = UriUtils.decode(pathname, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}

		Matcher matcher = packageURLFormat.matcher(pathname);

		// Disallow invalid URL formats.
		if (!matcher.matches()) {
			return null;
		}

		String packageName = matcher.group(1);
		String packageVersion = matcher.group(2) != null ? matcher.group(2) : "latest";
		String filename = matcher.group(3) != null ? matcher.group(3) : "";

		return new UnpkgUrl(
				// If the URL is /@scope/name@version/file.js?main=browser:
				pathname, // /@scope/name@version/path.js
				search != null ? search : "", // ?main=browser
				query, // { main: 'browser' }
				packageName, // @scope/name
				packageVersion, // version
				filename // /file.js
		);
	}
}
