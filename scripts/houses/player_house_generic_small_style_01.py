import sys
from services.housing import HouseTemplate
from engine.resources.scene import Point3D

def setup(housingTemplates):
	houseTemplate = HouseTemplate("object/tangible/deed/player_house_deed/shared_generic_house_small_deed.iff", "object/building/player/shared_player_house_generic_small_style_01.iff", 2)
	
	houseTemplate.addBuildingSign("object/tangible/sign/player/shared_house_address.iff", Point3D(float(-7.39), float(2.36), float(2)))
	houseTemplate.addPlaceablePlanet("tatooine")
	houseTemplate.addPlaceablePlanet("corellia")
	houseTemplate.addPlaceablePlanet("naboo")
	houseTemplate.addPlaceablePlanet("talus")
	houseTemplate.addPlaceablePlanet("rori")
	houseTemplate.addPlaceablePlanet("dantooine")
	houseTemplate.addPlaceablePlanet("lok")
	houseTemplate.setDefaultItemLimit(200)
	houseTemplate.setBaseMaintenanceRate(8)
	houseTemplate.setDestructionFee(800)
	
	housingTemplates.put(houseTemplate.getDeedTemplate(), houseTemplate)
	return