import sys

def setup(core, object):
	object.setStringAttribute('bio_link', '@obj_attr_n:bio_link_pending')
	object.setStringAttribute('required_level', '22')
	object.setStringAttribute('faction_restriction', 'Rebel')
	return