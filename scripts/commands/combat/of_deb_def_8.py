def setup(core, actor, target, command):
    core.buffService.addBuffToCreature(target, 'of_deb_def_8', actor)
    if actor.getSkillMod('expertise_of_adv_paint_debuff'):
		core.buffService.addBuffToCreature(target, 'of_deb_def_8', actor)
		core.buffService.addBuffToCreature(target, 'of_adv_paint_debuff_8', actor)
    if actor.getSkillMod('expertise_of_adv_paint_expose'):
		core.buffService.addBuffToCreature(target, 'of_deb_def_8', actor)
		core.buffService.addBuffToCreature(target, 'of_adv_paint_expose_8', actor)
		return
	
def preRun(core, actor, target, command):
	return

def run(core, actor, target, commandString):
	return