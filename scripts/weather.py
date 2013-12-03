import sys
 
def init(core):
	weatherSvc = core.weatherService
	weatherSvc.addPlanetSettings('tatooine', 75, 0)
	weatherSvc.addPlanetSettings('naboo', 85, 0)
	weatherSvc.addPlanetSettings('corellia', 85, 1)
	weatherSvc.addPlanetSettings('dantooine', 80, 1)
	weatherSvc.addPlanetSettings('rori', 75, 2)
	weatherSvc.addPlanetSettings('talus', 75, 2)
	weatherSvc.addPlanetSettings('lok', 75, 2)
	weatherSvc.addPlanetSettings('dathomir', 50, 3)
	weatherSvc.addPlanetSettings('endor', 80, 0)
	weatherSvc.addPlanetSettings('yavin4', 65, 2)
	weatherSvc.addPlanetSettings('kashyyyk_main', 80, 0)
   
