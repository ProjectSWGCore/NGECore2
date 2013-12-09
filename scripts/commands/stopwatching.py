import sys

def setup():
    return

def run(core, actor, target, commandString):
    dancer = actor.getPerformanceWatchee()
    if not dancer:
      return

    actor.setPerformanceWatchee(None)
    dancer.removeAudience(actor)
    if not actor.getPerformanceListenee():
      actor.setMoodAnimation('')

    actor.sendSystemMessage('@performance:dance_watch_stop_self',0) 
    return
