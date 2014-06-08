import sys

def setup(core, object):
	object.setStringAttribute('armor_category', '@obj_attr_n:armor_battle')
	object.setStringAttribute('required_faction', 'Rebel')
	return	