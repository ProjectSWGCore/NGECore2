
def itemTemplate():

	return ['object/weapon/melee/polearm/shared_lance_nightsister_legendary.iff']

def customItemName():

	return 'Legendary Nightsister Energy Lance'
	
def customItemStackCount():

	return 1
	
def customizationAttributes():

	return []
	
def customizationValues():

	return []
	
def requiredCL():

	return 88

def itemStats():

	stats = ['mindamage','750','800']
	stats += ['maxdamage','1100','1200']
	stats += ['attackspeed','1.0','1.0']
	stats += ['maxrange','0','5']
	stats += ['damagetype','energy','energy']
	stats += ['elemtype','@obj_attr_n:elemental_electricity','@obj_attr_n:elemental_electricity']
	stats += ['elemdamage','50','50']
	stats += ['weapontype','7','7']
	
	return stats 
