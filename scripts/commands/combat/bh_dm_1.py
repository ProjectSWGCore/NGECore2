import sys

def setup(core, actor, target, command):
	if actor.getSkillMod('expertise_cooldown_line_dm'):
		command.setCooldown(command.getCooldown() - (actor.getSkillMod('expertise_cooldown_line_dm').getBase()/10))
	return
def run(core, actor, target, commandString):
	return