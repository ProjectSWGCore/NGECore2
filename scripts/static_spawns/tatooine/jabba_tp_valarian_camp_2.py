import sys
# Project SWG:   Jabba TP Valarian Camp 2:  Static Spawns
# (C)2014 ProjectSWG



from resources.datatables import Options
from resources.datatables import State

def addPlanetSpawns(core, planet):


	stcSvc = core.staticService
	objSvc = core.objectService
	
	# TODO Check all NPCs for personalized scripting, change format.
	
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-3906.3), float(30.8), float(-4364), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_swooper', 'tatooine', long(0), float(-3913.1), float(30.8), float(-4360.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_scout', 'tatooine', long(0), float(-3918.5), float(31), float(-4355.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-3927.8), float(27.7), float(-4346), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_enforcer', 'tatooine', long(0), float(-3932.2), float(22.6), float(-4331.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-3924.2), float(21), float(-4324.7), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_assassin', 'tatooine', long(0), float(-3911.9), float(21.8), float(-4322.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_henchman', 'tatooine', long(0), float(-3897.1), float(23.9), float(-4322.3), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-3881.1), float(25.9), float(-4322.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-3871.6), float(23.2), float(-4302.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_swooper', 'tatooine', long(0), float(-3860.4), float(19.4), float(-4294.7), float(0), float(0), float(0), float(0), 45)	
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-3873.2), float(20.4), float(-4288.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_scout', 'tatooine', long(0), float(-3882.1), float(22.6), float(-4287.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-3888.6), float(22.9), float(-4279.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_enforcer', 'tatooine', long(0), float(-3889.3), float(20), float(-4259.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_worker', 'tatooine', long(0), float(-3888.7), float(19.4), float(-4221.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_assassin', 'tatooine', long(0), float(-3903.4), float(18.8), float(-4227.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_scout', 'tatooine', long(0), float(-3915.2), float(16.6), float(-4245), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-3913.9), float(16.8), float(-4255.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-3907), float(19.2), float(-4266.1), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_scout', 'tatooine', long(0), float(-3898.3), float(21.4), float(-4278.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-3893.9), float(22.5), float(-4287.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_enforcer', 'tatooine', long(0), float(-3878.3), float(19.8), float(-4250.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-3876.4), float(19.6), float(-4237), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_assassin', 'tatooine', long(0), float(-3898), float(30.1), float(-4347.8), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-3902.9), float(27.3), float(-4338.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_swooper', 'tatooine', long(0), float(-3919.5), float(19.8), float(-4307.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_henchman', 'tatooine', long(0), float(-3917.6), float(20.1), float(-4293.2), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_worker', 'tatooine', long(0), float(-3913.1), float(20.2), float(-4280.7), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-3928.4), float(17.7), float(-4265), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_scout', 'tatooine', long(0), float(-3927.5), float(16.4), float(-4240.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-3947.4), float(18.5), float(-4256.9), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_henchman', 'tatooine', long(0), float(-3949), float(20.7), float(-4270.5), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_depot_worker', 'tatooine', long(0), float(-3947.1), float(22.3), float(-4291.6), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_assassin', 'tatooine', long(0), float(-3951.7), float(22.4), float(-4311.4), float(0), float(0), float(0), float(0), 45)
	stcSvc.spawnObject('valarian_soldier', 'tatooine', long(0), float(-3890.2), float(29.2), float(-4342.9), float(0), float(0), float(0), float(0), 45)

	return
	
