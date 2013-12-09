import sys

def setup():
    return

def run(core, actor, target, commandString):

    newState = actor.toggleGroupDance()
    msg = '@performance:dance_group_on' if newState else '@performance:dance_group_off'
    actor.sendSystemMessage(msg, 0)
    return
