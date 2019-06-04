package io.unpkg.module.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.unpkg.module.npm.NpmInfo;
import io.unpkg.module.npm.NpmRegistry;

@Component
public class GetNpmPackageInfo {

	@Autowired
	private NpmRegistry registry;

	public GetNpmPackageInfo() {
	}

	public NpmInfo getNpmPackageInfo(String packageName) {
		return registry.getPackageInfo(packageName);
	}

}