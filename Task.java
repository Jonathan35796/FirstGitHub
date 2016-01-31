package scripts.BloodsAirCharger;

/**
 * Created by Andrew on 11/19/2015.
 */
public abstract class Task {
	public abstract void execute();

	public abstract boolean validate(); 

	public abstract int priority();

	public abstract String status();
}
