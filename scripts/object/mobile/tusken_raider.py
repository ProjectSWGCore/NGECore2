import sys
from java.util import TreeSet
from java.util import TreeMap

def setup(core, object):
	
	lootSpecification = TreeSet()
	#lootSpecification = [[['Junk',90],['Rifles',70],['ParentProbability',60]],[['Colorcrystal',100],['ParentProbability',50]]]
	#object.setLootSpecification(lootSpecification)
		
	lootPoolNames_1 = ['Junk','Rifles']
	lootPoolChances_1 = [90,70]
	lootGroupChance_1 = 80
	#core.lootService.saveLootData(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	object.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	lootPoolNames2 = ['Colorcrystal']
	lootPoolChances2 = [50]
	lootGroupChance2 = 60
	#core.lootService.saveLootData(lootPoolNames_2,lootPoolChances_2,lootGroupChance_2)
	object.addToLootGroups(lootPoolNames_2,lootPoolChances_2,lootGroupChance_2)
	
	#core.lootService.test3(allLootPoolNames)
	return 