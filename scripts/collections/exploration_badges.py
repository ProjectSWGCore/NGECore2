import sys
import services.collections.CollectionService

	
def setup(collectionService):

#Corellia
	collectionService.registerExplorationBadge('corellia', 'bdg_exp_cor_rogue_corsec_base', 5175, 1644, 64)
	collectionService.registerExplorationBadge('corellia', 'bdg_exp_cor_tyrena_theater', -5418, -6248, 64)
	collectionService.registerExplorationBadge('corellia', 'bdg_exp_cor_bela_vistal_fountain', 6760, -5617, 32)
	collectionService.registerExplorationBadge('corellia', 'bdg_exp_cor_rebel_hideout', -6530, 5967, 64)
	collectionService.registerExplorationBadge('corellia', 'exp_cor_agrilat_swamp', 1389, 3756, 64)
 
#Dantooine
	collectionService.registerExplorationBadge('dantooine', 'bdg_exp_dan_dantari_village1', -3862, -5706, 16)
	collectionService.registerExplorationBadge('dantooine', 'bdg_exp_dan_dantari_village2', -7156, -883, 32)
	collectionService.registerExplorationBadge('dantooine', 'exp_dan_rebel_base', -6836, 5520, 32)
	collectionService.registerExplorationBadge('dantooine', 'exp_dan_jedi_temple', 4195, 5203, 16)

#Dathomir
	collectionService.registerExplorationBadge('dathomir', 'exp_dat_tarpit', 668, -4835, 32)
	collectionService.registerExplorationBadge('dathomir', 'exp_dat_sarlacc', -2101, 3165, 16)
	collectionService.registerExplorationBadge('dathomir', 'exp_dat_escape_pod', -4437, 574, 32)
	collectionService.registerExplorationBadge('dathomir', 'exp_dat_misty_falls_1', 3558, 1554, 32)
	collectionService.registerExplorationBadge('dathomir', 'exp_dat_misty_falls_2', 3021, 1289, 32)
	collectionService.registerExplorationBadge('dathomir', 'bdg_exp_dat_imp_prison', -6304, 753, 32)
	collectionService.registerExplorationBadge('dathomir', 'bdg_exp_dat_crashed_ship', 5727, 1923, 32)

#Endor
	collectionService.registerExplorationBadge('endor', 'bdg_exp_end_ewok_tree_village', 4596, -2423, 32)
	collectionService.registerExplorationBadge('endor', 'bdg_exp_end_ewok_lake_village', 1454, -3272, 32)
	collectionService.registerExplorationBadge('endor', 'bdg_exp_end_dulok_village', 6051, -2477, 16)
	collectionService.registerExplorationBadge('endor', 'bdg_exp_end_imp_outpost', -4628, -2273, 32)

#Kashyyyk
	collectionService.registerExplorationBadge('kashyyyk', 'exp_kash_kachirho_found', -660, -201, 64)

#Lok
	collectionService.registerExplorationBadge('lok', 'exp_lok_volcano', 3091, -4638, 32)
	collectionService.registerExplorationBadge('lok', 'bdg_exp_lok_imp_outpost', -1814, -3086, 32)
	collectionService.registerExplorationBadge('lok', 'bdg_exp_lok_kimogila_skeleton', 4562, -1156, 16)

#Mustafar
	collectionService.registerExplorationBadge('mustafar', 'exp_must_mustafar_found', 0, 0, 32)

#Naboo
	collectionService.registerExplorationBadge('naboo', 'exp_nab_gungan_sacred_place', -2066, -5423, 32)
	collectionService.registerExplorationBadge('naboo', 'bdg_exp_nab_theed_falls_bottom', -4628, 4207, 32)
	collectionService.registerExplorationBadge('naboo', 'bdg_exp_nab_deeja_falls_top', 5157, -1646, 32)
	collectionService.registerExplorationBadge('naboo', 'bdg_exp_nab_amidalas_sandy_beach', -5828, -93, 32)

#Rori
	collectionService.registerExplorationBadge('rori', 'bdg_exp_ror_imp_camp', -5633, -5661, 32)
	collectionService.registerExplorationBadge('rori', 'bdg_exp_ror_imp_hyperdrive_fac', -1130, 4544, 32)
	collectionService.registerExplorationBadge('rori', 'bdg_exp_ror_rebel_outpost', 3664, -6501, 32)
	collectionService.registerExplorationBadge('rori', 'bdg_exp_ror_kobala_spice_mine', 7371, 169, 32)

#Talus
	collectionService.registerExplorationBadge('talus', 'bdg_exp_tal_creature_village', 4133, 962, 32)
	collectionService.registerExplorationBadge('talus', 'bdg_exp_tal_imp_base', -2184, 2405, 32)
	collectionService.registerExplorationBadge('talus', 'bdg_exp_tal_imp_vs_reb_battle', -2452, 3846, 32)
	collectionService.registerExplorationBadge('talus', 'bdg_exp_tal_aqualish_cave', -4425, -1414, 64)

#Tatooine
	collectionService.registerExplorationBadge('tatooine', 'exp_tat_lars_homestead', -2579, -5500, 32)
	collectionService.registerExplorationBadge('tatooine', 'exp_tat_bens_hut', -4512, -2270, 16)
	collectionService.registerExplorationBadge('tatooine', 'exp_tat_sarlacc_pit', -6176, -3372, 32)
	collectionService.registerExplorationBadge('tatooine', 'exp_tat_escape_pod', -3931, -4397, 32)
	collectionService.registerExplorationBadge('tatooine', 'exp_tat_krayt_skeleton', -4632, -4346, 16)
	collectionService.registerExplorationBadge('tatooine', 'exp_tat_krayt_graveyard', 7396, 4478, 64)
	collectionService.registerExplorationBadge('tatooine', 'exp_tat_tusken_pool', -3966, 6267, 32)

#Yavin
	collectionService.registerExplorationBadge('yavin4', 'exp_yav_temple_exar_kun', 5076, 5537, 32)
	collectionService.registerExplorationBadge('yavin4', 'exp_yav_temple_blueleaf', -875, -2047, 32)
	collectionService.registerExplorationBadge('yavin4', 'exp_yav_temple_woolamander', 519, -646, 32)
