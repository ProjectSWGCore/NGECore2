import sys
from services.housing import HouseTemplate
from engine.resources.scene import Point3D

def setup(housingTemplates):
	houseTemplate = HouseTemplate("object/tangible/deed/player_house_deed/shared_naboo_house_small_window_deed.iff", "object/building/player/shared_player_house_naboo_small_window_style_01.iff", 2)
	
	houseTemplate.addBuildingSign("object/tangible/sign/player/shared_house_address.iff", Point3D(float(-1.7),float(3),float(8.2)))
	houseTemplate.addPlaceablePlanet("naboo")
	houseTemplate.addPlaceablePlanet("rori")
	houseTemplate.addPlaceablePlanet("dantooine")
	houseTemplate.setDefaultItemLimit(200)
	houseTemplate.setBaseMaintenanceRate(18)
	housingTemplates.put(houseTemplate.getDeedTemplate(), houseTemplate)
	return