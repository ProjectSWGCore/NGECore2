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

    if actor.getAcceptBandflourishes():
      actor.sendSystemMessage('@performance:flourish_perform_band_self', 0)
      core.entertainmentService.performFlourish(actor, flourish)

    group = core.objectService.getObject(actor.getGroupId())
    if not group:
      return

    for creature in group.getMemberList():
      if creature == actor:
        continue
      if creature.getAcceptBandflourishes():
        creature.sendSystemMessage('@performance:flourish_perform_band_member', 0)
        core.entertainmentService.performFlourish(creature, flourish)
    return


