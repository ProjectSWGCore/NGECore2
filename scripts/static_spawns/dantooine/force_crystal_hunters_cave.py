from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):

	stcSvc = core.staticService
	objSvc = core.objectService
	
	hunter1 = stcSvc.spawnObject('object/mobile/shared_dressed_dark_force_crystal_hunter.iff', 'dantooine', long(8535483), float(0), float(0), float(2), float(0.71), float(0.71))
	hunter2 = stcSvc.spawnObject('object/mobile/shared_dressed_dark_force_crystal_hunter.iff', 'dantooine', long(8535483), float(3), float(2), float(4), float(0.71), float(0.71))
	
	wuher = stcSvc.spawnObject('object/mobile/shared_dressed_dark_force_crystal_hunter.iff', 'dantooine', long(8535483), float(8.6), float(-0.9), float(7), float(0.71), float(0.71))
	wuher.setCustomName2('Wuher')
	wuher.setOptionsBitmask(256)
	
	#hunter1.setCustomName2('Reelo Baruk')
	#hunter1.setOptionsBitmask(264)