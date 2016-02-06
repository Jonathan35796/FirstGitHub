package scripts.BloodsAirCharger;

import org.tribot.script.Script;

import scripts.BloodsAirCharger.API.ACamera;

/**
 * Task - Represents a specific task for the bot to do. Ex. Banking, Chopping, Mining.
 * 
 * @author Worthy - Modifications by Final Calibur
 *
 */
public abstract class newTask 
{
	//The script we're running
	protected Script script;
	
	//ACamera object for asynchronous camera movement
	protected ACamera aCamera;
	
	/**
	 * Task - The constructor to create task objects
	 * 
	 * @param script Script we're running
	 * @param aCamera ACamera object for asynchronous camera movement
	 */
	public newTask(Script script, ACamera aCamera)
	{
		this.script = script;
		this.aCamera = aCamera;
	}
	
	/**
	 * Executes / processes the task
	 */
	public abstract void execute();
		
	/**
	 * Determines whether or not the task should be executed
	 * 
	 * @return true if the task should be executed, false if otherwise
 	 */
	public abstract boolean shouldExecute();
		
	/**
	 * Generates the status associated with this task
	 * 
	 * @return The paint status associated with this task
	 */
	public abstract String getStatus();
}
