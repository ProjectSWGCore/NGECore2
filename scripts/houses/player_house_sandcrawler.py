import sys
from services.housing import HouseTemplate
from engine.resources.scene import Point3D

def setup(housingTemplates):
	houseTemplate = HouseTemplate("object/tangible/saga_system/rewards/shared_structure_deed_player_house_sandcrawler.iff", "object/building/player/shared_player_house_sandcrawler.iff", 5)
	
	houseTemplate.addBuildingSign("object/tangible/sign/player/shared_house_address.iff", Point3D(float(-8.75), float(3.86), float(1)))
	houseTemplate.addPlaceablePlanet("tatooine")
	houseTemplate.addPlaceablePlanet("corellia")
	houseTemplate.addPlaceablePlanet("naboo")
	houseTemplate.addPlaceablePlanet("talus")
	houseTemplate.addPlaceablePlanet("rori")
	houseTemplate.addPlaceablePlanet("dantooine")
	houseTemplate.addPlaceablePlanet("lok")
	houseTemplate.setDefaultItemLimit(350)
	houseTemplate.setBaseMaintenanceRate(26)
	
	housingTemplates.put(houseTemplate.getDeedTemplate(), houseTemplate)
	return