package fapra.magenta.apiClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings.Secure;
import android.util.Log;

public class Client implements Runnable {
	public static final String serverUrl = "http://fachpraktikum.hci.simtech.uni-stuttgart.de/ss2015/grp3/server/public/index.php/";

	private Activity activity;

	public Client(Activity activity) {
		this.activity = activity;
	}

	private boolean connectionAvailable() {
		ConnectivityManager connMgr = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			Log.d("connection check", "" + true);

			return true;
		}
		Log.d("connection check", "" + false);
		return false;
	}

	private void sendDeviceInfo() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("deviceHash", this.getDeviceHash());
		params.put("screenXPx", "5");
		params.put("screenYPx", "10");
		params.put("gridSizeX", "5");
		params.put("gridSizeY", "10");
		params.put("xDpi", "900");
		params.put("yDpi", "1200");
		params.put("density", "90");
		try {
			
		String postData = this.buildPostDataString(params);
		Log.d("postData", postData);

		URL url = new URL( Client.serverUrl + "api/device/update" );
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod( "POST" );
		connection.setDoInput( true );
		connection.setDoOutput( true );
		connection.setUseCaches( false );
		connection.setRequestProperty( "Content-Type",
		                               "application/x-www-form-urlencoded" );
		connection.setRequestProperty( "Content-Length", String.valueOf(postData.length()) );

		OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() );
		writer.write( postData );
		writer.flush();


		BufferedReader reader = new BufferedReader(
		                          new InputStreamReader(connection.getInputStream()) );

		// TODO remove this dummy output stuff
		for ( String line; (line = reader.readLine()) != null; )
		{
		  System.out.println( line );
		}

		writer.close();
		reader.close();
		connection.disconnect();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sendLineData() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("data", "{ 	\"device\": \""+ getDeviceHash() +"\", 	\"lines\": [ 		{ 			\"startGridX\": 1, 			\"startGridY\": 2, 			\"endGridX\": 3, 			\"endGridY\": 4, 			\"startPxX\": 120, 			\"startPxY\": 240, 			\"endPxX\": 180, 			\"endPxY\": 360, 			\"startTime\": 123456789, 			\"endTime\": 987456321, 			\"isLandscape\": false, 			\"scrollDirection\": 2, 			\"points\": [ 				{ 					\"xPx\": 17, 					\"yPx\": 99, 					\"timestamp\": 321654987 				}, 				{ 					\"xPx\": 37, 					\"yPx\": 40, 					\"timestamp\": 789456123 				}, 				{ 					\"xPx\": 46, 					\"yPx\": 39, 					\"timestamp\": 321456987 				}, 				{ 					\"xPx\": 60, 					\"yPx\": 45, 					\"timestamp\": 987456321 				} 			] 		}, 		{ 			\"startGridX\": 3, 			\"startGridY\": 5, 			\"endGridX\": 2, 			\"endGridY\": 6, 			\"startPxX\": 120, 			\"startPxY\": 240, 			\"endPxX\": 180, 			\"endPxY\": 360, 			\"startTime\": 123456789, 			\"endTime\": 987456321, 			\"isLandscape\": false, 			\"scrollDirection\": 4, 			\"points\": [ 				{ 					\"xPx\": 17, 					\"yPx\": 99, 					\"timestamp\": 321654987 				}, 				{ 					\"xPx\": 37, 					\"yPx\": 40, 					\"timestamp\": 789456123 				}, 				{ 					\"xPx\": 46, 					\"yPx\": 39, 					\"timestamp\": 321456987 				}, 				{ 					\"xPx\": 60, 					\"yPx\": 45, 					\"timestamp\": 987456321 				} 			] 		}, 		{ 			\"startGridX\": 2, 			\"startGridY\": 8, 			\"endGridX\": 1, 			\"endGridY\": 3, 			\"startPxX\": 120, 			\"startPxY\": 240, 			\"endPxX\": 180, 			\"endPxY\": 360, 			\"startTime\": 123456789, 			\"endTime\": 987456321, 			\"isLandscape\": true, 			\"scrollDirection\": 3, 			\"points\": [ 				{ 					\"xPx\": 17, 					\"yPx\": 99, 					\"timestamp\": 321654987 				}, 				{ 					\"xPx\": 37, 					\"yPx\": 40, 					\"timestamp\": 789456123 				}, 				{ 					\"xPx\": 46, 					\"yPx\": 39, 					\"timestamp\": 321456987 				}, 				{ 					\"xPx\": 60, 					\"yPx\": 45, 					\"timestamp\": 987456321 				} 			] 		} 	] }");
		
		try {
			
			String postData = this.buildPostDataString(params);
			Log.d("postData", postData);

			URL url = new URL( Client.serverUrl + "api/line/save-lines" );
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod( "POST" );
			connection.setDoInput( true );
			connection.setDoOutput( true );
			connection.setUseCaches( false );
			connection.setRequestProperty( "Content-Type",
			                               "application/x-www-form-urlencoded" );
			connection.setRequestProperty( "Content-Length", String.valueOf(postData.length()) );

			OutputStreamWriter writer = new OutputStreamWriter( connection.getOutputStream() );
			writer.write( postData );
			writer.flush();


			BufferedReader reader = new BufferedReader(
			                          new InputStreamReader(connection.getInputStream()) );

			// TODO remove this dummy output stuff
			for ( String line; (line = reader.readLine()) != null; )
			{
			  System.out.println( line );
			}

			writer.close();
			reader.close();
			connection.disconnect();
			} catch(Exception e) {
				e.printStackTrace();
			}
	}

	private String buildPostDataString(HashMap<String, String> params)
			throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (String key : params.keySet()) {
			if (first) {
				first = false;
			} else {
				result.append("&");
			}
			result.append(URLEncoder.encode(key, "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(params.get(key), "UTF-8"));
		}

		return result.toString();
	}

	@Override
	public void run() {
		while (!Thread.interrupted()) {
			if (this.connectionAvailable()) {
				// check for available lines that need to be uploaded
				
				
				this.sendDeviceInfo();
				this.sendLineData();
				
				// connection has been available --> long timeout
				try {
					Thread.sleep(120000); // 2min
				} catch (InterruptedException e) {
					break;
				}
			} else {
				// no connection - short timeout
				try {
					Thread.sleep(10000); // 10s
				} catch (InterruptedException e) {
					break;
				}
			}
		}
	}
	
	private String getDeviceHash() {
		String deviceId = Secure.getString(activity.getContentResolver(),
                Secure.ANDROID_ID);
		try {
			return sha256(deviceId);
		} catch(Exception e) {
			return deviceId;
		}
	}
	
	private String sha256(String input) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("SHA256");
        byte[] result = mDigest.digest(input.getBytes());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
         
        return sb.toString();
    }
}
