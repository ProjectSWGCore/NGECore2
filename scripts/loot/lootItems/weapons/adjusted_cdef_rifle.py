
def itemTemplate():

	return ['object/weapon/ranged/rifle/shared_rifle_cdef.iff']

def customItemName():

	return 'Adjusted CDEF Rifle'
	
def customItemStackCount():

	return 1
	
def customizationAttributes():

	return []
	
def customizationValues():

	return []
	
def requiredCL():

	return 10

def itemStats():

	stats = ['mindamage','12','27']
	stats += ['maxdamage','48','95']
	stats += ['attackspeed','0.8','0.8']
	stats += ['maxrange','0','64']
	stats += ['damagetype','energy','energy']
	stats += ['weapontype','0','0']
	
	return stats 
