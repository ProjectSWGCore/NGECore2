
def itemTemplate():

	return ['object/tangible/component/weapon/lightsaber/shared_lightsaber_module_force_crystal.iff']
	
def customItemName():

	return "Shard Of Ferocity"	
		
def biolink():
	
	return 1
	
def lootDescriptor():

	return 'rarebuffitem'

def itemStats():
	
	stats =['proc_name','towCrystalUberCombat','towCrystalUberCombat']
	stats +=['effectname','Ferocity','Ferocity']
	stats +=['duration','180','180']
	stats +=['cooldown','3600','3600']
	
	return stats

