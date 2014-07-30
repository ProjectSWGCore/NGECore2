#-----Rogue CorSec Base NPC's----- Location: 5291 5 1493 on Corellia and is a POI
#---- 7/29/14 ----
#  Scripted by --- Steve -- Aka Oswin ---
#/*******************************************************************************
# * Copyright (c) 2014 <Project SWG>
# * 
# * This File is part of NGECore2.
# * 
# * This program is free software: you can redistribute it and/or modify
# * it under the terms of the GNU Lesser General Public License as published by
# * the Free Software Foundation, either version 3 of the License, or
# * (at your option) any later version.
# * 
# * This program is distributed in the hope that it will be useful,
# * but WITHOUT ANY WARRANTY; without even the implied warranty of
# * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# * GNU Lesser General Public License for more details.
# * 
# * You should have received a copy of the GNU Lesser General Public License
# * along with this program.  If not, see <http://www.gnu.org/licenses/>.
# * 
# * Using NGEngine to work with NGECore2 is making a combined work based on NGEngine. 
# * Therefore all terms and conditions of the GNU Lesser General Public License cover the combination.
# ******************************************************************************/






import sys
from resources.datatables import Options
from resources.datatables import State
from resources.datatables import Posture


def addPlanetSpawns(core, planet):

    stcSvc = core.staticService
    objSvc = core.objectService
	
	#------------ Outside -------
    Ren_Cor_Trooper1 = stcSvc.spawnObject('rogue_corsec_trooper18', 'corellia', long(0),
                       float(5239.30517578),float(5.0),float(1532.01525879),
                       float(0.730644881725),float(0.682757735252))
    
    Ren_Cor_Trooper2 = stcSvc.spawnObject('rogue_corsec_trooper18', 'corellia', long(0),
                       float(5242.40283203),float(5.0),float(1534.21960449),
                       float(0.993126451969),float(0.117046073079))
    
    Rog_Cor_Trooper2 = stcSvc.spawnObject('rogue_corsec_trooper18', 'corellia', long(0),
                       float(5207.69628906),float(5.0),float(1514.98352051),
                       float(0.51064157486),float(0.85979372263))
    
    Rog_Cor_Trooper3 = stcSvc.spawnObject('rogue_corsec_trooper18', 'corellia', long(0),
                       float(5200.53564453),float(5.0),float(1515.94299316),
                       float(0.598824501038),float(0.80088031292))
    
    CorSec_Traitor1 = stcSvc.spawnObject('corsec_traitor', 'corellia', long(0),
                       float(5220.94091797),float(5.0),float(1582.70581055),
                       float(0.882547259331),float(-0.470223665237))
    
    CorSec_Traitor2 = stcSvc.spawnObject('corsec_traitor', 'corellia', long(0),
                       float(5213.69287109),float(5.0),float(1588.84338379),
                       float(0.963998377323),float(0.26590821147))
    
    CorSec_Deserter1 = stcSvc.spawnObject('corsec_deserter', 'corellia', long(0),
                       float(5225.44091797),float(5.0),float(1614.59960938),
                       float(0.287998735905),float(0.957630813122))
    
    CorSec_Deserter2 = stcSvc.spawnObject('corsec_deserter', 'corellia', long(0),
                       float(5223.74121094),float(5.0),float(1611.17736816),
                       float(0.297436177731),float(0.95474165678))
    
    CorSec_Deserter3 = stcSvc.spawnObject('corsec_deserter', 'corellia', long(0),
                       float(5229.30126953),float(5.0),float(1612.91882324),
                       float(0.278909385204),float(0.960317373276))
    
    
    
    
    #------------ End Of Outside -----
    
    #------ Inside medcenter 1 at Waypoint 5234 5 1529 
    
    Rogue_CorSec_Base_Med1 = core.objectService.getObject(long(1855744))
    
    Deserter1  = stcSvc.spawnObject('corsec_deserter', 'corellia', Rogue_CorSec_Base_Med1.getCellByCellNumber(2),
                        float(-9.90300655365),float(0.184067249298),float(-7.31617689133),
                        float(-0.688307523727),float(0),float(0.725418925285),float(0))
    
    Traitor1  = stcSvc.spawnObject('corsec_traitor', 'corellia',  Rogue_CorSec_Base_Med1.getCellByCellNumber(2),
                        float(-9.90300655365),float(0.184067249298),float(-7.31617689133),
                        float(-0.688307523727),float(0),float(0.725418925285),float(0))
    
    Traitor2  = stcSvc.spawnObject('corsec_traitor', 'corellia',  Rogue_CorSec_Base_Med1.getCellByCellNumber(2),
                        float(10.4480714798),float(0.184067159891),float(5.86545562744),
                        float(-0.601387441158),float(0),float(0.798957526684),float(0))
    
    #--------------------------- End MedCenter1
    #------ Inside medcenter 2 at Waypoint 5237 5 1562
    Rogue_CorSec_Base_Med2 = core.objectService.getObject(long(1855740))
    
    Rog_Cor_Trooper1 = stcSvc.spawnObject('rogue_corsec_trooper18', 'corellia',  Rogue_CorSec_Base_Med2.getCellByCellNumber(2),
                        float(-9.91256332397),float(0.1840672791),float(4.8388800621),
                        float(0.71504420042),float(0),float(0.699079334736),float(0))
    
    Deserter1  = stcSvc.spawnObject('corsec_deserter', 'corellia', Rogue_CorSec_Base_Med2.getCellByCellNumber(2),
                        float(-9.60784912109),float(0.184067249298),float(-2.13075208664),
                        float(0.905477404594),float(0),float(0.424394547939),float(0))
    
    Rog_Cor_Trooper2 = stcSvc.spawnObject('rogue_corsec_trooper18', 'corellia',  Rogue_CorSec_Base_Med2.getCellByCellNumber(2),
                        float(-3.65777277946),float(0.184067234397),float(1.14164805412),
                        float(-0.559259176254),float(0),float(0.828992724419),float(0))
    
        
    #--------------------------- End MedCenter2
    #------ inside med center 3 waypoint 5206 5 1602
    Rogue_CorSec_Base_Med3 = core.objectService.getObject(long(1855736))
    
    Reg_Cor_Trooper1 = stcSvc.spawnObject('rogue_corsec_trooper18', 'corellia',  Rogue_CorSec_Base_Med3.getCellByCellNumber(2),
                        float(12.173201561),float(0.18406714499),float(4.9269080162),
                        float(-0.439735233784),float(0),float(0.898127436638),float(0))
    
    Reg_Cor_Trooper2 = stcSvc.spawnObject('rogue_corsec_trooper18', 'corellia',  Rogue_CorSec_Base_Med3.getCellByCellNumber(2),
                        float(10.5546112061),float(0.184067204595),float(0.480068802834),
                        float(0.0608799904585),float(0),float(0.998145103455),float(0))
    
    Reg_Cor_Trooper3 = stcSvc.spawnObject('rogue_corsec_trooper18', 'corellia',  Rogue_CorSec_Base_Med3.getCellByCellNumber(2),
                        float(3.11612772942),float(0.184067234397),float(-2.51539731026),
                        float(0.968136191368),float(0),float(0.250424176455),float(0))
    
    Reg_Cor_Trooper4 = stcSvc.spawnObject('rogue_corsec_trooper18', 'corellia',  Rogue_CorSec_Base_Med3.getCellByCellNumber(2),
                        float(-6.4247841835),float(0.184067249298),float(5.43209028244),
                        float(0.745934486389),float(0),float(0.666019380093),float(0))
    
    
    #--------------------------- End MedCenter3
    #------ inside Bunker ------- Bugged :( working on it 
    Rogue_CorSec_Base_Bunker = core.objectService.getObject(long(1855720))
    
    ##### Line of Sight problems here #### NPC's walk though walls
   
   
    
    
    #--------------------------- End Bunker 
    
   
    
    
     
  
    return