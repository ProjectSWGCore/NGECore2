import sys

def setup():
    return

def run(core, actor, target, commandString):
    entSvc = core.entertainmentService
    perf = entSvc.getPerformanceByIndex(actor.getPerformanceId())

    if actor.getPosture() != 0x09 or not perf or perf.getDanceVisualId() < 0:
      actor.sendSystemMessage('@performance:dance_not_performing', 0)
      return

    #since we need to stop performance for any posture change,
    # all packets are triggered in setPosture.
    # this may not be very consistent, but it prevents
    # duplicate code.
    actor.stopPerformance()

    return
