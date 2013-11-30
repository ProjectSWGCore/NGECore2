import sys

def setup(core, object):
	object.setCustomName('Imperial Cape')
	object.setStringAttribute('condition', '100/100')
	object.setIntAttribute('volume', 1)
	object.setStringAttribute('cost', '[2500] Imperial Galactic Civil War token')
	object.setStringAttribute('faction_restriction', 'Imperial')
	
	
	return