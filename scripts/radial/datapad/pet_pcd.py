from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	if core.petService.getSpawnedPetForPCD(target) is None:
		radials.add(RadialOptions(0, 45, 1, '@pet/pet_menu:menu_call'))
	else:
		radials.add(RadialOptions(0, 45, 1, '@pet/pet_menu:menu_store'))
	radials.add(RadialOptions(0, 7, 1, ''))
	radials.add(RadialOptions(0, 15, 1, ''))
	return
	
def handleSelection(core, owner, target, option):
	if option == 45 and target:
		if core.petService.getSpawnedPetForPCD(target) is None:
			core.petService.call(owner, None, target)
		else:
			core.petService.store(owner, core.petService.getSpawnedPetForPCD(target))
	elif option == 15 and target:
		core.petService.destroy(owner, target)
	
	return
	