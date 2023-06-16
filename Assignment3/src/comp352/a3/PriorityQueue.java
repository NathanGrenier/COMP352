package comp352.a3;

public class PriorityQueue {
    Node queue[];
    int lastIndex;
    int startIndex;

    public PriorityQueue(int queueMaxSize) {
        this.lastIndex = -1;
        this.startIndex = 0;
        this.queue = new Node[queueMaxSize];
    }
    
    public void enqueue(Node value) {
        this.lastIndex++;
        this.queue[this.lastIndex] = value;
        this.sort();
    }
    
    // Insertion sort. Only applies to the last element (i.e the element that was just added). O(n) where n is the size of the queue
    public void sort() {
        for (int i=this.lastIndex; i > 0 && this.queue[i].frequency < this.queue[i-1].frequency; i--) {
            swap(i, i-1);
        }
    }

    private void swap(int firstIndex, int secondIndex) {
        Node temp = (Node) this.queue[firstIndex];
        this.queue[firstIndex] = this.queue[secondIndex];
        this.queue[secondIndex] = temp;
    }

    // FIXME: The queue will eventually read an index out of bounds. This is because the start and end pointers are never reset to index 0 of the array. [This is only true for the implementation where we have a startIndex pointer]
    public Node dequeue() {
        if (this.isEmpty()) {
            return null;
        }
        Node shifted = this.queue[0];
        this.shift();
        this.lastIndex--;
        return shifted;
        // Node removed = this.queue[this.startIndex];
        // this.startIndex++;
        // return removed;
    }

    private void shift() {
        for (int i=1; i <= this.lastIndex; i++) {
            this.queue[i-1] = this.queue[i];
        }
    }


    public boolean isEmpty() {
        if (this.queue == null || this.lastIndex == -1) {
            return true;
        }
        return false;
    }
}
