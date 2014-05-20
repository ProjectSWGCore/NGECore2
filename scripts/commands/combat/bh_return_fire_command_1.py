import sys

def setup(core, actor, target, command):
	if actor.getSkillMod('expertise_cooldown_line_bh_return_fire'):
		command.setCooldown(command.getCooldown() - (actor.getSkillMod('expertise_cooldown_line_bh_return_fire').getBase()/10))
	return
	
def run(core, actor, target, commandString):
	core.buffService.addBuffToCreature(actor, 'bh_return_fire_1', actor)
	return
	