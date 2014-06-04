
def itemTemplate():

	return ['object/tangible/item/quest/force_sensitive/shared_fs_crystal_force.iff'] # needs correct iff still

def customItemName():

	return "Synapse Focus Crystal"	
	
def requiredLevelForEffect():
	
	return 80
	
def biolink():
	
	return 1
	
def lootDescriptor():

	return 'rarebuffitem'

def itemStats():

	stats =['proc_name','forceCrystalForce','forceCrystalForce']	
	stats +=['effectname','Extended Action','Extended Action']
	stats +=['duration','10800','10800']
	stats +=['cooldown','86400','86400']
	
	return stats
