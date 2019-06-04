package io.faora.module.npm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class NpmInfo {

	private String name;

	private Set<NpmVersion> versions;

	private Map<String, String> distTags;

	public NpmInfo(String name) {
		this.name = name;
		this.versions = new TreeSet<NpmVersion>();
		this.distTags = new HashMap<String, String>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<NpmVersion> getVersions() {
		return versions;
	}

	public NpmVersion getVersion(String version) {
		return versions.stream().filter((NpmVersion item) -> item.getVersion().equals(version)).findFirst()
				.orElse(null);
	}

	public void setVersions(Set<NpmVersion> versions) {
		this.versions = versions;
		this.updateDistTags();
	}

	public void addVersions(NpmVersion version) {
		this.versions.add(version);
		this.updateDistTags();
	}
	
	public Map<String, String> getDistTags() {
		return distTags;
	}
	
	public void setDistTags(Map<String, String> distTags) {
		this.distTags = distTags;
	}

	public boolean containsDistTag(String version) {
		return this.distTags.containsKey(version);
	}

	private void updateDistTags() {
		this.distTags.put("latest", versions.iterator().next().getVersion());
		this.distTags.put("next", versions.iterator().next().getVersion());
	}

}
