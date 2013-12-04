from engine.resources.container import StaticContainerPermissions
import time
import sys

def setup(core, actor, target, command):
    return
    
def run(core, actor, target, commandString):
    doStimDrop(core, actor)
    return

def doStimDrop(core, actor):
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
    crate = core.staticService.spawnObject('object/tangible/container/loot/shared_npe_loot_crate_low.iff', shuttle.getPlanet().getName(), long(0), shuttle.getPosition().x, shuttle.getPosition().y, shuttle.getPosition().z, shuttle.getOrientation().y, shuttle.getOrientation().w)
    crate.setCustomName('Supply Crate')
    
    permissions = StaticContainerPermissions()
    permissions.canRemove(actor, crate)
    permissions.canView(actor, crate)
    crate.setContainerPermissions(permissions)
    
    tac1 = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_syren.iff', actor.getPlanet())
    crate.add(tac1)
    tac2 = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_syren.iff', actor.getPlanet())
    crate.add(tac2)
    tac3 = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_syren.iff', actor.getPlanet())
    crate.add(tac3)
    tac4 = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_syren.iff', actor.getPlanet())
    crate.add(tac4)
    tac5 = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_syren.iff', actor.getPlanet())
    crate.add(tac5)
    tac6 = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_syren.iff', actor.getPlanet())
    crate.add(tac6)
    tac7 = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_syren.iff', actor.getPlanet())
    crate.add(tac7)
    tac8 = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_syren.iff', actor.getPlanet())
    crate.add(tac8)
    
    stim1 = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_generic_a.iff', actor.getPlanet())
    crate.add(stim1)
    stim2 = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_generic_a.iff', actor.getPlanet())
    crate.add(stim2)
    stim3 = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_generic_a.iff', actor.getPlanet())
    crate.add(stim3)
    stim4 = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_generic_a.iff', actor.getPlanet())
    crate.add(stim4)
    stim5 = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_generic_a.iff', actor.getPlanet())
    crate.add(stim5)
    stim6 = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_generic_a.iff', actor.getPlanet())
    crate.add(stim6)
    stim7 = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_generic_a.iff', actor.getPlanet())
    crate.add(stim7)
    stim8 = core.objectService.createObject('object/tangible/medicine/instant_stimpack/shared_stimpack_generic_a.iff', actor.getPlanet())
    crate.add(stim8)
    
    time.sleep(60)
    core.objectService.destroyObject(crate)
    
    return