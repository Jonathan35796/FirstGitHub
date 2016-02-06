package scripts.BloodsAirCharger.Work.Utils;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.input.Mouse;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSPlayer;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;

import scripts.BloodsAirCharger.newTask;
import scripts.BloodsAirCharger.API.ACamera;
import scripts.BloodsAirCharger.API.References;

/**
 * Created by Andrew on 11/19/2015.
 */
public class Teleport extends newTask {
	public Teleport(Script script, ACamera aCamera) {
		super(script, aCamera);
	}

	static RSObject[] obelisk = Objects.findNearest(5, 2152);
	@Override
	public void execute() { 
		if(foundAltar()) {
		teleportToEdge();
		}
	}

	@Override
	public boolean shouldExecute() { 
			return !Banking.isBankScreenOpen()
					&&	foundAltar()
					&& Inventory.getCount("Unpowered orb") <= 0
					|| isPlayerAttackingMe() || 
					!Banking.isBankScreenOpen()
					&&	foundAltar() 
					&& Inventory.getCount("Cosmic rune") <= 3;
	}

	/*@Override
	public int priority() {
		return 9;
	}*/

	@Override
	public String getStatus() {
		return "Teleporting to Edgeville";
	} 
	
	
	private final RSArea spellCastArea = new RSArea(new RSTile[] { 
			new RSTile(3090, 3574, 0), 
			new RSTile(3092, 3567, 0), 
			new RSTile(3083, 3566, 0), 
			new RSTile(3084, 3574, 0) });
	
	private boolean foundAltar(){
		return spellCastArea.contains(Player.getPosition());
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

	public void teleportToEdge(){
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
							return !spellCastArea.contains(Player.getPosition());
						}
					},General.random(5000,10000));
				}
			}
		}
		
	}
		
}
