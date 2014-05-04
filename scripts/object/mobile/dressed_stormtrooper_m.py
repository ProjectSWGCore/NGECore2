import sys

def setup(core, object):
	
	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 90
	object.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	lootPoolNames_2 = ['composite_armor','random_loot_rifles']
	lootPoolChances_2 = [50,50]
	lootGroupChance_2 = 20
	object.addToLootGroups(lootPoolNames_2,lootPoolChances_2,lootGroupChance_2)
	
	lootPoolNames_3 = ['Colorcrystals']
	lootPoolChances_3 = [100]
	lootGroupChance_3 = 6
	object.addToLootGroups(lootPoolNames_3,lootPoolChances_3,lootGroupChance_3)

	return 