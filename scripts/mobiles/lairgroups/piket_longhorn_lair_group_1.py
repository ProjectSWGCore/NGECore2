# Spawn Group file created with PSWG Planetary Spawn Tool
import sys
from services.spawn import LairSpawnTemplate
from java.util import Vector

def addLairGroup(core):
	spawnTemplates = Vector()
	spawnTemplate = LairSpawnTemplate(-1, 'dantooine_piket_longhorn_lair_1', -1, -1)
	spawnTemplates.add(spawnTemplate)
	core.spawnService.addLairGroup('piket_longhorn_lair_group_1', spawnTemplates)
	return
