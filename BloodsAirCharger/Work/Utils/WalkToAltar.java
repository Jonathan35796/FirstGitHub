package scripts.BloodsAirCharger.Work.Utils;

import java.awt.Point;
import java.util.LinkedList;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.Equipment.SLOTS;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;
import org.tribot.script.Script;

import scripts.BloodsAirCharger.newTask;
import scripts.BloodsAirCharger.API.ACamera;
import scripts.BloodsAirCharger.API.References;

/**
 * Created by Andrew on 11/19/2015.
 */
public class WalkToAltar extends newTask {

	DPathNavigator nav = new DPathNavigator();
	
	
	public WalkToAltar(Script script, ACamera aCamera) {
		super(script, aCamera);
	}
//	long  t = System.currentTimeMillis();
	@Override
	public void execute() {
		
		walkEdgeTile();
		
		if(inEdgeArea.contains(Player.getPosition())) {
		trapDoor();
		}
		
		if(downTrapDoorArea.contains(Player.getPosition())
				||	firstGateWalkArea.contains(Player.getPosition())) {
		walkFirstGate();
		}
		
		if(firstGateArea.contains(Player.getPosition())) {
		openFirstGate();
		
	}
		
		if(Objects.findNearest(7, 7172).length > 0) {
			walkToSecondGate1();
		}
		
		openSecondGate();
		
		
		if(walkToSpellArea.contains(Player.getPosition())
				|| inGate.contains(Player.getPosition())) {
		walkToSpellLadder();
		}
		
		
		if(spellLadder.contains(Player.getPosition())) {
		climbSpellLadder();
	}
		General.sleep(10);
	}

	private final RSArea spellCastArea = new RSArea(new RSTile[] { 
			new RSTile(3090, 3574, 0), 
			new RSTile(3092, 3567, 0), 
			new RSTile(3083, 3566, 0), 
			new RSTile(3084, 3574, 0) });
	
	@Override
	public boolean shouldExecute() {
		RSTile myPos = Player.getPosition();
		int cosmic = Inventory.getCount("Cosmic rune");
		RSItem[] unpowered = Inventory.find("Unpowered orb");
		RSItem[] food = Inventory.find(References.get().food);
		return  !spellCastArea.contains(myPos) 
				&& cosmic > 60
				&& unpowered.length > 0
				//&& food.length
				//> 0
				&& Equipment.isEquipped(References.get().chargedGlorys);
	}

/*	@Override
	public int priority() {
		return 2;
	}*/

	@Override
	public String getStatus() {
		return "Walking to obelisk";
	}
	/*public boolean inARSArea() {
		for(RSArea area: areas) {
			areas.add(area);
			if(!areas.contains(Player.getPosition())) {
				return false;
			}
		}
		return true;
	}*/
	private final RSArea teleportArea = new RSArea(new RSTile[] { 
			new RSTile(3083, 3492, 0), 
			new RSTile(3091, 3492, 0), 
			new RSTile(3091, 3502, 0), 
			new RSTile(3083, 3501, 0)
	});

	private final RSArea inBank = new RSArea(new RSTile[] { 
			new RSTile(3091, 3488, 0), 
			new RSTile(3094, 3488, 0), 
			new RSTile(3095, 3493, 0), 
			new RSTile(3099, 3494, 0), 
			new RSTile(3097, 3497, 0), 
			new RSTile(3092, 3497, 0)
	});
	private final RSArea inEdgeArea = new RSArea(new RSTile[] { 
			new RSTile(3098, 3468, 0), 
			new RSTile(3098, 3474, 0), 
			new RSTile(3088, 3474, 0), 
			new RSTile(3088, 3467, 0)
	});
	private final RSArea downTrapDoorArea = new RSArea(new RSTile[] { 
			new RSTile(3099, 9866, 0), 
			new RSTile(3096, 9866, 0), 
			new RSTile(3095, 9870, 0), 
			new RSTile(3099, 9870, 0)
	});
	private final RSArea firstGateArea = new RSArea(new RSTile[] { 
			new RSTile(3104, 9916, 0), 
			new RSTile(3104, 9903, 0), 
			new RSTile(3093, 9904, 0), 
			new RSTile(3093, 9915, 0)
	});
	private final RSArea secondGateArea = new RSArea(new RSTile[] { 
			new RSTile(3138, 9917, 0), 
			new RSTile(3136, 9906, 0), 
			new RSTile(3124, 9906, 0), 
			new RSTile(3124, 9912, 0), 
			new RSTile(3128, 9918, 0)
	});
	private final RSArea inGate = new RSArea(new RSTile[] { 
			new RSTile(3134, 9918, 0), 
			new RSTile(3129, 9918, 0), 
			new RSTile(3130, 9922, 0), 
			new RSTile(3134, 9922, 0)
	});
	private final RSArea spellLadder = new RSArea(new RSTile[] { 
			new RSTile(3093, 9961, 0), 
			new RSTile(3084, 9961, 0), 
			new RSTile(3086, 9975, 0), 
			new RSTile(3092, 9975, 0)
	});
	private final RSArea inEdgeWalk= new RSArea(new RSTile[] { 
			new RSTile(3090, 3488, 0), 
			new RSTile(3098, 3488, 0), 
			new RSTile(3098, 3474, 0), 
			new RSTile(3090, 3474, 0), 
			new RSTile(3089, 3487, 0), 
			new RSTile(3098, 3487, 0), 
			new RSTile(3097, 3497, 0), 
			new RSTile(3089, 3497, 0)
	});

	private final RSArea firstGateWalkArea = new RSArea(new RSTile[] { 
			new RSTile(3097, 9865, 0), 
			new RSTile(3105, 9873, 0), 
			new RSTile(3104, 9887, 0), 
			new RSTile(3096, 9904, 0), 
			new RSTile(3093, 9898, 0), 
			new RSTile(3093, 9887, 0), 
			new RSTile(3092, 9879, 0), 
			new RSTile(3095, 9867, 0) });

	private final RSArea walkSecondGateArea = new RSArea(new RSTile[] { 
			new RSTile(3103, 9910, 0), 
			new RSTile(3104, 9906, 0), 
			new RSTile(3127, 9906, 0), 
			new RSTile(3127, 9902, 0), 
			new RSTile(3137, 9902, 0), 
			new RSTile(3137, 9914, 0), 
			new RSTile(3132, 9918, 0), 
			new RSTile(3128, 9918, 0), 
			new RSTile(3127, 9912, 0), 
			new RSTile(3103, 9912, 0) });

	private final RSArea walkToSpellArea = 	new RSArea(new RSTile[] { 
			new RSTile(3132, 9924, 0), 
			new RSTile(3135, 9925, 0), 
			new RSTile(3135, 9948, 0), 
			new RSTile(3128, 9959, 0), 
			new RSTile(3109, 9960, 0), 
			new RSTile(3108, 9957, 0), 
			new RSTile(3102, 9958, 0), 
			new RSTile(3098, 9963, 0), 
			new RSTile(3091, 9964, 0), 
			new RSTile(3088, 9965, 0), 
			new RSTile(3081, 9961, 0), 
			new RSTile(3078, 9957, 0), 
			new RSTile(3078, 9950, 0), 
			new RSTile(3088, 9947, 0), 
			new RSTile(3096, 9948, 0), 
			new RSTile(3102, 9953, 0), 
			new RSTile(3105, 9951, 0), 
			new RSTile(3111, 9951, 0),
			new RSTile(3112, 9955, 0), 
			new RSTile(3115, 9954, 0), 
			new RSTile(3116, 9948, 0), 
			new RSTile(3129, 9947, 0), 
			new RSTile(3131, 9945, 0) });


	
	
	public void walkToSpellLadder() {
		if(inGate.contains(Player.getPosition())
			|| walkToSpellArea.contains(Player.getPosition())) {
			RSTile[] path = new RSTile[]{new RSTile(3132,9923),
					new RSTile(3132,9928), new RSTile(3133,9934),
					new RSTile(3133,9940), new RSTile(3133,9945),
					new RSTile(3129,9949), new RSTile(3123,9953),
					new RSTile(3118,9957), new RSTile(3112,9958),
					new RSTile(3107,9954), new RSTile(3102,9955),
					new RSTile(3097,9957), new RSTile(3092,9959),
					new RSTile(3088,9963), new RSTile(3088,9969)
			};

			Walking.walkPath(Walking.randomizePath(path, 1, 1));
			
		
	
		}
	}
	public void openFirstGate() {
		if(firstGateArea.contains(Player.getPosition())) {
			if(!PathFinding.canReach(secondGateArea.getRandomTile(), false)) {
				RSObject[] objects_check = Objects.find(15, 7169);
				if (objects_check.length > 0) {
					if(!objects_check[0].isOnScreen()) {
						aCamera.turnToTile(objects_check[0]);
					}	
					if(!objects_check[0].isClickable()) {	

						aCamera.turnToTile(objects_check[0]);
						Timing.waitCondition(new Condition() { 
							@Override
							public boolean active() {
								General.sleep(30);
								return objects_check[0].isClickable();
							}
						}, 5000);
					}
					if(objects_check.length > 0 ) {
						if(Clicking.click("Open", objects_check[0])) {
							Timing.waitCondition(new Condition() {
								@Override
								public boolean active() {
									return Objects.findNearest(15, "Close")
											.length > 0;
								}
							}, 700);
						}
					} else {
						walkToSecondGate1();
					}



				}
			}

		}
	}
	public void walkToSecondGate1() {
		if(firstGateArea.contains(Player.getPosition()) ||
				walkSecondGateArea.contains(Player.getPosition())) {
			RSTile[] path = new RSTile[]{
					new RSTile(3106, 9909, 0),
					new RSTile(3110, 9909, 0),
					new RSTile(3114, 9909, 0),
					new RSTile(3118, 9909, 0), 
					new RSTile(3121, 9909, 0), 
					new RSTile(3124, 9909, 0), 
					new RSTile(3128, 9909, 0), 
					new RSTile(3131, 9911, 0), 
					new RSTile(3132, 9915, 0)};
			
			RSTile[] randomizedPath = Walking.randomizePath(path, 1, 1);
			Walking.walkPath(randomizedPath, new Condition() {
				@Override
				public boolean active() {
					return secondGateArea.contains(Player.getPosition());
				}
			}, 3000);
			General.sleep(200,400);
		}
	}


	public void walkFirstGate(){
		RSTile[] path = new RSTile[]{new RSTile(3097, 9871, 0),
				new RSTile(3097, 9877, 0),
				new RSTile(3097, 9883, 0),
				new RSTile(3097, 9887, 0),
				new RSTile(3096, 9892, 0),
				new RSTile(3096, 9897, 0),
				new RSTile(3096, 9902, 0),
				new RSTile(3096, 9906, 0),
				new RSTile(3099, 9909, 0),
				new RSTile(3101, 9909, 0)};
		if (downTrapDoorArea.contains(Player.getPosition())
			&& !walkSecondGateArea.contains(Player.getPosition())
			||	firstGateWalkArea.contains(Player.getPosition())) {
	
			RSTile[] randomizedPath = Walking.randomizePath(path, 1, 1);
			Walking.walkPath(randomizedPath, new Condition() {
				@Override
				public boolean active() {
					return firstGateArea.contains(Player.getPosition());
				}
			}, 3000
					);
			General.sleep(200,400);
		}
	
	}
	
	public void trapDoor(){
		if(inEdgeArea.contains(Player.getPosition())) {
			RSObject[] objects = Objects.findNearest(15, 7181);
			if (objects.length > 0) {
				if(!objects[0].isOnScreen() ){
					aCamera.turnToTile(objects[0]);
				}
				if(!objects[0].isClickable()) {
					aCamera.turnToTile(objects[0]);
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							General.sleep(30);
							return objects[0].isClickable();
						}
					}, 5000);
				}
				if(Clicking.click("Climb-down", objects[0])) {
				General.sleep(400,600);
				}
				General.sleep(600,1000);
			} else {
				RSObject[] open = Objects.findNearest(15, 7179);

				if (open.length > 0) {
					aCamera.turnToTile(open[0]);
					if(!open[0].isOnScreen()){
						aCamera.turnToTile(open[0]);
						General.sleep(300,700);
					}
					if(!open[0].isClickable()) {
						General.sleep(300);
						aCamera.turnToTile(open[0]);
					}
					if(Clicking.click("Open" ,open[0])) {
					General.sleep(300,400);
					}
				}
				General.sleep(500,800);
			}
		}
	}

	public void climbSpellLadder() {

		if (PathFinding.canReach(spellLadder.getRandomTile(), false)) {
			RSObject[] objects = Objects.getAt(new RSTile (3088,9971,0));
			if(objects.length > 0) {
				if(!objects[0].isOnScreen()) {
					aCamera.turnToTile(objects[0]);
				}
				if(Clicking.click("Climb-up", objects[0])){
					
				}
			}
			General.sleep(2000,3000);
		}
	}

	public void openSecondGate(){
		if (secondGateArea.contains(Player.getPosition())){
			RSObject[] objects = Objects.getAt(new RSTile(3132, 9917, 0));

			if (objects.length > 0 && objects[0].isOnScreen() ){
				if(Clicking.click("Open", objects[0])) {
					
				}
			}
			if(!objects[0].isOnScreen()) {
				aCamera.turnToTile(objects[0]);
			}
			General.sleep(2000,3000);
		}
	}

	public void walkEdgeTile(){
		if(Banking.isInBank()
	&& PathFinding.canReach(inEdgeArea.getRandomTile(), false)) {

			RSTile[] path = new RSTile[]{new RSTile(3093, 3486, 0),
					new RSTile(3093, 3482, 0),new RSTile(3093, 3476, 0),
					new RSTile(3094, 3472, 0),new RSTile(3094, 3472, 0)};

			RSTile[] randomizedPath = Walking.randomizePath(path, 1, 1);
			Walking.walkPath(randomizedPath, new Condition() {
				@Override
				public boolean active() {
					return inEdgeArea.contains(Player.getPosition());
				}
			}, 3000);
			General.sleep(200,400);

		}
	}


	public static RSArea createRSArea(RSTile center, int radius){
		int x = center.getX();
		int y = center.getY();
		return new RSArea(new RSTile(x-radius,y+radius),new RSTile(x+radius,y-radius));
	}

	public void handleEdgeTrapdoor(){
		if(!Player.isMoving()) {
			RSObject[] trapdoor = Objects.findNearest(10,7179);
			if(trapdoor.length > 0){
				if(!trapdoor[0].isOnScreen()){
					aCamera.turnToTile(trapdoor[0]);
				}
				if(Clicking.click("Open", trapdoor[0])){
					Timing.waitCondition(new Condition()
					{
						@Override
						public boolean active()
						{
							General.sleep(200,500);
							return Objects.findNearest(10,7179).length == 0;
						}
					}, General.random(700,1000));
				}else{
					if(trapdoor.length > 0) {
						aCamera.turnToTile(trapdoor[0]);
					}
				}
			}
		}
	}
}
