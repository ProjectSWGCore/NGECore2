from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.add(RadialOptions(0, 62, 1, ''))
	radials.add(RadialOptions(0, 7, 1, ''))
	return
	
def handleSelection(core, owner, target, option):

	if option == 61 and target:
		core.objectService.useObject(owner, target)
		
	if option == 62 and target:
	
		vehicleId = long(owner.getAttachment('activeVehicleID'))
		
		if vehicleId != None:
			vehicleObject = core.objectService.getObject(vehicleId)
			
			if owner.isMounted(): vehicleObject.unmount(owner)
			
			core.objectService.destroyObject(vehicleId)
			owner.setAttachment('activeVehicleID', None)
			
	return
	