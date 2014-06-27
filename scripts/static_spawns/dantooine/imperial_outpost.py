import sys
from resources.datatables import Options
from resources.datatables import State
from java.util import Vector
import engine.resources.scene.Point3D


def addPlanetSpawns(core, planet):
	stcSvc = core.staticService
	objSvc = core.objectService
	aiSvc = core.aiService

	stormtrooper1 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4211), float(3), float(-2384), float(0.011), float(0.999))
	stormtrooper2 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4212), float(3), float(-2384), float(0.011), float(0.999))
	stormtrooper3 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4211), float(3), float(-2386), float(0.011), float(0.999))
	stormtrooper4 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4212), float(3), float(-2386), float(0.011), float(0.999))
	stormtrooper5 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4212), float(3), float(-2388), float(0.011), float(0.999))
	
	point1_1 = engine.resources.scene.Point3D(float(-4209), float(3), float(-2360))
	point1_2 = engine.resources.scene.Point3D(float(-4225), float(3), float(-2396))
	point1_3 = engine.resources.scene.Point3D(float(-4272), float(3), float(-2396))
	point1_4 = engine.resources.scene.Point3D(float(-4272), float(3), float(-2418))
	point1_5 = engine.resources.scene.Point3D(float(-4203), float(3), float(-2418))
	point1_6 = engine.resources.scene.Point3D(float(-4203), float(3), float(-2396))
	point1_7 = engine.resources.scene.Point3D(float(-4225), float(3), float(-2396))
	point1_8 = engine.resources.scene.Point3D(float(-4242), float(3), float(-2361))

	patrolpoints1 = Vector()
	patrolpoints1.add(point1_1)
	patrolpoints1.add(point1_2)
	patrolpoints1.add(point1_3)
	patrolpoints1.add(point1_4)
	patrolpoints1.add(point1_5)
	patrolpoints1.add(point1_6)
	patrolpoints1.add(point1_7)
	patrolpoints1.add(point1_8)
	aiSvc.setPatrol(stormtrooper1, patrolpoints1)
	
	aiSvc.setPatrol(stormtrooper2, patrolpoints1)
	
	aiSvc.setPatrol(stormtrooper3, patrolpoints1)
	
	aiSvc.setPatrol(stormtrooper4, patrolpoints1)
	
	aiSvc.setPatrol(stormtrooper5, patrolpoints1)
	
	stormtrooper6 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4229), float(3), float(-2394), float(0.703), float(0.710))
	corporal1 = stcSvc.spawnObject('eow_fbase_imperial_corporal', 'dantooine', long(0), float(-4228), float(3), float(-2395), float(0.703), float(0.710))
	corporal2 = stcSvc.spawnObject('eow_fbase_imperial_corporal', 'dantooine', long(0), float(-4230), float(3), float(-2394), float(0.703), float(0.710))
	
	point6_1 = engine.resources.scene.Point3D(float(-4209), float(3), float(-2360))
	point6_2 = engine.resources.scene.Point3D(float(-4245), float(3), float(-2382))
	point6_3 = engine.resources.scene.Point3D(float(-4212), float(3), float(-2388))
	patrolpoints6 = Vector()
	patrolpoints6.add(point6_1)
	patrolpoints6.add(point6_2)
	patrolpoints6.add(point6_3)
	aiSvc.setPatrol(stormtrooper6, patrolpoints6)

	aiSvc.setPatrol(corporal1, patrolpoints6)

	aiSvc.setPatrol(corporal2, patrolpoints6)
	
	
	stormtrooper7 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4199), float(3), float(-2396), float(0.011), float(0.999))
	stormtrooper8 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4199), float(3), float(-2395), float(0.011), float(0.999))
	stormtrooper9 = stcSvc.spawnObject('eow_fbase_stormtrooper', 'dantooine', long(0), float(-4199), float(3), float(-2394), float(0.011), float(0.999))
	point7_1 = engine.resources.scene.Point3D(float(-4209), float(3), float(-2360))
	point7_2 = engine.resources.scene.Point3D(float(-4225), float(3), float(-2396))
	point7_3 = engine.resources.scene.Point3D(float(-4272), float(3), float(-2396))
	point7_4 = engine.resources.scene.Point3D(float(-4272), float(3), float(-2418))
	point7_5 = engine.resources.scene.Point3D(float(-4203), float(3), float(-2418))
	point7_6 = engine.resources.scene.Point3D(float(-4203), float(3), float(-2396))
	point7_7 = engine.resources.scene.Point3D(float(-4225), float(3), float(-2396))
	point7_8 = engine.resources.scene.Point3D(float(-4242), float(3), float(-2361))

	patrolpoints7 = Vector()
	patrolpoints7.add(point7_1)
	patrolpoints7.add(point7_2)
	patrolpoints7.add(point7_3)
	patrolpoints7.add(point7_4)
	patrolpoints7.add(point7_5)
	patrolpoints7.add(point7_6)
	patrolpoints7.add(point7_7)
	patrolpoints7.add(point7_8)
	
	aiSvc.setPatrol(stormtrooper7, patrolpoints7)
	aiSvc.setPatrol(stormtrooper8, patrolpoints7)
	aiSvc.setPatrol(stormtrooper9, patrolpoints7)
	
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
	
	juntah_herm =  stcSvc.spawnObject('juntah_herm', 'dantooine', long(0), float(-4223), float(3), float(-2380), float(0.264), float(0.964)) 

	bek_rabor = stcSvc.spawnObject('bek_rabor', 'dantooine', long(0), float(-4228), float(3), float(-2380), float(0.264), float(0.964)) 

	return	
