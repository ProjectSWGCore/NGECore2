/*******************************************************************************
 * Copyright (c) 2013 <Project SWG>
 * 
 * This File is part of NGECore2.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Using NGEngine to work with NGECore2 is making a combined work based on NGEngine. 
 * Therefore all terms and conditions of the GNU Lesser General Public License cover the combination.
 ******************************************************************************/
package services.collections;

import java.util.BitSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.python.core.Py;
import org.python.core.PyObject;

import resources.common.FileUtilities;
import resources.common.OutOfBand;
import resources.datatables.DisplayType;
import resources.objects.creature.CreatureObject;
import resources.objects.player.PlayerObject;
import main.NGECore;
import engine.clientdata.ClientFileManager;
import engine.clientdata.visitors.DatatableVisitor;
import engine.resources.scene.Point3D;
import engine.resources.service.INetworkDispatch;
import engine.resources.service.INetworkRemoteEvent;

public class CollectionService implements INetworkDispatch {
	
	private NGECore core;
	
	private Map<String, Map<String, ExplorationRegion>> explorationLocations = new TreeMap<String, Map<String, ExplorationRegion>>();
	
	private class ExplorationRegion {
		
		public Point3D location;
		public float range;
		
		public ExplorationRegion(Point3D location, float range) {
			this.location = location;
			this.range = range;
		}
		
	}
	
	public CollectionService(NGECore core) {
		this.core = core;
		
		if (FileUtilities.doesFileExist("scripts/collections/exploration_badges.py")) {
			PyObject method = core.scriptService.getMethod("scripts/collections/", "exploration_badges", "setup");
			
			if (method != null && method.isCallable()) {
				method.__call__(Py.java2py(this));
			}
		}
	}
	
	/**
	 * @see datatables/collection/collections.iff
	 * 
	 * Count and exploration count badges are added automatically.
	 * 
	 * You can add a new exploration location in scripts/collections
	 * /exploration_badges.py by defining setup(collectionService)
	 * and using collectionService.registerExplorationBadge(badge, x, z).
	 * 
	 * Scripts named collection's slotName or collectionName should be
	 * put in scripts/collections/ and can use the modify(core, actor, count)
	 * method upon modification or complete(core, actor, count) on completion.
	 * 
	 * Please distinguish a slot from a collection.  A slot could be
	 * a type of blacksun ship where you have to kill 25 of them, and a
	 * collection could be a row of different blacksun ships.
	 * 
	 * You'll need to choose carefully whether you make a script for the
	 * collection, or the specific slots, or both.
	 * 
	 * @param creature The player's CreatureObject instance.
	 * @param collection slotName of the collection you want to add.
	 * 
	 * @return True if added successfully.
	 */
	@SuppressWarnings("unused")
	public boolean addCollection(CreatureObject creature, String collection) {
		PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
		DatatableVisitor collectionTable;
		
		if (player == null) {
			return false;
		}
		
		collection = collection.toLowerCase();
		
		if (isComplete(creature, collection)) {
			return false;
		}
		
		try {
			collectionTable = ClientFileManager.loadFile("datatables/collection/collection.iff", DatatableVisitor.class);
			BitSet collections;
			String bookName = "";
			String pageName = "";
			String collectionName = "";
			boolean titleOnComplete = false;
			
			collections = BitSet.valueOf(player.getCollections());
			
			for (int c = 0; c < collectionTable.getRowCount(); c++) {
				if (collectionTable.getObject(c, 0) != null) {
					if (((String) collectionTable.getObject(c, 0)) != "") {
						bookName = ((String) collectionTable.getObject(c, 0));
						pageName = "";
						collectionName = "";
						titleOnComplete = false;
					} else if (((String) collectionTable.getObject(c, 1)) != "") {
						pageName = ((String) collectionTable.getObject(c, 1));
						collectionName = "";
						titleOnComplete = false;
					} else if (((String) collectionTable.getObject(c, 2)) != "") {
						collectionName = ((String) collectionTable.getObject(c, 2));
						titleOnComplete = ((Boolean) collectionTable.getObject(c, 27));
					} else if (((String) collectionTable.getObject(c, 3)) != "") {
						String slotName = ((String) collectionTable.getObject(c, 3));
						
						if (slotName.equals(collection)) {
							int bits = 0;
							boolean noScriptOnModify = false;
							boolean clearOnComplete = false;
							boolean noMessage = true;
							boolean grantIfPreReqMet = true;
							boolean buddyCollection = false;
							int numAltTitles = 0;
							int beginSlotId = ((Integer) collectionTable.getObject(c, 4));
							int endSlotId = ((Integer) collectionTable.getObject(c, 5));
							int maxSlotValue = ((Integer) collectionTable.getObject(c, 6));
							String music = ((String) collectionTable.getObject(c, 24));
							boolean hidden = ((Boolean) collectionTable.getObject(c, 26));
							boolean title = ((Boolean) collectionTable.getObject(c, 27));
							boolean noReward = ((Boolean) collectionTable.getObject(c, 33));
							boolean trackServerFirst = ((Boolean) collectionTable.getObject(c, 34));
							
							String[] categories = {
									((String) collectionTable.getObject(c, 7)),
									((String) collectionTable.getObject(c, 8)),
									((String) collectionTable.getObject(c, 9)),
									((String) collectionTable.getObject(c, 10)),
									((String) collectionTable.getObject(c, 11)),
									((String) collectionTable.getObject(c, 12)),
									((String) collectionTable.getObject(c, 13)),
									((String) collectionTable.getObject(c, 14)),
									((String) collectionTable.getObject(c, 15)),
									((String) collectionTable.getObject(c, 16)),
									((String) collectionTable.getObject(c, 17))
							};
							
							String[] prereqSlotNames = {
									((String) collectionTable.getObject(c, 18)),
									((String) collectionTable.getObject(c, 19)),
									((String) collectionTable.getObject(c, 20)),
									((String) collectionTable.getObject(c, 21)),
									((String) collectionTable.getObject(c, 22))
							};
							
							String[] alternateTitles = {
									((String) collectionTable.getObject(c, 28)),
									((String) collectionTable.getObject(c, 29)),
									((String) collectionTable.getObject(c, 30)),
									((String) collectionTable.getObject(c, 31)),
									((String) collectionTable.getObject(c, 32))
							};
							
							if (bookName.equals("")) {
								System.out.println(slotName + ", row " + c + ": slot " + slotName + " must be in a book");
								throw new Exception();
							}
							
							if (pageName.equals("")) {
								System.out.println(slotName + ", row " + c + ": slot " + slotName + " must be in a page");
								throw new Exception();
							}
							
							if (collectionName.equals("")) {
								System.out.println(slotName + ", row " + c + ": slot " + slotName + " must be in a collection");
								throw new Exception();
							}
							
							if (endSlotId != -1) {
								if (beginSlotId >= endSlotId) {
									System.out.println(slotName + ", row " + c + ": begin slot id " + beginSlotId + " must be < end slot id" + endSlotId);
									throw new Exception();
								}
								
								bits = (endSlotId - beginSlotId);
								
								if (bits > 32) {
									System.out.println(slotName + ", row " + c + ": counter-type slot uses " + bits + " bits which exceeds the limit of 32 bits for counter-type slot");
									throw new Exception();
								}
								
								if (maxSlotValue > -1) {
									if (!(maxSlotValue > 1)) {
										System.out.println(slotName + ", row " + c + ": max slot value " + maxSlotValue + " must be > 1");
										throw new Exception();
									}
									
									BitSet bitValue = new BitSet(64);
									bitValue.set(64-bits, 64);
									
									if (bitValue.toLongArray()[0] < maxSlotValue) {
										System.out.println(slotName + ", row " + c + ": counter-type slot uses " + bits + " bits, which can only hold a max value of " + bitValue.toLongArray()[0] + ", which is less than the specified max value of " + maxSlotValue);
										throw new Exception();
									}
								}
							} else {
								bits = 1;
							}
							
							for (String prereqSlotName : prereqSlotNames) {
								if (!prereqSlotName.equals("") && getCollection(creature, prereqSlotName) < 1) {
									if (prereqSlotName.equals(slotName)) {
										System.out.println(slotName + ", row " + c + ": slot " + slotName + " cannot have itself as a prereq");
										throw new Exception();
									}
									
									return false;
								}
							}
							
							for (String category : categories) {
								if (category.equals("noScriptOnModify")) {
									noScriptOnModify = true;
								} else if (category.equals("clearOnComplete")) {
									clearOnComplete = true;
								} else if (category.equals("noMessage")) {
									noMessage = true;
								} else if (category.equals("grantIfPreReqMet")) {
									grantIfPreReqMet = true;
								} else if (category.equals("buddyCollection")) {
									buddyCollection = true;
								} else if (category.startsWith("numAltTitles")) {
									numAltTitles = new Integer(category.split(":")[1]);
								} else if (category.startsWith("kill")) {
									continue;
								} else if (category.contains("showFullInformationToOthers")) {
									continue;
								} else if (category.contains("collection")) {
									continue;
								} else if (category.startsWith("updateOnCount")) {
									continue;
								} else if (category.equals("rewardOnUpdate")) {
									continue;
								} else if (category.equals("rewardOnComplete")) {
									continue;
								} else if (category.contains("relic")) {
									break;
								} else if (!category.equals("")){
									continue;
								}
							}
							
							if (title) {
								player.getTitleList().add(slotName);
								
								if (alternateTitles.length > 0) {
									for (String altTitle : alternateTitles) {
										if (altTitle != "") {
											player.getTitleList().add(altTitle);
										}
									}
								}
							} else {
								if (numAltTitles > 0 || alternateTitles.length > 0) {
									//System.out.println(slotName + ": slot " + slotName + " cannot have any alternative titles unless it is defined as \"titleable\"");
								}
							}
							
							if (maxSlotValue > -1) {
								BitSet value = BitSet.valueOf(new long[] { collections.get(beginSlotId, (endSlotId + 1)).toLongArray()[0]++ });
								
								for (int i = beginSlotId; i <= endSlotId; i++) {
									collections.set(i, value.get((i - beginSlotId)));
								}
							} else if (bits > 1) {
								int nextBit = collections.get(beginSlotId, (endSlotId + 1)).previousClearBit(endSlotId);
								
								if (nextBit == -1) {
									return false;
								}
								
								collections.set(nextBit);
							} else {
								collections.set(beginSlotId);
							}
							
							player.setCollections(collections.toByteArray());
							
							if (!hidden && !noMessage) {
								creature.sendSystemMessage(OutOfBand.ProsePackage("@collection_n:" + collection), DisplayType.Broadcast);
							}
							
							if (!music.equals("")) {
								creature.playMusic(music);
							}
							
							if (!noScriptOnModify) {
								if (FileUtilities.doesFileExist("scripts/collections/" + slotName + ".py")) {
									PyObject method = core.scriptService.getMethod("scripts/collections/", slotName, "modify");
									
									if (method != null && method.isCallable()) {
										method.__call__(Py.java2py(core), Py.java2py(creature), Py.java2py(getCollection(creature, slotName)));
									}
								}
								
								if (FileUtilities.doesFileExist("scripts/collections/" + collectionName + ".py")) {
									PyObject method = core.scriptService.getMethod("scripts/collections/", collectionName, "modify");
									
									if (method != null && method.isCallable()) {
										method.__call__(Py.java2py(core), Py.java2py(creature), Py.java2py(getCollection(creature, collectionName)));
									}
								}
							}
							
							if (isComplete(creature, slotName)) {
								if (FileUtilities.doesFileExist("scripts/collections/" + slotName + ".py")) {
									PyObject method = core.scriptService.getMethod("scripts/collections/", slotName, "complete");
									
									if (method != null && method.isCallable()) {
										method.__call__(Py.java2py(core), Py.java2py(creature), Py.java2py(getCollection(creature, collectionName)));
									}
								}
							}
							
							if (!isComplete(creature, collectionName)) {
								if (!noReward) {
									if (FileUtilities.doesFileExist("scripts/collections/" + collectionName + ".py")) {
										PyObject method = core.scriptService.getMethod("scripts/collections/", collectionName, "complete");
										
										if (method != null && method.isCallable()) {
											method.__call__(Py.java2py(core), Py.java2py(creature), Py.java2py(getCollection(creature, collectionName)));
										}
									}
								}
								
								if (titleOnComplete) {
									player.getTitleList().add(collectionName);
								}
								
								if (trackServerFirst) {
									if (core.guildService.getGuildObject().addServerFirst(collectionName, new ServerFirst(creature.getCustomName(), creature.getObjectId(), collectionName, System.currentTimeMillis()))) {
										addCollection(creature, "bdg_server_first_01");
									}
								}
								
								if (clearOnComplete) {
									if (endSlotId > -1) {
										if (collections.get(beginSlotId, (endSlotId + 1)).cardinality() == bits) {
											clearCollection(creature, collectionName);
										}
									}
								}
								
								if (bookName.equals("badge_book")) {
									int badges = getCollection(creature, "badge_book");
									
									switch (badges) {
										case 5:
											addCollection(creature, "count_5");
										case 10:
											addCollection(creature, "count_10");
										default:
											if (((badges % 25) == 0) && !(badges > 500)) {
												addCollection(creature, "count_" + badges);
											}
									}
								}
								
								if (pageName.equals("bdg_explore")) {
									int badges = getCollection(creature, "bdg_explore");
									
									switch (badges) {
										case 10:
											addCollection(creature, "bdg_exp_10_badges");
										case 20:
											addCollection(creature, "bdg_exp_20_badges");
										case 30:
											addCollection(creature, "bdg_exp_30_badges");
										case 40:
											addCollection(creature, "bdg_exp_40_badges");
										case 45:
											addCollection(creature, "bdg_exp_45_badges");
									}
								}
							}
							
							return true;
						}
					}
				}
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * @param creature The player's CreatureObject instance.
	 * @param collection slotName or collectionName from collection.iff.
	 * 
	 * @return True if the slot or collection is complete.
	 */
	public boolean isComplete(CreatureObject creature, String collection) {
		PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
		DatatableVisitor collectionTable;
		
		if (player == null) {
			return false;
		}
		
		collection = collection.toLowerCase();
		
		try {
			collectionTable = ClientFileManager.loadFile("datatables/collection/collection.iff", DatatableVisitor.class);
			BitSet collections;
			String bookName = "";
			String pageName = "";
			String collectionName = "";
			
			collections = BitSet.valueOf(player.getCollections());
			
			for (int c = 0; c < collectionTable.getRowCount(); c++) {
				if (collectionTable.getObject(c, 0) != null) {
					if (((String) collectionTable.getObject(c, 0)) != "") {
						bookName = ((String) collectionTable.getObject(c, 0));
						pageName = "";
						collectionName = "";
					} else if (((String) collectionTable.getObject(c, 1)) != "") {
						pageName = ((String) collectionTable.getObject(c, 1));
						collectionName = "";
					} else if (((String) collectionTable.getObject(c, 2)) != "") {
						collectionName = ((String) collectionTable.getObject(c, 2));
					} else if (((String) collectionTable.getObject(c, 3)) != "") {
						String slotName = ((String) collectionTable.getObject(c, 3));
						
						if (slotName.equals(collection)) {
							int beginSlotId = ((Integer) collectionTable.getObject(c, 4));
							
							if (bookName.equals("")) {
								System.out.println(slotName + ", row " + c + ": slot " + slotName + " must be in a book");
								throw new Exception();
							}
							
							if (pageName.equals("")) {
								System.out.println(slotName + ", row " + c + ": slot " + slotName + " must be in a page");
								throw new Exception();
							}
							
							if (collectionName.equals("")) {
								System.out.println(slotName + ", row " + c + ": slot " + slotName + " must be in a collection");
								throw new Exception();
							}
							
							if (collection.equals(bookName)) {
								System.out.println("Checking if a book is complete is unsupported at this time.");
								throw new Exception();
							} else if (collection.equals(pageName)) {
								System.out.println("Checking if a page is complete is unsupported at this time.");
								throw new Exception();
							} else if (collection.equals(collectionName)) {
								return collections.get(beginSlotId);
							} else if (collection.equals(slotName)) {
								return collections.get(beginSlotId);
							}
						}
					}
				}
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Returns number of completed bits in a slot or the number of completed
	 * slots in a collection, page or book.
	 * 
	 * @param creature The player's CreatureObject instance.
	 * @param collection slotName, collectionName, pageName or bookName from collection.iff.
	 * 
	 * @return Number of completed collections within the slot, collection, page or book.
	 */
	public int getCollection(CreatureObject creature, String collection) {
		PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
		DatatableVisitor collectionTable;
		
		if (player == null) {
			return 0;
		}
		
		collection = collection.toLowerCase();
		
		try {
			collectionTable = ClientFileManager.loadFile("datatables/collection/collection.iff", DatatableVisitor.class);
			BitSet collections;
			String bookName = "";
			String pageName = "";
			String collectionName = "";
			int found = 0;
			
			collections = BitSet.valueOf(player.getCollections());
			
			for (int c = 0; c < collectionTable.getRowCount(); c++) {
				if (collectionTable.getObject(c, 0) != null) {
					if (((String) collectionTable.getObject(c, 0)) != "") {
						bookName = ((String) collectionTable.getObject(c, 0));
						pageName = "";
						collectionName = "";
					} else if (((String) collectionTable.getObject(c, 1)) != "") {
						pageName = ((String) collectionTable.getObject(c, 1));
						collectionName = "";
					} else if (((String) collectionTable.getObject(c, 2)) != "") {
						collectionName = ((String) collectionTable.getObject(c, 2));
					} else if (((String) collectionTable.getObject(c, 3)) != "") {
						String slotName = ((String) collectionTable.getObject(c, 3));
						
						int beginSlotId = ((Integer) collectionTable.getObject(c, 4));
						int endSlotId = ((Integer) collectionTable.getObject(c, 5));
						int maxSlotValue = ((Integer) collectionTable.getObject(c, 6));
						int bits = 0;
						
						if (bookName.equals("")) {
							System.out.println(slotName + ", row " + c + ": slot " + slotName + " must be in a book");
							throw new Exception();
						}
						
						if (pageName.equals("")) {
							System.out.println(slotName + ", row " + c + ": slot " + slotName + " must be in a page");
							throw new Exception();
						}
						
						if (collectionName.equals("")) {
							System.out.println(slotName + ", row " + c + ": slot " + slotName + " must be in a collection");
							throw new Exception();
						}
						
						if (collection.equals(bookName)) {
							found += getCollection(creature, pageName);
							continue;
						} else if (collection.equals(pageName)) {
							found += getCollection(creature, collectionName);
							continue;
						} else if (collection.equals(collectionName)) {
							if (collections.get(beginSlotId)) {
								found++;
							}
							
							continue;
						} else if (collection.equals(slotName)) {
							if (endSlotId != -1) {
								if (beginSlotId >= endSlotId) {
									System.out.println(slotName + ", row " + c + ": begin slot id " + beginSlotId + " must be < end slot id" + endSlotId);
									throw new Exception();
								}
								
								bits = (endSlotId - beginSlotId);
								
								if (bits > 32) {
									System.out.println(slotName + ", row " + c + ": counter-type slot uses " + bits + " bits which exceeds the limit of 32 bits for counter-type slot");
									throw new Exception();
								}
								
								if (maxSlotValue > -1) {
									if (!(maxSlotValue > 1)) {
										System.out.println(slotName + ", row " + c + ": max slot value " + maxSlotValue + " must be > 1");
										throw new Exception();
									}
									
									BitSet bitValue = new BitSet(64);
									bitValue.set(64-bits, 64);
									
									if (bitValue.toLongArray()[0] < maxSlotValue) {
										System.out.println(slotName + ", row " + c + ": counter-type slot uses " + bits + " bits, which can only hold a max value of " + bitValue.toLongArray()[0] + ", which is less than the specified max value of " + maxSlotValue);
										throw new Exception();
									}
								}
								
								if (maxSlotValue > 0) {
									return (int) collections.get(beginSlotId, (endSlotId + 1)).toLongArray()[0];
								} else {
									return collections.get(beginSlotId, (endSlotId + 1)).cardinality();
								}
							} else {
								return ((collections.get(beginSlotId)) ? 1 : 0);
							}
						}
						
						if (found > 0) {
							return found;
						}
					}
				}
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	

	/**
	 * Registers an exploration badge location.
	 * 
	 * @param planet Name from Planet.getName().
	 * @param badgeName slotName from collection.iff.
	 * @param x The first 2d coordinate.
	 * @param z The second 2d coordinate.
	 * @param range The radius from the coordinates at which this will check for players and award them the badge.
	 */
	public void registerExplorationBadge(String planet, String badgeName, int x, int z, int range) {
		if (!explorationLocations.containsKey(planet)) {
			explorationLocations.put(planet, new TreeMap<String, ExplorationRegion>());
		}
		
		explorationLocations.get(planet).put(badgeName, new ExplorationRegion(new Point3D(x, 0, z), range));
	}
	
	/**
	 * Checks if the player is in range of any exploration regions
	 * and grants any ungranted badges.
	 * 
	 * @param creature The player's CreatreObject instance.
	 */
	public void checkExplorationRegions(CreatureObject creature) {
		String planet = creature.getPlanet().getName();
		
		if (explorationLocations.containsKey(planet)) {
			for (Entry<String, ExplorationRegion> badge : explorationLocations.get(planet).entrySet()) {
				if (creature.inRange(badge.getValue().location, badge.getValue().range)) {
					if (!isComplete(creature, badge.getKey())) {
						addCollection(creature, badge.getKey());
					}
				}
			}
		}
	}
	
	/**
	 * Clears the specified slot, collection, page or book.
	 * 
	 * @param creature The player's CreatureObject instance.
	 * @param collection slotName, collectionName, pageName or bookName from collection.iff
	 * 
	 * @return True if successful.
	 */
	public boolean clearCollection(CreatureObject creature, String collection) {
		PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
		DatatableVisitor collectionTable;
		
		if (player == null) {
			return false;
		}
		
		collection = collection.toLowerCase();
		
		try {
			collectionTable = ClientFileManager.loadFile("datatables/collection/collection.iff", DatatableVisitor.class);
			BitSet collections;
			String bookName = "";
			String pageName = "";
			String collectionName = "";
			boolean found = false;
			
			collections = BitSet.valueOf(player.getCollections());
			
			for (int c = 0; c < collectionTable.getRowCount(); c++) {
				if (collectionTable.getObject(c, 0) != null) {
					if (((String) collectionTable.getObject(c, 0)) != "") {
						bookName = ((String) collectionTable.getObject(c, 0));
						pageName = "";
						collectionName = "";
					} else if (((String) collectionTable.getObject(c, 1)) != "") {
						pageName = ((String) collectionTable.getObject(c, 1));
						collectionName = "";
					} else if (((String) collectionTable.getObject(c, 2)) != "") {
						collectionName = ((String) collectionTable.getObject(c, 2));
					} else if (((String) collectionTable.getObject(c, 3)) != "") {
						String slotName = ((String) collectionTable.getObject(c, 3));
						
						int beginSlotId = ((Integer) collectionTable.getObject(c, 4));
						int endSlotId = ((Integer) collectionTable.getObject(c, 5));
						
						if (bookName.equals("")) {
							System.out.println(slotName + ", row " + c + ": slot " + slotName + " must be in a book");
							throw new Exception();
						}
						
						if (pageName.equals("")) {
							System.out.println(slotName + ", row " + c + ": slot " + slotName + " must be in a page");
							throw new Exception();
						}
						
						if (collectionName.equals("")) {
							System.out.println(slotName + ", row " + c + ": slot " + slotName + " must be in a collection");
							throw new Exception();
						}
						
						if (collection.equals(bookName) || collection.equals(pageName) ||
						collection.equals(collectionName) || collection.equals(slotName)) {
							found = true;
							
							if (collections.get(beginSlotId, endSlotId + 1).cardinality() > 0) {
								collections.clear(beginSlotId, (endSlotId + 1));
								
								if (player.getTitleList().contains(slotName)) {
									player.getTitleList().remove(slotName);
								}
								
								continue;
							}
						}
						
						if (found) {
							player.setCollections(collections.toByteArray());
							return true;
						}
					}
				}
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Clears all collections.
	 * 
	 * This must cycle through all the collections because
	 * it needs to remove titles and other such things.
	 * 
	 * @param creature The player's CreatureObject instance.
	 * 
	 * @return True if successful.
	 */
	public boolean clearCollections(CreatureObject creature) {
		PlayerObject player = (PlayerObject) creature.getSlottedObject("ghost");
		DatatableVisitor collectionTable;
		
		if (player == null) {
			return false;
		}
		
		try {
			collectionTable = ClientFileManager.loadFile("datatables/collection/collection.iff", DatatableVisitor.class);
			BitSet collections;
			String bookName = "";
			String pageName = "";
			String collectionName = "";
			boolean found = false;
			
			collections = BitSet.valueOf(player.getCollections());
			
			for (int c = 0; c < collectionTable.getRowCount(); c++) {
				if (collectionTable.getObject(c, 0) != null) {
					if (((String) collectionTable.getObject(c, 0)) != "") {
						bookName = ((String) collectionTable.getObject(c, 0));
						pageName = "";
						collectionName = "";
					} else if (((String) collectionTable.getObject(c, 1)) != "") {
						pageName = ((String) collectionTable.getObject(c, 1));
						collectionName = "";
					} else if (((String) collectionTable.getObject(c, 2)) != "") {
						collectionName = ((String) collectionTable.getObject(c, 2));
					} else if (((String) collectionTable.getObject(c, 3)) != "") {
						String slotName = ((String) collectionTable.getObject(c, 3));
						
						int beginSlotId = ((Integer) collectionTable.getObject(c, 4));
						int endSlotId = ((Integer) collectionTable.getObject(c, 5));
						
						if (bookName.equals("")) {
							System.out.println(slotName + ", row " + c + ": slot " + slotName + " must be in a book");
							throw new Exception();
						}
						
						if (pageName.equals("")) {
							System.out.println(slotName + ", row " + c + ": slot " + slotName + " must be in a page");
							throw new Exception();
						}
						
						if (collectionName.equals("")) {
							System.out.println(slotName + ", row " + c + ": slot " + slotName + " must be in a collection");
							throw new Exception();
						}
						
						if (collections.get(beginSlotId, endSlotId + 1).cardinality() > 0) {
							found = true;
							
							collections.clear(beginSlotId, (endSlotId + 1));
							
							if (player.getTitleList().contains(slotName)) {
								player.getTitleList().remove(slotName);
							}
							
							continue;
						}
						
						if (found) {
							player.setCollections(collections.toByteArray());
							return true;
						}
					}
				}
			}
		}  catch (Exception e) {
			e.printStackTrace();
		}
		
		return ((player.getCollections().length > 0) ? false : true);
	}
	
	@Override
	public void insertOpcodes(Map<Integer, INetworkRemoteEvent> arg0, Map<Integer, INetworkRemoteEvent> arg1) {
		
	}
	
	@Override
	public void shutdown() {
		
	}
	
}
