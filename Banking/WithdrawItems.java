package scripts.BloodsAirCharger.Banking;

import java.util.HashMap;

import org.tribot.api.Clicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.Interfaces;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Player;
import org.tribot.api2007.Equipment.SLOTS;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSCharacter;
import org.tribot.api2007.types.RSInterface;
import org.tribot.api2007.types.RSItem;
import scripts.BloodsAirCharger.Task;
import scripts.BloodsAirCharger.Values;

/**
 * Created by Andrew on 11/19/2015.
 */
public class WithdrawItems extends Task{
	@Override
	public void execute() {


		if(Inventory.getCount("Cosmic rune") <= 75) {
			withdrawCosmics();

		}
		if(!Equipment.isEquipped(Values.chargedGlorys)){
			newGlory();
		}
		if(Values.useFood) {
			if(SKILLS.HITPOINTS.getCurrentLevel() < 
					SKILLS.HITPOINTS.getActualLevel()) {
				withdrawFood1();
			}
		}
		if(Values.useFood) {
			withdrawFood();
		}
		withdrawOrbs();
		if(Inventory.getCount("Cosmic rune") > 99) {
			Banking.withdraw(99 - Inventory.getCount("Cosmic rune"), "Cosmic rune");
		}
	}

	@Override
	public boolean validate() {
		return Banking.isBankScreenOpen() && Inventory.getCount("Cosmic rune") > 3  || Banking.isBankScreenOpen() && Equipment.isEquipped(1704);
	}

	@Override
	public int priority() {
		return 3;
	}

	@Override
	public String status() {
		return "Withdrawing items";
	}

	public static boolean withdrawFood() {
		if(Inventory.getCount(Values.food) < Values.foodToTake) {
			Banking.withdraw(Values.foodToTake - Inventory.getCount(Values.food), Values.food);
			return false;
		}
		if(Inventory.getCount(Values.food) == Values.foodToTake) {
			return false;
		}
		Banking.withdraw(Values.foodToTake, Values.food);
		return true;
	}
	public static boolean withdrawFood1(){
		if(!Banking.isBankScreenOpen()){
			if(Banking.openBank());
		}
		if(Inventory.isFull()){
			Banking.depositAll();
		}
		if(Inventory.find(Values.food).length == 0){
			RSItem[] food = Banking.find(Values.food);
			if(food != null && food.length > 0){
				Banking.withdrawItem(food[General.random(0,food.length - 1)],3);
				Timing.waitCondition(new Condition()
				{
					@Override
					public boolean active()
					{
						General.sleep(300,500);
						return Inventory.find(Values.food).length > 0;
					}
				}, General.random(1000, 2000));
			}
		
		}


		if(Banking.close()){
			if(Values.useFood) {
			RSItem[] salmon = Inventory.find(Values.food);
			int i = 0;
			while( salmon.length > i && SKILLS.HITPOINTS.getCurrentLevel() < SKILLS.HITPOINTS.getActualLevel()){
				Clicking.click(salmon[i]);
				i++;
				General.sleep(2000,2500);
			}
			}
		}



		RSItem[] salmon = Inventory.find(Values.food);
		if(salmon == null){
			if(!Banking.isBankScreenOpen()){
				if(Banking.openBank());
			}
			return true;
		}
		return false;
	}
	public static void withdrawStamina(){
		if(Inventory.find(Values.staminaIds).length == 0){
			RSItem staminas[] = Banking.find(Values.staminaIds);
			if(staminas.length > 0){
				if(Banking.withdrawItem(staminas[0],1)){
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							General.sleep(20,250);
							return Inventory.find(Values.staminaIds).length > 0;
						}
					},5000);
				}
			}

		}
	}

	public static void withdrawCosmics(){
		if(supplyCheck(SUPPLY_VALUES2)) {
			if(Inventory.getCount("Cosmic rune") < 100){
				if (Banking.withdraw(100, "Cosmic rune")) {
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							return Inventory.getCount("Cosmic rune") >= 100;
						}
					}, 5000);
				}else{
					if(Banking.withdraw(100, "Cosmic rune")) {
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								return Inventory.getCount("Cosmic rune") >= 100;
							}
						}, 5000);
					} else {
						General.println("Out of cosmics!");
						Values.runScript = false;
					}
				}
			}
		}
	}
	private static boolean supplyCheck(final HashMap<String, Integer> set){

		return Timing.waitCondition(new Condition() {
			@Override 
			public boolean active() {
				General.sleep(100);
				for (String item : set.keySet()){
					RSItem[] bankItem = Banking.find(item);
					if (bankItem.length <= 0 || bankItem[0].getStack() < set.get(item)){
						return false;
					}
				}
				return true;
			}
		}, General.random(1500, 2000));
	}
	private static final HashMap<String, Integer> SUPPLY_VALUES = new HashMap<String, Integer>(){
		{
			put("Unpowered orb", 0);
		}
	};

	private static final HashMap<String, Integer> CHARGED_GLORY = new HashMap<String, Integer>(){
		{
			put("Amulet of glory (4)", 1);
		}
	};

	private static final HashMap<String, Integer> SUPPLY_VALUES2 = new HashMap<String, Integer>(){
		{
			put("Cosmic rune", 100);
		}
	};
	private static final HashMap<String, Integer> SUPPLY_VALUES3 = new HashMap<String, Integer>(){
		{
			put(Values.food, Values.foodToTake);
		}
	};
	public static void getFood() {
		if(supplyCheck(SUPPLY_VALUES3)) {

		}
	}
	public static void withdrawOrbs(){
		if(supplyCheck(SUPPLY_VALUES)) {
			if(Banking.isBankScreenOpen()) {
				if(Banking.withdraw(0, "Unpowered orb")){
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							General.sleep(200,500);
							return Inventory.getCount("Unpowered orb") > 1;
						}
					},5000);
				}
			}else{
				General.println("Out of orbs! 3");
				Values.runScript = false;


			}
		}
	}

	public void letsGetNewGlory() {
		RSItem[] chargedGlory = Inventory.find(Values.chargedGlorys);
		RSItem[] unChargedGlory = Inventory.find(1704);
		RSItem[] getChargedGlory = Banking.find(Values.chargedGlorys);
		if(!Banking.isBankScreenOpen() && chargedGlory.length <= 0) {
			if(Banking.openBank()) {
				Timing.waitCondition(new Condition() {
					@Override
					public boolean active() {
						return Banking.isBankScreenOpen();
					}
				}, 2500); 
			}
		}
		if(Banking.isBankScreenOpen()) {
			if(Inventory.isFull()) {
				Banking.depositAll();
			}
			if(unChargedGlory.length > 0) {
				if(Clicking.click(unChargedGlory[0])) {
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							return unChargedGlory.length <= 0;
						}
					}, 2500);
				}
			}
		}
		if(Banking.isBankScreenOpen()) {
			Timing.waitCondition(new Condition() {
				@Override
				public boolean active() {
					return Banking.isBankScreenOpen();
				}
			}, 2500);

		}
		if(Banking.isBankScreenOpen()) {
			if(supplyCheck(CHARGED_GLORY)) {
				if(Inventory.isFull()) {
					Banking.depositAll();

				}




				if(Banking.withdrawItem(getChargedGlory[0], 1)) {
					if(Banking.close()) {
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								return !Banking.isBankScreenOpen();
							}
						}, 2500);
						if(Clicking.click(chargedGlory[0])) {
							Timing.waitCondition(new Condition() {
								@Override
								public boolean active() {
									return Equipment.find(SLOTS.AMULET) == Equipment.find(1704);
								}
							}, 2500);

						}
					}
				}
			}

		} else {
			if(!supplyCheck(CHARGED_GLORY)) {
				Values.runScript = false;
			}
		}
	}



	public static boolean newGlory(){
		if(!Banking.isBankScreenOpen()){
			if(Banking.openBank());
		}
		if(Inventory.isFull()){
			Banking.depositAll();
		}
		if(Inventory.find(Values.chargedGlorys).length == 0){
			RSItem[] glorys = Banking.find(Values.chargedGlorys);
			if(glorys != null && glorys.length > 0){
				Banking.withdrawItem(glorys[General.random(0,glorys.length - 1)],1);
				General.sleep(Values.abc.DELAY_TRACKER.ITEM_INTERACTION.next());
				Values.abc.DELAY_TRACKER.ITEM_INTERACTION.reset();
				Timing.waitCondition(new Condition()
				{
					@Override
					public boolean active()
					{
						General.sleep(300,500);
						return Inventory.find(Values.chargedGlorys).length > 0;
					}
				}, General.random(1000, 2000));
			}
		}
		if(Banking.close()){
			RSItem[] glory = Inventory.find(Values.chargedGlorys);
			if(glory != null && glory.length > 0){
				glory[0].click();
				General.sleep(Values.abc.DELAY_TRACKER.ITEM_INTERACTION.next());
				Values.abc.DELAY_TRACKER.ITEM_INTERACTION.reset();
				Timing.waitCondition(new Condition()
				{
					@Override
					public boolean active()
					{
						General.sleep(300,500);
						return Inventory.find(1704).length > 0;
					}
				}, General.random(1000, 2000));
			}
		}

		RSItem[] oldGlory = Inventory.find(1704);
		if(oldGlory != null && oldGlory.length > 0){
			if(!Banking.isBankScreenOpen()){
				if(Banking.openBank());
			}
			Banking.depositItem(oldGlory[0],1);
			General.sleep(Values.abc.DELAY_TRACKER.ITEM_INTERACTION.next());
			Values.abc.DELAY_TRACKER.ITEM_INTERACTION.reset();
			return true;
		}
		return false;
	}

}
