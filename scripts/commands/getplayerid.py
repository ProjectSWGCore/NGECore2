import sys
 
def setup():
    return
   
def run(core, actor, target, commandString):
 
	if commandString.startswith("quart"):
		print ('commnad: ' + commandString)
		#cmdArgs = commandString.split(" ")
		#qY = cmdArgs[1]
		#qW = cmdArgs[2]

		quaternion = actor.getOrientation()
		#tp = core.travelService.getTravelPointByName(actor.getPlanet().name, "Mos Eisley Starport")
		#obj = tp.getShuttle()
		target.setOrientation(quaternion)
		print ('Orientation: qY- ' + str(quaternion.y) + ' --- qW- ' + str(quaternion.w))
		Console.println('Orientation: qY- ' + str(quaternion.y) + ' --- qW- ' + str(quaternion.w))
		return

	elif commandString.startswith("id"):
		print ( str(target.getObjectId()))
		return
	return