import sys

def setup(core, object):
	object.setStringAttribute('bio_link', '@obj_attr_n:bio_link_pending')
	object.setStringAttribute('armor_category', '@obj_attr_n:armor_reconnaissance')
	object.setStringAttribute('faction_restriction', 'Rebel')
	return