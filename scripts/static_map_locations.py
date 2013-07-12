import sys

def addLocations(core, planet):
	
	if planet.getName() == 'tatooine':
		tatooineLocations(core, planet)
		
def tatooineLocations(core, planet):

	mapService = core.mapService
	
	# Cities
	
	mapService.addLocation(planet, 'Bestine', -1290, -3590, 17, 0, 0)
	mapService.addLocation(planet, 'Mos Espa', -2902, 2130, 17, 0, 0)
	mapService.addLocation(planet, 'Mos Entha', 1291, 3138, 17, 0, 0)
	mapService.addLocation(planet, 'Wayfar', -5124, -6530, 17, 0, 0)
	mapService.addLocation(planet, 'Mos Eisley', 3528, -4804, 17, 0, 0)
	mapService.addLocation(planet, 'Anchorhead', 40, -5348, 17, 0, 0)

	