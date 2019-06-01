package io.unpkg.npm;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

@Component
public class NpmRegistry {

	private static final Logger logger = LoggerFactory.getLogger(NpmRegistry.class);

	private static Map<String, NpmInfo> REGISTRY = new HashMap<String, NpmInfo>();

	public NpmRegistry() {
		initRegistry("../../../../../npm");
	}

	public NpmInfo getPackageInfo(String packageName) {
		return REGISTRY.get(packageName);
	}

	private void addVersion(NpmVersion version) {
		NpmInfo info = REGISTRY.get(version.getName());
		if (info == null) {
			info = new NpmInfo(version.getName());
			REGISTRY.put(version.getName(), info);
		}
		REGISTRY.get(version.getName()).addVersions(version);
		info.getDistTags().put("latest", REGISTRY.get(version.getName()).getVersions().iterator().next().getVersion());
		info.getDistTags().put("next", REGISTRY.get(version.getName()).getVersions().iterator().next().getVersion());
	}

	private List<File> initRegistry(String folder) {
		List<File> filesList = new ArrayList<File>();
		File[] fileList = getResourceFolderFiles(folder);
		if (fileList != null) {
			for (File file : fileList) {
				if (file.isDirectory()) {
					recurse(filesList, file);
				} else {
					filesList.add(file);
				}
			}
		}
		return filesList;
	}

	private void recurse(List<File> filesList, File parent) {
		File list[] = parent.listFiles();
		for (File file : list) {
			if (file.isDirectory()) {
				recurse(filesList, file);
			} else {
				NpmDist dist;
				try {
					dist = new NpmDist(file.toURI().toURL().toExternalForm());
				} catch (MalformedURLException e) {
					throw new RuntimeException(e);
				}
				NpmVersion version = new NpmVersion(file);
				addVersion(version);
			}
		}
	}

	private File[] getResourceFolderFiles(String folder) {
		try {
			return new ClassPathResource(folder, NpmRegistry.class).getFile().listFiles();
		} catch (IOException e) {
			logger.error("could not find {} on the classpath", folder);
			return null;
		}
	}

}
