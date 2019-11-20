package cy.ac.ucy.anyplace;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AnyplacePost {

	private String host;
	private String path;
	private String port;

	public AnyplacePost(String host, String port) {

		setHost(host);
		setPort(port);

	}
	public String buildingsByCampus(String cuid) {

		RestClient client = new RestClient();
		setPath("/anyplace/mapping/campus/all_cucode");
		Map<String, String> params = new HashMap<String, String>();
		params.put("cuid", cuid);
		

		String response = client.doPost(params, getHost(), getPath());
		return response;
	}
	
	public String nearbyBuildings(String access_token, String coordinates_lat, String coordinates_lon) {

		RestClient client = new RestClient();
		setPath("/anyplace/mapping/building/coordinates");
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", access_token);
		params.put("coordinates_lat", coordinates_lat);
		params.put("coordinates_lon", coordinates_lon);

		

		String response = client.doPost(params, getHost(), getPath());
		return response;
	}
	
	public String allBuildingFloors(String buid) {

		RestClient client = new RestClient();
		setPath("/anyplace/mapping/floor/all");
		Map<String, String> params = new HashMap<String, String>();
		params.put("buid", buid);
		

		String response = client.doPost(params, getHost(), getPath());
		
		JSONObject obj = new JSONObject(response);

		JSONArray floors =  obj.getJSONArray("floors");
		int length = floors.length();
		for (int i =0; i<length;i++) {
			String temp ="res/" + buid + "/" + floors.getJSONObject(i).getString("floor_name");
			Path path = Paths.get(temp);
			
			try {
				Files.createDirectories(path);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String filename = "res/" + buid + "/" + floors.getJSONObject(i).getString("floor_name") + "/" + "floor.txt";

			FileOutputStream outputStream;
			try {
				outputStream = new FileOutputStream(filename);
				outputStream.write(floors.getJSONObject(i).toString().getBytes());
				outputStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		}
		
		return response;
	}
	
	public String buildingsByBuildingCode() {
		RestClient client = new RestClient();
		setPath("/anyplace/mapping/building/all_bucode");

		return client.doPost(null, getHost(), getPath());
	}

	public String radioheatMapBuildingFloor(String buid, String floor) {

		RestClient client = new RestClient();
		Map<String, String> params = new HashMap<String, String>();
		params.put("buid", buid);
		params.put("floor", floor);
		setPath("/anyplace/mapping/radio/heatmap_building_floor");

		return client.doPost(params, getHost(), getPath());

	}

	public String buildingAll() {
		RestClient client = new RestClient();
		setPath("/anyplace/mapping/building/all");

		return client.doPost(null, getHost(), getPath());
	}

	public String floorplans64(String access_token, String buid, String floor) {

		RestClient client = new RestClient();
		setPath("/anyplace/floorplans64/" + buid + "/" + floor);
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", access_token);
		params.put("buid", buid);
		params.put("floor", floor);

		return client.doPost(params, getHost(), getPath());
	}

	public String radioByBuildingFloor(String access_token, String buid, String floor) {

		RestClient client = new RestClient();
		setPath("/anyplace/position/radio_by_building_floor");
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", access_token);
		params.put("buid", buid);
		params.put("floor", floor);

		String response = client.doPost(params, getHost(), getPath());
		

		JSONObject obj = new JSONObject(response);
		int statusCode = obj.getInt("status_code");

		if (statusCode == 200) {
			String map_url_parameters = obj.getString("map_url_parameters");
			byte[] parameters = client.getFileWithPost(getHost(), map_url_parameters);
			String map_url_mean = obj.getString("map_url_mean");
			byte[] mean = client.getFileWithPost(getHost(), map_url_mean);
			String map_url_weights = obj.getString("map_url_weights");
			byte[] weights = client.getFileWithPost(getHost(), map_url_weights);
			
			String temp ="res/" + buid + "/" + floor;
			Path path = Paths.get(temp);
			
			try {
				Files.createDirectories(path);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String indoor_radiomap_parameters = "res/" + buid + "/" + floor + "/indoor_radiomap_parameters.txt";
			String indoor_radiomap_mean = "res/" + buid + "/" + floor + "/indoor_radiomap_mean.txt";
			String indoor_radiomap_weights = "res/" + buid + "/" + floor + "/indoor_radiomap_weights.txt";

			try {
				FileOutputStream outputStream = new FileOutputStream(indoor_radiomap_parameters);
				outputStream.write(parameters);
				outputStream.close();

				outputStream = new FileOutputStream(indoor_radiomap_mean);
				outputStream.write(mean);
				outputStream.close();

				outputStream = new FileOutputStream(indoor_radiomap_weights);
				outputStream.write(weights);
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else {
			System.out.println("Bad response");
		}

		return response;

	}
	public String navigationXY(String access_token,String pois_to,  String buid, String floor, String coordinates_la, String coordinates_lo) {

		RestClient client = new RestClient();
		setPath("/anyplace/position/radio_by_building_floor");
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", access_token);
		params.put("buid", buid);
		params.put("floor", floor);

		String response = client.doPost(params, getHost(), getPath());
		

		JSONObject obj = new JSONObject(response);
		int statusCode = obj.getInt("status_code");

		if (statusCode == 200) {
			String map_url_parameters = obj.getString("map_url_parameters");
			byte[] parameters = client.getFileWithPost(getHost(), map_url_parameters);
			String map_url_mean = obj.getString("map_url_mean");
			byte[] mean = client.getFileWithPost(getHost(), map_url_mean);
			String map_url_weights = obj.getString("map_url_weights");
			byte[] weights = client.getFileWithPost(getHost(), map_url_weights);
			
			String temp ="res/" + buid + "/" + floor;
			Path path = Paths.get(temp);
			
			try {
				Files.createDirectories(path);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String indoor_radiomap_parameters = "res/" + buid + "/" + floor + "/indoor_radiomap_parameters.txt";
			String indoor_radiomap_mean = "res/" + buid + "/" + floor + "/indoor_radiomap_mean.txt";
			String indoor_radiomap_weights = "res/" + buid + "/" + floor + "/indoor_radiomap_weights.txt";

			try {
				FileOutputStream outputStream = new FileOutputStream(indoor_radiomap_parameters);
				outputStream.write(parameters);
				outputStream.close();

				outputStream = new FileOutputStream(indoor_radiomap_mean);
				outputStream.write(mean);
				outputStream.close();

				outputStream = new FileOutputStream(indoor_radiomap_weights);
				outputStream.write(weights);
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		else {
			System.out.println("Bad response");
		}

		return response;

	}

	public String floortiles(String access_token, String buid, String floor) {

		RestClient client = new RestClient();
		setPath("/anyplace/floortiles/" + buid + "/" + floor);
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", access_token);
		params.put("buid", buid);
		params.put("floor", floor);

		String response = client.doPost(params, getHost(), getPath());

		JSONObject obj = new JSONObject(response);
		int statusCode = obj.getInt("status_code");

		if (statusCode == 200) {
			String tiles_archive = obj.getString("tiles_archive");
			byte[] zip = client.getFileWithGet(getHost(), tiles_archive);
			String filename = "res/tiles_archive" + "_buid_" + buid + "_floor_" + floor + ".zip";

			try {
				FileOutputStream outputStream = new FileOutputStream(filename);

				outputStream.write(zip);
				outputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			System.out.println("Bad response");
		}

		return response;
	}
	
	public String poiDetails(String access_token, String pois) {

		RestClient client = new RestClient();
		setPath("/anyplace/navigation/pois/id");
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", access_token);
		params.put("pois", pois);
		

		String response = client.doPost(params, getHost(), getPath());
		return response;
	}

	public String navigationPoiToPoi(String access_token, String pois_to, String pois_from) {

		RestClient client = new RestClient();
		setPath("/anyplace/navigation/route");
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", access_token);
		params.put("pois_from", pois_from);
		params.put("pois_to", pois_to);

		

		String response = client.doPost(params, getHost(), getPath());
		return response;
	}
	
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

}
