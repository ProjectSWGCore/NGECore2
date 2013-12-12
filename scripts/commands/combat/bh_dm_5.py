import sys

def setup(core, actor, target, command):
	if actor.getSkillMod('expertise_cooldown_line_dm'):
		command.setCooldown(command.getCooldown() - (actor.getSkillMod('expertise_cooldown_line_dm').getBase()/10))
		
	if actor.getSkillMod('expertise_damage_line_dm'):
		command.setAddedDamage(command.getAddedDamage() + ((command.getAddedDamage()*actor.getSkillMod('expertise_damage_line_dm').getBase())/100))
	return
def run(core, actor, target, commandString):
	return