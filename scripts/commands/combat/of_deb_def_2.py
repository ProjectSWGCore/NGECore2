def setup(core, actor, target, command):
    core.buffService.addBuffToCreature(target, 'of_deb_def_2', actor)
    if actor.getSkillMod('expertise_of_adv_paint_debuff'):
		core.buffService.addBuffToCreature(target, 'of_deb_def_2', actor)
		core.buffService.addBuffToCreature(target, 'of_adv_paint_debuff_2', actor)
    if actor.getSkillMod('expertise_of_adv_paint_expose'):
		core.buffService.addBuffToCreature(target, 'of_deb_def_2', actor)
		core.buffService.addBuffToCreature(target, 'of_adv_paint_expose_2', actor)
		return
	
def preRun(core, actor, target, command):
	return

def run(core, actor, target, commandString):
	return