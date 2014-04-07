from resources.datatables import PlayerFlags
import sys

def setup():
  return

def run(core, actor, target, commandString):
  command = core.commandService.getCommandByName("toggleawayfromkeyboard")
  
  if command:
    core.commandService.processCommand(actor, target, command, 0, commandString)
  
  return
