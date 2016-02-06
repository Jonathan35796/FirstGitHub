package scripts.BloodsAirCharger.Workers;

public interface Worker
{
	//Performs the "task" that this worker has to do
	public void work();
	
	//Gets the next worker to transition to
	public Worker getNextWorker();
}