import sys
import java.util.Random

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService	
	random = java.util.Random()
	 
	mintime = 14400
	maxtime = 25200
	
	respawntimer = random.nextInt((maxtime - mintime) + 1800)
		
	stcSvc.spawnObject('restuss_emperors_hand', 'rori', long(0), float(5283), float(80), float(5746), float(0.641), float(0.767),float(0),float(0), int(respawntimer))	
	return