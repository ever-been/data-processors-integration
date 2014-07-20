package cz.everbeen.processing.integration;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * A HTTP request assembling utility facade
 *
 * @author darklight
 * @since 7/20/14.
 */
public final class RequestUtils {

	private final URL baseUrl;

	private RequestUtils(URL baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * Create request utils with a base url
	 * @param baseUrl The URL serving as base for all requests
	 * @return The request util instance
	 */
	public static RequestUtils over(URL baseUrl) {
		return new RequestUtils(baseUrl);
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

	private URI uri(String... path) throws MalformedURLException, URISyntaxException {
		URL u = baseUrl;
		for (String pathElm: path) {
			u = new URL(u, pathElm);
		}
		return u.toURI();
	}
}
