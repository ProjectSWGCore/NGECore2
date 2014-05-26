import sys
from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):
	stcSvc = core.staticService
	objSvc = core.objectService

	stormtrooper1 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4211), float(3), float(-2384), float(0.011), float(0.999))
	stormtrooper2 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4212), float(3), float(-2384), float(0.011), float(0.999))
	stormtrooper3 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4211), float(3), float(-2386), float(0.011), float(0.999))
	stormtrooper4 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4212), float(3), float(-2386), float(0.011), float(0.999))
	stormtrooper5 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4212), float(3), float(-2388), float(0.011), float(0.999))
	
	stormtrooper6 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4229), float(3), float(-2394), float(0.703), float(0.710))
	corporal1 = stcSvc.spawnObject('eow_fbase_imperial_corporal', 'dantooine', long(0), float(-4228), float(3), float(-2395), float(0.703), float(0.710))
	corporal2 = stcSvc.spawnObject('eow_fbase_imperial_corporal', 'dantooine', long(0), float(-4230), float(3), float(-2394), float(0.703), float(0.710))
	
	recruiter1 = stcSvc.spawnObject('object/mobile/shared_dressed_imperial_officer_f.iff', 'dantooine', long(0), float(-4198), float(3), float(-2411), float(0.819), float(-0.572))
	
	atst1 = stcSvc.spawnObject('object/mobile/shared_atst.iff', 'dantooine', long(0), float(-4291), float(3), float(-2433), float(0.819), float(-0.572))
	atst2 = stcSvc.spawnObject('object/mobile/shared_atst.iff', 'dantooine', long(0), float(-4154), float(3), float(-2383), float(0.264), float(0.964))
	
	r5u0 = stcSvc.spawnObject('object/creature/npc/droid/shared_r4_base.iff', 'dantooine', long(0), float(-4231), float(3), float(-2363), float(0.264), float(0.964))
	r4h3 = stcSvc.spawnObject('object/creature/npc/droid/shared_r5_base.iff', 'dantooine', long(0), float(-4215), float(3), float(-2363), float(0.264), float(0.964))
		
	commoner1 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_human_male_01.iff', 'dantooine', long(0), float(-4224), float(3), float(-2366), float(0.264), float(0.964)) 
	commoner1.setOptionsBitmask(256)
	commoner2 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_human_female_01.iff', 'dantooine', long(0), float(-4238), float(3), float(-2365), float(0.264), float(0.964)) 
	commoner2.setOptionsBitmask(256)
	return	
