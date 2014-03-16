import sys

def setup():
    return

def run(core, actor, target, commandString):
	entSvc = core.entertainmentService
	
	if not target and commandString is not None:
		target = core.chatService.getObjectByFirstName(commandString)
	
	if target is None:
		return
	
	if not target.isPlayer():
		actor.sendSystemMessage('@performance:dance_watch_npc', 0)
		return

	perf = entSvc.getPerformanceByIndex(target.getPerformanceId())

	if target.getPosture() != 0x09 or not perf or perf.getDanceVisualId() < 0:
		actor.sendSystemMessage(target.getCustomName() + ' is not dancing.',0)
		return

	oldWatchee = actor.getPerformanceWatchee()
	if oldWatchee and oldWatchee != target:
		oldWatchee.removeAudience(actor)

	actor.setPerformanceWatchee(target)
	target.addAudience(actor)
	actor.setMoodAnimation('entertained')
	actor.sendSystemMessage('You start watching ' + target.getCustomName() + '.',0) 
	return