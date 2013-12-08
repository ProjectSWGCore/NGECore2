import sys

def setup():
    return

def run(core, actor, target, commandString):

    if len(commandString) <= 0:
      actor.sendSystemMessage('@performance:flourish_not_valid', 0)
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
      
    actor.sendSystemMessage('@performance:flourish_perform', 0)
    animation = 'skill_action_' + str(flourish)
    if flourish == 9:
      animation = 'mistake'

    actor.doSkillAnimation(animation)
