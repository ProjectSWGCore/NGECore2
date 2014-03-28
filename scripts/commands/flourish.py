import sys

def setup():
    return

def run(core, actor, target, commandString):
	
    if len(commandString) == 0 or commandString == None or commandString == "":
      actor.sendSystemMessage('@performance:flourish_format')
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

    core.entertainmentService.performFlourish(actor, flourish)