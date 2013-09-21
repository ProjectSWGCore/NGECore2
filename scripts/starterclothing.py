

import sys
 
def CreateStarterClothing(core, object, starterProfession, raceTemplate):

        if starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/human_male.iff':
				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutHumanoidMale", "outdoors_scout_humanoid_male", core, object)
        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/zabrak_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutHumanoidMale", "outdoors_scout_humanoid_male", core, object)
        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/bothan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutHumanoidMale", "outdoors_scout_humanoid_male", core, object)
        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/sullustan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutHumanoidMale", "outdoors_scout_humanoid_male", core, object)
        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/twilek_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutHumanoidMale", "outdoors_scout_humanoid_male", core, object)
        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/moncal_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutHumanoidMale", "outdoors_scout_humanoid_male", core, object)
        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/rodian_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutHumanoidMale", "outdoors_scout_humanoid_male", core, object)

        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/human_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutHumanoidFemale", "outdoors_scout_humanoid_female", core, object)
        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/zabrak_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutHumanoidFemale", "outdoors_scout_humanoid_female", core, object)
        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/bothan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutHumanoidFemale", "outdoors_scout_humanoid_female", core, object)
        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/sullustan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutHumanoidFemale", "outdoors_scout_humanoid_female", core, object)
        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/twilek_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutHumanoidFemale", "outdoors_scout_humanoid_female", core, object)
        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/moncal_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutHumanoidFemale", "outdoors_scout_humanoid_female", core, object)      
        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/rodian_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutHumanoidFemale", "outdoors_scout_humanoid_female", core, object)      
            
        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/trandoshan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutTrandoshanMale", "outdoors_scout_trandoshan_male", core, object)

        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/trandoshan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutTrandoshanFemale", "outdoors_scout_trandoshan_female", core, object)

        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/wookiee_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutWookieeMale", "outdoors_scout_wookiee_male", core, object)

        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/wookiee_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutWookieeFemale", "outdoors_scout_wookiee_female", core, object)
        
        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/ithorian_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutIthorianMale", "outdoors_scout_ithorian_male", core, object)
  
        elif starterProfession == 'outdoors_scout' and raceTemplate == 'object/creature/player/ithorian_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "OutdoorScoutIthorianFemale", "outdoors_scout_ithorian_female", core, object)

        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/human_male.iff':
				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerHumanoidMale", "combat_brawler_humanoid_male", core, object)
        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/zabrak_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerHumanoidMale", "combat_brawler_humanoid_male", core, object)
        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/bothan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerHumanoidMale", "combat_brawler_humanoid_male", core, object)
        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/sullustan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerHumanoidMale", "combat_brawler_humanoid_male", core, object)
        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/twilek_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerHumanoidMale", "combat_brawler_humanoid_male", core, object)
        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/moncal_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerHumanoidMale", "combat_brawler_humanoid_male", core, object)
        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/rodian_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerHumanoidMale", "combat_brawler_humanoid_male", core, object)

        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/human_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerHumanoidFemale", "combat_brawler_humanoid_female", core, object)
        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/zabrak_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerHumanoidFemale", "combat_brawler_humanoid_female", core, object)
        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/bothan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerHumanoidFemale", "combat_brawler_humanoid_female", core, object)
        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/sullustan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerHumanoidFemale", "combat_brawler_humanoid_female", core, object)
        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/twilek_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerHumanoidFemale", "combat_brawler_humanoid_female", core, object)
        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/moncal_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerHumanoidFemale", "combat_brawler_humanoid_female", core, object)      
        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/rodian_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerHumanoidFemale", "combat_brawler_humanoid_female", core, object)      
            
        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/trandoshan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerTrandoshanMale", "combat_brawler_trandoshan_male", core, object)

        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/trandoshan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerOutdoorScoutTrandoshanFemale", "combat_brawler_trandoshan_female", core, object)

        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/wookiee_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerWookieeMale", "combat_brawler_wookiee_male", core, object)

        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/wookiee_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerWookieeFemale", "combat_brawler_wookiee_female", core, object)
        
        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/ithorian_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerIthorianMale", "combat_brawler_ithorian_male", core, object)
  
        elif starterProfession == 'combat_brawler' and raceTemplate == 'object/creature/player/ithorian_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatBrawlerIthorianFemale", "combat_brawler_ithorian_female", core, object)


        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/human_male.iff':
				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanHumanoidMale", "combat_marksman_humanoid_male", core, object)
        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/zabrak_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanHumanoidMale", "combat_marksman_humanoid_male", core, object)
        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/bothan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanHumanoidMale", "combat_marksman_humanoid_male", core, object)
        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/sullustan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanHumanoidMale", "combat_marksman_humanoid_male", core, object)
        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/twilek_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanHumanoidMale", "combat_marksman_humanoid_male", core, object)
        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/moncal_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanHumanoidMale", "combat_marksman_humanoid_male", core, object)
        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/rodian_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanHumanoidMale", "combat_marksman_humanoid_male", core, object)

        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/human_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanHumanoidFemale", "combat_marksman_humanoid_female", core, object)
        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/zabrak_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanHumanoidFemale", "combat_marksman_humanoid_female", core, object)
        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/bothan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanHumanoidFemale", "combat_marksman_humanoid_female", core, object)
        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/sullustan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanHumanoidFemale", "combat_marksman_humanoid_female", core, object)
        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/twilek_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanHumanoidFemale", "combat_marksman_humanoid_female", core, object)
        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/moncal_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanHumanoidFemale", "combat_marksman_humanoid_female", core, object)      
        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/rodian_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanHumanoidFemale", "combat_marksman_humanoid_female", core, object)      
            
        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/trandoshan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanTrandoshanMale", "combat_marksman_trandoshan_male", core, object)

        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/trandoshan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanTrandoshanFemale", "combat_marksman_trandoshan_female", core, object)

        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/wookiee_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanWookieeMale", "combat_marksman_wookiee_male", core, object)

        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/wookiee_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanWookieeFemale", "combat_marksman_wookiee_female", core, object)
        
        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/ithorian_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanIthorianMale", "combat_marksman_ithorian_male", core, object)
  
        elif starterProfession == 'combat_marksman' and raceTemplate == 'object/creature/player/ithorian_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CombatMarksmanIthorianFemale", "combat_marksman_ithorian_female", core, object)

        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/human_male.iff':
				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanHumanoidMale", "crafting_artisan_humanoid_male", core, object)
        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/zabrak_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanHumanoidMale", "crafting_artisan_humanoid_male", core, object)
        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/bothan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanHumanoidMale", "crafting_artisan_humanoid_male", core, object)
        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/sullustan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanHumanoidMale", "crafting_artisan_humanoid_male", core, object)
        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/twilek_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanHumanoidMale", "crafting_artisan_humanoid_male", core, object)
        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/moncal_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanHumanoidMale", "crafting_artisan_humanoid_male", core, object)
        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/rodian_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanHumanoidMale", "crafting_artisan_humanoid_male", core, object)

        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/human_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanHumanoidFemale", "crafting_artisan_humanoid_female", core, object)
        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/zabrak_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanHumanoidFemale", "crafting_artisan_humanoid_female", core, object)
        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/bothan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanHumanoidFemale", "crafting_artisan_humanoid_female", core, object)
        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/sullustan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanHumanoidFemale", "crafting_artisan_humanoid_female", core, object)
        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/twilek_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanTwilekFemale", "crafting_artisan_twilek_female", core, object)
        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/moncal_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanHumanoidFemale", "crafting_artisan_humanoid_female", core, object)      
        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/rodian_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanHumanoidFemale", "crafting_artisan_humanoid_female", core, object)      
            
        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/trandoshan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanTrandoshanMale", "crafting_artisan_trandoshan_male", core, object)

        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/trandoshan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanTrandoshanFemale", "crafting_artisan_trandoshan_female", core, object)

        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/wookiee_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanWookieeMale", "crafting_artisan_wookiee_male", core, object)

        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/wookiee_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanWookieeFemale", "crafting_artisan_wookiee_female", core, object)
        
        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/ithorian_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanIthorianMale", "crafting_artisan_ithorian_male", core, object)
  
        elif starterProfession == 'crafting_artisan' and raceTemplate == 'object/creature/player/ithorian_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "CraftingArtisanIthorianFemale", "crafting_artisan_ithorian_female", core, object)


        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/human_male.iff':
				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicHumanoidMale", "science_medic_humanoid_male", core, object)
        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/zabrak_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicHumanoidMale", "science_medic_humanoid_male", core, object)
        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/bothan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicHumanoidMale", "science_medic_humanoid_male", core, object)
        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/sullustan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicHumanoidMale", "science_medic_humanoid_male", core, object)
        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/twilek_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicHumanoidMale", "science_medic_humanoid_male", core, object)
        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/moncal_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicHumanoidMale", "science_medic_humanoid_male", core, object)
        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/rodian_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicHumanoidMale", "science_medic_humanoid_male", core, object)

        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/human_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicHumanoidFemale", "science_medic_humanoid_female", core, object)
        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/zabrak_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicHumanoidFemale", "science_medic_humanoid_female", core, object)
        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/bothan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicHumanoidFemale", "science_medic_humanoid_female", core, object)
        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/sullustan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicHumanoidFemale", "science_medic_humanoid_female", core, object)
        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/twilek_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicHumanoidFemale", "science_medic_humanoid_female", core, object)
        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/moncal_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicHumanoidFemale", "science_medic_humanoid_female", core, object)      
        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/rodian_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicHumanoidFemale", "science_medic_humanoid_female", core, object)      
            
        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/trandoshan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicTrandoshanMale", "science_medic_trandoshan_male", core, object)

        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/trandoshan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicTrandoshanFemale", "science_medic_trandoshan_female", core, object)

        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/wookiee_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicWookieeMale", "science_medic_wookiee_male", core, object)

        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/wookiee_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicWookieeFemale", "science_medic_wookiee_female", core, object)
        
        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/ithorian_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicIthorianMale", "science_medic_ithorian_male", core, object)
  
        elif starterProfession == 'science_medic' and raceTemplate == 'object/creature/player/ithorian_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "ScienceMedicIthorianFemale", "science_medic_ithorian_female", core, object)



        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/human_male.iff':
				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerHumanoidMale", "social_entertainer_humanoid_male", core, object)
        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/zabrak_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerHumanoidMale", "social_entertainer_humanoid_male", core, object)
        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/bothan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerHumanoidMale", "social_entertainer_humanoid_male", core, object)
        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/sullustan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerHumanoidMale", "social_entertainer_humanoid_male", core, object)
        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/twilek_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerHumanoidMale", "social_entertainer_humanoid_male", core, object)
        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/moncal_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerHumanoidMale", "social_entertainer_humanoid_male", core, object)
        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/rodian_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerHumanoidMale", "social_entertainer_humanoid_male", core, object)

        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/human_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerHumanoidFemale", "social_entertainer_humanoid_female", core, object)
        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/zabrak_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerHumanoidFemale", "social_entertainer_humanoid_female", core, object)
        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/bothan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerHumanoidFemale", "social_entertainer_humanoid_female", core, object)
        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/sullustan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerHumanoidFemale", "social_entertainer_humanoid_female", core, object)
        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/twilek_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerTwilekFemale", "social_entertainer_twilek_female", core, object)
        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/moncal_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerHumanoidFemale", "social_entertainer_humanoid_female", core, object)      
        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/rodian_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerHumanoidFemale", "social_entertainer_humanoid_female", core, object)      
            
        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/trandoshan_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerTrandoshanMale", "social_entertainer_trandoshan_male", core, object)

        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/trandoshan_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerTrandoshanFemale", "social_entertainer_trandoshan_female", core, object)

        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/wookiee_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerWookieeMale", "social_entertainer_wookiee_male", core, object)

        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/wookiee_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerWookieeFemale", "social_entertainer_wookiee_female", core, object)
        
        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/ithorian_male.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerIthorianMale", "social_entertainer_ithorian_male", core, object)
  
        elif starterProfession == 'social_entertainer' and raceTemplate == 'object/creature/player/ithorian_female.iff':
 				core.scriptService.callScript("scripts/starter_clothing/", "SocialEntertainerIthorianFemale", "social_entertainer_ithorian_female", core, object)
        