package io.unpkg.module.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import io.unpkg.module.middleware.FetchPackage;
import io.unpkg.module.middleware.FindFile;
import io.unpkg.module.middleware.ServeFile;
import io.unpkg.module.middleware.VaidateQuery;
import io.unpkg.module.middleware.ValidatePackageName;
import io.unpkg.module.middleware.ValidatePackageURL;

@Component
public class MiddlewareConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(validatePackageURL());
		registry.addInterceptor(validatePackageName());
		registry.addInterceptor(vaidateQuery());
		registry.addInterceptor(fetchPackage());
		registry.addInterceptor(findFile());
		registry.addInterceptor(serveFile());
	}

	@Bean
	ValidatePackageURL validatePackageURL() {
		return new ValidatePackageURL();
	}

	@Bean
	ValidatePackageName validatePackageName() {
		return new ValidatePackageName();
	}

	@Bean
	VaidateQuery vaidateQuery() {
		return new VaidateQuery();
	}

	@Bean
	FetchPackage fetchPackage() {
		return new FetchPackage();
	}

	@Bean
	FindFile findFile() {
		return new FindFile();
	}

	@Bean
	ServeFile serveFile() {
		return new ServeFile();
	}

}
