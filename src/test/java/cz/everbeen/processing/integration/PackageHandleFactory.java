package cz.everbeen.processing.integration;

import org.junit.Assert;

import java.io.File;

/**
 * Factory for {@link PackageHandle} instances
 *
 * @author darklight
 * @since 7/20/14.
 */
public class PackageHandleFactory {
	private final String processingGroupId = "cz.everbeen.processing";
	private final String processiongVersion = System.getProperty(ImportedSysprops.PROCESSING_VERSION);
	private final File packageDir = new File(System.getProperty(ImportedSysprops.PROCESSING_PACKAGEDIR));

	/**
	 * Create a package handle for a BPK of given ID
	 * @param bpkId ID of the bpk (identifies the package)
	 * @return The package handle
	 */
	public PackageHandle bpk(String bpkId) {
		final File bpkFile = new File(packageDir, bpkId + processiongVersion + ".bpk");
		Assert.assertTrue(bpkFile.exists());
		Assert.assertFalse(bpkFile.isDirectory());
		return new PackageHandle(processingGroupId, bpkId, processiongVersion, bpkFile);
	}
}
