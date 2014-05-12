import sys
from engine.resources.container import CreaturePermissions

def setup(core, object):

	inventory = core.objectService.createObject("object/tangible/inventory/shared_lightsaber_inventory_5.iff", object.getPlanet())
	inventory.setContainerPermissions(CreaturePermissions.CREATURE_PERMISSIONS);
	
	object.add(inventory)
	
	object.setAttachment('radial_filename', 'item/lightsaber')
	
	return