package cz.everbeen.processing.integration;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

/**
 * Test the JSON export
 *
 * @author darklight
 * @since 6/29/14.
 */
public class JSONExportTest extends RestBotTestBase {

	@Test
	public void testPlainSelection() throws IOException, URISyntaxException {
		final PackageHandle dummyData = packages.bpk("dummy-data");
		client.uploadBpk(dummyData);
		client.runPackage(dummyData, "tds:dummyData.td.xml");
	}
}
