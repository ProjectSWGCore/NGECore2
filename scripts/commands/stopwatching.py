import sys

def setup():
    return

def run(core, actor, target, commandString):
    performer = target
    if commandString is not None and target is None:
        performer = core.chatService.getObjectByFirstName(commandString)
    
    if performer is None:
    	return
    performer.removeSpectator(actor)
    actor.setPerformanceWatchee(None)
    if not actor.getPerformanceListenee():
      actor.setMoodAnimation('')

    actor.sendSystemMessage('@performance:dance_watch_stop_self',0) 
    return
