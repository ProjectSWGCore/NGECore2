import sys
from services.spawn import LairSpawnTemplate
from java.util import Vector

def addLairGroup(core):
	spawnTemplate = LairSpawnTemplate(-1, 'tatooine_kreetle_lair_neutral_small', 5, 20)
	spawnTemplates = Vector()
	spawnTemplates.add(spawnTemplate)
	core.spawnService.addLairGroup('tatooine_starter', spawnTemplates)