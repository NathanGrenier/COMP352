package comp352.a3;

public class PriorityQueue {
    Node queue[];
    int lastIndex;

    public PriorityQueue(int queueMaxSize) {
        this.lastIndex = -1;
        this.queue = new Node[queueMaxSize];
    }
    
    public void enqueue(Node value) {
        this.lastIndex++;
        this.queue[this.lastIndex] = value;
        this.sort();
    }
    
    // Insertion sort
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

    public Node dequeue() {
        if (this.isEmpty()) {
            return null;
        }
        Node shifted = this.queue[0];
        this.shift();
        this.lastIndex--;
        return shifted;
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
