import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class EmsClient {
	public static void main(String[] args) {
		/**
		 * Setting parameters for the API call
		 */
		String cdmAddress_ = "211.224.204.130";
		String cdmPort_ = "8443";
		String taskAPI_ = "com.cisco.unicorn.ui.ListApiServlet";
		String action_ = "getDeliveryServices";
		String channelId_ = "all";
		String urlString_ = "https://" + cdmAddress_ + ":" + cdmPort_
				+ "/servlet/" + taskAPI_ + "?action=" + action_ + "&param="
				+ channelId_;
		String userName_ = "admin";
		String password_ = "Cds123$";

		/**
		 * Install the all-trusting trust manager
		 */

		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection
					.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			System.out.println("Printing Exception Message " + e);
		}

		/**
		 * Insert the credentials
		 */
		String sAuth = userName_ + ":" + password_;
		String sEncodedAuth = com.kt.naas.util.Base64Utils.base64Encode(sAuth
				.getBytes()); // new
								// sun.misc.BASE64Encoder().encode(sAuth.getBytes());
		/**
		 * Create the HTTPS Connection
		 */
		HttpsURLConnection conn = null;
		try {
			URL url = new URL(null, urlString_);
			System.out.println(url.toString());
			conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestProperty("Authorization", "Basic " + sEncodedAuth);
			conn.setHostnameVerifier(new newHostNameVerifier());
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestMethod("GET");
		} catch (MalformedURLException ex) {
			System.out.println("Printing Exception Message " + ex);
		} catch (IOException ioexception) {
			System.out.println("Printing Exception Message " + ioexception);
		}
		
		/**
		 * Handling the response from CDSM
		 */
		try {
			BufferedReader inStreamReader = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String str;
			while ((str = inStreamReader.readLine()) != null) {
				System.out.println("Response from CDSM : ");
				System.out.println(str);
			}
			inStreamReader.close();
		} catch (IOException ioexception) {
			System.out.println("Printing Exception Message " + ioexception);
		}

	}

	/**
	 * Create a trust manager that does not validate certificate chains
	 */
	private static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] certs, String authType) {
		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] certs, String authType) {
		}
	} };

	private static class newHostNameVerifier implements HostnameVerifier {
		/**
		 * ignore hostname checking
		 */
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}
}