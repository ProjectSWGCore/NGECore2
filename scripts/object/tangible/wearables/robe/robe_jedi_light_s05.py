import sys

def setup(core, object):
	return
	
def equip(core, actor, target):
	core.skillModService.addSkillMod(actor, 'agility_modified', 250)
	core.skillModService.addSkillMod(actor, 'constitution_modified', 250)
	core.skillModService.addSkillMod(actor, 'strength_modified', 250)
	
	Buff = actor.getBuffByName('proc_old_light_jedi_gift')
	if actor.getBuffList().contains(Buff):
		core.buffService.removeBuffFromCreature(actor, Buff)
		return
		
	if actor:
		core.buffService.addBuffToCreature(actor, 'proc_old_light_jedi_gift')
		return
	return
	
def unequip(core, actor, target):
	core.skillModService.deductSkillMod(actor, 'agility_modified', 250)
	core.skillModService.deductSkillMod(actor, 'constitution_modified', 250)
	core.skillModService.deductSkillMod(actor, 'strength_modified', 250)
	
	Buff = actor.getBuffByName('proc_old_light_jedi_gift')
	if actor.getBuffList().contains(Buff):
		core.buffService.removeBuffFromCreature(actor, Buff)
		return
	return
	import sys

def setup(core, object):
	return