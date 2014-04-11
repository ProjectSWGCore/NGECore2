import sys

def setup(core, object):
	
	lootPoolNames_1 = ['Junk','Rifles']
	lootPoolChances_1 = [90,70]
	lootGroupChance_1 = 90
	object.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	lootPoolNames_2 = ['Colorcrystals']
	lootPoolChances_2 = [100]
	lootGroupChance_2 = 20
	object.addToLootGroups(lootPoolNames_2,lootPoolChances_2,lootGroupChance_2)
	
	return 