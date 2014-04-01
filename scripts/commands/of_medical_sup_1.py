from engine.resources.container import StaticContainerPermissions
import time
import sys

def setup(core, actor, target, command):
    return
    
def run(core, actor, target, commandString):
    doStimDrop(core, actor)
    return

def doStimDrop(core, actor):
    actor.sendSystemMessage('Stims incoming Mr. Bossman!', 0)
    stcSvc = core.staticService
    shuttle = stcSvc.spawnObject('object/creature/npc/theme_park/shared_lambda_shuttle.iff', actor.getPlanet().getName(), long(0), actor.getPosition().x, actor.getPosition().y, actor.getPosition().z, actor.getOrientation().y, actor.getOrientation().w)
    shuttle.setPosture(0)
    time.sleep(10) # Wait time before shuttle arrives after command is called
    shuttle.setPosture(2)
    time.sleep(20) # wait time for the crate to show (length of landing animation for shuttle)
    spawnCrate(core, actor, shuttle)
    time.sleep(5) # Wait time for the shuttle to leave after crate is spawned
    shuttle.setPosture(0)
    time.sleep(20) # How long the take-off sequence is, until the Shuttle is destroyed
    core.objectService.destroyObject(shuttle)
    return

def spawnCrate(core, actor, shuttle):
    crate = core.staticService.spawnObject('object/tangible/container/drum/shared_supply_drop_crate.iff', shuttle.getPlanet().getName(), long(0), shuttle.getPosition().x, shuttle.getPosition().y, shuttle.getPosition().z, shuttle.getOrientation().y, shuttle.getOrientation().w)
    crate.setCustomName('Supply Crate')
	
    group = core.objectService.getObject(actor.getGroupId())
    
    permissions = StaticContainerPermissions()
    permissions.canRemove(group, crate)
    permissions.canView(group, crate)
    crate.setContainerPermissions(permissions)
	
    if actor.hasSkill('expertise_of_tactical_sup_1'):
        for i in range(0, 8):
            tactical = core.objectService.createObject('object/tangible/loot/generic_usable/shared_stim_syringe_generic.iff', actor.getPlanet()) #FIXME: Use the correct template
            tactical.setCustomName('Tactical Serum A')
            tactical.setStringAttribute('proc_name', '@ui_buff:of_tactical_drop_6')
            tactical.setAttachment("tempUseCount", 1)
            crate.add(tactical)
    
    for i in range(0, 8):
        stimpack = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_e.iff', actor.getPlanet()) #FIXME: Use the correct template
        stimpack.setCustomName('Field Stimpack A')
        crate.add(stimpack)
    
    time.sleep(60)
    core.objectService.destroyObject(crate)
    
    return