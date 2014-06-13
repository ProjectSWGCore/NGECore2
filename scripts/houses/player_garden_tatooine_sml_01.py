import sys
from services.housing import HouseTemplate
from engine.resources.scene import Point3D

def setup(housingTemplates):
	houseTemplate = HouseTemplate("object/tangible/deed/city_deed/shared_garden_tatooine_sml_01_deed.iff", "object/building/player/city/shared_garden_tatooine_sml_01.iff", 0)
	
	houseTemplate.addBuildingSign("object/tangible/sign/player/shared_house_address.iff", Point3D(1, 2, 3))
	houseTemplate.addPlaceablePlanet("tatooine")
	houseTemplate.addPlaceablePlanet("dantooine")
	houseTemplate.addPlaceablePlanet("lok")
	houseTemplate.setDefaultItemLimit(0)

	houseTemplate.setCivicStructure(True)		
	houseTemplate.setBaseMaintenanceRate(2000)
	housingTemplates.put(houseTemplate.getDeedTemplate(), houseTemplate)
	return