import sys

def setup(core, actor, buff):
	return
	
def run(core, actor, target, commandString):
	core.buffService.addBuffToCreature(actor, 'co_position_secured')
	if actor.getSkillMod('expertise_action_line_co_imp_pos_sec'):
		core.buffService.addBuffToCreature(actor, 'co_pos_sec_action_1')
	if actor.getSkillMod('expertise_action_line_co_imp_pos_sec'):
		core.buffService.addBuffToCreature(actor, 'co_pos_sec_action_2')
	if actor.getSkillMod('expertise_action_line_co_imp_pos_sec'):
		core.buffService.addBuffToCreature(actor, 'co_pos_sec_action_3')
	if actor.getSkillMod('expertise_co_pos_secured_line_burst_fire_proc'):
		core.buffService.addBuffToCreature(actor, 'co_pos_sec_proc_1')
	if actor.getSkillMod('expertise_co_pos_secured_line_burst_fire_proc'):
		core.buffService.addBuffToCreature(actor, 'co_pos_sec_proc_2')
	if actor.getSkillMod('expertise_co_pos_secured_line_critical'):
		core.buffService.addBuffToCreature(actor, 'co_pos_sec_critical_1')
	if actor.getSkillMod('expertise_co_pos_secured_line_critical'):
		core.buffService.addBuffToCreature(actor, 'co_pos_sec_critical_2')
	if actor.getSkillMod('expertise_co_pos_secured_line_critical'):
		core.buffService.addBuffToCreature(actor, 'co_pos_sec_critical_3')
	if actor.getSkillMod('expertise_co_pos_secured_line_critical'):
		core.buffService.addBuffToCreature(actor, 'co_pos_sec_critical_4')
	if actor.getSkillMod('expertise_co_pos_secured_line_armor'):
		core.buffService.addBuffToCreature(actor, 'co_base_of_operations')
		group = core.objectService.getObject(actor.getGroupId())
	for creature in group.getMemberList():
		core.buffService.addBuffToCreature(creature, 'co_base_of_operations')
	return
