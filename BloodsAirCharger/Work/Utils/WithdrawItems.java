package scripts.BloodsAirCharger.Work.Utils;

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
import org.tribot.script.Script;

import scripts.BloodsAirCharger.newTask;
import scripts.BloodsAirCharger.API.ACamera;
import scripts.BloodsAirCharger.API.References;

/**
 * Created by Andrew on 11/19/2015.
 */
public class WithdrawItems extends newTask{
	
	

	public WithdrawItems(Script script, ACamera aCamera) {
		super(script, aCamera);
	}
	private static String withdrawItemsStatus  = "";
	
	@Override
	public void execute() {
		

		if(Inventory.getCount("Cosmic rune") <= 75) {
			withdrawCosmics();

		}
		if(Inventory.getCount(References.get().food)
				<= References.get().foodToTake) {
			getFood();
		}
		
		if(References.get().airOrbsInv.length
				< 1) {
		withdrawOrbs();
		}
	}

	@Override
	public boolean shouldExecute() {
		return	!Inventory.isFull()
				&&	References.get().airOrbsInv.length <= 0
				&&	Banking.isBankScreenOpen() 
				&& Inventory.getCount("Cosmic rune") > 3  
				||!Inventory.isFull()
				&& References.get().airOrbsInv.length <= 0
				&& Banking.isBankScreenOpen() 
				&& Equipment.isEquipped(1704)
				||!Inventory.isFull()
				&&	References.get().airOrbsInv.length <= 0
				&&	Banking.isBankScreenOpen()
				&& !References.get().getCorrectInventory()
				|| !Inventory.isFull() && Banking.isBankScreenOpen();
				
				/*
				 * when I have glory on, staff of air, 
				 * don't have unpowered orbs
				 * don't have cosmics > 75
				 * don't have food I need
				 */
	}

/*	@Override
	public int priority() {
		return 7;
	}*/

	@Override
	public String getStatus() {
		return ""+withdrawItemsStatus;
	}

	public static boolean withdrawFood() {
		withdrawItemsStatus = "Withdrawing Food...";
		RSItem[] inventory = Inventory.getAll();
		if(!(inventory.length > References.get().foodToTake - 28)) {
			Banking.deposit(References.get().foodToTake,"Unpowered orb");
			
		}
		if(Inventory.getCount(References.get().food) < References.get().foodToTake) {
			Banking.withdraw(References.get().foodToTake,References.get().food);
			return false;
		}
		if(Inventory.getCount(References.get().food) == References.get().foodToTake) {
			return false;
		}
		
		return Banking.withdraw(References.get().foodToTake, References.get().food);
	}

	public static void withdrawStamina(){
		withdrawItemsStatus = "Withdrawing Staminas...";
		if(Inventory.find(References.get().staminaIds).length == 0){
			RSItem staminas[] = Banking.find(References.get().staminaIds);
			if(staminas.length > 0){
				if(Banking.withdrawItem(staminas[0],1)){
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							General.sleep(20,250);
							return Inventory.find(References.get().staminaIds).length > 0;
						}
					},5000);
				}
			}

		}
	}

	public static void withdrawCosmics(){
		withdrawItemsStatus = "Withdrawling Cosmics...";
		if(supplyCheck(SUPPLY_VALUES2)) {
			if(Inventory.getCount("Cosmic rune") < 99){
				Banking.withdraw(100, "Cosmic rune");
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
						References.get().runScript = false;
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
			put(References.get().food, References.get().foodToTake);
		}
	};
	public static boolean getFood() {
		if(supplyCheck(SUPPLY_VALUES3)) {
			withdrawItemsStatus = "Withdrawing Food...";
			RSItem[] inventory = Inventory.getAll();
			if(!(inventory.length > References.get().foodToTake - 28)) {
				Banking.deposit(References.get().foodToTake,"Unpowered orb");
				
			}
			if(Inventory.getCount(References.get().food) < References.get().foodToTake) {
				Banking.withdraw(References.get().foodToTake,References.get().food);
				return false;
			}
			if(Inventory.getCount(References.get().food) == References.get().foodToTake) {
				return false;
			}
		}
			
			return Banking.withdraw(References.get().foodToTake, References.get().food);
		}
	
	public static void withdrawOrbs(){
		if(supplyCheck(SUPPLY_VALUES)) {
			if(Banking.isBankScreenOpen()) {
				Banking.withdraw(0, "Unpowered orb");
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
				References.get().runScript = false;


			}
		
	}

}
