from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	if core.petService.getMount(target) is None:
		radials.add(RadialOptions(0, 61, 1, '@pet/pet_menu:menu_call'))
	else:
		radials.add(RadialOptions(0, 61, 1, '@pet/pet_menu:menu_store'))
	radials.add(RadialOptions(0, 7, 1, ''))
	radials.add(RadialOptions(0, 15, 1, ''))
	return
	
def handleSelection(core, owner, target, option):
	if option == 61 and target:
		if core.petService.getMount(target) is None:
			core.petService.call(owner, target)
		else:
			core.petService.store(owner, core.petService.getMount(target))
	elif option == 15 and target:
		core.petService.destroy(owner, target)
	
	return
	