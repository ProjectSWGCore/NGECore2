import sys

def add(core, actor, skillMod, divisor):
	core.skillModService.addSkillMod(actor, 'kinetic', skillMod)
	return
	
def deduct(core, actor, skillMod, divisor):
	core.skillModService.deductSkillMod(actor, 'kinetic', skillMod)
	return
	