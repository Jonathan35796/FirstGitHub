package scripts.BloodsAirCharger.Work.Utils;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Equipment.SLOTS;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.types.RSArea;
import org.tribot.api2007.types.RSItem;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.interfaces.InterfaceClicking;

import scripts.BloodsAirCharger.newTask;
import scripts.BloodsAirCharger.API.ACamera;
import scripts.BloodsAirCharger.API.References;

public class DeadWalk extends newTask {

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
	
	
	public DeadWalk(Script script, ACamera aCamera) {
		super(script, aCamera);
	}

	@Override
	public void execute() {
		if(Inventory.find(References.get().chargedGlorys).length > 0) {
			Clicking.click("Amulet of glory ");
		}
		if(Equipment.isEquipped("Amulet of glory(")) {
			if(GameTab.getOpen() != TABS.EQUIPMENT) {
				GameTab.open(TABS.EQUIPMENT);
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						return GameTab.getOpen() == TABS.EQUIPMENT;
						
					}
				}, 3000);
			
		}
			RSItem[] gloryClick = Equipment.find(SLOTS.AMULET);
			Clicking.click("Edgeville", gloryClick);
			General.sleep(3000);
		}
		
	}
	//	if(Interfaces.get(219) != null) {
			//if(Interfaces.getChild(0) != null) {
				//if(Interfaces.get(index, child))
				
		//	}
		//
	//}

	@Override
	public boolean shouldExecute() {
		RSTile myPos = Player.getPosition();
		return lumby.contains(myPos);
	}

	@Override
	public String getStatus() {
		return "We died! Lets teleport back!";
	}
	
}
