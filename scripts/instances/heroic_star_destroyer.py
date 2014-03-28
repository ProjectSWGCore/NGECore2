import sys
import services.InstanceService.Instance

def getTerrain():
	return 'terrain/dungeon1.trn'

def getBuilding():
	return 'object/building/general/shared_space_dungeon_star_destroyer.iff'

def getCellId():
	return 36

def getSpawnPosition(position):
	position.x = float(0)
	position.y = float(173.834625)
	position.z = float(30)
	return

def getDuration():
	return 120

# NK lockout time was 30 minutes.  Most instances were 0 and got reset at 12AM PTC.
def getLockoutTime():
	return 0

def getExitTerrain():
	return 'terrain/corellia.trn'

def getExitPosition(position):
	position.x = float(-131)
	position.y = float(0)
	position.z = float(-4723)
	return

# Called on instance creation to spawn npcs and objects.
def setup(core, instance):
	return

# Called when there is a new participant added to the instance.
def add(core, instance, actor):
	return

# Called when a participant is removed from the instance.
def remove(core, instance, actor):
	return

# Any operations that need to be carried out regularly
def run(core, instance):
	return

# Called on instance destruction to destroy the instance.
def destroy(core, instance):
	return