package scripts.BloodsAirCharger.Work.Utils;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Equipment;
import org.tribot.api2007.GameTab;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Equipment.SLOTS;
import org.tribot.api2007.GameTab.TABS;
import org.tribot.api2007.types.RSItem;
import org.tribot.script.Script;

import scripts.BloodsAirCharger.API.References;
import scripts.BloodsAirCharger.newTask;
import scripts.BloodsAirCharger.API.ACamera;

public class newGlory extends newTask{

	public newGlory(Script script, ACamera aCamera) {
		super(script, aCamera);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		newGlory();
		General.sleep(10);
		
	}

		@Override
		public boolean shouldExecute() {
			return !Equipment.isEquipped(References.get().chargedGlorys)
					&& Banking.isInBank();

		}

		@Override
		public String getStatus() {
			return "Getting charged Glory...";
	}

		public boolean newGlory(){
			if(!Banking.isBankScreenOpen()){
				if(Banking.openBank());
			}
			if(Inventory.isFull()){
				Banking.depositAll();
			}
			if(Inventory.find(References.get().chargedGlorys).length == 0){
				RSItem[] glorys = Banking.find(References.get().chargedGlorys);
				if(glorys != null && glorys.length > 0){
					Banking.withdrawItem(glorys[General.random(0,glorys.length - 1)],1);
					General.sleep(References.abc.DELAY_TRACKER.ITEM_INTERACTION.next());
					References.abc.DELAY_TRACKER.ITEM_INTERACTION.reset();
					Timing.waitCondition(new Condition()
					{
						@Override
						public boolean active()
						{
							General.sleep(300,500);
							return Inventory.find(References.get().chargedGlorys).length > 0;
						}
					}, General.random(1000, 2000));
				}
			}else {
				if(Equipment.isEquipped("Amulet of glory")) {
					if(GameTab.getOpen() != TABS.EQUIPMENT) {
						GameTab.open(TABS.EQUIPMENT);
						Timing.waitCondition(new Condition() {
							@Override
							public boolean active() {
								return GameTab.getOpen() == TABS.EQUIPMENT;
							}
						},2500);
					}
					Equipment.remove(SLOTS.AMULET);
					Timing.waitCondition(new Condition() {
						@Override
						public boolean active() {
							return Equipment.find(SLOTS.AMULET) == null;
						}
					},3000);
				}
			}
			if(Banking.close()){
				RSItem[] glory = Inventory.find(References.get().chargedGlorys);
				if(glory != null && glory.length > 0){
					glory[0].click();
					General.sleep(References.abc.DELAY_TRACKER.ITEM_INTERACTION.next());
					References.abc.DELAY_TRACKER.ITEM_INTERACTION.reset();
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
				General.sleep(References.abc.DELAY_TRACKER.ITEM_INTERACTION.next());
				References.abc.DELAY_TRACKER.ITEM_INTERACTION.reset();
				return true;
			}
			return false;
		}
		
}
