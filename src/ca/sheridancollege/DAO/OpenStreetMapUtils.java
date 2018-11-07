package ca.sheridancollege.DAO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import com.jayway.jsonpath.JsonPath;
/**
 * 
 * @author MAGS
 *
 */
public class OpenStreetMapUtils {
	/**
	 * get coordinate for facility location
	 */
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/55.0.2883.87 Safari/537.36";


	private static OpenStreetMapUtils instance = null;

	public static OpenStreetMapUtils getInstance() {
		if (instance == null) {
			instance = new OpenStreetMapUtils();
		}
		return instance;
	}

	private String getRequest(String url) throws Exception {

		URL obj = new URL(url);
		System.out.println("Connect: " + url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setConnectTimeout(5000); // set timeout to 5 seconds
		con.setReadTimeout(10000); // set read time out
		System.out.println("66");
		// optional default is GET
		con.setRequestMethod("GET");
		System.out.println("68");
		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		System.out.println("71");
		System.out.println(con.getHeaderField("Remote Address"));
		System.out.println(con.getHeaderField("origin"));

		if (con.getResponseCode() != 200) {
			System.out.println("request web is not 200 " + con.getResponseCode());
			return null;
		}

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}

	public Map<String, Double> getCoordinates(String address) {
		Map<String, Double> res;
		StringBuffer query;
		String queryResult = null;

		query = new StringBuffer();
		res = new HashMap<String, Double>();

		query.append("https://nominatim.openstreetmap.org/search.php?q=");

		query.append(address);
		query.append("&format=json&addressdetails=1");

		System.out.println("query -> " + query.toString());


		try {
			queryResult = getRequest(query.toString());
		} catch (Exception e) {
			System.out.println("Error when trying to get data with the following query " + query);
		}

		//error code != 200
		if (queryResult == null) {
			System.out.println("query result is null");
			res.put("lat", 43.7468176);
			res.put("lon", -79.4737335);
			return null;
		}
		System.out.println("queryRestul -> " + queryResult);
		
		

		try {
			String lat = JsonPath.read(queryResult, "$.[0].lat");
			String lon = JsonPath.read(queryResult, "$.[0].lon");
			res.put("lat", Double.parseDouble(lat));
			res.put("lon", Double.parseDouble(lon));
		} catch (Exception e) {
			res.put("lat", null);
			res.put("lon", null);
		}

		/*
		 * if (obj instanceof JSONArray) { JSONArray array = (JSONArray) obj;
		 * System.out.println("it is a json array"); if (((Map<String, Double>)
		 * array).size() > 0) { JSONObject jsonObject = (JSONObject) array.get(0);
		 * System.out.println("It's a json array -> " +jsonObject.toString() );
		 * 
		 * String lon = (String) jsonObject.get("lon"); String lat = (String)
		 * jsonObject.get("lat"); // log.debug("lon=" + lon); // log.debug("lat=" +
		 * lat); res.put("lon", Double.parseDouble(lon)); res.put("lat",
		 * Double.parseDouble(lat));
		 * 
		 *
		 */

		return res;
	}
}