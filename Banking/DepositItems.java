package scripts.BloodsAirCharger.Banking;

import org.tribot.api.Clicking;
import org.tribot.api.General;

import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import scripts.BloodsAirCharger.Task;
import scripts.BloodsAirCharger.Values;

/**
 * Created by Andrew on 11/19/2015.
 */ 
public class DepositItems extends Task {
	@Override
	public void execute() {
		if(Inventory.getCount("Cosmic rune") > 0) {
			Banking.depositAll();
		}
		depositVial();
		depositOrbs();
		if(!Values.useFood) {
			depositFood();
		}
		
	}

	@Override
	public boolean validate() {
		return Banking.isInBank() && Banking.isBankScreenOpen() && Inventory.getCount("Air orb","Vial") > 0 ||
				Banking.isInBank() && Banking.isBankScreenOpen() && Inventory.getCount("Cosmic rune") > 99;
	}

	@Override
	public int priority() {
		return 3;
	}

	@Override
	public String status() {
		return "Depositing items";
	}

	public static void depositOrbs(){
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
