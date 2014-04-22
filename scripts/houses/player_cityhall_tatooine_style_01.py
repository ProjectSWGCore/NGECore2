import sys
from services.housing import HouseTemplate
from engine.resources.scene import Point3D


def setup(core):
	houseTemplate = HouseTemplate("object/tangible/deed/city_deed/shared_cityhall_tatooine_deed.iff", "object/building/player/city/shared_cityhall_tatooine.iff", 5)
	
	houseTemplate.addBuildingSign("object/tangible/sign/player/shared_house_address.iff", Point3D(1, 2, 3))
	houseTemplate.addPlaceablePlanet("tatooine")
	houseTemplate.addPlaceablePlanet("dantooine")
	houseTemplate.addPlaceablePlanet("lok")
	houseTemplate.setDefaultItemLimit(400)
	
	core.housingService.addHousingTemplate(houseTemplate)
	return