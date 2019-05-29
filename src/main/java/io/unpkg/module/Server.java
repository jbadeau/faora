package io.unpkg.module;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Server {

	private static final Logger logger = LoggerFactory.getLogger(Server.class);

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
