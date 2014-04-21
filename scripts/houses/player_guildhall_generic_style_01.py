import sys
from services.housing import HouseTemplate
from engine.resources.scene import Point3D

def setup(core):
	houseTemplate = HouseTemplate("object/tangible/deed/guild_deed/shared_generic_guild_deed.iff", "object/building/player/shared_player_guildhall_generic_style_01.iff", 2)
	
	houseTemplate.addBuildingSign("object/tangible/sign/player/shared_house_address.iff", Point3D(1, 2, 3))
	houseTemplate.addPlaceablePlanet("tatooine")
	houseTemplate.addPlaceablePlanet("corellia")
	houseTemplate.addPlaceablePlanet("naboo")
	houseTemplate.addPlaceablePlanet("talus")
	houseTemplate.addPlaceablePlanet("rori")
	houseTemplate.addPlaceablePlanet("dantooine")
	houseTemplate.addPlaceablePlanet("lok")
	houseTemplate.setDefaultItemLimit(400)
	
	core.housingService.addHousingTemplate(houseTemplate)
	return