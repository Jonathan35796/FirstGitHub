package scripts.BloodsAirCharger.Work.Utils;

import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.Script;

import scripts.BloodsAirCharger.Manager;
import scripts.BloodsAirCharger.newTask;
import scripts.BloodsAirCharger.API.ACamera;

public class EquipStaff extends newTask{

	String methodStatus = "";
	
	public EquipStaff(Script script, ACamera aCamera) {
		super(script, aCamera);
	}

	@Override
	public void execute() {
		RSItem[] staffOfAir = Inventory.find("Staff of air");
		boolean wearingStaff = Equipment.isEquipped("Staff of air");
		if(Banking.isBankScreenOpen()
				&& !wearingStaff) {
			methodStatus = "Withdrawing Air staff";
			Banking.withdraw(1, "Staff of air");
		}
		if(staffOfAir.length > 0 && !Banking.isBankScreenOpen()) {
			methodStatus = "Equiping Air staff";
			Clicking.click(staffOfAir[0]);
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					return wearingStaff;
				}
			},3000);
		}	else {
			methodStatus = "Closing Bank";
			Banking.close();
			General.sleep(1500,2000);
		}
		
	}

	@Override
	public boolean shouldExecute() {
		return !Equipment.isEquipped("Staff of air")
				&& Banking.isInBank();
	}

	@Override
	public String getStatus() {
		return "" +methodStatus;
	}

}
