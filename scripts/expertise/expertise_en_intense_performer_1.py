import sys

def addExpertisePoint(core, actor):

        player = actor.getSlottedObject('ghost')

        if not player:
                return

        if not player.getProfession() == 'entertainer_1a':
                return

        actor.addSkill('expertise_en_intense_performer_1')
        core.skillModService.addSkillMod(actor, 'expertise_en_inspire_pulse_duration_increase', 1)
        addAbilities(core, actor, player)
        return

def removeExpertisePoint(core, actor):

        player = actor.getSlottedObject('ghost')

        if not player:
                return

        if not player.getProfession() == 'entertainer_1a':
                return

        actor.removeSkill('expertise_en_intense_performer_1')
        core.skillModService.deductSkillMod(actor, 'expertise_en_inspire_pulse_duration_increase', 1)
        removeAbilities(core, actor, player)
        return

# this checks what abilities the player gets by level, need to also call this on level-up
def addAbilities(core, actor, player):

        return

def removeAbilities(core, actor, player):

        return

