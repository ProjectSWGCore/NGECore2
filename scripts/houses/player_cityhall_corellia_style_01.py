import sys
from services.housing import HouseTemplate
from engine.resources.scene import Point3D

def setup(housingTemplates):
	houseTemplate = HouseTemplate("object/tangible/deed/city_deed/shared_cityhall_corellia_deed.iff", "object/building/player/city/shared_cityhall_corellia.iff", 0)
	
	houseTemplate.addBuildingSign("object/tangible/sign/player/shared_house_address.iff", Point3D(1, 2, 3))
	houseTemplate.addPlaceablePlanet("corellia")
	houseTemplate.addPlaceablePlanet("talus")
	houseTemplate.setDefaultItemLimit(400)
	houseTemplate.setBaseMaintenanceRate(1000)
	houseTemplate.setCivicStructure(True)	
	housingTemplates.put(houseTemplate.getDeedTemplate(), houseTemplate)
	
	return