package scripts.BloodsAirCharger.Navigation;

import org.tribot.api.General;
import org.tribot.api2007.Player;

import scripts.BloodsAirCharger.*;
public class FailSafe extends Task{

	@Override
	public void execute() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean validate() {
		int count = 0;
		while(!Player.isMoving()) {
			count++;
			General.sleep(10);

		}
		return false;
	}

	@Override
	public int priority() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String status() {
		// TODO Auto-generated method stub
		return "A Problem! Fail Safing...";
	}

}
