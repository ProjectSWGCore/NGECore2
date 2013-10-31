from resources.objects.waypoint import WaypointObject
from engine.resources.scene import Point3D
from engine.resources.common import CRC
import sys


def run(core, actor, target, commandString):
    validPlanets = ["tatooine", "naboo", "corellia", "rori", "talus", "yavin4", "endor", "lok", "dantooine", "dathomir", "kachirho", "etyyy", "khowir", "mustafar"]
    validColors = ["blue", "green", "orange", "purple", "white", "yellow"]
    crc = CRC
    commandArgs = commandString.split(" ")
    actorPlayer = actor.getSlottedObject("ghost")
    
    #/wp PLANET X Z Y COLOR NAME
    if commandArgs[0] in validPlanets and commandArgs[4] in validColors:
        try:
            float(commandArgs[1])
            float(commandArgs[2])
            float(commandArgs[3])
        except ValueError:
            return
        planet = core.terrainService.getPlanetByName(commandArgs[0])
        waypoint = core.objectService.createObject('object/waypoint/shared_waypoint.iff', planet, float(commandArgs[1]), float(commandArgs[2]), float(commandArgs[3]))
        waypoint.setActive(True)
        waypoint.setColor(colorCheck(core, actor, target, commandString, commandArgs[4]))
        name = commandString.split(" ", 5)
        waypoint.setName(name[5])
        waypoint.setPlanetCRC(crc.StringtoCRC(planet.getName()))
        actorPlayer.getWaypoints().add(waypoint)
        actorPlayer.waypointAdd(waypoint)
        actor.sendSystemMessage('A waypoint has been created in your datapad at your location.', 0)
        return
    
    #/wp X Z Y NAME
    if isFloat(commandArgs[0]) and isFloat(commandArgs[1]) and isFloat(commandArgs[2]):
        waypoint = core.objectService.createObject('object/waypoint/shared_waypoint.iff', actor.getPlanet(), float(commandArgs[0]), float(commandArgs[2]), float(commandArgs[1]))
        waypoint.setActive(True)
        waypoint.setColor(WaypointObject.BLUE)
        name = commandString.split(" ", 3)
        waypoint.setName(name[3])
        waypoint.setPlanetCRC(crc.StringtoCRC(actor.getPlanet().getName()))
        actorPlayer.getWaypoints().add(waypoint)
        actorPlayer.waypointAdd(waypoint)
        actor.sendSystemMessage('A waypoint has been created in your datapad at your location.', 0)
        return
    
    #/wp NAME
    else:
        waypoint = core.objectService.createObject('object/waypoint/shared_waypoint.iff', actor.getPlanet(), actor.getWorldPosition().x, actor.getWorldPosition().z, actor.getWorldPosition().y)
        waypoint.setActive(True)
        waypoint.setColor(WaypointObject.BLUE)
        waypoint.setName(commandString)
        waypoint.setPlanetCRC(crc.StringtoCRC(actor.getPlanet().getName()))
        actorPlayer.getWaypoints().add(waypoint)
        actorPlayer.waypointAdd(waypoint)
        actor.sendSystemMessage('A waypoint has been created in your datapad at your location.', 0)
        return
    
    return

def colorCheck(core, actor, target, commandString, validcolors):
    if validcolors == "blue":
        return WaypointObject.BLUE
    if validcolors == "green":
        return WaypointObject.GREEN
    if validcolors == "orange":
        return WaypointObject.ORANGE
    if validcolors == "purple":
        return WaypointObject.PURPLE
    if validcolors == "white":
        return WaypointObject.WHITE
    if validcolors == "yellow":
        return WaypointObject.YELLOW
    return

def isFloat(string):
    try:
        stringFloat = float(string)
    except ValueError:
        return False
    
    else:
        return True
    return