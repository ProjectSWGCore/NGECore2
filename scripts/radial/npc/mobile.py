from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.clear()	
	if target.getAttachment('AI') and core.resourceService and core.resourceService.canMilk(owner, target):
		radials.add(RadialOptions(0, 167, 3, '@pet/pet_menu:milk_me'))
		
	return
	
def handleSelection(core, owner, target, option):

	if option == 167 and target.getAttachment('AI') and core.resourceService and core.resourceService.canMilk(owner, target):
		core.resourceService.doMilk(owner, target)
	return
	