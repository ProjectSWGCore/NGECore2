import sys
from services.housing import HouseTemplate
from engine.resources.scene import Point3D

def setup(housingTemplates):
	houseTemplate = HouseTemplate("object/tangible/deed/player_house_deed/shared_generic_house_medium_style_02_deed.iff", "object/building/player/shared_player_house_generic_medium_style_02.iff", 2)
	
	houseTemplate.addBuildingSign("object/tangible/sign/player/shared_house_address.iff", Point3D(float(5.3), float(2.26), float(7.7)))
	houseTemplate.addPlaceablePlanet("tatooine")
	houseTemplate.addPlaceablePlanet("corellia")
	houseTemplate.addPlaceablePlanet("naboo")
	houseTemplate.addPlaceablePlanet("talus")
	houseTemplate.addPlaceablePlanet("rori")
	houseTemplate.addPlaceablePlanet("dantooine")
	houseTemplate.addPlaceablePlanet("lok")
	houseTemplate.setDefaultItemLimit(200)
	houseTemplate.setBaseMaintenanceRate(18)
	
	housingTemplates.put(houseTemplate.getDeedTemplate(), houseTemplate)
	return