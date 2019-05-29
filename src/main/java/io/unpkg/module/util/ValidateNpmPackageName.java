package io.unpkg.module.util;

import static io.unpkg.module.util.EncodeURIComponent.encodeURIComponent;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.unpkg.npm.NpmPackageNameValidationReport;

public class ValidateNpmPackageName {

	private static Pattern scopedPackagePattern = Pattern.compile("^(?:@([^/]+?)[/])?([^/]+?)$");

	private static List<String> blacklist;

	// TODO add semver support like https://github.com/juliangruber/builtins
	private static List<String> builtins;

	static {
		blacklist = new ArrayList<>(2);
		blacklist.add("node_modules");
		blacklist.add("favicon.ico");

		builtins = new ArrayList<>(2);
		blacklist.add("assert");
		blacklist.add("buffer");
		blacklist.add("child_process");
		blacklist.add("cluster");
		blacklist.add("console");
		blacklist.add("constants");
		blacklist.add("crypto");
		blacklist.add("dgram");
		blacklist.add("dns");
		blacklist.add("domain");
		blacklist.add("events");
		blacklist.add("fs");
		blacklist.add("http");
		blacklist.add("https");
		blacklist.add("module");
		blacklist.add("net");
		blacklist.add("os");
		blacklist.add("path");
		blacklist.add("punycode");
		blacklist.add("querystring");
		blacklist.add("readline");
		blacklist.add("repl");
		blacklist.add("stream");
		blacklist.add("string_decoder");
		blacklist.add("sys");
		blacklist.add("timers");
		blacklist.add("tls");
		blacklist.add("tty");
		blacklist.add("url");
		blacklist.add("util");
		blacklist.add("vm");
		blacklist.add("zlib");
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
		// the thisisareallyreallylongpackagenameitshouldpublishdowenowhavealimittothelengthofpackagenames-poch.
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
