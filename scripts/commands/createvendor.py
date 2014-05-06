import sys

def setup():
	return
	
def run(core, actor, target, commandString):
	
	if actor.getCombatFlag() > 0:
		return
	
	cell = actor.getContainer()
	building = actor.getGrandparent()
	ghost = actor.getSlottedObject('ghost')
		
	if not ghost or not cell or not building or not core.housingService.getPermissions(player, cell)
		return
		
	if ghost.getAmountOfVendors() >= actor.getSkillModBase('manage_vendor'):
		actor.sendSystemMessage('@player_structure:full_vendors', 0)
		return
	
	
	
	return
	