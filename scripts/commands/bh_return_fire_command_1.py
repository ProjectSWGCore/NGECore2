import sys
from services.command import CombatCommand

def setup(core, actor, buff):
	return
	
def run(core, actor, target, commandString):
		
	command = core.commandService.getCommandByName('bh_return_fire_command_1')

	core.buffService.addBuffToCreature(actor, 'bh_return_fire_1')

		
		#Needs a command for "attack on damage" ?
	
	return
	