import sys
from services.housing import HouseTemplate
from engine.resources.scene import Point3D

def setup(housingTemplates):
	houseTemplate = HouseTemplate("object/tangible/deed/player_house_deed/shared_tatooine_house_small_deed.iff", "object/building/player/shared_player_house_tatooine_small_style_01.iff", 2)
	
	houseTemplate.addBuildingSign("object/tangible/sign/player/shared_house_address.iff", Point3D(float(-1.7),float(3),float(7.9)))
	houseTemplate.addPlaceablePlanet("tatooine")
	houseTemplate.addPlaceablePlanet("lok")
	houseTemplate.addPlaceablePlanet("dantooine")
	houseTemplate.setDefaultItemLimit(200)
	houseTemplate.setBaseMaintenanceRate(8)
	housingTemplates.put(houseTemplate.getDeedTemplate(), houseTemplate)
	return