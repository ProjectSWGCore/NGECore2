from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	#if not core.mountService.getMount(target):
	#	radials.add(RadialOptions(0, 21, 3, '@pet_menu:menu_call'))
	#else:
	#	radials.add(RadialOptions(0, 21, 3, '@pet_menu:menu_store'))
	radials.add(RadialOptions(0, 61, 1, ''))
	radials.add(RadialOptions(0, 7, 1, ''))
	radials.add(RadialOptions(0, 15, 1, ''))
	return
	
def handleSelection(core, owner, target, option):
	if option == 61 and target:
		core.mountService.call(owner, target)
	elif option == 15 and target:
		core.mountService.destroy(owner, target)
	
	return
	