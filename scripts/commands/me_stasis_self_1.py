import sys
from services.command import CombatCommand

def setup(core, actor, buff):
	return
	
def run(core, actor, target, commandString):

	combatSvc = core.combatService
	
	weapon = core.objectService.getObject(actor.getWeaponId())
	
	if not weapon:
		weapon = actor.getSlottedObject('default_weapon')
		
	command = core.commandService.getCommandByName('me_stasis_self_1')
	
	if not combatSvc.applySpecialCost(actor, weapon, CombatCommand('me_stasis_self_1')):	
		return

	core.buffService.addBuffToCreature(actor, 'me_stasis_self_1', actor)
	
	return
	