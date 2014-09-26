import sys
# Project SWG:   MosEisley Quest:  Static Spawns
# (C)2014 ProjectSWG

# This script is intended for any quest conversation mobiles, or single attackable npcs for a quest, within Mos Eisley
def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# Talk to Pall
	pall = stcSvc.spawnObject('majolnir', 'tatooine', long(0), float(3526), float(5), float(-4619), float(0), float(0), float(0), float(0), 1)	
	
	# Bib's Offer
	vourk = stcSvc.spawnObject('vourk', 'tatooine', long(0), float(3521.0), float(5.0), float(-4821.0), float(0.42), float(0.91))

	# Byxle Pedette
	byxle = stcSvc.spawnObject('byxle', 'tatooine', long(0), float(3365), float(5), float(-4639), float(0.99), float(0.12)) 
	
	# Drixa Dreul
	drixa = stcSvc.spawnObject('drixa', 'tatooine', long(0), float(3259), float(5), float(-4863), float(-0.38), float(0.92))
	
	# Nogri Hessen
	nogri = stcSvc.spawnObject('nogri', 'tatooine', long(0), float(3449), float(4), float(-5076), float(-0.38), float(0.92))
	
	# Jano Bix
	jano = stcSvc.spawnObject('jano', 'tatooine', long(0), float(3186), float(5), float(-4781), float(-0.38), float(0.92))
	
	# Kaleb
	kaleb = stcSvc.spawnObject('kaleb', 'tatooine', long(0), float(3216), float(5), float(-4610), float(0.25), float(1))
	return