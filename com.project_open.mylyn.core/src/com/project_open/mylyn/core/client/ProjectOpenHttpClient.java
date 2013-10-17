package com.project_open.mylyn.core.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethodBase;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.contrib.ssl.EasySSLProtocolSocketFactory;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.io.IOUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.mylyn.commons.net.AbstractWebLocation;
import org.eclipse.mylyn.commons.net.AuthenticationCredentials;
import org.eclipse.mylyn.commons.net.AuthenticationType;
import org.eclipse.mylyn.commons.net.Policy;
import org.eclipse.mylyn.commons.net.WebUtil;

import com.project_open.mylyn.core.exception.ProjectOpenException;

public class ProjectOpenHttpClient {

	private final AbstractWebLocation location;

	private final HttpClient httpClient;

	public ProjectOpenHttpClient(AbstractWebLocation location,
			String characterEncoding, boolean selfSignedSSL) {
		this.location = location;
		this.httpClient = createAndInitHttpClient(characterEncoding,
				selfSignedSSL);
	}

	private HttpClient createAndInitHttpClient(String characterEncoding,
			boolean selfSignedSSL) {
		if (selfSignedSSL) {
			Protocol.registerProtocol("https", new Protocol("https",
					new EasySSLProtocolSocketFactory(), 443));
		}
		HttpClient httpClient = new HttpClient();
		WebUtil.configureHttpClient(httpClient, "Mylyn");
		httpClient.getParams().setContentCharset(characterEncoding);
		return httpClient;
	}

	public void validate(IProgressMonitor monitor) throws ProjectOpenException {
		GetMethod loginRequest = new GetMethod(location.getUrl()
				+ "/intranet-rest/");

		monitor = Policy.monitorFor(monitor);
		try {
			monitor.beginTask("Executing request", IProgressMonitor.UNKNOWN);
			int httpStatus = executeRequest(loginRequest, monitor);
			if (httpStatus == HttpStatus.SC_OK) {
				// login OK
			} else if (httpStatus == HttpStatus.SC_FORBIDDEN) {
				throw new RuntimeException("Invalid credentials!");
			} else {
				// TODO Use a custom exception for error handling
				throw new RuntimeException("]project-open[ site is not up!");
			}
		} finally {
			loginRequest.releaseConnection();
			monitor.done();
		}
	}

	protected boolean hasHTTPAuthenticationCredentials() {
		AuthenticationCredentials credentials = location
				.getCredentials(AuthenticationType.HTTP);
		return (credentials != null && credentials.getUserName() != null && credentials
				.getUserName().length() > 0);
	}

	private String stripSlash(String url) {
		if (url.endsWith("/")) {
			return url.substring(0, url.lastIndexOf("/"));
		}
		return url;
	}

	public String executeGet(String url, IProgressMonitor monitor)
			throws ProjectOpenException {
		GetMethod getRequest = new GetMethod(stripSlash(location.getUrl())
				+ url);

		return executeMethod(getRequest, monitor);
	}

	public String executePost(String url, IProgressMonitor monitor)
			throws ProjectOpenException {
		return executePost(url, new HashMap<String, String>(), monitor);
	}

	public String executePost(String url, Map<String, String> parameters,
			IProgressMonitor monitor) throws ProjectOpenException {
		PostMethod postRequest = new PostMethod(stripSlash(location.getUrl())
				+ url);

		for (String key : parameters.keySet()) {
			postRequest.setParameter(key, parameters.get(key));
		}

		return executeMethod(postRequest, monitor);
	}

	private String executeMethod(HttpMethodBase request,
			IProgressMonitor monitor) {
		monitor = Policy.monitorFor(monitor);
		try {
			monitor.beginTask("Executing request", IProgressMonitor.UNKNOWN);

			executeRequest(request, monitor);
			return getResponseBodyAsString(request, monitor);
		} finally {
			request.releaseConnection();
			monitor.done();
		}
	}

	private int executeRequest(HttpMethodBase request, IProgressMonitor monitor) {
		HostConfiguration hostConfiguration = WebUtil.createHostConfiguration(
				httpClient, location, monitor);
		try {
			request.setDoAuthentication(true);
			return WebUtil.execute(httpClient, hostConfiguration, request,
					monitor);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String getResponseBodyAsString(HttpMethodBase request,
			IProgressMonitor monitor) {
		try {
			return IOUtils.toString(WebUtil.getResponseBodyAsStream(request,
					monitor));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}