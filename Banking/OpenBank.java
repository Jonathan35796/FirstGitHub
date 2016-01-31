package scripts.BloodsAirCharger.Banking;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Player;

import scripts.BloodsAirCharger.Task;
import scripts.BloodsAirCharger.Navigation.WalkToAltar;

/** 
 * Created by Andrew on 11/19/2015.
 */
public class OpenBank extends Task {
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
	public boolean validate() {
		return Banking.isInBank() && !Banking.isBankScreenOpen();
	}

	@Override
	public int priority() {
		return 1;
	}

	@Override
	public String status() {
		return "Opening bank";
	}
}
