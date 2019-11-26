package cy.ac.ucy.anyplace;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

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
	
	private static BufferedImage decodeToImage(String imageString) {
		 
        BufferedImage image = null;
        byte[] imageByte;
        try {
            Base64.Decoder decoder =Base64.getDecoder();
            imageByte = decoder.decode(imageString);
            ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
            image = ImageIO.read(bis);
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
            
        }
        return image;
    }

	// Navigation
	// POI Details

	public String poiDetails(String access_token, String pois) {

		RestClient client = new RestClient();
		setPath("/anyplace/navigation/pois/id");
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", access_token);
		params.put("pois", pois);

		String response = client.doPost(params, getHost(), getPath());
		return response;
	}

	/**
	 * Navigation instructions given from a given location to a POI
	 * 
	 * @param access_token
	 * @param pois_to
	 * @param buid
	 * @param floor
	 * @param coordinates_la
	 * @param coordinates_lo
	 * @return
	 */
	public String navigationXY(String access_token, String pois_to, String buid, String floor, String coordinates_lat,
			String coordinates_lon) {

		RestClient client = new RestClient();
		setPath("/anyplace/navigation/route_xy");
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", access_token);
		params.put("pois_to", pois_to);
		params.put("buid", buid);
		params.put("floor_number", floor);
		params.put("coordinates_lat", coordinates_lat);
		params.put("coordinates_lon", coordinates_lon);


		String response = client.doPost(params, getHost(), getPath());
		
		JSONObject obj = new JSONObject(response);
		int statusCode = obj.getInt("status_code");

		if (statusCode == 200) {
			
			String temp = "res/" + buid + "/" + floor;
			Path path = Paths.get(temp);

			try {
				Files.createDirectories(path);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String filename = "res/" + buid + "/" + floor + "/pathTo_" + pois_to +".json";
		
			try {
				FileOutputStream outputStream = new FileOutputStream(filename);
				outputStream.write(response.getBytes());
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

	// Navigation instructions between 2 POIs.

	public String navigationPoiToPoi(String access_token, String pois_to, String pois_from) {

		RestClient client = new RestClient();
		setPath("/anyplace/navigation/route");
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", access_token);
		params.put("pois_from", pois_from);
		params.put("pois_to", pois_to);

		String response = client.doPost(params, getHost(), getPath());
		

		JSONObject obj = new JSONObject(response);
		int statusCode = obj.getInt("status_code");

		if (statusCode == 200) {
			
			String temp = "res/navigation" ;
			Path path = Paths.get(temp);

			try {
				Files.createDirectories(path);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			String filename = "res/navigation/" +"pois_from_" + pois_from + "_to_pois_" + pois_to +".json";
		
			try {
				FileOutputStream outputStream = new FileOutputStream(filename);
				outputStream.write(response.getBytes());
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

	// ---------------------------------------------------------------------------------------------------------------------

	// Get all annotated buildings
	public String buildingAll() {
		RestClient client = new RestClient();
		setPath("/anyplace/mapping/building/all");

		return client.doPost(null, getHost(), getPath());
	}

	// Get all buildings for a campus
	public String buildingsByCampus(String cuid) {

		RestClient client = new RestClient();
		setPath("/anyplace/mapping/campus/all_cucode");
		Map<String, String> params = new HashMap<String, String>();
		params.put("cuid", cuid);

		String response = client.doPost(params, getHost(), getPath());
		return response;
	}

	// Get all buildings with the same code
	public String buildingsByBuildingCode(String bucode) {
		RestClient client = new RestClient();
		setPath("/anyplace/mapping/building/all_bucode");
		Map<String, String> params = new HashMap<String, String>();
		params.put("bucode", bucode);

		return client.doPost(params, getHost(), getPath());
	}

	// Get all nearby buildings - 50 meter radius
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

	// Get all floors of a building
	public String allBuildingFloors(String buid) {

		RestClient client = new RestClient();
		setPath("/anyplace/mapping/floor/all");
		Map<String, String> params = new HashMap<String, String>();
		params.put("buid", buid);

		String response = client.doPost(params, getHost(), getPath());

		JSONObject obj = new JSONObject(response);

		JSONArray floors = obj.getJSONArray("floors");
		int length = floors.length();
		for (int i = 0; i < length; i++) {
			String temp = "res/" + buid + "/" + floors.getJSONObject(i).getString("floor_name");
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

	// Get all POIs inside of a building
	public String allBuildingPOIs(String buid) {

		RestClient client = new RestClient();
		setPath("/anyplace/mapping/pois/all_building");
		Map<String, String> params = new HashMap<String, String>();
		params.put("buid", buid);

		String response = client.doPost(params, getHost(), getPath());

		String temp = "res/" + buid;
		Path path = Paths.get(temp);

		try {
			Files.createDirectories(path);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String filename = "res/" + buid + "/" + "allPOIsOfBuilding.json";

		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(filename);
			outputStream.write(response.getBytes());
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}

	// Get all POIs inside of a floor of a building
	public String allBuildingFloorPOIs(String buid, String floor) {

		RestClient client = new RestClient();
		setPath("/anyplace/mapping/pois/all_floor");
		Map<String, String> params = new HashMap<String, String>();
		params.put("buid", buid);
		params.put("floor_number", floor);


		String response = client.doPost(params, getHost(), getPath());

		String temp = "res/" + buid + "/" + floor;
		Path path = Paths.get(temp);

		try {
			Files.createDirectories(path);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String filename = "res/" + buid + "/" + floor + "/" + "allPOIsOfFloor.json";

		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(filename);
			outputStream.write(response.getBytes());
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;
	}
	
	// Get all connections between POIs inside of a floor of a building
		public String connectionsByFloor(String buid, String floor) {

			RestClient client = new RestClient();
			setPath("/anyplace/mapping/connection/all_floor");
			Map<String, String> params = new HashMap<String, String>();
			params.put("buid", buid);
			params.put("floor_number", floor);

			String response = client.doPost(params, getHost(), getPath());

			String temp = "res/" + buid + "/" + floor;
			Path path = Paths.get(temp);

			try {
				Files.createDirectories(path);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String filename = "res/" + buid + "/" + floor + "/" + "connections.json";

			FileOutputStream outputStream;
			try {
				outputStream = new FileOutputStream(filename);
				outputStream.write(response.getBytes());
				outputStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return response;
		}

	// Get all positions with their respective Wi-Fi radio measurements.

	public String radioheatMapBuildingFloor(String buid, String floor) {

		RestClient client = new RestClient();
		Map<String, String> params = new HashMap<String, String>();
		params.put("buid", buid);
		params.put("floor", floor);
		setPath("/anyplace/mapping/radio/heatmap_building_floor");

		String response = client.doPost(params, getHost(), getPath());

		String temp = "res/" + buid + "/" + floor;
		Path path = Paths.get(temp);

		try {
			Files.createDirectories(path);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String filename = "res/" + buid + "/" + floor + "/" + "radioHeatMap.json";

		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(filename);
			outputStream.write(response.getBytes());
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}
	
	//------------------------------------------------------------------------------------------------------------------
	
	//Download the floor plan in base64 png format

	public String floorplans64(String access_token, String buid, String floor) {

		RestClient client = new RestClient();
		setPath("/anyplace/floorplans64/" + buid + "/" + floor);
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", access_token);
		params.put("buid", buid);
		params.put("floor", floor);

		String response =client.doPost(params, getHost(), getPath());
		
		String temp = "res/" + buid + "/" + floor;
		Path path = Paths.get(temp);

		try {
			Files.createDirectories(path);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String filename = "res/" + buid + "/" + floor + "/" + "floorplan.png";

		
		try {
			File outputfile = new File(filename);
			ImageIO.write(decodeToImage(response), "png", outputfile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return response;
	}
	
	
	//Download the floor plan tiles in a zip file
	
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
			String filename = "res/"+ buid + "/"+ floor + "/" + "floorPlanTiles.zip";

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

	
	//------------------------------------------------------------------------------------------------
	
	
	//Radio map using all entries near the location
	
	public String radioByCoordinatesFloor(String access_token, String coordinates_lat, String coordinates_lon, String floor) {

		RestClient client = new RestClient();
		setPath("/anyplace/position/radio_download_floor");
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", access_token);
		params.put("coordinates_lat", coordinates_lat);
		params.put("coordinates_lon", coordinates_lon);
		params.put("floor_number", floor);
		params.put("mode", "foo");

		String response = client.doPost(params, getHost(), getPath());

		// NEED MORE INFORMATION
		
		/*
		
		JSONObject obj = new JSONObject(response);
		int statusCode = obj.getInt("status_code");

		if (statusCode == 200) {
			
			String temp = "res/" + coordinates_lat+"-"+coordinates_lon + "/" + floor;
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
	*/
		return response;

	}
	
	//Get a radio map in a floor with a range
	public String radioByBuildingFloorRange(String access_token, String buid, String floor, String coordinates_lat, String coordinates_lon, String range) {

		RestClient client = new RestClient();
		setPath("/anyplace/position/radio_by_floor_bbox");
		Map<String, String> params = new HashMap<String, String>();
		//params.put("access_token", access_token);
		params.put("buid", buid);
		params.put("floor_number", floor);
		params.put("coordinates_lat", coordinates_lat);
		params.put("coordinates_lon", coordinates_lon);
		params.put("range", range);

		String response = client.doPost(params, getHost(), getPath());
/*
		JSONObject obj = new JSONObject(response);
		int statusCode = obj.getInt("status_code");

		if (statusCode == 200) {
			String map_url_parameters = obj.getString("map_url_parameters");
			byte[] parameters = client.getFileWithPost(getHost(), map_url_parameters);
			String map_url_mean = obj.getString("map_url_mean");
			byte[] mean = client.getFileWithPost(getHost(), map_url_mean);
			String map_url_weights = obj.getString("map_url_weights");
			byte[] weights = client.getFileWithPost(getHost(), map_url_weights);

			String temp = "res/" + buid + "/" + floor;
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
*/
		return response;

	}
	
	//Radiomap using all the entries of an entire floor of a building

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

			String temp = "res/" + buid + "/" + floor;
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
	
	
	//Get an estimation on the user's position based on the APs.
	public String estimatePosition(String buid, String floor, String aps[], String algorithm) {

		RestClient client = new RestClient();
		setPath("/anyplace/position/estimate_position");
		Map<String, String> params = new HashMap<String, String>();
		params.put("buid", buid);
		params.put("floor", floor);
		String addb = "\\\"bssid\\\"";
		String addr = "\\\"rss\\\"";
		String addq = "\\\"";
		String ap = "[";
		for (int i =0; i<aps.length;i++) {
			
			JSONObject obj = new JSONObject(aps[i]);
			String bssid = obj.getString("bssid");
			int rss = obj.getInt("rss");
			ap +="{"+ addb + ":" + addq + bssid + addq + "," + addr + ":" + rss +"}," ;
						
			
		}
		ap = ap.substring(0, ap.length() - 1) + "]";
	
		//System.out.println(ap);
		//ap = "[{\\\"bssid\\\":\\\"d4:d7:48:d8:28:b0\\\",\\\"rss\\\":-40},{\\\"bssid\\\":\\\"00:0e:38:7a:37:77\\\",\\\"rss\\\":-50}]";
		//System.out.println(ap);
		
		params.put("APs", ap);
		params.put("algorithm_choice", algorithm);
		

		
		String response = client.doPost(params, getHost(), getPath());

		String temp = "res/" + buid + "/" + floor;
		Path path = Paths.get(temp);

		try {
			Files.createDirectories(path);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String filename = "res/" + buid + "/" + floor + "/" + "connections.json";

		FileOutputStream outputStream;
		try {
			outputStream = new FileOutputStream(filename);
			outputStream.write(response.getBytes());
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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