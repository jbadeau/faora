package io.unpkg.module.util;

import java.io.UnsupportedEncodingException;

public class EncodeURIComponent {

	public static final String ALLOWED_CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789-_.!~*'()";

	private EncodeURIComponent() {
	}

	public static String encodeURIComponent(String input) {
		return encodeURIComponent(input, "UTF-8");
	}

	public static String encodeURIComponent(String input, String charset) {
		if (input.isEmpty()) {
			return input;
		}

		int l = input.length();
		StringBuilder o = new StringBuilder(l * 3);

		try {
			String e = "";

			for (int i = 0; i < l; i++) {
				e = input.substring(i, i + 1);

				if (ALLOWED_CHARS.indexOf(e) == -1) {
					byte[] b = e.getBytes(charset);
					o.append(getHex(b));
					continue;
				}

				o.append(e);
			}

			return o.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return input;
	}

	private static String getHex(byte buf[]) {
		StringBuilder o = new StringBuilder(buf.length * 3);
		int n = 0;

		for (int i = 0; i < buf.length; i++) {
			n = (int) buf[i] & 0xff;
			o.append("%");

			if (n < 0x10) {
				o.append("0");
			}

			o.append(Long.toString(n, 16).toUpperCase());
		}

		return o.toString();
	}

}
