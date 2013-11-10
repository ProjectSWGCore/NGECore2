 buff.setDuration(buff.getDuration()+(actor.getSkillModBase('expertise_buff_duration_line_fs_avoid_damage')))
core.skillModService.addSkillMod(actor, 'dodge', 50)
core.skillModService.addSkillMod(actor, 'avoid_area_effects', 75)
def addExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'spy_1a':
		return

	actor.addSkill('expertise_sp_avoid_damage_1')


	addAbilities(core, actor, player)

	return

def removeExpertisePoint(core, actor):

	player = actor.getSlottedObject('ghost')

	if not player:
		return

	if not player.getProfession() == 'spy_1a':
		return

	actor.removeSkill('expertise_sp_avoid_damage_1')


	removeAbilities(core, actor, player)

	return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):

	actor.addAbility('sp_avoid_damage')

	return

def removeAbilities(core, actor, player):

	actor.removeAbility('sp_avoid_damage')

	return
