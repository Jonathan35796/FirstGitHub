package scripts.BloodsAirCharger.Charging;


import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.*;
import org.tribot.api2007.types.RSInterfaceChild;
import org.tribot.api2007.types.RSObject;
import scripts.BloodsAirCharger.Banking.OpenBank;
import scripts.BloodsAirCharger.OrbCharger;
import scripts.BloodsAirCharger.Task;
import scripts.BloodsAirCharger.Timer;
import scripts.BloodsAirCharger.Values;

/**
 * Created by Andrew on 11/19/2015.
 */
public class ChargeOrbs extends Task{
	Timer timer = new Timer(3000);
	public static boolean Now_Animating = false; 
	long t = System.currentTimeMillis();
	@Override
	public void execute() {
		if(Timing.timeFromMark(t) < General.random(3000,4000))	{
			General.sleep(10);
		}
		if(Player.getAnimation() != -1) {		
			t = System.currentTimeMillis();
		}
		if(Player.getAnimation() == -1 && Timing.timeFromMark(t) 
				> General.random(3000, 4000)) {
			RSObject altar[] = Objects.find(15, "Obelisk of Air");
			RSInterfaceChild spell = Interfaces.get(218,50);
			RSInterfaceChild craft = Interfaces.get(309,2);
			if(altar.length > 0){
				if(craft != null){
					if(Clicking.click("Make All", craft)){
						t = System.currentTimeMillis();
						General.sleep(300,400);
						Timing.waitCondition(new Condition() {
							@Override 
							public boolean active() {
								General.sleep(1200,1500);
								return altar.length > 0 && craft!=null;

							}
						}, 2000);
					}
				}else if(Game.isUptext("Cast Charge Air Orb ->")){

					if(Clicking.click(altar[0])){
						General.sleep(300,400);
						Timing.waitCondition(new Condition() {
							@Override 
							public boolean active() {
								General.sleep(300,400);
								return Player.getAnimation() != -1;

							}
						}, 1000);
					}

				}else {
					if (spell != null) {
						if (GameTab.getOpen() != GameTab.TABS.MAGIC) {
							GameTab.open(GameTab.TABS.MAGIC);
						}
						else if (Clicking.click("Charge Air Orb", spell)){
							Timing.waitCondition(new Condition() {
								@Override 
								public boolean active() {
									General.sleep(300,400);
									return Magic.isSpellSelected();

								}
							}, 1000);
						}

					}

				}
			}
		} else {
			General.sleep(10);
		}
	}




	@Override
	public boolean validate() {
		return atAltar() && Inventory.find("Unpowered orb").length > 0 && Inventory.getCount("Cosmic rune") > 3;
	}

	@Override
	public int priority() {
		return 1;
	}

	@Override
	public String status() {
		return "Charging orbs";
	}

	public static boolean atAltar(){
		return Objects.find(15,"Obelisk of Air").length > 0;
	}


}
