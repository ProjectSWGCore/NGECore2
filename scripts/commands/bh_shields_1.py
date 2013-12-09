import sys
from services.command import CombatCommand

def setup(core, actor, buff):
	return
	
def run(core, actor, target, commandString):
		
	command = core.commandService.getCommandByName('bh_shields_1')

	core.buffService.addBuffToCreature(actor, 'bh_shields_1')
	
	return
	