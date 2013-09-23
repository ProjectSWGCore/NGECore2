import sys
from services.command import CombatCommand

def setup(core, actor, buff):
	return
	
def run(core, actor, target, commandString):

	combatSvc = core.combatService
	
	weapon = core.objectService.getObject(actor.getWeaponId())
	
	if not weapon:
		weapon = actor.getSlottedObject('default_weapon')
		
	command = core.commandService.getCommandByName('me_reckless_stimulation_5')
	
	if not combatSvc.applySpecialCost(actor, weapon, CombatCommand('me_reckless_stimulation_5')):	
		return

	actor.playEffectObject('clienteffect/medic_reckless_stimulation.cef', 'root')		
	actor.setAction(actor.getAction() + 3000)
	
	return
	