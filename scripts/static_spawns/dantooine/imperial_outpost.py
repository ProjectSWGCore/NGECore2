import sys
from resources.datatables import Options
from resources.datatables import State
from java.util import Vector
from engine.resources.scene import Point3D


def addPlanetSpawns(core, planet):
	stcSvc = core.staticService
	objSvc = core.objectService
	aiSvc = core.aiService

	stormtrooper1 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4211), float(3), float(-2384), float(0.011), float(0.999))
	stormtrooper2 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4212), float(3), float(-2384), float(0.011), float(0.999))
	stormtrooper3 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4211), float(3), float(-2386), float(0.011), float(0.999))
	stormtrooper4 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4212), float(3), float(-2386), float(0.011), float(0.999))
	stormtrooper5 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4212), float(3), float(-2388), float(0.011), float(0.999))
	
	patrolpoints1 = Vector()
	patrolpoints1.add(Point3D(float(-4209), float(3), float(-2360)))
	patrolpoints1.add(Point3D(float(-4225), float(3), float(-2396)))
	patrolpoints1.add(Point3D(float(-4272), float(3), float(-2396)))
	patrolpoints1.add(Point3D(float(-4272), float(3), float(-2418)))
	patrolpoints1.add(Point3D(float(-4203), float(3), float(-2418)))
	patrolpoints1.add(Point3D(float(-4203), float(3), float(-2396)))
	patrolpoints1.add(Point3D(float(-4225), float(3), float(-2396)))
	patrolpoints1.add(Point3D(float(-4242), float(3), float(-2361)))
	
	aiSvc.setPatrol(stormtrooper1, patrolpoints1)	
	aiSvc.setPatrol(stormtrooper2, patrolpoints1)	
	aiSvc.setPatrol(stormtrooper3, patrolpoints1)	
	aiSvc.setPatrol(stormtrooper4, patrolpoints1)	
	aiSvc.setPatrol(stormtrooper5, patrolpoints1)
	
	stormtrooper6 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4229), float(3), float(-2394), float(0.703), float(0.710))
	corporal1 = stcSvc.spawnObject('eow_fbase_imperial_corporal', 'dantooine', long(0), float(-4228), float(3), float(-2395), float(0.703), float(0.710))
	corporal2 = stcSvc.spawnObject('eow_fbase_imperial_corporal', 'dantooine', long(0), float(-4230), float(3), float(-2394), float(0.703), float(0.710))
	
	patrolpoints6 = Vector()
	patrolpoints6.add(Point3D(float(-4209), float(3), float(-2360)))
	patrolpoints6.add(Point3D(float(-4245), float(3), float(-2382)))
	patrolpoints6.add(Point3D(float(-4212), float(3), float(-2388)))
	
	aiSvc.setPatrol(stormtrooper6, patrolpoints6)
	aiSvc.setPatrol(corporal1, patrolpoints6)
	aiSvc.setPatrol(corporal2, patrolpoints6)
	
	#Group of 3
	stormtrooper7 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4212), float(3), float(-2395), float(0.011), float(0.999))
	stormtrooper8 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4212), float(3), float(-2396), float(0.011), float(0.999))
	stormtrooper9 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4214), float(3), float(-2396), float(0.011), float(0.999))
	
	patrolpoints7 = Vector()
	patrolpoints7.add(Point3D(float(-4177), float(3), float(-2396)))
	patrolpoints7.add(Point3D(float(-4177), float(3), float(-2419)))
	patrolpoints7.add(Point3D(float(-4246), float(3), float(-2417)))
	patrolpoints7.add(Point3D(float(-4246), float(3), float(-2396)))
	
	aiSvc.setPatrol(stormtrooper7, patrolpoints7)
	aiSvc.setPatrol(stormtrooper8, patrolpoints7)
	aiSvc.setPatrol(stormtrooper9, patrolpoints7)
	
	
	stormtrooper10 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4242), float(3), float(-2420), float(0.011), float(0.999))
	stormtrooper11 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4242), float(3), float(-2418), float(0.011), float(0.999))
	corporal3 = stcSvc.spawnObject('eow_fbase_imperial_corporal', 'dantooine', long(0), float(-4240), float(3), float(-2420), float(0.011), float(0.999))
	stormtrooper12 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4240), float(3), float(-2418), float(0.011), float(0.999))
	stormtrooper13 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4238), float(3), float(-2420), float(0.703), float(0.710))
	
	patrolpoints8 = Vector()
	patrolpoints8.add(Point3D(float(-4273), float(3), float(-2418)))
	patrolpoints8.add(Point3D(float(-4273), float(3), float(-2396)))
	patrolpoints8.add(Point3D(float(-4177), float(3), float(-2396)))
	patrolpoints8.add(Point3D(float(-4177), float(3), float(-2418)))
	patrolpoints8.add(Point3D(float(-4242), float(3), float(-2420)))
	
	aiSvc.setPatrol(stormtrooper10, patrolpoints8)
	aiSvc.setPatrol(stormtrooper11, patrolpoints8)
	aiSvc.setPatrol(stormtrooper12, patrolpoints8)
	aiSvc.setPatrol(stormtrooper13, patrolpoints8)
	aiSvc.setPatrol(corporal3, patrolpoints8)
	
	
	#Outside perimeter patrol
	stormtrooper14 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4282), float(3), float(-2366), float(0.011), float(0.999))
	stormtrooper15 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4284), float(3), float(-2366), float(0.011), float(0.999))
	stormtrooper16 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4284), float(3), float(-2362), float(0.011), float(0.999))
	
	patrolpoints9 = Vector()
	patrolpoints9.add(Point3D(float(-4298), float(3), float(-2399)))
	patrolpoints9.add(Point3D(float(-4299), float(3), float(-2418)))
	patrolpoints9.add(Point3D(float(-4266), float(3), float(-2453)))
	patrolpoints9.add(Point3D(float(-4180), float(3), float(-2451)))
	patrolpoints9.add(Point3D(float(-4146), float(3), float(-2420)))
	patrolpoints9.add(Point3D(float(-4146), float(3), float(-2396)))
	patrolpoints9.add(Point3D(float(-4195), float(3), float(-2332)))
	patrolpoints9.add(Point3D(float(-4258), float(3), float(-2333)))
	
	aiSvc.setPatrol(stormtrooper14, patrolpoints9)
	aiSvc.setPatrol(stormtrooper15, patrolpoints9)
	aiSvc.setPatrol(stormtrooper16, patrolpoints9)
	
		
	atst1 = stcSvc.spawnObject('fbase_at_st', 'dantooine', long(0), float(-4291), float(3), float(-2433), float(0.819), float(-0.572))
	atst2 = stcSvc.spawnObject('fbase_at_st', 'dantooine', long(0), float(-4154), float(3), float(-2383), float(0.264), float(0.964))
	
	aiSvc.setLoiter(atst1, float(5), float(10))
	aiSvc.setLoiter(atst2, float(5), float(10))
	
	r5u0 = stcSvc.spawnObject('r4_base', 'dantooine', long(0), float(-4231), float(3), float(-2363), float(0.264), float(0.964))
	r4h3 = stcSvc.spawnObject('r5_base', 'dantooine', long(0), float(-4215), float(3), float(-2363), float(0.264), float(0.964))
		
	aiSvc.setLoiter(r5u0, float(4), float(5))
	aiSvc.setLoiter(r4h3, float(4), float(5))
	
	commoner1 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_human_male_01.iff', 'dantooine', long(0), float(-4224), float(3), float(-2366), float(0.264), float(0.964)) 
	commoner1.setOptionsBitmask(Options.INVULNERABLE)
	commoner2 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_human_female_01.iff', 'dantooine', long(0), float(-4238), float(3), float(-2365), float(0.264), float(0.964)) 
	commoner2.setOptionsBitmask(Options.INVULNERABLE)
	commoner3 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_fat_human_female_01.iff', 'dantooine', long(0), float(-4229), float(3), float(-2421), float(0.264), float(0.964)) 
	commoner3.setOptionsBitmask(Options.INVULNERABLE)
	commoner4 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_old_human_female_02.iff', 'dantooine', long(0), float(-4212), float(3), float(-2411), float(0.264), float(0.964)) 
	commoner4.setOptionsBitmask(Options.INVULNERABLE)
	commoner5 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_old_human_male_01.iff', 'dantooine', long(0), float(-4186), float(3), float(-2428), float(0.264), float(0.964)) 
	commoner5.setOptionsBitmask(Options.INVULNERABLE)
	commoner6 = stcSvc.spawnObject('object/mobile/shared_dressed_commoner_old_human_male_02.iff', 'dantooine', long(0), float(-4245), float(3), float(-2371), float(0.264), float(0.964)) 
	commoner6.setOptionsBitmask(Options.INVULNERABLE)
	
	stormtrooper17 = stcSvc.spawnObject('eow_scout_trooper', 'dantooine', long(0), float(-4217), float(3), float(-2431), float(0.011), float(0.999))
	stormtrooper18 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4184), float(3), float(-2388), float(0.935), float(-0.354))
	
	juntah_herm =  stcSvc.spawnObject('juntah_herm', 'dantooine', long(0), float(-4223), float(3), float(-2380), float(0.992), float(-0.124)) 
	bek_rabor = stcSvc.spawnObject('bek_rabor', 'dantooine', long(0), float(-4228), float(3), float(-2380), float(0.992), float(-0.124)) 

	return	
