import sys

def setup(core, object):

	lootPoolNames_1 = ['rareloot']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 5
	object.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	return