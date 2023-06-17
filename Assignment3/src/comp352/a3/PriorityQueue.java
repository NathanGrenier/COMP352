package comp352.a3;

import java.util.NoSuchElementException;

/**
 * This priority queue is circular. It maintains a stable order by applying insertion sort on all nodes that are enqueued.
 */
public class PriorityQueue {
    Node queue[];
    int front;
    int rear;
    int size;
    static final int DEFAULT_SIZE = 8;

    public PriorityQueue() {
        this.front = -1;
        this.rear = -1;
        this.size = DEFAULT_SIZE;
        this.queue = new Node[this.size];
    }
    
    // amortized O(1) time complexity since resizes are guaranteed to not happen often (because we double when it gets full)
    public void enqueue(Node value) {
        if (this.isFull()) {
            this.resize();
        } else if(this.isEmpty()) {
            front++;
        }
        this.rear = Math.floorMod(this.rear + 1, this.size);
        this.queue[this.rear] = value;
        this.sort();
    }
    
    // Insertion sort. Only applies to the last element (i.e the element that was just added). O(n) where n is the size of the queue
    public void sort() {
        for (int i=this.rear; i != this.front && (this.queue[i].frequency < this.queue[Math.floorMod(i-1, this.size)].frequency); i = Math.floorMod(i-1, this.size)) {
            swap(i, Math.floorMod(i-1, this.size)); // Math.floorMod() is necessary as it works with negative numbers. % just means remainder which isn't enough
        }
    }

    private void swap(int firstIndex, int secondIndex) {
        Node temp = this.queue[firstIndex];
        this.queue[firstIndex] = this.queue[secondIndex];
        this.queue[secondIndex] = temp;
    }

    // O(1) time complexity
    public Node dequeue() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        Node temp = this.queue[this.front];
        // Check to see if we only have 1 element. If so, reset the pointers -1 (empty)
        if (this.front == this.rear) {
            this.front = this.rear = -1;
        } else {
            this.front = Math.floorMod(this.front + 1, this.size);
        }
        return temp;
    }

    public boolean isEmpty() {
        return this.front == -1;
    }

    public boolean isFull() {
        return this.front == Math.floorMod(this.rear + 1, this.size);
    }

    public int length() {
        if (this.isEmpty()) {
            return 0;
        }
        return Math.floorMod(this.rear - this.front, this.size) + 1;
    
    }

    private void resize() {
        Node[] resizedArr = new Node[this.queue.length * 2];
        int i = 0; 
        int j = this.front;
        do {
            resizedArr[i++] = this.queue[j];
            j = Math.floorMod(j+1, this.size);

        } while (j != this.front);
        this.size = this.queue.length * 2;
        this.queue = resizedArr;
    }
}
