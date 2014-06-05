import sys

def setup():
    return

def run(core, actor, target, commandString):
    entSvc = core.entertainmentService
    perf = entSvc.getPerformanceByIndex(actor.getPerformanceId())

    if actor.getPosture() != 0x09 or not perf or perf.getDanceVisualId() < 0:
      actor.sendSystemMessage('@performance:dance_not_performing', 0)
      return

    actor.stopPerformance()
    return
