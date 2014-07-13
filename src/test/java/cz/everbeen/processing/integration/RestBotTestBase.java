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

    private static final String BASEURL_PROPERTY = "cz.everbeen.restapi.url";

    private CloseableHttpClient client;
    private URL baseUrl;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        baseUrl = new URL(System.getProperty(BASEURL_PROPERTY));
        client = HttpClients.createMinimal();
    }

    @AfterClass
    public void tearDown() throws IOException {
        client.close();
        client = null;
    }

    /**
     * Create an HTTP GET request
     * @param path context path of the request
     * @return The request
     */
    protected HttpGet get(String... path) throws MalformedURLException, URISyntaxException {
        return new HttpGet(uri(path));
    }

    /**
     * Create an HTTP GET request
     * @param path context path of the request
     * @return The request
     */
    protected HttpPut put(InputStream content, String... path) throws MalformedURLException, URISyntaxException {
        return new HttpPut(uri(path));
    }

    /**
     * Execute give HTTP request against the EverBEEN REST API
     * @param request Request to execute
     * @return The response from the REST API server
     * @throws IOException When request fails
     */
    protected HttpResponse execute(HttpUriRequest request) throws IOException {
        return client.execute(request);
    }

    private URI uri(String... path) throws MalformedURLException, URISyntaxException {
        URL u = baseUrl;
        for (String pathElm: path) {
            u = new URL(u, pathElm);
        }
        return u.toURI();
    }
}
