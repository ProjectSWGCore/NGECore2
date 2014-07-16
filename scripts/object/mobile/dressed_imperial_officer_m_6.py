import sys

def setup(core, object):
    core.mapService.addLocation(object.getPlanet(), '@map_loc_cat_n:imperial', object.getPosition().x, object.getPosition().z, 46, 48, 0)
    return
