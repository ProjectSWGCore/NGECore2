from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	
	if target.getContainer() == owner:
		return
	
	if target.getTemplate() == "object/tangible/wearables/robe/shared_robe_s32.iff" or target.getTemplate() == "object/tangible/wearables/robe/shared_robe_s33.iff":
		radials.add(RadialOptions(0, 98, 1, 'Hood up'))
		
	elif target.getTemplate() == "object/tangible/wearables/robe/shared_robe_s32_h1.iff" or target.getTemplate() == "object/tangible/wearables/robe/shared_robe_s33_h1.iff":
		radials.add(RadialOptions(0, 99, 1, 'Hood down'))
		
	return
	
def handleSelection(core, owner, target, option):
	inventory = owner.getSlottedObject('inventory')

	if inventory:	
		newTemplate = ""

		if option == 98 and target:
					
			if target.getTemplate() == "object/tangible/wearables/robe/shared_robe_s32.iff":
				newTemplate = "object/tangible/wearables/robe/shared_robe_s32_h1.iff"
				
			elif target.getTemplate() == "object/tangible/wearables/robe/shared_robe_s33.iff":
				newTemplate = "object/tangible/wearables/robe/shared_robe_s33_h1.iff"
			
			if newTemplate != "":
				object = core.objectService.createObject(newTemplate, owner.getPlanet())	
				core.objectService.destroyObject(target)
				inventory.add(object)
			
		elif option == 99 and target:
		
			if target.getTemplate() == "object/tangible/wearables/robe/shared_robe_s32_h1.iff":
				newTemplate = "object/tangible/wearables/robe/shared_robe_s32.iff"
				
			elif target.getTemplate() == "object/tangible/wearables/robe/shared_robe_s33_h1.iff":
				newTemplate = "object/tangible/wearables/robe/shared_robe_s33.iff"
			
			if newTemplate != "":
				object = core.objectService.createObject(newTemplate, owner.getPlanet())	
				core.objectService.destroyObject(target)
				inventory.add(object)
	return
	