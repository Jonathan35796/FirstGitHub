package scripts.BloodsAirCharger.Work.Utils;

import org.tribot.api.Clicking;
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

public class BankEat extends newTask{

	private static String bankEatStatus = "";

	public BankEat(Script script, ACamera aCamera) {
		super(script, aCamera);
	}
	
	RSItem[] food = Inventory.find(References.get().food);

	@Override
	public void execute() {
		eatFood();
		General.sleep(10);
	}
/*public static void shouldEatFood() {
	if(Inventory.find(References.foodIDs).length <= 0) {
		if(Banking.openBank()) {
			if(Inventory.getAll().length > 22) {
				Banking.depositAll();
			}
			if(Banking.withdraw(5, References.get().food)) {
			}
				if(Inventory.find(References.get().food).length > 0
						&& Banking.isBankScreenOpen()) {
					Banking.close();
				}
			if(Inventory.find(References.get().food).length > 0) {
				RSItem[] food = Inventory.find(References.get().food);
				for(int i = 0; i > food.length; i++) {
					Clicking.click(food[i]);
					General.sleep(3200,4000);
				}
			
				}
			
			
		}
	} else {
		if(Inventory.find(References.get().food).length > 0) {
			RSItem[] food = Inventory.find(References.get().food);
			for(int i = 0; i > food.length; i++) {
				Clicking.click(food[i]);
				General.sleep(3200,4000);
			}
		}
	}
}*/
	
	public static void eatFood(){
		boolean wasOpen = false;
		if(Banking.isBankScreenOpen()) {
			Banking.close();
			wasOpen = true;
		}
		RSItem[] food = Inventory.find(References.get().foodIDs);
		if(food.length > 0){
			
			for(int i = 1; i > food.length; i++ ) {
				
				General.println("Found food");
				food[i].click("Eat");
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						General.sleep(20,50);
						return food.length <= 0;
					}
				}, General.random(1000,2000));
			}
			
	//	if(wasOpen) {
		//	Banking.openBank();
		//	wasOpen = false;
	//	}
		if(wasOpen) {
			if(Banking.openBank()) {
				Banking.depositAll();
			}
			
		}
	}
	}

	/*public static boolean eatFood(){
		bankEatStatus = "Eating Food...";
		if(!Banking.isBankScreenOpen()){
			if(Banking.openBank()) {

			}
		}
		if(Inventory.isFull())	{
			Banking.depositAll();

		}

		if(Inventory.find(References.get().food).length == 0){
			RSItem[] food = Banking.find(References.get().food);

			if(food.length > 0){

				Banking.withdrawItem(food[General.random(0,food.length - 1)],3);

				Timing.waitCondition(new Condition()
				{
					@Override
					public boolean active()
					{
						General.sleep(300,500);
						return Inventory.find(References.get().food).length
								> 0;
					}
				}, General.random(1999,2000));
			}

		}

		if(References.get().useFood) {
			if(Banking.close() && Inventory.find(References.get().foodIDs).length > 0){
				RSItem[] salmon = Inventory.find(References.get().foodIDs);
				for(int i = 0; i >= Inventory.getCount(References.get().foodIDs); i++){

					if(SKILLS.HITPOINTS.getCurrentLevel() 
							< SKILLS.HITPOINTS.getActualLevel()) {
						Clicking.click(salmon[i]);
						General.sleep(2500, 3300);
						break;
					}
				}

			} else {
				if(Banking.openBank()) {
					
				}
			}
		}




		RSItem[] salmon = Inventory.find(References.get().food);

		if(salmon.length <= 0){					//If for some reason
			if(!Banking.isBankScreenOpen()){	//we don't have food
				Banking.openBank();				//and bank screen
			}									//is closed
			return true;
		}

		return false;
	}
*/
	@Override
	public boolean shouldExecute() {
		return false;// Banking.isInBank()
				//&&	SKILLS.HITPOINTS.getCurrentLevel() 
				//< SKILLS.HITPOINTS.getActualLevel()
				//&& References.get().useFood;
	}

	/*@Override
	public int priority() {
		return 6;
	}*/

	@Override
	public String getStatus() {
		return "" +bankEatStatus;
	}

}
