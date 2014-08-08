import sys
from services.spawn import LairSpawnTemplate
from java.util import Vector

def addLairGroup(core):
	spawnTemplate1 = LairSpawnTemplate(-1, 'lok_gurk_lair', 5, 20)	
	spawnTemplates = Vector()
	spawnTemplates.add(spawnTemplate1)	

	core.spawnService.addLairGroup('lok_bosstest', spawnTemplates)
	return