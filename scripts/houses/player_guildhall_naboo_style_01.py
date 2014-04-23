import sys
from services.housing import HouseTemplate
from engine.resources.scene import Point3D

def setup(core):
	houseTemplate = HouseTemplate("object/tangible/deed/guild_deed/shared_naboo_guild_deed.iff", "object/building/player/shared_player_guildhall_naboo_style_01.iff", 5)
	
<<<<<<< HEAD
	houseTemplate.addBuildingSign("object/tangible/sign/player/shared_house_address.iff", Point3D(1, 2, 3))
=======
	houseTemplate.addBuildingSign("object/tangible/sign/player/shared_house_address.iff", Point3D(float(6), float(3), float(18.4))) 
>>>>>>> upstream/master
	houseTemplate.addPlaceablePlanet("naboo")
	houseTemplate.addPlaceablePlanet("rori")
	houseTemplate.addPlaceablePlanet("dantooine")
	houseTemplate.setDefaultItemLimit(400)
	
	core.housingService.addHousingTemplate(houseTemplate)
	return