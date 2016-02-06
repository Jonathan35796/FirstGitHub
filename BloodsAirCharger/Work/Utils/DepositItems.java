package scripts.BloodsAirCharger.Work.Utils;

import org.tribot.api.Clicking;
import org.tribot.api.General;

import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;

import scripts.BloodsAirCharger.newTask;
import scripts.BloodsAirCharger.API.ACamera;
import scripts.BloodsAirCharger.API.References;

/**
 * Created by Andrew on 11/19/2015.
 */ 
public class DepositItems extends newTask {
	
	public DepositItems(Script script, ACamera aCamera) {
		super(script, aCamera);
	}

	private static String depositStatus = "";
	
	@Override
	public void execute() {
		RSTile myPos = Player.getPosition();
		int cosmic = Inventory.getCount("Cosmic rune");
		RSItem[] airorb = Inventory.find("Air orb");
			depositStatus = "Depositing junk...";
			if(airorb.length > 0) {
			Banking.depositAll();
			General.sleep(300);
			}
		
		if(!References.get().useFood) {
			depositFood();
		}
		
	}

	@Override
	public boolean shouldExecute() {
		RSItem[] airorb = Inventory.find("Air orb");
		RSItem[] unpoweredOrb = Inventory.find("Unpowered Orb");
		RSItem[] cosmicRune = Inventory.find("Cosmic rune");
		RSItem[] food = Inventory.find(References.get().foodIDs);
		
		return 	Banking.isInBank() 
				&& Banking.isBankScreenOpen() 
				&& airorb.length > 0 ||
				Banking.isInBank() 
				&& Banking.isBankScreenOpen()
				&& Inventory.getAll().length > 0
				&& unpoweredOrb.length <= 0
				&& cosmicRune.length <= 0 
				|| Banking.isBankScreenOpen()
				&& Inventory.getAll().length > 0
				&& unpoweredOrb.length <= 0
				&& cosmicRune.length <= 0 
				&& food.length <= 0;
	}

	@Override
	public String getStatus() {
		return "" + depositStatus;
	}

	public static void depositOrbs(){
		depositStatus = "Depositing Orbs...";
		RSItem orbs[] = Inventory.find("Air orb");
		if(orbs.length > 0){
			if(Banking.depositItem(orbs[0],0)){
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(200,500);
						return Inventory.getCount("Air orb") == 0;
					}
				},5000);
			}
		}
	}
	public static void depositFood() {
		depositStatus = "Depositing Food...";
		RSItem[] food = Inventory.find(1965, 1967, 2142, 2327, 315, 2140, 3228, 319, 325, 1971, 347, 355, 
				333, 7223, 339, 351, 329, 361, 379, 365, 373, 7946, 385, 3971, 391, 2309, 1891, 1893, 1895, 1897, 1899, 6701, 7060);
		if(food.length > 0) {
			Banking.depositItem(food[0], 0);
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(50,200);
					return Inventory.getCount(food.length) == 0;
				}
			},5000);
		}
	}

	public static void depositVial(){
		depositStatus = "Depositing Vials...";
		RSItem vials[] = Inventory.find("Vial");
		if(vials.length > 0){
			if(Banking.deposit(1,"Vial")){
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(50,200);
						return Inventory.getCount("Vial") == 0;
					}
				},5000);
			}
		}
	}
}
