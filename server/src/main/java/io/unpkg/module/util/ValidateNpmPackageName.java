package io.unpkg.module.util;

import static io.unpkg.module.util.URIComponentEncoder.encodeURIComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.unpkg.module.npm.NpmPackageNameValidationReport;

public class ValidateNpmPackageName {

	private static Pattern scopedPackagePattern = Pattern.compile("^(?:@([^/]+?)[/])?([^/]+?)$");

	// TODO add semver support like https://github.com/juliangruber/builtins
	private static List<String> builtins;

	private static List<String> blacklist;

	static {
		builtins = new ArrayList<>(2);
		builtins.add("assert");
		builtins.add("buffer");
		builtins.add("child_process");
		builtins.add("cluster");
		builtins.add("console");
		builtins.add("constants");
		builtins.add("crypto");
		builtins.add("dgram");
		builtins.add("dns");
		builtins.add("domain");
		builtins.add("events");
		builtins.add("fs");
		builtins.add("http");
		builtins.add("https");
		builtins.add("module");
		builtins.add("net");
		builtins.add("os");
		builtins.add("path");
		builtins.add("punycode");
		builtins.add("querystring");
		builtins.add("readline");
		builtins.add("repl");
		builtins.add("stream");
		builtins.add("string_decoder");
		builtins.add("sys");
		builtins.add("timers");
		builtins.add("tls");
		builtins.add("tty");
		builtins.add("url");
		builtins.add("util");
		builtins.add("vm");
		builtins.add("zlib");

		blacklist = new ArrayList<>(2);
		blacklist.add("node_modules");
		blacklist.add("favicon.ico");
	}

	private ValidateNpmPackageName() {
	}

	public static NpmPackageNameValidationReport validateNpmPackageName(final String name) {

		NpmPackageNameValidationReport report = new NpmPackageNameValidationReport();

		if (name == null) {
			report.addError("name cannot be null");
			return done(report);
		}

		if (name.length() == 0) {
			report.addError("name length must be greater than zero");
		}

		if (name.matches("^\\.")) {
			report.addError("name cannot start with a period");
		}

		if (name.matches("^_")) {
			report.addError("name cannot start with an underscore");
		}

		if (!name.trim().equals(name)) {
			report.addError("name cannot contain leading or trailing spaces");
		}

		// No funny business
		blacklist.forEach(blacklistedName -> {
			if (name.toLowerCase().contains(blacklistedName)) {
				report.addError(blacklistedName + " is a blacklisted name");
			}
		});

		// Generate warnings for stuff that used to be allowed

		// core module names like http, events, util, etc
		builtins.forEach(builtin -> {
			if (name.toLowerCase().contains(builtin)) {
				report.addWarning(builtin + " is a core module name");
			}
		});

		// really-long-package-names-------------------------------such--length-----many---wow
		// the
		// thisisareallyreallylongpackagenameitshouldpublishdowenowhavealimittothelengthofpackagenames-poch.
		if (name.length() > 214) {
			report.addWarning("name can no longer contain more than 214 characters");
		}

		// mIxeD CaSe nAMEs
		if (!name.toLowerCase().equals(name)) {
			report.addWarning("name can no longer contain capital letters");
		}

		if (name.matches("[~'!()*]")) {
			report.addWarning("name can no longer contain special characters (\"~\'!()*\")");
		}

		if (encodeURIComponent(name).equals(name)) {
			// Maybe it's a scoped package name, like @user/package
			Matcher matcher = scopedPackagePattern.matcher(name);
			if (matcher.matches()) {
				String user = matcher.group();
				String pkg = matcher.group(2);
				if (encodeURIComponent(user).equals(user) && encodeURIComponent(pkg).equals(pkg)) {
					return done(report);
				}
			}

			report.addError("name can only contain URL-friendly characters");
		}

		return done(report);
	}

	private static NpmPackageNameValidationReport done(NpmPackageNameValidationReport report) {
		report.setValidForNewPackages(report.getErrors().isEmpty() && report.getWarnings().isEmpty());
		report.setValidForOldPackages(report.getErrors().isEmpty());
		return report;
	}

}
