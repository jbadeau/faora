package io.unpkg.module.middleware;

import static io.unpkg.module.util.AppendSearch.appendSearch;
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

public class VaidateQuery extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(VaidateQuery.class);

	private static Map<String, Boolean> knownQueryParams;

	static {
		knownQueryParams = new HashMap<>(3);
		knownQueryParams.put("main", true);
		knownQueryParams.put("meta", true);
		knownQueryParams.put("module", true);
	}

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		if (req.getDispatcherType() == DispatcherType.REQUEST) {
			return validateQuery(req, res);
		}
		return true;
	}

	protected boolean validateQuery(HttpServletRequest req, HttpServletResponse res) throws Exception {
		logger.info("validateQuery");

		if (getQuery(req).keySet().stream().anyMatch(key -> !isKnownQueryParam(key))) {
			ServletUriComponentsBuilder builder = ServletUriComponentsBuilder.fromContextPath(req);
			appendSearch(builder, sanitizeQuery(getQuery(req)));
			res.sendRedirect(builder.toString());
			return false;
		}

		return true;
	}

	private Boolean isKnownQueryParam(String param) {
		return knownQueryParams.containsKey(param);
	}

	private Map<String, String> sanitizeQuery(MultiValueMap<String, String> originalQuery) {
		Map<String, String> query = new HashMap<>();
		originalQuery.keySet().stream().forEach(key -> {
			if (isKnownQueryParam(key)) {
				query.put(key, originalQuery.getFirst(key));
			}
		});
		return query;

	}

}