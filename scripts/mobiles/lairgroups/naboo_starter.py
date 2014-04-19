import sys
from services.spawn import LairSpawnTemplate
from java.util import Vector

def addLairGroup(core):
	spawnTemplate1 = LairSpawnTemplate(-1, 'naboo_chuba_lair_neutral_small', 5, 20)
	
	spawnTemplates = Vector()
	spawnTemplates.add(spawnTemplate1)	

	core.spawnService.addLairGroup('naboo_starter', spawnTemplates)