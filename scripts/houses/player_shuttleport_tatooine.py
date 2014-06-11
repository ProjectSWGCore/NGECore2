import sys
from services.housing import HouseTemplate
from engine.resources.scene import Point3D

def setup(housingTemplates):
	houseTemplate = HouseTemplate("object/tangible/deed/city_deed/shared_shuttleport_tatooine_deed.iff", "object/building/tatooine/shared_shuttleport_tatooine.iff", 1)
	
	houseTemplate.addBuildingSign("object/tangible/sign/player/shared_house_address.iff", Point3D(float(3.5),float(-0.3),float(3.5)))
	houseTemplate.addPlaceablePlanet("tatooine")
	houseTemplate.setDefaultItemLimit(0)
	houseTemplate.setBaseMaintenanceRate(2000)

	houseTemplate.setCivicStructure(True)	
	housingTemplates.put(houseTemplate.getDeedTemplate(), houseTemplate)
	return