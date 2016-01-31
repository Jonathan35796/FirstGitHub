package scripts.BloodsAirCharger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Andrew on 11/19/2015.
 */
public class TaskManager implements Comparator<Task> {

	private List<Task> list;

	public TaskManager() {
		list = new ArrayList<>();
	}

	public void addTasks(Task... tasks) {
		for (Task task: tasks) {
			if (!list.contains(task)) {
				list.add(task);
			}
		}
	}

	public void removeTask(Task task) {
		if (list.contains(task)) {
			list.remove(task);
		}
	}

	public void clearTasks() {
		list.clear();
	}

	public int size() {
		return list.size();
	}

	/*
    Return the highest priority valid task.
	 */
	public Task getValidTask() {
		if (list.size() > 0) {
			Collections.sort(list, this);
			return list.get(0);
		}
		return null;
	}

	/*
    Overridden compare method from the Comparator interface used by the
    Collections sort method to determine what task is at the head of the priority.

    Added to the standard compare method is a check to see if each
    task validates or not before comparing them.
    If one task does not validate and the other does, the task that validates
    assumes higher priority.

    Refer to the javadoc for the Comparator interface for an explanation of the
    return values from this method.
	 */
	@Override
	public int compare(Task o1, Task o2) {
		boolean o1Valid = o1.validate(), o2Valid = o2.validate();
		if (!o1Valid && o2Valid) return 1;
		else if (o1Valid && !o2Valid) return -1;
		return o2.priority() - o1.priority();
	}

}