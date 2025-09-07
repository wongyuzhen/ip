package jane;

import java.util.ArrayList;

/**
 * Represents a list of tasks that can be added, removed, marked as done/undone, and retrieved.
 * Provides methods to manipulate the task list, such as adding and removing tasks,
 * getting tasks by index, and marking tasks as done or undone.
 *
 * <p>The class ensures that tasks are stored and manipulated in an ordered list.</p>
 */
public class TaskList {
    private final ArrayList<Task> list;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.list = new ArrayList<>();
    }

    /**
     * Constructs a TaskList with an initial list of tasks.
     *
     * @param storedList The list of tasks to initialize the TaskList with.
     */
    public TaskList(ArrayList<Task> storedList) {
        this.list = storedList;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return The number of tasks in the list.
     */
    public int size() {
        return this.list.size();
    }

    /**
     * Retrieves a task by its index in the list.
     *
     * @param idx The index of the task to retrieve.
     * @return The task at the specified index.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public Task get(int idx) {
        return list.get(idx);
    }

    /**
     * Adds a task to the list.
     *
     * @param t The task to add.
     */
    public void add(Task t) {
        list.add(t);
    }

    /**
     * Removes a task from the list by its index.
     *
     * @param idx The index of the task to remove.
     * @return The task that was removed.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public Task remove(int idx) {
        return list.remove(idx);
    }

    /**
     * Returns the list of tasks as an ArrayList.
     *
     * @return The list of tasks.
     */
    public ArrayList<Task> asArrayList() {
        return list;
    }

    /**
     * Marks a task as done or undone, based on the provided status.
     *
     * @param idx The index of the task to mark.
     * @param done The status to set the task to (true for done, false for undone).
     * @return The task after being marked.
     * @throws IndexOutOfBoundsException if the index is out of range.
     */
    public Task mark(int idx, boolean done) {
        Task t = list.get(idx);
        if (done) {
            t.markAsDone();
        } else {
            t.markAsUndone();
        }
        return t;
    }

    /**
     * Checks if the task list is empty.
     *
     * @return true if the list is empty, false otherwise.
     */
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
}

