
def itemTemplate():

	return ['object/tangible/loot/creature_loot/collections/shared_sith_holocron_01.iff']

def customItemName():

	return 'Sith Holocron'

def biolink():
	
	return 1
	
def lootDescriptor():

	return 'rarebuffitem'

def itemStats():

	stats =['proc_name','towCrystalABImmune','towCrystalABImmune']
	stats +=['effectname','Forbidden Knowledge I','Forbidden Knowledge I']
	stats +=['duration','180','180']
	stats +=['cooldown','3600','3600']
	
	return stats