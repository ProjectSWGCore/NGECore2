import sys

def addLocations(core, planet):
	
	if planet.getName() == 'tatooine':
		tatooineLocations(core, planet)
		
	if planet.getName() == 'corellia':
		corelliaLocations(core, planet)
		
	if planet.getName() == 'naboo':
		nabooLocations(core, planet)
		
	if planet.getName() == 'rori':
		roriLocations(core, planet)
		
	if planet.getName() == 'endor':
		endorLocations(core, planet)								
		
	if planet.getName() == 'talus':
		talusLocations(core, planet)

	if planet.getName() == 'yavin4':
		yavin4Locations(core, planet)

	if planet.getName() == 'dantooine':
		dantooineLocations(core, planet)		
		
	if planet.getName() == 'dathomir':
		dathomirLocations(core, planet)
		
	if planet.getName() == 'lok':
		lokLocations(core, planet)
	
	if planet.getName() == 'kaas':
		kaasLocations(core, planet)	
				
				
def tatooineLocations(core, planet):

	mapService = core.mapService
	
	# Cities
	
	mapService.addLocation(planet, 'Bestine', -1290, -3590, 17, 0, 0)
	mapService.addLocation(planet, 'Mos Espa', -2902, 2130, 17, 0, 0)
	mapService.addLocation(planet, 'Mos Entha', 1291, 3138, 17, 0, 0)
	mapService.addLocation(planet, 'Wayfar', -5124, -6530, 17, 0, 0)
	mapService.addLocation(planet, 'Mos Eisley', 3528, -4804, 17, 0, 0)
	mapService.addLocation(planet, 'Anchorhead', 40, -5348, 17, 0, 0)
	mapService.addLocation(planet, 'Mos Taike', 3813, 2354, 17, 0, 0)

def corelliaLocations(core, planet):

	mapService = core.mapService
	
	# Cities
	
	mapService.addLocation(planet, 'Coronet', -178, -4504, 17, 0, 0)
	mapService.addLocation(planet, 'Tyrena', -5140, -2450, 17, 0, 0)
	mapService.addLocation(planet, 'Bela Vistal', 6766, -5692, 17, 0, 0)
	mapService.addLocation(planet, 'Kor Vella', -3420, 3146, 17, 0, 0)
	mapService.addLocation(planet, 'Doaba Guerfel', 3274, 5582, 17, 0, 0)
	mapService.addLocation(planet, 'Vreni Island', -5538, -6176, 17, 0, 0)
	
def nabooLocations(core, planet):

	mapService = core.mapService
	
	# Cities
	
	mapService.addLocation(planet, 'Theed', -5488, 4380, 17, 0, 0)
	mapService.addLocation(planet, 'Keren', 1888, 2700, 17, 0, 0)
	mapService.addLocation(planet, 'Moenia', 4836, -4830.5, 17, 0, 0)
	mapService.addLocation(planet, 'Deeja Peak', 4686, -1375, 17, 0, 0)
	mapService.addLocation(planet, 'Kaadara', 5288, 6687, 17, 0, 0)

def roriLocations(core, planet):

	mapService = core.mapService
	
	# Cities
	
	mapService.addLocation(planet, 'Narmle', -5140, -2368, 17, 0, 0)
	mapService.addLocation(planet, 'Restuss', 5318, 5680, 17, 0, 0)
	mapService.addLocation(planet, 'Rebel Outpost', 3677, -6447, 17, 0, 0)
	
def endorLocations(core, planet):

	mapService = core.mapService
	
	# Cities
	
	mapService.addLocation(planet, 'an Outpost', -905, 1584, 17, 0, 0)

def talusLocations(core, planet):

	mapService = core.mapService
	
	# Cities
	
	mapService.addLocation(planet, 'Dearic', 422, -3004, 17, 0, 0)
	mapService.addLocation(planet, 'Nashal', 4163, 5220, 17, 0, 0)
	mapService.addLocation(planet, 'Imperial Outpost', -2178, 2300, 17, 0, 0)
	
def yavin4Locations(core, planet):

	mapService = core.mapService
	
	# Cities
	
	mapService.addLocation(planet, 'Mining Outpost', -312, 4865, 17, 0, 0)
	mapService.addLocation(planet, 'Labor Outpost', -6925, -5707, 17, 0, 0)
	
def dantooineLocations(core, planet):

	mapService = core.mapService
	
	# Cities
	
	mapService.addLocation(planet, 'Mining Outpost', -640, 2486, 17, 0, 0)
	mapService.addLocation(planet, 'Pirate Outpost', 1588, -6399, 17, 0, 0)
	mapService.addLocation(planet, 'Imperial Outpost', -4224, -2400, 17, 0, 0)
	
def dathomirLocations(core, planet):

	mapService = core.mapService
	
	# Cities
	
	mapService.addLocation(planet, 'a restricted area', -6358, 930, 17, 0, 0)
	mapService.addLocation(planet, 'Trade Outpost', 599, 3046, 17, 0, 0)
	mapService.addLocation(planet, 'Science Outpost', -85, -1600, 17, 0, 0)	

def lokLocations(core, planet):

	mapService = core.mapService
	
	# Cities
	
	mapService.addLocation(planet, 'Nym\'s Stronghold', 440, 5029, 17, 0, 0)
	mapService.addLocation(planet, 'Imperial outpost', -1920, -3084, 17, 0, 0)
	
def kaasLocations(core, planet):
	mapService = core.mapService
	
	# Cities
	
	mapService.addLocation(planet, 'Imperial Outpost', -5118, -2386, 17, 0, 0)
	
	
	
	
