package cz.everbeen.processing.integration;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * EverBEEN REST API test base
 *
 * @author darklight
 * @since 6/28/14.
 */
abstract class RestBotTestBase {

	protected RestApiClient client;
	protected PackageHandleFactory packages;

	@BeforeClass
	public void setUp() throws MalformedURLException {
		client = RestApiClient.forService(new URL(System.getProperty(ImportedSysprops.RESTAPI_URL)));
		packages = new PackageHandleFactory();
	}

	@AfterClass
	public void tearDown() throws IOException {
		client = null;
		packages = null;
	}

}
