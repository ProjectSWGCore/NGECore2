import sys
 
def addPoints(core, planet):
   
    if planet.getName() == 'tatooine':
        tatooinePoints(core, planet)
 
    return
 
def tatooinePoints(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Bestine Starport", -1376, 12, -3576)
    trvSvc.addTravelPoint(planet, "Mos Eisley Starport", 3619, 5, -4801)
    trvSvc.addTravelPoint(planet, "Mos Espa Starport", -2829, 5, 2080)
    trvSvc.addTravelPoint(planet, "Mos Entha Starport", 1238, 7, 3062)
    return