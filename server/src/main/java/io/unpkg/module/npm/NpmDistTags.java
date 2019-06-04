package io.unpkg.module.npm;

public class NpmDistTags {

	private String latest;

	public NpmDistTags() {
	}

	public NpmDistTags(String latest) {
		super();
		this.latest = latest;
	}

	public String getLatest() {
		return latest;
	}

	public void setLatest(String latest) {
		this.latest = latest;
	}

}