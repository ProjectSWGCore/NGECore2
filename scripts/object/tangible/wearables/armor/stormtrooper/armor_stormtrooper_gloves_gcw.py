import sys

def setup(core, object):
	object.setStringAttribute('required_faction', 'Imperial')
<<<<<<< HEAD
	object.setStringAttribute('armor_category', 'Battle')
=======
		object.setStringAttribute('armor_category', '@obj_attr_n:armor_battle')
>>>>>>> origin/master
	return	