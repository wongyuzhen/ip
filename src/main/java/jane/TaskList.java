package jane;

import java.util.ArrayList;

public class TaskList {
    private final ArrayList<Task> list;

    public TaskList() {
        this.list = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> storedList) {
        this.list = storedList;
    }

    public int size() {
        return this.list.size();
    }

    public Task get(int idx) {
        return list.get(idx);
    }

    public void add(Task t) {
        list.add(t);
    }

    public Task remove(int idx) {
        return list.remove(idx);
    }

    public ArrayList<Task> asArrayList() {
        return list;
    }

    public Task mark(int idx, boolean done) {
        Task t = list.get(idx);
        if (done) t.markAsDone(); else t.markAsUndone();
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
