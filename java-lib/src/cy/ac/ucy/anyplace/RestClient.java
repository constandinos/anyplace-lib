package cy.ac.ucy.anyplace;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/*
 * This class makes the requests to the server using HTTP POST
 * 
 * 
 * 
 */

public class RestClient {

	/* Here we build the POST request for the server api
	 * 
	 */
	public String doPost(Map<String, String> map, String host, String path) {

		String query = makeRequestBody(map);

		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(new UnzippingInterceptor());
		OkHttpClient client = clientBuilder.build();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, query);
		Request request = new Request.Builder().url("https://" + host + path).post(body)
				.addHeader("Content-Type", "application/json").addHeader("Accept", "*/*")
				.addHeader("Cache-Control", "no-cache").addHeader("Host", host)
				.addHeader("Accept-Encoding", "application/json").addHeader("Content-Length", "45")
				.addHeader("Connection", "keep-alive").addHeader("cache-control", "no-cache").build();

		try {
			Response response = client.newCall(request).execute();

			return response.body().string();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	
	
	public byte[] getFileWithGet(String host, String url) {

		

		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(new UnzippingInterceptor());
		OkHttpClient client = clientBuilder.build();

		
		
		Request request = new Request.Builder().url(url).get()
				.addHeader("Accept", "*/*")
				.addHeader("Cache-Control", "no-cache").addHeader("Host", host)
				.addHeader("Accept-Encoding", "gzip, deflate")
				.addHeader("Connection", "keep-alive").addHeader("cache-control", "no-cache").build();

		try {
			Response response = client.newCall(request).execute();

			return response.body().bytes();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
public byte[] getFileWithPost(String host, String url) {

		
	String query = makeRequestBody(null);

		OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder().addInterceptor(new UnzippingInterceptor());
		OkHttpClient client = clientBuilder.build();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, query);

		
		Request request = new Request.Builder().url(url).post(body)
				.addHeader("Accept", "*/*")
				.addHeader("Cache-Control", "no-cache").addHeader("Host", host)
				.addHeader("Accept-Encoding", "gzip, deflate")
				.addHeader("Connection", "keep-alive").addHeader("cache-control", "no-cache").build();

		try {
			Response response = client.newCall(request).execute();

			return response.body().bytes();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	/* Here we make the payload that contains all the necessary parameters
	 * 
	 */
	private String makeRequestBody(Map<String, String> map) {

		String body = "{";

		if (map != null) {
			for (Entry<String, String> str : map.entrySet()) {

				body = body + "\"" + str.getKey() + "\":\"" + str.getValue() + "\",";

			}

			int length = body.length();
			body = body.substring(0, length - 1) + "}";
		} else {
			body = body + "}";
		}

		return body;
	}

}
