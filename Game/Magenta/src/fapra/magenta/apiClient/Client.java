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
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.provider.Settings.Secure;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import fapra.magenta.R;
import fapra.magenta.database.KeyValueRepository;
import fapra.magenta.database.LinesRepository;

public class Client implements Runnable {
	public static final String serverUrl = "http://fachpraktikum.hci.simtech.uni-stuttgart.de/ss2015/grp3/server/public/index.php/";

	private Activity activity;
	private LinesRepository linesRepo;
	private KeyValueRepository keyValueRepo;

	public Client(Activity activity) {
		this.activity = activity;
		this.linesRepo = new LinesRepository((Context) activity);
		this.keyValueRepo = new KeyValueRepository((Context) activity);
	}

	private boolean connectionAvailable() {
		ConnectivityManager connMgr = (ConnectivityManager) activity
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		NetworkInfo mWifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
		
		if (networkInfo != null && networkInfo.isConnected()) {
			Log.d("connection check", "" + true);
			if (prefs.getBoolean(activity.getString(R.string.preference_just_wifi), false) && !mWifi.isConnected()) {
			    // should upload just with wifi but not available
			    return false;
			}
			return true;
		}
		Log.d("connection check", "" + false);
		return false;
	}

	private void sendDeviceInfo() {
		HashMap<String, String> params = new HashMap<String, String>();

		// get screen sizes in px and cm
		WindowManager windowManager = activity.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics displayMetrics = new DisplayMetrics();
		display.getMetrics(displayMetrics);

		int screenXPx = displayMetrics.widthPixels;
		int screenYPx = displayMetrics.heightPixels;

		DisplayMetrics dm = new DisplayMetrics();
		activity.getWindowManager().getDefaultDisplay().getMetrics(dm);

		double screenXCm = (screenXPx / dm.xdpi) * 2.54;
		double screenYCm = (screenYPx / dm.ydpi) * 2.54;

		params.put("deviceHash", this.getDeviceHash());
		params.put("screenXPx", ""+screenXPx);
		params.put("screenYPx", ""+screenYPx);
		params.put("gridSizeX", ""+(int) screenXCm);
		params.put("gridSizeY", ""+(int) screenYCm);
		params.put("xDpi", ""+dm.xdpi);
		params.put("yDpi", ""+dm.ydpi);
		params.put("density", ""+dm.density);
		try {

			String postData = this.buildPostDataString(params);
			Log.d("postData", postData);

			URL url = new URL(Client.serverUrl + "api/device/update");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length",
					String.valueOf(postData.length()));

			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(postData);
			writer.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			// TODO remove this dummy output stuff
			for (String line; (line = reader.readLine()) != null;) {
				System.out.println(line);
			}

			writer.close();
			reader.close();
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void sendLineData(JSONArray jsonLines) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("data", "{ 	\"device\": \"" + getDeviceHash()
				+ "\", 	\"lines\": " + jsonLines.toString() + " }");

		try {

			String postData = this.buildPostDataString(params);
			Log.d("postData", postData);

			URL url = new URL(Client.serverUrl + "api/line/save-lines");
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			connection.setRequestProperty("Content-Length",
					String.valueOf(postData.length()));

			OutputStreamWriter writer = new OutputStreamWriter(
					connection.getOutputStream());
			writer.write(postData);
			writer.flush();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));

			// TODO remove this dummy output stuff
			for (String line; (line = reader.readLine()) != null;) {
				System.out.println(line);
			}

			writer.close();
			reader.close();
			connection.disconnect();
		} catch (Exception e) {
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
				String lastUploadedId;
				try {
					lastUploadedId = this.keyValueRepo
							.getValue("lastUploadedId");
				} catch (Exception e1) {
					lastUploadedId = "0";
				}

				ArrayList<HashMap<String, String>> lines = linesRepo
						.getLines(lastUploadedId);

				if (lines.size() > 0) {
					this.sendDeviceInfo();

					// build the lines json
					JSONArray jsonLines = new JSONArray();
					for (HashMap<String, String> line : lines) {
						try {
							jsonLines.put(new JSONObject(line.get("json")));
						} catch (JSONException e) {
							Log.e("lineparsing", "invalid line, skipped");
						}
					}
					this.keyValueRepo.setValue("lastUploadedId",
							lines.get(lines.size() - 1).get("id"));
					this.sendLineData(jsonLines);
				}
				// connection has been available --> long timeout
				try {
					Thread.sleep(60000); // 60sec
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

		this.keyValueRepo.close();
		this.linesRepo.close();
	}

	private String getDeviceHash() {
		String deviceId = Secure.getString(activity.getContentResolver(),
				Secure.ANDROID_ID);
		try {
			return sha256(deviceId);
		} catch (Exception e) {
			return deviceId;
		}
	}

	private String sha256(String input) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("SHA256");
		byte[] result = mDigest.digest(input.getBytes());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < result.length; i++) {
			sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16)
					.substring(1));
		}

		return sb.toString();
	}
}
