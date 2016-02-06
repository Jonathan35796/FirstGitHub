package scripts.BloodsAirCharger.Work.Utils;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Banking;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Skills;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.Script;

import scripts.BloodsAirCharger.newTask;
import scripts.BloodsAirCharger.API.ACamera;
import scripts.BloodsAirCharger.API.References;

/**
 * Created by Andrew on 11/21/2015.
 */
public class EatWalk extends newTask {
	
	public EatWalk(Script script, ACamera aCamera) {
		super(script, aCamera);
	}

	@Override
	public void execute() { 
		eatFood();
	}

	@Override
	public boolean shouldExecute() {
		RSItem[] food = Inventory.find(References.get().food);
		return food.length > 0 && 
				Skills.getCurrentLevel(Skills.SKILLS.HITPOINTS)
				< (Skills.getActualLevel(Skills.SKILLS.HITPOINTS)/1.5);
	}

/*	@Override
	public int priority() {
		return 1;
	}*/

	@Override
	public String getStatus() {
		return "Eating food";
	}

	public static void eatFood(){
		boolean wasOpen = false;
		if(Banking.isBankScreenOpen()) {
			Banking.close();
			wasOpen = true;
		}
		RSItem[] food = Inventory.find(References.get().food);
		if(food.length > 0){
			General.println("Found food");
			Clicking.click(food[0]);
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(20,50);
					return food[0] == null;
				}
			}, General.random(1000,2000));
		}else{
			General.println("Couldn't find food");
		}
		if(wasOpen) {
			Banking.openBank();
			wasOpen = false;
		}
	}
}
