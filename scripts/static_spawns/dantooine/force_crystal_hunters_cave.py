from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService		
	hunter1 = stcSvc.spawnObject('force_sensitive_crypt_crawler', 'dantooine', long(8535484), float(26), float(-41), float(-68), float(2.77), float(1.82))
	hunter2 = stcSvc.spawnObject('untrained_wielder_of_the_dark_side', 'dantooine', long(8535484), float(23), float(-41), float(-65), float(1.52), float(2.94))	
	hunter3 = stcSvc.spawnObject('untrained_wielder_of_the_dark_side', 'dantooine', long(8535484), float(47), float(-48), float(-65), float(0.72), float(-0.68))	
	hunter4 = stcSvc.spawnObject('untrained_wielder_of_the_dark_side', 'dantooine', long(8535484), float(49.12), float(-47.48), float(-13.41), float(1), float(-0.05))	
	hunter5 = stcSvc.spawnObject('dark_force_crystal_hunter', 'dantooine', long(8535485), float(93.10), float(-61.87), float(-14.25), float(-0.7), float(0.7))
	hunter6 = stcSvc.spawnObject('force_sensitive_crypt_crawler', 'dantooine', long(8535485), float(62.2), float(-69.0), float(-37.8), float(0.68), float(0.72))	
	hunter7 = stcSvc.spawnObject('dark_force_crystal_hunter', 'dantooine', long(8535484), float(52.14), float(-67.8), float(-42.9), float(0.51), float(0.86))	
	hunter8 = stcSvc.spawnObject('force_sensitive_crypt_crawler', 'dantooine', long(8535486), float(84.94), float(-77.09), float(-63.28), float(-0.19), float(0.98))	
	hunter9 = stcSvc.spawnObject('force_sensitive_crypt_crawler', 'dantooine', long(8535486), float(65.30), float(-76.16), float(-70.15), float(0.38), float(0.92))	
	hunter10 = stcSvc.spawnObject('untrained_wielder_of_the_dark_side', 'dantooine', long(8535486), float(65.636), float(-76.87), float(-81.40), float(-0.123), float(0.99))	
	hunter11 = stcSvc.spawnObject('dark_force_crystal_hunter', 'dantooine', long(8535487), float(80.34), float(-77.29), float(-92.77), float(-0.2), float(0.97))	
	hunter12 = stcSvc.spawnObject('dark_force_crystal_hunter', 'dantooine', long(8535487), float(80.34), float(-77.29), float(-92.77), float(-0.2), float(0.97))
	objSvc = core.objectService
	magSeal1 = objSvc.getObject(200335); #(200336-1)	
	if magSeal1 != None:
		lootPoolNames_1 = ['Junk']
		lootPoolChances_1 = [100]
		lootGroupChance_1 = 90
		magSeal1.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
		lotSvc = core.lootService
		lotSvc.handleContainer(200335,'Junk','dantooine')
		
	return
	