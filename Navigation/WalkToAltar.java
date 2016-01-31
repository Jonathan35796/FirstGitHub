package scripts.BloodsAirCharger.Navigation;

import java.awt.Point;
import java.util.LinkedList;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.Equipment.SLOTS;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;
import org.tribot.api2007.util.DPathNavigator;
import scripts.BloodsAirCharger.Charging.ChargeOrbs;
import scripts.BloodsAirCharger.Task;
import scripts.BloodsAirCharger.Values;

/**
 * Created by Andrew on 11/19/2015.
 */
public class WalkToAltar extends Task {

	DPathNavigator nav = new DPathNavigator();
	//LinkedList<RSArea> areas = new LinkedList<>();
	//RSArea a;
	@Override
	public void execute() {
		walkEdgeTile();
		action();
		walkFirstGate();
		openFirstGate();
		if(Objects.findNearest(15, 7172)
				.length > 0) {
			walkToSecondGate1();
		}
		openSecondGate();
		walkToSpellLadder();

		climbSpellLadder();
		//findPath();
		General.sleep(10);
	}

	@Override
	public boolean validate() {
		return !ChargeOrbs.atAltar() && Inventory.getCount("Unpowered orb") > 0 && Inventory.getCount("Cosmic rune") > 98 && 
				!teleportArea.contains(Player.getPosition()) && Equipment.isEquipped(Values.chargedGlorys);
	}

	@Override
	public int priority() {
		return 5;
	}

	@Override
	public String status() {
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

	/*public void findPath() {
    			long timer = System.currentTimeMillis();
    			if(Player.isMoving()) {
    				timer = System.currentTimeMillis();
    			}
    			if(inEdgeWalk.contains(Player.getPosition())) {
    				walkEdgeTile();
    			} else {
    			if(firstGateWalkArea.contains(Player.getPosition())) {
    				walkFirstGate();
    			} else {
    			if(walkSecondGateArea.contains(Player.getPosition())) {
    				walkToSecondGate1();
    			} else {
    			if(walkToSpellArea.contains(Player.getPosition())) {
    				walkToSpellLadder();
    				} else {
    					Teleport.teleportToEdge();

    			}
    			}
    			}
    			}
    		}*/

	public void walkToSpellLadder() {
		if(inGate.contains(Player.getPosition())) {
			RSTile[] path = new RSTile[]{new RSTile(3132,9923),
					new RSTile(3132,9928), new RSTile(3133,9934),
					new RSTile(3133,9940), new RSTile(3133,9945),
					new RSTile(3129,9949), new RSTile(3123,9953),
					new RSTile(3118,9957), new RSTile(3112,9958),
					new RSTile(3107,9954), new RSTile(3102,9955),
					new RSTile(3097,9957), new RSTile(3092,9959),
					new RSTile(3088,9963), new RSTile(3088,9969)
			};

			Walking.walkPath(Walking.randomizePath(path, 2, 2));
			General.sleep(200,400);
		}
	}
	public void openFirstGate() {
		if(firstGateArea.contains(Player.getPosition())) {
			if(!PathFinding.canReach(secondGateArea.getRandomTile(), false)) {
				RSObject[] objects_check = Objects.find(15, 7169);
				if (objects_check.length > 0) {
					if(!objects_check[0].isOnScreen()) {
						Camera.turnToTile(objects_check[0]);
					}	
					if(!objects_check[0].isClickable()) {	

						Camera.turnToTile(objects_check[0]);
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
		if(firstGateArea.contains(Player.getPosition())) {
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
			Walking.walkPath(Walking.randomizePath(path, 2, 2));
			General.sleep(200,400);
		}
	}

	public void failSafe() {
		int count =0;
		if(Player.isMoving()) {
			count = 0;
			return;
		}
		while(!Player.isMoving()) {
			count++;
			General.sleep(10);
			if(count > 10000) {
				RSItem[] glory = Equipment.find(SLOTS.AMULET);
				if(glory.length>0) {
					if(Clicking.click("Edgeville", glory)) {
						General.sleep(2000,3000);
						count = 0;
					}
				}
			}
		}


	}

	public void walkFirstGate(){
		if (PathFinding.canReach(firstGateArea.getRandomTile(), false)
				&& !firstGateArea.contains(Player.getPosition())) {
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

			Walking.walkPath(path);
			General.sleep(200,400);
		}
	}
	public void walkSecondGate() {
		if(PathFinding.canReach(secondGateArea.getRandomTile(), false)
				&& !secondGateArea.contains(Player.getPosition())) {
			RSTile[] TO_AREA = {
					new RSTile(3107, 9909, 0), 
					new RSTile(3112, 9909, 0), 
					new RSTile(3118, 9909, 0), 
					new RSTile(3123, 9909, 0), 
					new RSTile(3129, 9911, 0), 
					new RSTile(3132, 9914, 0)};
			Walking.walkPath(TO_AREA);
			General.sleep(200,400);

		}
	}
	public void action(){
		if(inEdgeArea.contains(Player.getPosition())) {
			RSObject[] objects = Objects.findNearest(15, 7181);
			if (objects.length > 0) {
				if(!objects[0].isOnScreen() ){
					Camera.turnToTile(objects[0]);
				}
				if(!objects[0].isClickable()) {
					Camera.turnToTile(objects[0]);
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							General.sleep(30);
							return objects[0].isClickable();
						}
					}, 5000);
				}
				Clicking.click("Climb-down", objects[0]);
				General.sleep(400,600);

				General.sleep(600,1000);
			} else {
				RSObject[] open = Objects.findNearest(15, 7179);

				if (open.length > 0) {
					Camera.turnToTile(open[0]);
					if(!open[0].isOnScreen()){
						Camera.turnToTile(open[0]);
						General.sleep(300,700);
					}
					if(!open[0].isClickable()) {
						General.sleep(300);
						Camera.turnToTile(open[0]);
					}
					Clicking.click("Open" ,open[0]);
					General.sleep(300,400);
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
					Camera.turnToTile(objects[0]);
				}
				Clicking.click("Climb-up", objects[0]);
			}
			General.sleep(500,800);
		}
	}


	public void openSecondGate(){
		if (secondGateArea.contains(Player.getPosition())){
			RSObject[] objects = Objects.getAt(new RSTile(3132, 9917, 0));

			if (objects.length > 0 && objects[0].isOnScreen() ){
				Clicking.click("Open", objects[0]);
			}
			if(!objects[0].isOnScreen()) {
				Camera.turnToTile(objects[0]);
			}
			General.sleep(500,800);
		}
	}


	public void walkEdgeTile(){
		if(!inEdgeArea.contains(Player.getPosition()) &&
				PathFinding.canReach(inEdgeArea.getRandomTile(), false)) {

			RSTile[] path = new RSTile[]{new RSTile(3093, 3486, 0),
					new RSTile(3093, 3482, 0),new RSTile(3093, 3476, 0),
					new RSTile(3094, 3472, 0),new RSTile(3094, 3472, 0)};

			Walking.walkPath(Walking.randomizePath(path, 1, 1));	

		}
	}


	public static RSArea createRSArea(RSTile center, int radius){
		int x = center.getX();
		int y = center.getY();
		return new RSArea(new RSTile(x-radius,y+radius),new RSTile(x+radius,y-radius));
	}

	public static void handleEdgeTrapdoor(){
		if(!Player.isMoving()) {
			RSObject[] trapdoor = Objects.findNearest(10,7179);
			if(trapdoor.length > 0){
				if(!trapdoor[0].isOnScreen()){
					Camera.turnToTile(trapdoor[0]);
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
						Camera.turnToTile(trapdoor[0]);
					}
				}
			}
		}
	}
	public void walkToSecondGate(){
		RSObject[] objects_check = Objects.find(15, 7168);
		if (objects_check.length > 0 && objects_check[0].isOnScreen() ){
			RSObject[] objects = Objects.getAt(new RSTile(3103, 9910, 0));
			if ( objects.length > 0 && objects[0].isOnScreen() ){
				Clicking.click("Open", objects[0]);
			}
			General.sleep(300,500);
		} else {
			RSTile[] path = new RSTile[]{new RSTile(3106, 9909, 0), new RSTile(3110, 9909, 0), new RSTile(3114, 9909, 0), new RSTile(3118, 9909, 0), new RSTile(3121, 9909, 0), new RSTile(3124, 9909, 0), new RSTile(3128, 9909, 0), new RSTile(3131, 9911, 0), new RSTile(3132, 9915, 0)};
			RSTile[] newPath = Walking.randomizePath(path, 2, 2);
			Walking.walkPath(newPath);
			//Define sleep 2
		}
	}
}
