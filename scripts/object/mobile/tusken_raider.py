import sys

def setup(core, object):
	
	lootPoolNames_1 = ['Junk','Rifles']
	lootPoolChances_1 = [90,70]
	lootGroupChance_1 = 80
	object.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	lootPoolNames_2 = ['Colorcrystals']
	lootPoolChances_2 = [50]
	lootGroupChance_2 = 60
	object.addToLootGroups(lootPoolNames_2,lootPoolChances_2,lootGroupChance_2)
	
	lootPoolNames_3 = ['Rareloot']
	lootPoolChances_3 = [100]
	lootGroupChance_3 = 1
	object.addToLootGroups(lootPoolNames_3,lootPoolChances_3,lootGroupChance_3)

	return 