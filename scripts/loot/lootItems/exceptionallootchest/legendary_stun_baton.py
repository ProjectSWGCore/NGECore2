
def itemTemplate():

	return ['object/weapon/melee/baton/shared_baton_stun_legendary.iff']

def customItemName():

	return 'Legendary Stun Baton'
	
def customItemStackCount():

	return 1
	
def customizationAttributes():

	return []
	
def customizationValues():

	return []
	
def requiredCL():

	return 88

def itemStats():

	stats = ['mindamage','550','600']
	stats += ['maxdamage','1250','1300']
	stats += ['attackspeed','1.0','1.0']
	stats += ['maxrange','0','5']
	stats += ['damagetype','energy','energy']
	stats += ['elemtype','elemental_electricity','elemental_electricity']
	stats += ['elemdamage','75','75']
	stats += ['weapontype','5','5']
	
	return stats 
