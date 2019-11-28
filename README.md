# anyplace-lib

All endpoints implemented in our Anyplace Library can be found below:

Navigation
----------
-poiDetails: Get Point of Interest details.

-navigationXY: Get Navigation instructions from a given	location to a POI.

-navPoiToPoi: Get Navigation instructions between 2	POIs

Mapping(Public)
---------------
-buildingAll: Get all annotated buildings.

-buildingsByCampus: Get all buildings for a campus.

-buildingsByBuildingCode: Get all buildings with the same code.

-nearbyBuildings: Get annotated buildings near you (50 meters radius).

-allBuildingFloors: Get all floors of a building.

-allBuildingPOIs: Get all POIs inside a building.

-allBuildingFloorPOIs: Get all POIs inside a floor.

-connectionsByFloor: Get all POI connections inside a floor.

-heatmapBuidFloor: Get all positions with their respective Wi-Fi radio measurements.

Blueprints
----------
-floor64: Downloads the floor plan in a base64 png format (w/o prefix).

-floortiles: Fetches the floor plan tiles zip link.

Position
--------
-radioByCoordinatesFloor: Radiomap using all the entries near the coordinate parameters.

-radioBuidFloor: Radiomap using all the entries near the coordinate parameters.

-estimatePosition: Estimate the location of the user. 
