package io.faora.module;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class FaoraServer {

	private static final Logger logger = LoggerFactory.getLogger(FaoraServer.class);

	/**
	 * Special startup request from App Engine
	 * https://cloud.google.com/appengine/docs/standard/nodejs/how-instances-are-managed
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/_ah/start", method = RequestMethod.GET, produces = "application/json")
	public String appEngineStart() {
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/html")
	public String serveMainPage() {
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/api/public-key", method = RequestMethod.GET, produces = "application/json")
	public String servePublicKey() {
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/api/stats", method = RequestMethod.GET, produces = "application/json")
	public String serveStats() {
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "*", method = RequestMethod.GET, produces = "application/javascript")
	public Callable<String> serveFile() {

		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				return "function foo() {alrt(1);}";
			}
		};

		return callable;
	}

}