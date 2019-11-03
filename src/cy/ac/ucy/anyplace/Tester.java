package cy.ac.ucy.anyplace;

public class Tester {
	/**
	 * The main entry to our library. Inside the main function you can find the
	 * implementation of the command line interface.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// building_0213a78b-aba7-4b76-a60e-02e6c1aa42e5_1540195272732
		String buid = "username_1373876832005";
		String access_token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjhjNThlMTM4NjE0YmQ1ODc0MjE3MmJkNTA4MGQxOTdkMmIyZGQyZjMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJhY2NvdW50cy5nb29nbGUuY29tIiwiYXpwIjoiNTg3NTAwNzIzOTcxLXNpOHM0cXFhdDl2NWVmZ2VtbmViaWhwaTNxZTlvbmxwLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwiYXVkIjoiNTg3NTAwNzIzOTcxLXNpOHM0cXFhdDl2NWVmZ2VtbmViaWhwaTNxZTlvbmxwLmFwcHMuZ29vZ2xldXNlcmNvbnRlbnQuY29tIiwic3ViIjoiMTA0NDQxMzA0OTI3MzE2MzM5NDM2IiwiZW1haWwiOiJhY2hpbC5jaHJpc3Rvc0BnbWFpbC5jb20iLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiYXRfaGFzaCI6InpSVUJ4cVBjT29xejB0cVpkNEg1WnciLCJuYW1lIjoiY2hyaXN0b3MgYWNoaWxsZW9zIiwicGljdHVyZSI6Imh0dHBzOi8vbGgzLmdvb2dsZXVzZXJjb250ZW50LmNvbS8tVTVqVzlpRk9kRVEvQUFBQUFBQUFBQUkvQUFBQUFBQUFBQUEvQUNIaTNyYzZfTEEzLWV2dGFJbXVTdDU0cFJRdmd1T1BOQS9zOTYtYy9waG90by5qcGciLCJnaXZlbl9uYW1lIjoiY2hyaXN0b3MiLCJmYW1pbHlfbmFtZSI6ImFjaGlsbGVvcyIsImxvY2FsZSI6ImVuIiwiaWF0IjoxNTcwMDIzNDE2LCJleHAiOjE1NzAwMjcwMTYsImp0aSI6ImMxMWY2YzIwMjgwZjc1YmMxZjE4NDMzM2QyZGM5NWY4MTYxYTZkNWUifQ.W_8IsTty5D7UdbcHkjrHyhNkEOyFc1r8fluvnd3kpV5wmK9Z4Tb0zv-W9DOr6mOGZUbaLvHR0Hncbqgec_iN9YNV281O3NRd-XERsn-Gf3oZ2z0Nbm5-_4NRg-WkLER4Ouo-upCd9TvXZwWqK0NNZm1Ka8N_JCzU0vb29T7lASZAZQ5POLtg3Z7PoAIk-h1HoO8Wb8acb-fkVaoLd-WR4sEhC93mxEaKe3DycXT0QtaO27GAYypz6HfWM3PsyPHio9nGr-GSt7ZNZuJYjnzqyRhXnx-H2dRggWbS6EAREWmBH2sdWe7fzMBFt_GNCl9q3yGVJQht5IOTmPDG9gixsw";
		String pois_to = "poi_064f4a01-07bd-45fa-9579-63fa197d3d90";
		String coordinates_la = "35.14414934169342";
		String coordinates_lo = "33.41130472719669";
		String floor = "0";
		String response;
		String pois_from = "poi_88a34fd5-75bd-4601-81dc-fe5aef69bd3c";

		if (args.length == 0) {
			System.out.println("Please use one of the available options available options:");
			System.out.println("-allBuildingFloors: Get all floors of a building.");
			System.out.println("-buildingAll: Get all annotated buildings.");
			System.out.println("-headmapBuidFloor: Get all POI connecitons inside a floor.");
			System.out.println("-buidFloorPOI: Get all POIs inside a floor.");
			System.out.println("-floor64: Downloads the floor plan in a base64 png format (w/o prefix)");
			System.out.println("-floortiles: Fetches the floor plan tiles zip link.");
			System.out.println("-radioBuidFloor: Radiomap using all the entries near the coordinate parameters.");
			System.out.println("-navigationXY: Get Navigation instructions from a given location to a POI.");
			System.out.println("-navPoiToPoi: Get Navigation instructions between 2 POIs");
		} else {
			AnyplacePost client = new AnyplacePost("ap.cs.ucy.ac.cy", "443");

			if (args[0].equals("-allBuildingFloors")) {
				response = client.allBuildingFloors(buid);
				System.out.println(response + "\n");
			} else if (args[0].equals("-buildingAll")) {
				response = client.buildingAll();
				System.out.println(response + "\n");
			} else if (args[0].equals("-headmapBuidFloor")) {
				response = client.radioheatMapBuildingFloor(buid, floor);
				System.out.println(response + "\n");
			} else if (args[0].equals("-buidFloorPOI")) {
				response = client.allBuildingFloorPOIs(buid, floor);
				System.out.println(response + "\n");
			} else if (args[0].equals("-floor64")) {
				response = client.floorplans64(access_token, buid, floor);
				System.out.println(response.substring(0, 100) + "\n");
			} else if (args[0].equals("-floortiles")) {
				response = client.floortiles(access_token, buid, floor);
				System.out.println(response + "\n"); /* .substring(0, 100) */
			} else if (args[0].equals("-radioBuidFloor")) {
				response = client.radioByBuildingFloor(access_token, buid, floor);
				System.out.println(response + "\n"); /* .substring(0, 100) */
			} else if (args[0].equals("-navigationXY")) {
				response = client.navigationXY(access_token, pois_to, buid, floor, coordinates_la, coordinates_lo);
				System.out.println(response + "\n"); /* .substring(0, 100) */
			} else if (args[0].equals("-navPoiToPoi")) {
				response = client.navigationPoiToPoi(access_token, pois_to, pois_from);
				System.out.println(response + "\n");
			}

		}

	}
}
