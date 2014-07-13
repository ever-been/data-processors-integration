package cz.everbeen.processing.integration;

import cz.everbeen.restapi.protocol.ProtocolObjectSerializer;
import cz.everbeen.restapi.protocol.TaskStatus;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.net.URISyntaxException;

/**
 * Generic export test
 *
 * @author darklight
 * @since 6/29/14.
 */
abstract class ExportTestBase extends RestBotTestBase {

    private final String processingGroupId = "cz.everbeen.processing";
    private final File packageDir = new File(System.getProperty(ImportedSysprops.PROCESSING_PACKAGEDIR));
    private final ProtocolObjectSerializer os = new ProtocolObjectSerializer();

    /**
     * @return The BPK's groupId
     */
    protected final String getGroupId() {
        return processingGroupId;
    }

    /**
     * @return The BPK's bkpId
     */
    protected abstract String getBpkId();

    /**
     * @return The BPK's version
     */
    protected final String getVersion() {
        return System.getProperty(ImportedSysprops.PROCESSING_VERSION);
    }

    /**
     * Get the export-performing BPK
     *
     * @return The BPK file with export logic
     */
    protected final File getExportPackage() {
        return new File(packageDir, getBpkId() + getVersion() + ".bpk");
    }

    @Test
    public abstract void doTest();

    /**
     * Upload the exporting package to the server
     */
    protected final void uploadPackage() throws IOException, URISyntaxException {
        final InputStream content = new BufferedInputStream(new FileInputStream(getExportPackage()));
        final HttpPut put;
        try {
            put = put(content, "bpk");
        } finally {
            content.close();
        }
    }

    /**
     * Run a package server-side
     * @param descriptorName The name of the task-descriptor within the package
     * @return The task status of the task that was run
     */
    protected final TaskStatus runPackage(String descriptorName) throws IOException, URISyntaxException {
        final HttpPut put = put(null, "task", getGroupId(), getBpkId(), getVersion(), descriptorName);
        final TaskStatus status = os.deserialize(readResponseContent(execute(put)), TaskStatus.class);
        Assert.assertEquals("OK", status.getTaskState());
        return status;
    }

    /**
     * Read the content of a {@link org.apache.http.HttpResponse} as a String
     * @param response Response to read
     * @return The response's String representation
     */
    protected final String readResponseContent(HttpResponse response) throws IOException {
        final StringWriter sw = new StringWriter();
        IOUtils.copy(response.getEntity().getContent(), sw);
        return sw.toString();
    }
}
