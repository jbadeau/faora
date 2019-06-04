package io.faora.module.util;

import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

public class CreateSearch {

	private CreateSearch() {
	}

	public static void createSearch(UriComponentsBuilder builder, Map<String, String> query) {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		for (Entry<String, String> entry : query.entrySet()) {
			params.add(entry.getKey(), entry.getValue());
		}
		builder.queryParams(params);
	}

}