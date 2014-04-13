from resources.common import RadialOptions

import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 7, 1, ''))
	
	if owner.canMount(target):
		radials.add(RadialOptions(0, 208, 1, '@pet/pet_menu:menu_enter_exit'))
		
	if target.getOwnerId() == owner.getObjectID():
		radials.add(RadialOptions(0, 60, 3, '@pet/pet_menu:menu_store'))
		
	return
	
def handleSelection(core, owner, target, option):
	if target:
		if option == 208:
			if not owner.isMounted():
				owner.setMounted(1)
				target.initMount(owner)	
			else:
				target.unmount(owner)
			
		if option == 60:
			if owner.isMounted(): vehicleObject.unmount(owner)
			core.objectService.destroyObject(target)
			owner.setAttachment('activeVehicleID', None)
	return