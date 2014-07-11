import sys
# Project SWG:   MosEisley mites:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	
	# Mound Mites
	mound_mite1 = stcSvc.spawnObject('mound_mite', 'tatooine', long(0), float(3785), float(57), float(-5057), float(0), float(0), float(0), float(0), 30)	
	mound_mite11 = stcSvc.spawnObject('mound_mite', 'tatooine', long(0), float(3775), float(55), float(-5067), float(0), float(0), float(0), float(0), 30)	
	mound_mite12 = stcSvc.spawnObject('mound_mite', 'tatooine', long(0), float(3765), float(53), float(-5077), float(0), float(0), float(0), float(0), 30)	
	mound_mite13 = stcSvc.spawnObject('mound_mite', 'tatooine', long(0), float(3795), float(56), float(-5047), float(0), float(0), float(0), float(0), 30)	
	mound_mite14 = stcSvc.spawnObject('mound_mite', 'tatooine', long(0), float(3805), float(53), float(-5037), float(0), float(0), float(0), float(0), 30)	
	mound_mite15 = stcSvc.spawnObject('mound_mite', 'tatooine', long(0), float(3780), float(58), float(-5050), float(0), float(0), float(0), float(0), 30)	
	mound_mite16 = stcSvc.spawnObject('mound_mite', 'tatooine', long(0), float(3790), float(55), float(-5040), float(0), float(0), float(0), float(0), 30)	
	
	# Mound Mites
	mound_mite2 = stcSvc.spawnObject('mound_mite', 'tatooine', long(0), float(3560), float(63), float(-4339), float(0), float(0), float(0), float(0), 30)	
	mound_mite21 = stcSvc.spawnObject('mound_mite', 'tatooine', long(0), float(3550), float(63), float(-4349), float(0), float(0), float(0), float(0), 30)	
	mound_mite22 = stcSvc.spawnObject('mound_mite', 'tatooine', long(0), float(3540), float(63), float(-4359), float(0), float(0), float(0), float(0), 30)	
	mound_mite23 = stcSvc.spawnObject('mound_mite', 'tatooine', long(0), float(3570), float(63), float(-4329), float(0), float(0), float(0), float(0), 30)	
	mound_mite24 = stcSvc.spawnObject('mound_mite', 'tatooine', long(0), float(3580), float(63), float(-4319), float(0), float(0), float(0), float(0), 30)	
	mound_mite25 = stcSvc.spawnObject('mound_mite', 'tatooine', long(0), float(3555), float(63), float(-4345), float(0), float(0), float(0), float(0), 30)	
	mound_mite26 = stcSvc.spawnObject('mound_mite', 'tatooine', long(0), float(3565), float(63), float(-4325), float(0), float(0), float(0), float(0), 30)	
	
	
	# Legacy RockMites
	
	rockmite = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3140), float(5), float(-4907), float(0), float(0), float(0), float(0), 30)	
	rockmite1 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3150), float(5), float(-4917), float(0), float(0), float(0), float(0), 30)	
	rockmite2 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3160), float(5), float(-4927), float(0), float(0), float(0), float(0), 30)	
	rockmite3 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3130), float(5), float(-4897), float(0), float(0), float(0), float(0), 30)	
	rockmite4 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3120), float(5), float(-4887), float(0), float(0), float(0), float(0), 30)	
	rockmite5 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3145), float(5), float(-4900), float(0), float(0), float(0), float(0), 30)	
	rockmite6 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3155), float(5), float(-4908), float(0), float(0), float(0), float(0), 30)	
	
	# Legacy RockMites
	rockmite2 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3710), float(5), float(-4652), float(0), float(0), float(0), float(0), 30)	
	rockmite21 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3724), float(5), float(-4661), float(0), float(0), float(0), float(0), 30)	
	rockmite22 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3733), float(5), float(-4659), float(0), float(0), float(0), float(0), 30)	
	rockmite23 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3747), float(5), float(-4655), float(0), float(0), float(0), float(0), 30)	
	rockmite24 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3752), float(5), float(-4645), float(0), float(0), float(0), float(0), 30)	
	rockmite25 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3761), float(5), float(-4644), float(0), float(0), float(0), float(0), 30)	
	rockmite26 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3762), float(5), float(-4654), float(0), float(0), float(0), float(0), 30)	
	rockmite27 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3732), float(5), float(-4664), float(0), float(0), float(0), float(0), 30)	
	rockmite28 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3741), float(5), float(-4663), float(0), float(0), float(0), float(0), 30)	
	rockmite29 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3752), float(5), float(-4659), float(0), float(0), float(0), float(0), 30)	
	rockmite210 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3769), float(5), float(-4650), float(0), float(0), float(0), float(0), 30)	
	rockmite211 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3778), float(5), float(-4647), float(0), float(0), float(0), float(0), 30)	


	
	return