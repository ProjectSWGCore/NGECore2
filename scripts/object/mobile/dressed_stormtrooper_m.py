import sys

def setup(core, object):
	
	lootPoolNames_1 = ['Junk']
	lootPoolChances_1 = [100]
	lootGroupChance_1 = 90
	object.addToLootGroups(lootPoolNames_1,lootPoolChances_1,lootGroupChance_1)
	
	lootPoolNames_2 = ['armor_various','random_loot_rifles']
	lootPoolChances_2 = [99,1]
	lootGroupChance_2 = 100
	object.addToLootGroups(lootPoolNames_2,lootPoolChances_2,lootGroupChance_2)
	
	lootPoolNames_3 = ['Colorcrystals']
	lootPoolChances_3 = [100]
	lootGroupChance_3 = 6
	object.addToLootGroups(lootPoolNames_3,lootPoolChances_3,lootGroupChance_3)
	
	# just to test
	lootPoolNames_4 = ['random_stat_jewelry']
	lootPoolChances_4 = [100]
	lootGroupChance_4 = 10
	object.addToLootGroups(lootPoolNames_4,lootPoolChances_4,lootGroupChance_4)

	return 