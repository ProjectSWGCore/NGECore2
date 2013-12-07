import sys

def setup():
    return

def run(core, actor, target, commandString):

    if len(commandString) <= 0:
      actor.sendSystemMessage('@performance:flourish_not_valid', 0)
      return

    if commandString == 'on':
      actor.setAcceptBandflourishes(1)
      actor.sendSystemMessage('@performance:band_flourish_status_on', 0)
      return
    if commandString == 'off':
      actor.setAcceptBandflourishes(0)
      actor.sendSystemMessage('@performance:band_flourish_status_off', 0)
      return

    try:
      flourish = int(commandString)
    except ValueError:
      actor.sendSystemMessage('@performance:flourish_not_valid', 0)
      return

    if flourish < 1 or flourish > 9:
      actor.sendSystemMessage('@performance:flourish_not_valid', 0)
      return

    if actor.getPerformanceId() <= 0:
      actor.sendSystemMessage('@performance:flourish_not_performing', 0)
      return
      
    animation = 'skill_action_' + str(flourish)
    if flourish == 9:
      animation = 'mistake'

    group = core.objectService.getObject(actor.getGroupId())
    if not group:
      if actor.getAcceptBandflourishes():
        actor.sendSystemMessage('@performance:flourish_perform_band_self', 0)
        actor.sendSystemMessage('@performance:flourish_perform', 0)
        actor.doSkillAnimation(animation)
      return

    for creature in group.getMemberList():
      if creature.getAcceptBandflourishes():
        if actor == creature:
          creature.sendSystemMessage('@performance:flourish_perform_band_self', 0)
        else:
          creature.sendSystemMessage('@performance:flourish_perform_band_member', 0)
        creature.sendSystemMessage('@performance:flourish_perform', 0)
        creature.doSkillAnimation(animation)
    return


