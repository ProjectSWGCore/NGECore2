import sys

def setup(core, actor, buff):
		return

def add(core, actor, buff):
		actor.setInStealth(True)
		actor.setRadarVisible(False)
		return

def remove(core, actor, buff):
		actor.setInStealth(False)
		actor.setRadarVisible(True)
		return
