
# Project SWG:   MosEisley mites:  Static Spawns
# (C)2014 ProjectSWG

import sys
from resources.datatables import Options
from resources.datatables import State
from java.util import Vector
from engine.resources.scene import Point3D

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	aiSvc = core.aiService
	
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
	rockmite25 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3761), float(5), float(-4644), float(0), float(0), float(0), float(0), 30)	
	rockmite26 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3762), float(5), float(-4654), float(0), float(0), float(0), float(0), 30)	
	rockmite27 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3732), float(5), float(-4664), float(0), float(0), float(0), float(0), 30)	
	rockmite28 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3741), float(5), float(-4663), float(0), float(0), float(0), float(0), 30)	
	rockmite29 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3752), float(5), float(-4659), float(0), float(0), float(0), float(0), 30)	
	rockmite210 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3769), float(5), float(-4650), float(0), float(0), float(0), float(0), 30)	
	rockmite211 = stcSvc.spawnObject('rockmite', 'tatooine', long(0), float(3778), float(5), float(-4647), float(0), float(0), float(0), float(0), 30)	

	aiSvc.setLoiter(mound_mite1, float(1), float(8))
	aiSvc.setLoiter(mound_mite11, float(1), float(8)) 
	aiSvc.setLoiter(mound_mite12, float(1), float(8)) 
	aiSvc.setLoiter(mound_mite13, float(1), float(8)) 
	aiSvc.setLoiter(mound_mite14, float(1), float(8)) 
	aiSvc.setLoiter(mound_mite15, float(1), float(8)) 
	aiSvc.setLoiter(mound_mite16, float(1), float(8)) 
	
	aiSvc.setLoiter(mound_mite2, float(1), float(8))  
	aiSvc.setLoiter(mound_mite21, float(1), float(8))
	aiSvc.setLoiter(mound_mite22, float(1), float(8)) 
	aiSvc.setLoiter(mound_mite23, float(1), float(8)) 
	aiSvc.setLoiter(mound_mite24, float(1), float(8)) 
	aiSvc.setLoiter(mound_mite25, float(1), float(8)) 
	aiSvc.setLoiter(mound_mite26, float(1), float(8)) 
	
	aiSvc.setLoiter(rockmite, float(1), float(8)) 
	aiSvc.setLoiter(rockmite1, float(1), float(8))
	aiSvc.setLoiter(rockmite2, float(1), float(8)) 
	aiSvc.setLoiter(rockmite3, float(1), float(8))
	aiSvc.setLoiter(rockmite4, float(1), float(8))
	aiSvc.setLoiter(rockmite5, float(1), float(8))
	aiSvc.setLoiter(rockmite6, float(1), float(8))

	aiSvc.setLoiter(rockmite2, float(1), float(8)) 
	aiSvc.setLoiter(rockmite21, float(1), float(8))
	aiSvc.setLoiter(rockmite22, float(1), float(8)) 
	aiSvc.setLoiter(rockmite23, float(1), float(8))
	aiSvc.setLoiter(rockmite25, float(1), float(8))
	aiSvc.setLoiter(rockmite26, float(1), float(8))
	aiSvc.setLoiter(rockmite27, float(1), float(8))
	aiSvc.setLoiter(rockmite28, float(1), float(8)) 
	aiSvc.setLoiter(rockmite29, float(1), float(8))
	aiSvc.setLoiter(rockmite210, float(1), float(8))
	aiSvc.setLoiter(rockmite211, float(1), float(8))
	
	return