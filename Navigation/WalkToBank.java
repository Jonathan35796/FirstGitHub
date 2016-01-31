package scripts.BloodsAirCharger.Navigation;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSTile;

import scripts.BloodsAirCharger.Task;

/**
 * Created by Andrew on 11/19/2015.
 */
public class WalkToBank extends Task {
	long t = System.currentTimeMillis();

	private final RSArea teleportArea = new RSArea(new RSTile[] { 
			new RSTile(3083, 3492, 0), 
			new RSTile(3091, 3492, 0), 
			new RSTile(3091, 3502, 0), 
			new RSTile(3083, 3501, 0)
	});
	
	@Override
	public void execute() {
		if(!teleportArea.contains(Player.getPosition())) {
			if(WebWalking.walkToBank()) {
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						return Banking.isInBank();
					}
				}, 50000);
			}
		}
			if(teleportArea.contains(Player.getPosition())) {
				if(WebWalking.walkToBank()) {
					t = System.currentTimeMillis();
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							General.sleep(30);
							return Banking.isInBank();
						}
					}, 1000);
				}
			}
			
		}
	
	@Override
	public boolean validate() {
		return !Banking.isInBank() && Inventory.getCount("Unpowered orb") == 0 && (!atAltar());
	}

	@Override
	public int priority() {
		return 1;
	}

	public boolean atAltar(){
		return Objects.find(15, "Obelisk of Air").length > 0;
	}

	@Override
	public String status() {
		return "Walking to bank"; 
	}
}
