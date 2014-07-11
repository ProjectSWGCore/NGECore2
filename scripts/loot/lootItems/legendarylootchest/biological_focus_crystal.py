
def itemTemplate():

	return ['object/tangible/item/quest/force_sensitive/shared_fs_crystal_health.iff'] # needs correct iff still

def customItemName():

	return "Biological Focus Crystal"	
	
def requiredLevelForEffect():
	
	return 80
	
def biolink():
	
	return 1
	
def lootDescriptor():

	return 'rarebuffitem'

def itemStats():

	stats =['proc_name','forceCrystalHealth','forceCrystalHealth']
	stats +=['effectname','Extended Constitution','Extended Constitution']
	stats +=['duration','10800','10800']
	stats +=['cooldown','86400','86400']
	
	return stats
