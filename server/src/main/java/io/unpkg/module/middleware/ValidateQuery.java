package io.unpkg.module.middleware;

import static io.unpkg.module.util.CreateSearch.createSearch;
import static io.unpkg.module.util.RequestAttributeMask.getQuery;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.MultiValueMap;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class ValidateQuery extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(ValidateQuery.class);

	private static Map<String, Boolean> knownQueryParams;

	static {
		knownQueryParams = new HashMap<>(3);
		knownQueryParams.put("main", true); // Deprecated, see #63
		knownQueryParams.put("meta", true); // Deprecated
		knownQueryParams.put("module", true); // Deprecated
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		if (req.getDispatcherType() == DispatcherType.REQUEST) {
			return validateQuery(req, res);
		}
		return true;
	}

	private Boolean isKnownQueryParam(String param) {
		return knownQueryParams.containsKey(param);
	}

	private Map<String, String> sanitizeQuery(MultiValueMap<String, String> originalQuery) {
		final Map<String, String> query = new HashMap<>();
		
		originalQuery.keySet().stream().forEach(key -> {
			if (isKnownQueryParam(key)) {
				query.put(key, originalQuery.getFirst(key));
			}
		});
		
		return query;
	}

	protected boolean validateQuery(HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.info("validateQuery");

		if (getQuery(req).keySet().stream().anyMatch(key -> !isKnownQueryParam(key))) {
			ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromContextPath(req);
			createSearch(builder, sanitizeQuery(getQuery(req)));
			res.sendRedirect(builder.toString());
			return false;
		}

		return true;
	}

}