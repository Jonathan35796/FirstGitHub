package scripts.BloodsAirCharger;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.util.ThreadSettings;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;
import scripts.BloodsAirCharger.Banking.DepositItems;
import scripts.BloodsAirCharger.Banking.OpenBank;
import scripts.BloodsAirCharger.Banking.WithdrawItems;
import scripts.BloodsAirCharger.Charging.ChargeOrbs;
import scripts.BloodsAirCharger.Navigation.Teleport;
import scripts.BloodsAirCharger.Navigation.WalkToAltar;
import scripts.BloodsAirCharger.Navigation.WalkToBank;
import javax.swing.*;
import java.awt.*;

/**
 * Created by Andrew on 11/19/2015.
 */
@ScriptManifest(name="BloodFlavors Air Orb",authors ="BloodFlavor/Jonathan35796",description = "Start this script in Edgeville bank, this script currently does NOT equip a Staff of Air, you must have a Staff of Air equip before starting script."
,category = "Money making", version = 1.0)
public class OrbCharger extends Script implements Painting {
	@Override
	public void run() {
		General.useAntiBanCompliance(true);
		ThreadSettings.get().getClickingAPIUseDynamic(); 
		Values.initialXp = Skills.getXP(Skills.SKILLS.MAGIC);
		if(!Values.guiComplete) {
			BloodsGui startGui = new BloodsGui();
			initGui(startGui);
		}
		loop(20,50);
	}



	public static void initGui(BloodsGui startGui){
		startGui.setVisible(true);
		while(!Values.guiComplete){
			General.sleep(100,300);
		}

		startGui.dispose();
	}


	public boolean ShouldClick(long t) {
		t = System.currentTimeMillis();
		if(Timing.timeFromMark(t) > General.random(3000, 4000)) {
			return true;
		}
		if(Player.isMoving()) {
			t = System.currentTimeMillis();
			return false;
		}
		return false;
	}
	public boolean ShouldFailSafe(long t) {
		t = System.currentTimeMillis();
		if(Timing.timeFromMark(t) > General.random(28000, 30000)) {
			return true;
		}
		if(Player.isMoving()) {
			t = System.currentTimeMillis();
			return false;
		}
		return false;
	}
	//public static boolean canClick = false;
	//public static boolean canFailSafe = false;
	private void loop(int min, int max) {
		TaskManager manager = new TaskManager();
		manager.addTasks(new OpenBank(), new DepositItems(), new WithdrawItems(), new WalkToBank(), new ChargeOrbs(), new Teleport(), new WalkToAltar(), new EatFood());
		Thread pvpThread = new PvpThread();
		pvpThread.start();
		while (Values.runScript) {

			pvpThread.run();
			Task task = manager.getValidTask();
			if (task != null) {
				Values.status = task.status();
				task.execute();
			}

		}
		sleep(min,max);
	}

	private final Font font2 = new Font("Arial", 1, 13);
	@Override
	public void onPaint(Graphics graphics) {

		graphics.setColor(Color.CYAN);
		graphics.setFont(font2);
		Values.orbsMade = (Skills.getXP(Skills.SKILLS.MAGIC) - Values.initialXp) / 76;
		graphics.drawString("Run time: " + Timing.msToString(getRunningTime()),15,290);
		graphics.drawString("Status: " + Values.status,15,305);
		graphics.drawString("Orbs made: " + Values.orbsMade + " (" +  Math.round(Values.orbsMade / (getRunningTime() / 3600000.0)) + ")",15,320);
	}
}
