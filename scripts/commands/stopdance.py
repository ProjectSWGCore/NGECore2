import sys

def setup():
    return

def run(core, actor, target, commandString):
    entSvc = core.entertainmentService

    if (actor.getPerformanceId() <= 0):
      actor.sendSystemMessage('@performance:dance_not_performing', 0)
      return

    #since we need to stop performance for any posture change,
    # all packets are triggered in setPosture.
    # this may not be very consistent, but it prevents
    # duplicate code.
    actor.setPosture(0x00)

    return
