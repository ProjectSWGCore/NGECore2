import sys

def setup(core, object):
	
	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 90
	object.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	lootPoolNames_2 = ['batons','random_loot_rifles']
	lootPoolChances_2 = [60,40]
	lootGroupChance_2 = 20
	object.addToLootGroups(lootPoolNames_2,lootPoolChances_2,lootGroupChance_2)
	
	lootPoolNames_3 = ['tusken_raider_clothing']
	lootPoolChances_3 = [100]
	lootGroupChance_3 = 10
	object.addToLootGroups(lootPoolNames_3,lootPoolChances_3,lootGroupChance_3)
	
	lootPoolNames_4 = ['Colorcrystals']
	lootPoolChances_4 = [100]
	lootGroupChance_4 = 6
	object.addToLootGroups(lootPoolNames_4,lootPoolChances_4,lootGroupChance_4)
	
	return 