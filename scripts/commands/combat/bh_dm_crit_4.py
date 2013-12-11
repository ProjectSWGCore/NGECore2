import sys

def setup(core, actor, target, command):
	if actor.getSkillMod('expertise_cooldown_line_dm_crit'):
		command.setCooldown(command.getCooldown() - (actor.getSkillMod('expertise_cooldown_line_dm_crit').getBase()/10))
	return

def run(core, actor, target, commandString):
	return