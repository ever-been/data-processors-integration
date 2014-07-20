package cz.everbeen.processing.integration;

import cz.everbeen.restapi.protocol.ProtocolObjectSerializer;
import cz.everbeen.restapi.protocol.TaskStatus;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Assert;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * An integration-test-purpose (limited) client for the EverBEEN REST API
 *
 * @author darklight
 * @since 7/20/14.
 */
public class RestApiClient {


	private final RequestUtils requestUtils;
	private final CloseableHttpClient client;
	private final ProtocolObjectSerializer os;

	private RestApiClient(URL baseUrl) {
		this.requestUtils = RequestUtils.over(baseUrl);
		this.client = HttpClients.createMinimal();
		this.os = new ProtocolObjectSerializer();
	}

	/**
	 * Create a REST API client for a service on given URL
	 * @param baseUrl Base URL of the service
	 * @return The REST API client for given service
	 */
	public static RestApiClient forService(URL baseUrl) {
		return new RestApiClient(baseUrl);
	}

	/**
	 * Upload the exporting package to the server
	 */
	public final void upload(PackageHandle packageHandle) throws IOException, URISyntaxException {
		final InputStream content = new BufferedInputStream(new FileInputStream(packageHandle.getFile()));
		final HttpPut put;
		try {
			put = requestUtils.put(content, "bpk");
		} finally {
			content.close();
		}
		client.execute(put);
	}

	/**
	 * Run a package server-side
	 * @param descriptorName The name of the task-descriptor within the package
	 * @return The task status of the task that was run
	 */
	protected final TaskStatus runPackage(PackageHandle packageHandle, String descriptorName) throws IOException, URISyntaxException {
		final HttpPut put = requestUtils.put(null, "task", packageHandle.getGroupId(), packageHandle.getBpkId(), packageHandle.getVersion(), descriptorName);
		final TaskStatus status = os.deserialize(readResponseContent(client.execute(put)), TaskStatus.class);
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
