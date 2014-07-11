from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.clear()	
	# and check if owner is a beast master!
	#owner.sendSystemMessage('UNTAMED', 1)
	#if target.getAttachment('tamed'):
	radials.add(RadialOptions(0, 159, 3, '@pet/pet_menu:menu_tame'))
		
	return
	
def handleSelection(core, owner, target, option):

	if option == 159:
		core.petService.tame(owner, target)
	return
	