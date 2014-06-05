from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	master = core.objectService.getObject(target.getOwnerId())
	
	radials.add(RadialOptions(0, 7, 1, ''))
	if core.mountService.canMount(owner, target):
		radials.add(RadialOptions(0, 40, 3, '@pet/pet_menu:menu_enter_exit'))
	if target.getOwnerId() == owner.getObjectID():
		radials.add(RadialOptions(0, 60, 3, '@pet/pet_menu:menu_store'))
	if core.mountService.canRepair(owner, target):
		radials.add(RadialOptions(0, 114, 3, '@pet/pet_menu:menu_repair_vehicle'))
	return

def handleSelection(core, owner, target, option):
	mntSvc = core.mountService
	
	if target:
		if option == 40:
			if mntSvc.isMounted(owner):
				mntSvc.dismount(owner, target)
			else:
				mntSvc.mount(owner, target)
				core.objectService.useObject(owner, target)
		elif option == 60:
			mntSvc.store(owner, target)
		elif option == 114:
			mntSvc.repair(owner, target)
	return

