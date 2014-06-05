from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	if core.mountService.getMount(target) is None:
		radials.add(RadialOptions(0, 61, 1, '@pet/pet_menu:menu_call'))
	else:
		radials.add(RadialOptions(0, 61, 1, '@pet/pet_menu:menu_store'))
	radials.add(RadialOptions(0, 7, 1, ''))
	radials.add(RadialOptions(0, 15, 1, ''))
	return
	
def handleSelection(core, owner, target, option):
	if option == 61 and target:
		if core.mountService.getMount(target) is None:
			core.mountService.call(owner, target)
		else:
			core.mountService.store(owner, core.mountService.getMount(target))
	elif option == 15 and target:
		core.mountService.destroy(owner, target)
	
	return
	