package io.unpkg.module.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponents;

import io.unpkg.module.UnpkgUrl;

public class ParsePackageURL {

	static Pattern packageURLFormat = Pattern.compile("^/((?:@[^/@]+/)?[^/@]+)(?:@([^/]+))?(/.*)?$");

	private ParsePackageURL() {
	}
	
	public static UnpkgUrl parsePackageURL(HttpServletRequest req) {

		UriComponents uric = ServletUriComponentsBuilder.fromRequest(req).build();

		String pathname;
		String search;
		MultiValueMap<String, String> query;

		pathname = uric.getPath();
		search = uric.getQuery();
		query = uric.getQueryParams();

		Matcher matcher = packageURLFormat.matcher(pathname);

		if (!matcher.matches()) {
			return null;
		}

		String packageName = matcher.group(1);
		String packageVersion = matcher.group(2) != null ? matcher.group(2) : "latest";
		String filename = matcher.group(3) != null ? matcher.group(3) : "";

		return new UnpkgUrl(packageName, search, query, packageName, packageVersion, filename);
	}
}
