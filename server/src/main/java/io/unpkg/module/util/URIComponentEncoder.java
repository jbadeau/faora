package io.unpkg.module.util;

import org.springframework.web.util.UriUtils;

public class URIComponentEncoder {

	private URIComponentEncoder() {
	}

	public static String encodeURIComponent(String source) {
		try {
			return UriUtils.encode(source, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String decodeURIComponent(String source) {
		try {
			return UriUtils.decode(source, "UTF-8");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}