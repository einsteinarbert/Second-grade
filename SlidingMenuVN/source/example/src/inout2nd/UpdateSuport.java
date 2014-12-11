package inout2nd;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import android.os.StrictMode;
import android.util.Log;

public class UpdateSuport {
	String url = "http://wireless-vietvudanh.c9.io/public/api/v1/user/";

	public UpdateSuport() {

	}

	public static String getContent(String url,String masterField) throws Exception {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		String res = getStringContent(url);
		//int len = field.length;
		//String returnString[] = new String[len];
		JSONObject json = new JSONObject(res);
		String masterjson = (String) json.get(masterField);
		/*for (int i = 0; i < len; i++) {
			returnString[i] = new String(masterjson.getString(field[i]));
		}*/
		return masterjson;
	}

	public static String getStringContent(String uri) throws TimeoutException,
			Exception {

		try {
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet();
			request.setURI(new URI(uri));
			HttpResponse response = client.execute(request);
			InputStream ips = response.getEntity().getContent();
			BufferedReader buf = new BufferedReader(new InputStreamReader(ips,
					"UTF-8"));

			StringBuilder sb = new StringBuilder();
			String s;
			int i = 0;
			while (true) {
				s = buf.readLine();
				if (s == null || s.length() == 0) {
					Log.e("loop: ", Integer.toString(i++));
					break;
				}
				sb.append(s);

			}
			buf.close();
			ips.close();
			return sb.toString();
		} finally {
			// any cleanup code...
		}
	}
}
