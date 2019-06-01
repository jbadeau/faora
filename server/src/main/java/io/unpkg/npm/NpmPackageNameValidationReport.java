package io.unpkg.npm;

import java.util.ArrayList;
import java.util.List;

public class NpmPackageNameValidationReport {

	private boolean validForNewPackages;

	private boolean validForOldPackages;

	List<String> warnings = new ArrayList<String>();

	List<String> errors = new ArrayList<String>();

	public NpmPackageNameValidationReport() {
		super();
	}

	public boolean isValidForNewPackages() {
		return validForNewPackages;
	}

	public void setValidForNewPackages(boolean validForNewPackages) {
		this.validForNewPackages = validForNewPackages;
	}

	public boolean isValidForOldPackages() {
		return validForOldPackages;
	}

	public void setValidForOldPackages(boolean validForOldPackages) {
		this.validForOldPackages = validForOldPackages;
	}

	public List<String> getWarnings() {
		return warnings;
	}

	public void addWarning(String warning) {
		this.warnings.add(warning);
	}

	public List<String> getErrors() {
		return errors;
	}

	public void addError(String error) {
		this.errors.add(error);
	}

}
