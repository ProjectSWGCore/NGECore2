import sys
from services.spawn import LairSpawnTemplate
from java.util import Vector

def addLairGroup(core):
	spawnTemplate1 = LairSpawnTemplate(-1, 'tatooine_kreetle_lair_neutral_small', 5, 20)
	spawnTemplate2 = LairSpawnTemplate(-1, 'tatooine_womprat_lair_neutral_small', 5, 15)
	
	spawnTemplates = Vector()
	#spawnTemplates.add(spawnTemplate1)	
	spawnTemplates.add(spawnTemplate2)

	core.spawnService.addLairGroup('tatooine_starter', spawnTemplates)