import sys

def setup():
    return

def run(core, actor, target, commandString):
	entSvc = core.entertainmentService
	
	if not target and commandString is not None:
		target = core.chatService.getObjectByFirstName(commandString)
	
	if target is None or actor.getPerformanceWatchee():
		return


	if target.getWorldPosition().getDistance2D(actor.getWorldPosition()) > float(20):
		actor.sendSystemMessage(target.getCustomName() + ' is too far away to watch.', 0)
		return
    
	if not target.isPlayer():
		actor.sendSystemMessage('@performance:dance_watch_npc', 0)
		return

	perf = entSvc.getPerformanceByIndex(target.getPerformanceId())

	if target.getPosture() != 0x09 or not perf or perf.getDanceVisualId() < 0:
		actor.sendSystemMessage(target.getCustomName() + ' is not dancing.',0)
		return
	
	if target.getCoverCharge() > 0 or target.getCoverCharge() == None:
		core.entertainmentService.handleCoverCharge(actor, target)
		return
	else:
		core.entertainmentService.startSpectating(actor, target, True)
	return