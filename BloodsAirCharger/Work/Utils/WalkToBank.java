package scripts.BloodsAirCharger.Work.Utils;

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
import org.tribot.script.Script;

import scripts.BloodsAirCharger.newTask;
import scripts.BloodsAirCharger.API.ACamera;
import scripts.BloodsAirCharger.API.References;

/**
 * Created by Andrew on 11/19/2015.
 */
public class WalkToBank extends newTask {
	
	public WalkToBank(Script script, ACamera acamera) {
		super(script, acamera);
	}

	private final RSArea teleportArea = new RSArea(new RSTile[] { 
			new RSTile(3083, 3492, 0), 
			new RSTile(3091, 3492, 0), 
			new RSTile(3091, 3502, 0), 
			new RSTile(3083, 3501, 0)
	});
	
	RSArea lumby =new RSArea(new RSTile[] { 
			new RSTile(3216, 3234, 0), 
			new RSTile(3222, 3234, 0), 
			new RSTile(3227, 3229, 0), 
			new RSTile(3227, 3221, 0), 
			new RSTile(3229, 3220, 0),
			new RSTile(3229, 3217, 0), 
			new RSTile(3227, 3216, 0), 
			new RSTile(3226, 3207, 0),
			new RSTile(3217, 3208, 0) }); 
	
	@Override
	public void execute() {
		if(!teleportArea.contains(Player.getPosition())) {
			if(WebWalking.walkToBank()) {
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						return Banking.isInBank();
					}
				}, 4000);
			}
		}
			if(teleportArea.contains(Player.getPosition())) {
				if(WebWalking.walkToBank()) {
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
	public boolean shouldExecute() {
		RSTile myTile = Player.getPosition();
		return !Banking.isInBank() 
				&& Inventory.getCount("Unpowered orb") < 3
				&& !atAltar() || 
				teleportArea.contains(Player.getPosition())
				&& !Player.getRSPlayer().isMoving() &&
				!lumby.contains(myTile);
	}
/*
	@Override
	public int priority() {
		return 2;
	}*/

	public boolean atAltar(){
		return Objects.find(15, "Obelisk of Air").length > 0;
	}

	@Override
	public String getStatus() {
		return "Walking to bank"; 
	}

}
