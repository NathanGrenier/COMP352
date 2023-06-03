package comp352.a3;

public class Node implements Cloneable{
    Character value;
    int frequency;
    Node left;
    Node right;
    
    public Node() {
        this.value = null;
        this.frequency = 0;
        this.left = null;
        this.right = null;
    }

    public Node(Character value, int frequency, Node left, Node right) {
        this.value = value;
        this.frequency = frequency;
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "Node{value=" + this.value + ", frequency=" + this.frequency + ", left=" + this.left + ", right=" + this.right + "}";
    }
}