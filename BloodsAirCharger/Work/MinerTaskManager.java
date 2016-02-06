package scripts.BloodsAirCharger.Work;

import java.util.LinkedList;

import org.tribot.api.General;
import org.tribot.script.Script;

import scripts.BloodsAirCharger.Mode;
import scripts.BloodsAirCharger.API.ACamera;
import scripts.BloodsAirCharger.API.References;
import scripts.BloodsAirCharger.Work.Utils.*;
import scripts.BloodsAirCharger.Manager;
import scripts.BloodsAirCharger.newTask;

public class MinerTaskManager extends Manager
{

	public MinerTaskManager(Script script, ACamera aCamera)
	{
		super(script, aCamera);
	}
	

	@Override
	public LinkedList<newTask> getTaskList()
	{
		LinkedList<newTask> tasks = new LinkedList<newTask>();	
		
		
		tasks.add(new DeadWalk(script, aCamera));
		
		
		tasks.add(new EquipStaff(script, aCamera));
		
		
		tasks.add(new newGlory(script, aCamera));
		
		tasks.add(new Teleport(script, aCamera));
		
		tasks.add(new EatWalk(script, aCamera));
		
		tasks.add(new ChargeOrbs(script, aCamera));
		
		tasks.add(new WalkToBank(script, aCamera));
		
		tasks.add(new WalkToAltar(script, aCamera));
		
		tasks.add(new BankEat(script, aCamera));
		
		tasks.add(new OpenBank(script, aCamera));
		
		tasks.add(new DepositItems(script, aCamera));
		
		tasks.add(new WithdrawItems(script, aCamera));
		
		return tasks;
	}
	
	public void addAppropriateTasks()
	{
		switch(References.get().mode)
		{
		
			case DEADWALK:
			tasks.add(new DeadWalk(script, aCamera));
			break;
			case WALKTOBANK:
				tasks.add(new WalkToBank(script, aCamera));
			break;
			
			case WALKTOALTAR:
				tasks.add(new WalkToAltar(script, aCamera));
			break;
			
			case BANKEAT:
				tasks.add(new BankEat(script, aCamera));
			break;
			case OPENBANK:
				tasks.add(new OpenBank(script, aCamera));
			break;
			case CHARGEORBS:
				tasks.add(new ChargeOrbs(script, aCamera));
			break;
			case TELEPORT:
				tasks.add(new Teleport(script, aCamera));
			break;
			case WITHDRAWITEMS:
				tasks.add(new WithdrawItems(script, aCamera));
			break;
			case DEPOSITITEMS:
				tasks.add(new DepositItems(script, aCamera));
			break;
			case EATWALK:
				tasks.add(new EatWalk(script, aCamera));
			break;
			
			default:
				tasks.add(new DeadWalk(script, aCamera));
		}
		
		tasks.add(new ChargeOrbs(script, aCamera));
		
		General.println("Added appropriate tasks for your goals");
	}

}