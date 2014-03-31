from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 7, 1, ''))
	radials.add(RadialOptions(1, 40, 3, '@pet/pet_menu:menu_enter_exit'))
	radials.add(RadialOptions(0, 21, 1, ''))
	radials.add(RadialOptions(0, 60, 3, '@pet/pet_menu:menu_store'))
	return
	
def handleSelection(core, owner, target, option):
	if target:
		if option == 21 or option == 40:
			owner.sendSystemMessage('21', 0)
			core.objectService.useObject(owner, target)
		if option == 60:
			core.objectService.destroyObject(target)
	return