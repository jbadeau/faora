package io.faora.module;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import io.faora.module.action.ServeFile;
import io.faora.module.action.ServeMetadata;
import io.faora.module.action.ServeModule;
import io.faora.module.middleware.FetchPackage;
import io.faora.module.middleware.FindFile;
import io.faora.module.middleware.ValidatePackageName;
import io.faora.module.middleware.ValidatePackageURL;
import io.faora.module.middleware.ValidateQuery;

@Component
public class FaoraConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(validatePackageURL());
		registry.addInterceptor(validatePackageName());
		registry.addInterceptor(vaidateQuery());
		registry.addInterceptor(fetchPackage());
		registry.addInterceptor(findFile());
		registry.addInterceptor(serveMetadata()).addPathPatterns("/_metadata");
		registry.addInterceptor(serveModule()).addPathPatterns("/_module");
		registry.addInterceptor(serveFile()).addPathPatterns("*");
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
	ValidateQuery vaidateQuery() {
		return new ValidateQuery();
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
	ServeMetadata serveMetadata() {
		return new ServeMetadata();
	}

	@Bean
	ServeModule serveModule() {
		return new ServeModule();
	}

	@Bean
	ServeFile serveFile() {
		return new ServeFile();
	}

}