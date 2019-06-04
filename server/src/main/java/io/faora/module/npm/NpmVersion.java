package io.faora.module.npm;

import java.io.File;

import com.github.yuchi.semver.Version;

public class NpmVersion implements Comparable<NpmVersion> {

	private String name;

	private String version;

	private NpmDist dist;

	public NpmVersion() {
		super();
		dist = new NpmDist();
	}

	public NpmVersion(final File file) {
		super();
		String fileName = file.getName();
		this.name = fileName.substring(0, fileName.lastIndexOf("-"));
		this.version = fileName.substring(fileName.lastIndexOf("-") + 1, fileName.lastIndexOf("."));
		this.dist = new NpmDist(file);
	}

	public NpmVersion(final String name, final String version, final NpmDist dist) {
		super();
		this.name = name;
		this.version = version;
		this.dist = dist;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(final String version) {
		this.version = version;
	}

	public NpmDist getDist() {
		return dist;
	}

	public void setDist(final NpmDist dist) {
		this.dist = dist;
	}

	@Override
	public int compareTo(NpmVersion other) {
		return Version.from(other.getVersion(), false).compareTo(Version.from(this.getVersion(), false));
	}

}