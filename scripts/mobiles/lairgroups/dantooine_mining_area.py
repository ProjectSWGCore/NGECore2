import sys
from services.spawn import LairSpawnTemplate
from java.util import Vector

def addLairGroup(core):
	spawnTemplate1 = LairSpawnTemplate(-1, 'dantooine_huurton_lair_1', -1, -1)
	spawnTemplate2 = LairSpawnTemplate(-1, 'dantooine_huurton_lair_2', -1, -1)
	spawnTemplate3 = LairSpawnTemplate(-1, 'dantooine_bol_lair_1', -1, -1)
	spawnTemplate4 = LairSpawnTemplate(-1, 'dantooine_piket_lair_1',-1, -1)
	spawnTemplate5 = LairSpawnTemplate(-1, 'dantooine_piket_longhorn_lair_1',-1, -1)
	spawnTemplate6 = LairSpawnTemplate(-1, 'dantooine_quenker_lair_1', -1, -1)	
	spawnTemplates = Vector()
	spawnTemplates.add(spawnTemplate1)	
	spawnTemplates.add(spawnTemplate2)
	spawnTemplates.add(spawnTemplate3)
	spawnTemplates.add(spawnTemplate4)

	core.spawnService.addLairGroup('dantooine_mining_area', spawnTemplates)
	return