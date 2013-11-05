import sys
 
def addPoints(core, planet):
   
    if planet.getName() == 'corellia':
        corelliaPoints(core, planet)
    if planet.getName() == 'dantooine':
        dantooinePoints(core, planet)
    if planet.getName() == 'dathomir':
        dathomirPoints(core, planet)
    if planet.getName() == 'endor':
        endorPoints(core, planet)
    if planet.getName() == 'lok':
        lokPoints(core, planet)
    if planet.getName() == 'naboo':
        nabooPoints(core, planet)
    if planet.getName() == 'rori':
        roriPoints(core, planet)
    if planet.getName() == 'talus':
        talusPoints(core, planet)
    if planet.getName() == 'tatooine':
        tatooinePoints(core, planet)
    if planet.getName() == 'yavin4':
        yavin4Points(core, planet)                
    return

def corelliaPoints(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Coronet Starport", -51, 28, -4735)
    trvSvc.addTravelPoint(planet, "Tyrena Starport", -4975, 21, -2230)
    trvSvc.addTravelPoint(planet, "Kor Vella Starport", -3136, 31, 2894)
    trvSvc.addTravelPoint(planet, "Doaba Guerful Starport", 3377, 308, 5605)
    return        

def dantooinePoints(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Coronet Starport", -51, 28, -4735)
    trvSvc.addTravelPoint(planet, "Tyrena Starport", -4975, 21, -2230)
    trvSvc.addTravelPoint(planet, "Kor Vella Starport", -3136, 31, 2894)
    trvSvc.addTravelPoint(planet, "Doaba Guerful Starport", 3377, 308, 5605)
    return                
        
def dathomirPoints(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Science Outpost", -76, 18, -1586)
    trvSvc.addTravelPoint(planet, "Trade Outpost", -592, 6, 3087)
    return        

def endorPoints(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Smuggler Outpost", -978, 73, 1554)
    trvSvc.addTravelPoint(planet, "Research Outpost", 3222, 24, -3482)
    return        
        
def lokPoints(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Nym's Stronghold", 459, 9, 5494)
    return

def nabooPoints(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Kaadara Starport", 5295, -192, 6664)
    trvSvc.addTravelPoint(planet, "Keren Starport", 1352, 13, 2768)
    trvSvc.addTravelPoint(planet, "Moenia Starport", 4728, 4, -4650)
    return        
        
def roriPoints(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Narmle Starport", -5472, 80, 2161)
    trvSvc.addTravelPoint(planet, "Rebel Outpost", 3672, 96, -6421)
    return                
        
def talusPoints(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Dearic Starport", 245, 6, -2931)
    trvSvc.addTravelPoint(planet, "Nashal Starport", 4480, 2, 6365)
    trvSvc.addTravelPoint(planet, "Imperial Outpost", -2212, 20, 2302)
    return                
        
def tatooinePoints(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Bestine Starport", -1376, 12, -3576)
    trvSvc.addTravelPoint(planet, "Mos Eisley Starport", 3619, 5, -4801)
    trvSvc.addTravelPoint(planet, "Mos Espa Starport", -2829, 5, 2080)
    trvSvc.addTravelPoint(planet, "Mos Entha Starport", 1238, 7, 3062)
    return
        
def yavin4Points(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Imperial Base", 4033, 37, -6234)
    trvSvc.addTravelPoint(planet, "Labor Outpost", -6939, 73, -5706)
    trvSvc.addTravelPoint(planet, "Mining Outpost", -277, 35, 4879)
    return