import sys
from services.spawn import LairSpawnTemplate
from java.util import Vector

def addLairGroup(core):
	spawnTemplate1 = LairSpawnTemplate(-1, 'tatooine_kreetle_lair_neutral_small', 5, 20)
	spawnTemplate2 = LairSpawnTemplate(-1, 'tatooine_womprat_lair_neutral_small', 5, 15)
	spawnTemplate3 = LairSpawnTemplate(-1, 'tatooine_rill_lair_neutral_small', 5, 15)
	spawnTemplate4 = LairSpawnTemplate(-1, 'tatooine_worrt_lair_neutral_small', 5, 15)
	spawnTemplates = Vector()
	spawnTemplates.add(spawnTemplate1)	
	spawnTemplates.add(spawnTemplate2)
	spawnTemplates.add(spawnTemplate3)
	spawnTemplates.add(spawnTemplate4)

	core.spawnService.addLairGroup('tatooine_starter', spawnTemplates)
	return