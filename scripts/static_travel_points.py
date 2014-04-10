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
    if planet.getName() == 'kashyyyk_main':
    	kashyyyk_mainPoints(core, planet)
    if planet.getName() == 'mustafar':
    	mustafarPoints(core, planet)
    return

def corelliaPoints(core, planet):
    trvSvc = core.travelService
    
    trvSvc.addTravelPoint(planet, "Coronet Shuttle A", -25, 28, -4409)
    trvSvc.addTravelPoint(planet, "Coronet Shuttle B", -329, 28, -4641)
    trvSvc.addTravelPoint(planet, "Vreni Island Shuttle", -5551, 15, -6059)
    trvSvc.addTravelPoint(planet, "Tyrena Shuttle A", -5005, 21, -2386)
    trvSvc.addTravelPoint(planet, "Tyrena Shuttle B", -5600, 21, -2790)
    trvSvc.addTravelPoint(planet, "Doaba Guerfel Shuttleport", 3085, 280, 4993)
    trvSvc.addTravelPoint(planet, "Kor Vella Shuttleport", -3775, 86, 3234)
    trvSvc.addTravelPoint(planet, "Bela Vistal Shuttleport A", 6644, 330, -5922)
    trvSvc.addTravelPoint(planet, "Bela Vistal Shuttleport B", 6930, 330, -5534)
    trvSvc.addTravelPoint(planet, "Coronet Starport", -51, 28, -4735)
    trvSvc.addTravelPoint(planet, "Tyrena Starport", -4975, 21, -2230)
    trvSvc.addTravelPoint(planet, "Kor Vella Starport", -3136, 31, 2894)
    trvSvc.addTravelPoint(planet, "Doaba Guerfel Starport", 3377, 308, 5605)
    return        

def dantooinePoints(core, planet):
    trvSvc = core.travelService
    
    trvSvc.addTravelPoint(planet, "Mining Outpost", -635, 3, 2507)
    trvSvc.addTravelPoint(planet, "Imperial Outpost", -4208, 3, -2350)
    trvSvc.addTravelPoint(planet, "Agro Outpost", 1569, 4, -6415)
    return                
        
def dathomirPoints(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Science Outpost", -49, 18, -1584)
    trvSvc.addTravelPoint(planet, "Trade Outpost", 618, 6, 3092)
    trvSvc.addTravelPoint(planet, "Quarantine Zone", -5691, 511, -6467)
    return        

def endorPoints(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Smuggler Outpost", -950, 73, 1553)
    trvSvc.addTravelPoint(planet, "Research Outpost", 3201, 24, -3499)
    return        
        
def lokPoints(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Nym's Stronghold", 459, 9, 5494)
    return

def nabooPoints(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Kaadara Starport", 5295, -192, 6664)
    trvSvc.addTravelPoint(planet, "Kaadara Shuttleport", 5123, -192, 6616)
    trvSvc.addTravelPoint(planet, "Dee'ja Peak Shuttleport", 5331, 327, -1576)
    trvSvc.addTravelPoint(planet, "Moenia Shuttleport", 4961, 3, -4892)
    trvSvc.addTravelPoint(planet, "Lake Retreat Shuttleport", -5494, -150, -21)
    trvSvc.addTravelPoint(planet, "Keren Shuttleport", 2021, 19, 2525)
    trvSvc.addTravelPoint(planet, "Keren Shuttleport South", 1567, 25, 2837)  
    trvSvc.addTravelPoint(planet, "Keren Starport", 1352, 13, 2768)
    trvSvc.addTravelPoint(planet, "Moenia Starport", 4728, 4, -4650)
    trvSvc.addTravelPoint(planet, "Theed Shuttle A", -5856, 19, 4172)
    trvSvc.addTravelPoint(planet, "Theed Shuttle B", -5005, 19, 4072)
    trvSvc.addTravelPoint(planet, "Theed Shuttle C", -5411, 19, 4332)
    trvSvc.addTravelPoint(planet, "Theed Spaceport", -4858, 5, 4164)
    return        
        
def roriPoints(core, planet):
    trvSvc = core.travelService

    trvSvc.addTravelPoint(planet, "Narmle Starport", -5374, 80, -2188)
    trvSvc.addTravelPoint(planet, "Narmle Shuttleport", -5255, 80, -2161)    
    trvSvc.addTravelPoint(planet, "Rebel Outpost", 3672, 96, -6421)
    trvSvc.addTravelPoint(planet, "Restuss Starport", 5281, 80, 6171)
    trvSvc.addTravelPoint(planet, "Restuss Shuttleport", 5398, 80, 6195)
    return                

def talusPoints(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Dearic Starport", 263, 6, -2952)
    trvSvc.addTravelPoint(planet, "Dearic Shuttleport", 699, 6, -3041)
    trvSvc.addTravelPoint(planet, "Nashal Starport", 4453, 2, 5354)
    trvSvc.addTravelPoint(planet, "Nashal Shuttleport", 4334, 9, 5431)
    trvSvc.addTravelPoint(planet, "Imperial Outpost", -2226, 20, 2319)
    return                
        
def tatooinePoints(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Bestine Starport", -1376, 12, -3576)
    trvSvc.addTravelPoint(planet, "Mos Eisley Starport", 3619, 5, -4801)
    trvSvc.addTravelPoint(planet, "Mos Espa Starport", -2829, 5, 2080)
    trvSvc.addTravelPoint(planet, "Mos Entha Starport", 1238, 7, 3062)
    trvSvc.addTravelPoint(planet, "Anchorhead Shuttle", 48, 52, -5319)
    trvSvc.addTravelPoint(planet, "Mos Entha Shuttleport A", 1731, 7, 3205)
    trvSvc.addTravelPoint(planet, "Mos Entha Shuttleport B", 1396, 7, 3487)
    trvSvc.addTravelPoint(planet, "Mos Espa Shuttle West", -3132, 5, 2172)
    trvSvc.addTravelPoint(planet, "Mos Espa Shuttle East", -2799, 5, 2163)
    trvSvc.addTravelPoint(planet, "Mos Espa Shuttle South", -2892, 5, 1914)
    trvSvc.addTravelPoint(planet, "Mos Eisley Shuttleport", 3434, 5, -4659)
    trvSvc.addTravelPoint(planet, "Bestine Shuttleport", -1071, 12, -3566)
    trvSvc.addTravelPoint(planet, "Wayfar Shuttleport", -5089, 75, -6594)
    return
        
def yavin4Points(core, planet):
    trvSvc = core.travelService
   
    trvSvc.addTravelPoint(planet, "Imperial Base", 4054, 37, -6216)
    trvSvc.addTravelPoint(planet, "Labor Outpost", -6921, 73, -5726)
    trvSvc.addTravelPoint(planet, "Mining Outpost", -267, 35, 4896)
    return
    
def kashyyyk_mainPoints(core, planet):
	trvSvc = core.travelService
	
	trvSvc.addTravelPoint(planet, "Kachirho Starport", -688, 19, -165)
	return
	
def mustafarPoints(core, planet):
	trvSvc = core.travelService
	
	trvSvc.addTravelPoint(planet, "Mensix Mining Facility", 405, 230, -1352)
	return
