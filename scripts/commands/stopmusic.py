import sys

def setup():
    return

def run(core, actor, target, commandString):
	entSvc = core.entertainmentService
	perf = entSvc.getPerformanceByIndex(actor.getPerformanceId())

	if actor.getPosture() != 0x09 or not perf or perf.getDanceVisualId() < 0:
		actor.sendSystemMessage('@performance:music_not_performing', 0)
		return

	actor.stopPerformance()#FIXME: Deprecated method
	actor.sendSystemMessage('@performance:music_stop_self', 0)
	return
