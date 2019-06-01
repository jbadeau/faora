package io.unpkg.npm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import io.unpkg.npm.NpmInfo;
import io.unpkg.npm.NpmRegistry;

public class NpmRegistryTests {

	@Test
	public void getKnownPackage() {
		NpmRegistry registry = new NpmRegistry();
		NpmInfo info = registry.getPackageInfo("react");
		assertEquals("react", info.getName());
	}

}
