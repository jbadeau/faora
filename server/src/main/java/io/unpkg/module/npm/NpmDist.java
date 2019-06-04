package io.unpkg.module.npm;

import java.io.File;

public class NpmDist {

	private String tarball;

	public NpmDist() {
	}

	public NpmDist(String tarball) {
		super();
		this.tarball = tarball;
	}

	public NpmDist(File file) {
		super();
		try {
			this.tarball = file.toURI().toURL().toExternalForm();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public String getTarball() {
		return tarball;
	}

	public void setTarball(String tarball) {
		this.tarball = tarball;
	}

}