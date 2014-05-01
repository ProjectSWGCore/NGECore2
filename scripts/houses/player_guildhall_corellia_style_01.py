import sys
from services.housing import HouseTemplate
from engine.resources.scene import Point3D

def setup(housingTemplates):
	houseTemplate = HouseTemplate("object/tangible/deed/guild_deed/shared_corellia_guild_deed.iff", "object/building/player/shared_player_guildhall_corellia_style_01.iff", 5)
	
	houseTemplate.addBuildingSign("object/tangible/sign/player/shared_house_address.iff", Point3D(float(6), float(3), float(18.4))) 
	houseTemplate.addPlaceablePlanet("corellia")
	houseTemplate.addPlaceablePlanet("talus")
	houseTemplate.setDefaultItemLimit(400)
	houseTemplate.setBaseMaintenanceRate(100)
	
	housingTemplates.put(houseTemplate.getDeedTemplate(), houseTemplate)
	return