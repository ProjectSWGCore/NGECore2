from resources.common import RadialOptions
import sys

def createRadial(core, owner, target, radials):
	radials.clear()	
	radials.add(RadialOptions(0, 7, 0, 'Examine'))
	radials.add(RadialOptions(0, 15, 0, 'Destroy'))
	if target.getAttachment("tunerId") == None or target.getAttachment("tunerId") == 0: radials.add(RadialOptions(0, 19, 0, 'Tune'))
	return
	
def handleSelection(core, owner, target, option):
	if option == 19 and target:
		if owner is not None:	
			#owner.sendSystemMessage('You are not attuned enough with the force yet.',1)
			
			if target.getAttachment("tunerId") == None or target.getAttachment("tunerId") == 0:
				target.getAttributes().put("@obj_attr_n:crystal_owner", owner.getCustomName())
				target.setAttachment("tunerId", int(owner.getObjectId()))
				
				# Need to change this to generate valid numbers based on the quality and type ... stats found here: http://swg.wikia.com/wiki/Lightsaber_Crystal
				if target.getAttributes().get("@obj_attr_n:color") is None:
					target.getAttributes().put("@obj_attr_n:componentbonuslow", "23")
					target.getAttributes().put("@obj_attr_n:componentbonushigh", "25")
			else:
				owner.sendSystemMessage("You cannot tune this crystal.",1)
			return
