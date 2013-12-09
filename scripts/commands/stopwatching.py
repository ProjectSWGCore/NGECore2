import sys

def setup():
    return

def run(core, actor, target, commandString):
    dancer = actor.getPerformanceWatchee()
    if not dancer:
      return

    dancer.removeAudience(actor)
    actor.setPerformanceWatchee(None)
    if not actor.getPerformanceListenee():
      actor.setMoodAnimation('')

    actor.sendSystemMessage('@performance:dance_watch_stop_self',0) 
    return
