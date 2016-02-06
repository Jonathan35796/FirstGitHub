package scripts.BloodsAirCharger.Work.Utils;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.Script;

import scripts.BloodsAirCharger.newTask;
import scripts.BloodsAirCharger.API.ACamera;
import scripts.BloodsAirCharger.API.References;

/** 
 * Created by Andrew on 11/19/2015.
 */
public class OpenBank extends newTask {
	public OpenBank(Script script, ACamera aCamera) {
	super(script, aCamera);
	}

	@Override
	public void execute() {
		if(!Player.isMoving() && Banking.openBank()){
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					General.sleep(200,500);
					return Banking.isBankScreenOpen();
				}
			},General.random(2000,5000));
		}
	}

	@Override
	public boolean shouldExecute() {
		RSItem[] cosmicRune = Inventory.find("Cosmic rune");
		RSItem[] unpoweredOrb = Inventory.find("Unpowered orb");
		RSItem[] food = Inventory.find("Cosmic rune");
		return 	Banking.isInBank()
				&& !Banking.isBankScreenOpen()
				&& Inventory.find("Unpowered orb").length <= 0
				|| Banking.isInBank()
				&& !Banking.isBankScreenOpen()
				&& Inventory.find("Air orb").length > 0
				|| cosmicRune.length < 3
				&& unpoweredOrb.length < 4
				&& food.length < 4;
	}

/*	@Override
	public int priority() {
		return 3;
	}*/

	@Override
	public String getStatus() {
		return "Opening bank...";
	}
}
