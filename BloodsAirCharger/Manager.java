package scripts.BloodsAirCharger;
import java.util.LinkedList;
import java.util.List;

import org.tribot.script.Script;

import scripts.BloodsAirCharger.API.ACamera;

/**
 * This class manages the tasks a script has. It handles
 * the creation of the task list as well as the execution of it.
 * 
 * @author Freddy
 *
 */
public abstract class Manager 
{
	protected List<newTask> tasks;
	protected newTask currentTask;
	protected boolean running;
	protected Script script;
	protected ACamera aCamera;
	
	public Manager(Script script, ACamera aCamera)
	{
		this.script = script;
		this.aCamera = aCamera;
		this.tasks = getTaskList();
	}
	
	public boolean executeTasks()
	{
		synchronized(tasks)
		{
			running = false;
			
			for(newTask task : tasks)
			{
				if(task.shouldExecute())
				{
					currentTask = task;
					
					task.execute();
					
					running = true;
				}
			}
			
			return running;
		}
	}
	
	public boolean removeTask(newTask task)
	{
		synchronized(tasks)
		{
			return tasks.remove(task);
		}
	}
	
	public boolean addTask(newTask task)
	{
		synchronized(tasks)
		{
			return tasks.add(task);
		}
	}
	
	public void setTaskList(List<newTask> tasks)
	{
		synchronized(tasks)
		{
			this.tasks = tasks;
		}
	}
	
	public newTask getCurrentTask()
	{
		return this.currentTask;
	}
	
	public abstract LinkedList<newTask> getTaskList();

}