package scripts.BloodsAirCharger;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.util.ThreadSettings;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;

import scripts.BloodsAirCharger.API.ACamera;
import scripts.BloodsAirCharger.API.References;
import scripts.BloodsAirCharger.GUI.BloodsGui;
import scripts.BloodsAirCharger.Work.MinerTaskManager;
import scripts.BloodsAirCharger.Work.Utils.ChargeOrbs;
import scripts.BloodsAirCharger.Work.Utils.DepositItems;
import scripts.BloodsAirCharger.Work.Utils.EatWalk;
import scripts.BloodsAirCharger.Work.Utils.OpenBank;
import scripts.BloodsAirCharger.Work.Utils.Teleport;
import scripts.BloodsAirCharger.Work.Utils.WalkToAltar;
import scripts.BloodsAirCharger.Work.Utils.WalkToBank;
import scripts.BloodsAirCharger.Work.Utils.WithdrawItems;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Andrew on 11/19/2015.
 */
@ScriptManifest(name="Private Air Script",authors ="BloodFlavor/Jonathan35796",description = "Start this script in Edgeville bank, this script currently does NOT equip a Staff of Air, you must have a Staff of Air equip before starting script."
,category = "Money making", version = 2.0)
public class ScriptMain extends Script implements Painting {
	
	private final ACamera ACAMERA = new ACamera();
	BloodsGui startGui = new BloodsGui(this);
	public final MinerTaskManager	TASK_MANAGER = new MinerTaskManager(this, ACAMERA);
	
	@Override
	public void run() {
		while(References.get().runScript) {
			
			if(!References.get().guiComplete) {
				initGui(startGui);
				continue;
			}
			
			TASK_MANAGER.executeTasks();
			
			
		}
		}
	
/*	@Override
	public String[] getPaintInfo()
	{
		/*
		 * These variables are here because the info they hold is used more than 1 time throughout this method
		 
		int xpGained = Skills.getXP(SKILLS.MINING) - References.get().startingXp;
		int level = Skills.getActualLevel(SKILLS.MINING);
		newTask currentTask = TASK_MANAGER.getCurrentTask();
		String newTask = currentTask == null || currentTask.getStatus() == null ? "None" : currentTask.getStatus();
		
		return new String[]{MANIFEST.name() + " v" + MANIFEST.version() + " by " + MANIFEST.authors()[0], 
				"Time ran: " + References.get().paint.getTimeRan(), "Mode: " + Vars.get().mode, "Current Task: " + task,
				"Current level (gained): " + level + " (" + (level - Vars.get().startingLevel) + ")",
				"Ore mined (p/h): " + Vars.get().oresMined + " (" + Vars.get().paint.getPerHour(Vars.get().oresMined) + ")",
				"Ore stolen (p/h): " + Vars.get().oresStolen + " (" + Vars.get().paint.getPerHour(Vars.get().oresStolen) + ")",
				"XP gained (p/h): " + xpGained + " (" + Vars.get().paint.getPerHour(xpGained) + ")", 
				"Times hopped (p/h): " + Vars.get().timesHopped + " (" + Vars.get().paint.getPerHour(Vars.get().timesHopped) + ")"};
	}
	*/
	public void onStart() {
	General.useAntiBanCompliance(true);
	ThreadSettings.get().getClickingAPIUseDynamic(); 
	References.reset();
	References.get().initialXp = Skills.getXP(Skills.SKILLS.MAGIC);
	initGui(startGui);
}

	public static void initGui(BloodsGui startGui){
		startGui.setVisible(true);
		while(!References.get().guiComplete){
			General.sleep(100,300);
		}

		startGui.dispose();
	}
	/*
	//public static boolean canClick = false;
	//public static boolean canFailSafe = false;
	private void loop(int min, int max) {
		TaskManager manager = new TaskManager();
		manager.addTasks(new OpenBank(), new DepositItems(), new WithdrawItems(), new WalkToBank(), new ChargeOrbs(), new Teleport(), new WalkToAltar(), new EatWalk());
		//Thread pvpThread = new PvpThread();
		//pvpThread.start();
		while (References.runScript) {

		//	pvpThread.run();
			Task task = manager.getValidTask();
			if (task != null) {
				References.get().status = task.status();
				task.execute();
			}

		}
		sleep(min,max);
	}
*/
	private final Font font2 = new Font("Arial", 1, 13);
	@Override
	public void onPaint(Graphics graphics) {
		newTask currentTask = TASK_MANAGER.getCurrentTask();
		String newTask = currentTask == null || currentTask.getStatus() == null ? "None" : currentTask.getStatus();

		graphics.setColor(Color.CYAN);
		graphics.setFont(font2);
		References.get().orbsMade = (Skills.getXP(Skills.SKILLS.MAGIC) - References.get().initialXp) / 76;
		graphics.drawString("Run time: " + Timing.msToString(getRunningTime()),15,290);
		graphics.drawString("Status: " + newTask,15,305);
		graphics.drawString("Orbs made: " + References.get().orbsMade + " (" +  Math.round(References.get().orbsMade / (getRunningTime() / 3600000.0)) + ")",15,320);
	}
}
