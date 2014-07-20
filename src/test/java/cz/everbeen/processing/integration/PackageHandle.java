package cz.everbeen.processing.integration;

import java.io.File;

/**
 * Handle to a BPK package on the disk
 *
 * @author darklight
 * @since 6/29/14.
 */
public final class PackageHandle {

	private final String groupId, bpkId, version;
	private final File file;

	PackageHandle(String groupId, String bpkId, String version, File file) {
		this.groupId = groupId;
		this.bpkId = bpkId;
		this.version = version;
		this.file = file;
	}

    /**
     * @return The BPK's groupId
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * @return The BPK's bkpId
     */
    public String getBpkId() {
		return bpkId;
	}

    /**
     * @return The BPK's version
     */
    public String getVersion() {
		return version;
    }

    /**
     * Get the BPK
     *
     * @return The BPK file with export logic
     */
    public File getFile() {
        return file;
    }
}
