import sys

def setup():
    return

def run(core, actor, target, commandString):
    if not target:
      #FIXME: should accept name as a parameter.
      # since that probably needs support in many places, 
      # best find a generic place for it in the server code
      return

    if not target.isPlayer():
      actor.sendSystemMessage('@performance:dance_watch_npc')
      return

    if target.getPosture() != 0x09 or target.getPerformanceId() >0:
      actor.sendSystemMessage('@performance:dance_watch_not_dancing',0)
      return

    oldWatchee = actor.getPerformanceWatchee()
    if oldWatchee:
      oldWatchee.removeAudience(actor)

    actor.setPerformanceWatchee(target)
    target.addAudience(actor)
    actor.setMoodAnimation('entertained')
    actor.sendSystemMessage('@performance:dance_watch_self',0) 
    return

