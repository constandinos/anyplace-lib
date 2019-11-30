/*
 * AnyPlace: A free and open Indoor Navigation Service with superb accuracy!
 *
 * Anyplace is a first-of-a-kind indoor information service offering GPS-less
 * localization, navigation and search inside buildings using ordinary smartphones.
 *
 * Author(s): Marcos Antonios Charalambous, Constandinos Demetriou, Christakis Achilleos
 *
 * Supervisor: Demetrios Zeinalipour-Yazti
 *
 * Co-supervisor: Paschalis Mpeis
 *
 * URL: http://anyplace.cs.ucy.ac.cy
 * Contact: anyplace@cs.ucy.ac.cy
 *
 * Copyright (c) 2019, Data Management Systems Lab (DMSL), University of Cyprus.
 * All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in the
 * Software without restriction, including without limitation the rights to use, copy,
 * modify, merge, publish, distribute, sublicense, and/or sell copies of the Software,
 * and to permit persons to whom the Software is furnished to do so, subject to the
 * following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 *
 */

package cy.ac.ucy.anyplace;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Tester {

	/**
	 * The main entry to our library. Inside the main function you can find the
	 * implementation of the command line interface.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// building_0213a78b-aba7-4b76-a60e-02e6c1aa42e5_1540195272732
		// String buid = "username_1373876832005";
		// String pois_to = "poi_064f4a01-07bd-45fa-9579-63fa197d3d90";
		// String coordinates_la = "35.14414934169342";
		// String coordinates_lo = "33.41130472719669";
		// String floor = "0";
		// String pois_from = "poi_88a34fd5-75bd-4601-81dc-fe5aef69bd3c";
		String access_token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjhjNThlMTM4NjE0YmQ1ODc0MjE3MmJkNTA4MGQxOTdkMmIyZGQyZjMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiNTg3NTAwNzIzOTcxLXNpOHM0cXFhdDl2NWVmZ2VtbmViaWhwaTNxZTlvbmxwLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiNTg3NTAwNzIzOTcxLXNpOHM0cXFhdDl2NWVmZ2VtbmViaWhwaTNxZTlvbmxwLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTA0NDQxMzA0OTI3MzE2MzM5NDM2IiwiZW1haWwiOiJhY2hpbC5jaHJpc3Rvc0BnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXRfaGFzaCI6InpSVUJ4cVBjT29xejB0cVpkNEg1WnciLCJuYW1lIjoiY2hyaXN0b3MgYWNoaWxsZW9zIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS8tVTVqVzlpRk9kRVEvQUFBQUFBQUFBQUkvQUFBQUFBQUFBQUEvQUNIaTNyYzZfTEEzLWV2dGFJbXVTdDU0cFJRdmd1T1BOQS9zOTYtYy9waG90by5qcGciLCJnaXZlbl9uYW1lIjoiY2hyaXN0b3MiLCJmYW1pbHlfbmFtZSI6ImFjaGlsbGVvcyIsImxvY2FsZSI6ImVuIiwiaWF0IjoxNTcwMDIzNDE2LCJleHAiOjE1NzAwMjcwMTYsImp0aSI6ImMxMWY2YzIwMjgwZjc1YmMxZjE4NDMzM2QyZGM5NWY4MTYxYTZkNWUifQ.W_8IsTty5D7UdbcHkjrHyhNkEOyFc1r8fluvnd3kpV5wmK9Z4Tb0zv-W9DOr6mOGZUbaLvHR0Hncbqgec_iN9YNV281O3NRd-XERsn-Gf3oZ2z0Nbm5-_4NRg-WkLER4Ouo-upCd9TvXZwWqK0NNZm1Ka8N_JCzU0vb29T7lASZAZQ5POLtg3Z7PoAIk-h1HoO8Wb8acb-fkVaoLd-WR4sEhC93mxEaKe3DycXT0QtaO27GAYypz6HfWM3PsyPHio9nGr-GSt7ZNZuJYjnzqyRhXnx-H2dRggWbS6EAREWmBH2sdWe7fzMBFt_GNCl9q3yGVJQht5IOTmPDG9gixsw";
		String response;

		if (args.length == 0) {/* Menu of options is shown if no arguments are given. */
			System.out.println("Please use one of the available options available options:");

			System.out.println("Navigation");
			System.out.println("----------");
			System.out.println("-poiDetails: Get Point of Interest details.");
			System.out.println("-navigationXY: Get Navigation instructions from a given	location to a POI.");
			System.out.println("-navPoiToPoi: Get Navigation instructions between 2 POIs");

			System.out.println("\nMapping(Public)");
			System.out.println("---------------");
			System.out.println("-buildingAll: Get all annotated buildings.");
			System.out.println("-buildingsByCampus: Get all buildings for a campus.");
			System.out.println("-buildingsByBuildingCode: Get all buildings with the same code.");
			System.out.println("-nearbyBuildings: Get annotated buildings near you (50 meters radius).");
			System.out.println("-allBuildingFloors: Get all floors of a building.");
			System.out.println("-allBuildingPOIs: Get all POIs inside a building.");
			System.out.println("-allBuildingFloorPOIs: Get all POIs inside a floor.");
			System.out.println("-connectionsByFloor: Get all POI connections inside a floor.");
			System.out.println("-heatmapBuidFloor: Get all positions with their respective Wi-Fi radio measurements.");

			System.out.println("\nBlueprints");
			System.out.println("----------");
			System.out.println("-floor64: Downloads the floor plan in a base64 png format (w/o prefix).");
			System.out.println("-floortiles: Fetches the floor plan tiles zip link.");

			System.out.println("\nPosition");
			System.out.println("--------");
			System.out.println("-radioByCoordinatesFloor: Radiomap using all the entries near the coordinate parameters.");
			System.out.println("-radioBuidFloor: Radiomap based on building and floor.");
			System.out.println("-radioBuidFloorRange: Radiomap by limiting the range.");
			System.out.println("-estimatePosition: Estimate the location of the user.");
			System.out.println("-estimatePosOffline: Estimate the location of the user offline. Needs the radiomap file");

		} else {
			AnyplacePost client = new AnyplacePost("ap-dev.cs.ucy.ac.cy", "443", "res/");

			if (args[0].equals("-poiDetails")) {
				if (args.length != 2) {
					System.out.println("Usage: -poiDetails <pois>");
					System.exit(0);
				}
				String pois = args[1];
				response = client.poiDetails(access_token, pois);
				System.out.println(response + "\n");
			} else if (args[0].equals("-navigationXY")) {
				if (args.length != 6) {
					System.out
							.println("Usage: -navigationXY <buid> <floor> <pois_to> <coordinates_la> <coordinates_lo>");
					System.exit(0);
				}
				String buid = args[1];
				String floor = args[2];
				String pois_to = args[3];
				String coordinates_la = args[4];
				String coordinates_lo = args[5];
				response = client.navigationXY(access_token, pois_to, buid, floor, coordinates_la, coordinates_lo);
				System.out.println(response + "\n"); /* .substring(0, 100) */
			} else if (args[0].equals("-navPoiToPoi")) {
				if (args.length != 3) {
					System.out.println("Usage: -navPoiToPoi <pois_from> <pois_to>");
					System.exit(0);
				}
				String pois_from = args[1];
				String pois_to = args[2];
				response = client.navigationPoiToPoi(access_token, pois_to, pois_from);
				System.out.println(response + "\n");
			} else if (args[0].equals("-buildingAll")) {
				response = client.buildingAll();
				System.out.println(response + "\n");
			} else if (args[0].equals("-buildingsByCampus")) {
				if (args.length != 2) {
					System.out.println("Usage: -buildingsByCampus <cuid>");
					System.exit(0);
				}
				String cuid = args[1];
				response = client.buildingsByCampus(cuid);
				System.out.println(response + "\n");
			} else if (args[0].equals("-buildingsByBuildingCode")) {
				if (args.length != 2) {
					System.out.println("Usage: -buildingsByBuildingCode <bucode>");
					System.exit(0);
				}
				String bucode = args[1];
				response = client.buildingsByBuildingCode(bucode);
				System.out.println(response + "\n");
			} else if (args[0].equals("-nearbyBuildings")) {
				if (args.length != 3) {
					System.out.println("Usage: -nearbyBuildings <coordinates_lat> <coordinates_lon>");
					System.exit(0);
				}
				String coordinates_lat = args[1];
				String coordinates_lon = args[2];
				response = client.nearbyBuildings(access_token, coordinates_lat, coordinates_lon);
				System.out.println(response + "\n");
			} else if (args[0].equals("-allBuildingFloors")) {
				if (args.length != 2) {
					System.out.println("Usage: -allBuildingFloors <buid>");
					System.exit(0);
				}
				String buid = args[1];
				response = client.allBuildingFloors(buid);
				System.out.println(response + "\n");
			} else if (args[0].equals("-allBuildingPOIs")) {
				if (args.length != 2) {
					System.out.println("Usage: -allBuildingPOIs <buid>");
					System.exit(0);
				}
				String buid = args[1];
				response = client.allBuildingPOIs(buid);
				System.out.println(response + "\n");
			} else if (args[0].equals("-allBuildingFloorPOIs")) {
				if (args.length != 3) {
					System.out.println("Usage: -allBuildingPOIs <buid> <floor>");
					System.exit(0);
				}
				String buid = args[1];
				String floor = args[2];
				response = client.allBuildingFloorPOIs(buid, floor);
				System.out.println(response + "\n");
			} else if (args[0].equals("-connectionsByFloor")) {
				if (args.length != 3) {
					System.out.println("Usage: -connectionsByFloor <buid> <floor>");
					System.exit(0);
				}
				String buid = args[1];
				String floor = args[2];
				response = client.connectionsByFloor(buid, floor);
				System.out.println(response + "\n");
			} else if (args[0].equals("-heatmapBuidFloor")) {
				if (args.length != 3) {
					System.out.println("Usage: -heatmapBuidFloor <buid> <floor>");
					System.exit(0);
				}
				String buid = args[1];
				String floor = args[2];
				response = client.radioheatMapBuildingFloor(buid, floor);
				System.out.println(response + "\n");
			} else if (args[0].equals("-floor64")) {
				if (args.length != 3) {
					System.out.println("Usage: -floor64 <buid> <floor>");
					System.exit(0);
				}
				String buid = args[1];
				String floor = args[2];
				response = client.floorplans64(access_token, buid, floor);
				System.out.println(response.substring(0, 100) + "\n");
			} else if (args[0].equals("-floortiles")) {
				if (args.length != 3) {
					System.out.println("Usage: -floortiles <buid> <floor>");
					System.exit(0);
				}
				String buid = args[1];
				String floor = args[2];
				response = client.floortiles(access_token, buid, floor);
				System.out.println(response + "\n"); /* .substring(0, 100) */
			} else if (args[0].equals("-radioByCoordinatesFloor")) {
				if (args.length != 4) {
					System.out.println("Usage: -radioByCoordinatesFloor <coordinates_lat> <coordinates_lon> <floor>");
					System.exit(0);
				}
				String coordinates_lat = args[1];
				String coordinates_lon = args[2];
				String floor = args[3];
				response = client.radioByCoordinatesFloor(access_token, coordinates_lat, coordinates_lon, floor);
				System.out.println(response + "\n"); /* .substring(0, 100) */
			} else if (args[0].equals("-radioBuidFloor")) {
				if (args.length != 3) {
					System.out.println("Usage: -radioBuidFloor <buid> <floor>");
					System.exit(0);
				}
				String buid = args[1];
				String floor = args[2];
				response = client.radioByBuildingFloor(access_token, buid, floor);
				System.out.println(response + "\n"); /* .substring(0, 100) */
			} else if (args[0].equals("-radioBuidFloorRange")) {
						if (args.length != 3) {
							System.out.println("Usage: -radioBuidFloorRange <buid> <foor> <coordinates_lat> <coordinates_lon> <range>");
							System.exit(0);
						}
						String buid = args[1];
						String floor = args[2];
						String coordinates_lat = args[3];
						String coordinates_lon = args[4];
						String range =args[5];
						response = client.radioByBuildingFloorRange(buid, floor, coordinates_lat, coordinates_lon, range);
						System.out.println(response + "\n"); /* .substring(0, 100) */
			} else if (args[0].equals("-estimatePosition")) {
				if (args.length != 5) {
					System.out.println("Usage: -estimatePosition <operating_system> <buid> <floor> <algorithm>");
					System.exit(0);
				}
				String operating_system = args[1];
				String buid = args[2];
				String floor = args[3];
				String algorithm = args[4];
				
				String cmd[] = new String[3];
				if (operating_system.equals("linux")) {
					cmd[0] = "/bin/sh";
					cmd[1] = "-c";
					cmd[2] = "sudo iwlist wlo1 scan | awk  '/Address/ {print $5}; /level/ {print $3}' |  cut -d\"=\" -f2 ";
				}
				else if (operating_system.equals("mac")) {
					/*
					�o be completed by Xristakis
					cmd[0] =
					cmd[1] = 
					cmd[2] = 
					*/
				}
				else {
					System.out.println("Only linux and mac are the available operating systems");
					System.exit(0);
				}

				String aps[] = new String[200];
				Process p;
				String s, temp;
				int counter = 0;
				try {
					p = Runtime.getRuntime().exec(cmd);

					BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while ((s = br.readLine()) != null && counter <= 20) {
						temp = "{\"bssid\":\"";
						temp += s;
						temp += "\",\"rss\":";
						s = br.readLine();
						temp += s;
						temp += "}";
						temp = temp.toLowerCase();
						aps[counter++] = temp;
					}
					p.destroy();
					br.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				aps=Arrays.copyOf(aps, counter);

				response = client.estimatePosition(buid, floor, aps, algorithm);
				System.out.println(response + "\n"); /* .substring(0, 100) */
			} else if (args[0].equals("-estimatePosOffline")) {
				if (args.length != 5) {
					System.out.println("Usage: -estimatePosOffline <operating_system> <buid> <floor> <algorithm>");
					System.exit(0);
				}
				String operating_system = args[1];
				String buid = args[2];
				String floor = args[3];
				String algorithm = args[4];
				
				String cmd[] = new String[3];
				if (operating_system.equals("linux")) {
					cmd[0] = "/bin/sh";
					cmd[1] = "-c";
					cmd[2] = "sudo iwlist wlo1 scan | awk  '/Address/ {print $5}; /level/ {print $3}' |  cut -d\"=\" -f2 ";
				}
				else if (operating_system.equals("mac")) {
					/*
					�o be completed by Xristakis
					cmd[0] =
					cmd[1] = 
					cmd[2] = 
					*/
				}
				else {
					System.out.println("Only linux and mac are the available operating systems");
					System.exit(0);
				}

				String aps[] = new String[200];
				Process p;
				String s, temp;
				int counter = 0;
				try {
					p = Runtime.getRuntime().exec(cmd);

					BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
					while ((s = br.readLine()) != null && counter <= 20) {
						temp = "{\"bssid\":\"";
						temp += s;
						temp += "\",\"rss\":";
						s = br.readLine();
						temp += s;
						temp += "}";
						temp = temp.toLowerCase();
						aps[counter++] = temp;
					}
					p.destroy();
					br.close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				aps=Arrays.copyOf(aps, counter);

				response = client.estimatePositionOffline(buid, floor, aps, algorithm);
				System.out.println(response + "\n"); /* .substring(0, 100) */
			} else {
				System.out.println("The option given is not valid.");
			}

		}
	}
}
