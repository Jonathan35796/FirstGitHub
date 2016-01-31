package scripts.BloodsAirCharger.Navigation;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSPlayer;
import scripts.BloodsAirCharger.Task;
import scripts.BloodsAirCharger.Values;

/**
 * Created by Andrew on 11/19/2015.
 */
public class Teleport extends Task {

	@Override
	public void execute() { 
		teleportToEdge();
	}

	@Override
	public boolean validate() { 
		return !atAltar() && Inventory.getCount("Unpowered orb") == 0 || !atAltar() && Inventory.getCount("Cosmic rune") < 3;
	}

	@Override
	public int priority() {
		return 9;
	}

	@Override
	public String status() {
		return "Teleporting to Edgeville";
	} 

	public boolean atAltar(){
		return Objects.find(7, 2152).length > 0;
	} 

	private boolean isPlayerAttackingMe() {
		RSPlayer[] players = Players.getAll();
		for (RSPlayer p: players) {
			if (p.isInteractingWithMe() && Player.getRSPlayer().isInCombat() && p.getAnimation() != 1) {
				General.println(p.getName() + " is attacking me!");
				return true;
			}
		}
		return false;
	}

	public static boolean teleportToEdge(){
		if(Magic.isSpellSelected()) {
			Mouse.click(1);
		}
		if(Player.getAnimation() != 714){
			RSItem glory = Equipment.getItem(Equipment.SLOTS.AMULET);
			if(glory != null){
				if(Clicking.click("Edgeville", glory)){
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							General.sleep(500,2000);
							return Player.getAnimation() == -1;
						}
					},General.random(5000,10000));
					return true;
				}
			}
		}
		return false;
	}
}
