import sys
from services.housing import HouseTemplate
from engine.resources.scene import Point3D

def setup(housingTemplates):
	houseTemplate = HouseTemplate("object/tangible/deed/guild_deed/shared_tatooine_guild_style_02_deed.iff", "object/building/player/shared_player_guildhall_tatooine_style_02.iff", 5)
	
	houseTemplate.addBuildingSign("object/tangible/sign/player/shared_house_address.iff", Point3D(float(5.5), float(4), float(12.7))) 
	houseTemplate.addPlaceablePlanet("tatooine")
	houseTemplate.addPlaceablePlanet("dantooine")
	houseTemplate.addPlaceablePlanet("lok")
	houseTemplate.setDefaultItemLimit(400)
	houseTemplate.setBaseMaintenanceRate(100)
	
	housingTemplates.put(houseTemplate.getDeedTemplate(), houseTemplate)
	return