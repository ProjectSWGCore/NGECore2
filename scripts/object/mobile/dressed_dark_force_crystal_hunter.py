import sys

def setup(core, object):
	
	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 65
	object.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	lootPoolNames_2 = ['jedi_relic_1']
	lootPoolChances_2 = [100]
	lootGroupChance_2 = 85
	object.addToLootGroups(lootPoolNames_2,lootPoolChances_2,lootGroupChance_2)
	
	lootPoolNames_3 = ['colorcrystals']
	lootPoolChances_3 = [100]
	lootGroupChance_3 = 12
	object.addToLootGroups(lootPoolNames_3,lootPoolChances_3,lootGroupChance_3)
	
	lootPoolNames_4 = ['armor_various','random_loot_rifles']
	lootPoolChances_4 = [80,20]
	lootGroupChance_4 = 35
	object.addToLootGroups(lootPoolNames_4,lootPoolChances_4,lootGroupChance_4)
	
	lootPoolNames_5 = ['sithholocrons']
	lootPoolChances_5 = [100]
	lootGroupChance_5 = 1
	object.addToLootGroups(lootPoolNames_5,lootPoolChances_5,lootGroupChance_5)
	
	
	
	return 