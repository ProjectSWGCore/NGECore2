from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):

	radials.add(RadialOptions(0, 45, 1, '@pet/pet_menu:menu_store'))
	radials.add(RadialOptions(0, 7, 1, ''))
	radials.add(RadialOptions(0, 15, 1, ''))
	return
	
def handleSelection(core, owner, target, option):
	if option == 45 and target:	
		core.petService.store(owner, target)
	elif option == 15 and target:
		core.petService.destroy(owner, target)
	
	return
	