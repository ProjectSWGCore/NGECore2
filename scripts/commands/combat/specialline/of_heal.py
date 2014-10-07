import sys

def setup(core, actor, target, command):

	if actor.getSkillMod('expertise_cooldown_line_of_heal'):
		command.setCooldown(command.getCooldown() - actor.getSkillMod('expertise_cooldown_line_of_heal').getBase())
	return
	
def run(core, actor, target, commandString):
	return
	