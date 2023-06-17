package comp352.a3;
/*
 * Q1: What is the purpose of using a priority queue in Huffman coding, and how does it help to generate an optimal code?
 * A1: In Huffman coding, the purpose of using a priority queue is to efficiently generate an optimal code by assigning shorter codes to more frequently occurring characters or symbols. The priority queue helps in achieving this goal by organizing the characters based on their frequencies, allowing for easy selection of the characters with the lowest frequencies. This results in the nodes with the lowest frequencies being selected first for merging. Because they were selected first, the characters with the lowest frequencies end up near or at the bottom of the Huffman encoding tree.
 * 
 * Q2: How does the length of a Huffman code relate to the frequency of the corresponding symbol, and why is this useful for data compression?
 * A2: In general, more frequently occurring symbols are assigned shorter codes, while less frequently occurring symbols are assigned longer codes. By assigning shorter codes to frequently occurring symbols and longer codes to infrequently occurring symbols, Huffman coding effectively reduces the average number of bits required to represent the data.
 * 
 * Q3: What is the time complexity of building a Huffman code, and how can you optimize it?
 * A3: The time complexity is affected by multiple steps.
 *      1. We must first read all the characters in a sample text to determine the frequency of each ASCII character. This takes O(c) time complexity where c is the number of characters in the file.
 *      2. We then enqueue all of the (character, frequency) nodes. We add n characters to the end of an array, O(n). When we enqueue a value, we must sort it. I've implemented insertion sort, which makes n comparisons. Therefore, we make n comparisons for n elements resulting in O(n^2) complexity.
 *      3. When building the tree, we dequeue 2 nodes, then enqueue the combination of those 2 nodes. We repeat this cycle for all the nodes in the priority queue until we only have 1 left, or n-1 times. Complexity: O(n) [assuming enqueue and dequeue are O(1)] because O(n(2(1) + 1)) = O(n), where the first 1 is the number of dequeues and the second is enqueues. 
 *      4. Inorder traversal to generate the binary codes for the lookup table. O(n) because we visit each node.
 *      5. Encode String: We just use the lookup table. O(1) on all the characters
 *      6. Decode String: Iterate over the binary string O(b) where b is the number of binary digits
 * Optimizations: Using merge sort to sort the priority queue after adding all elements would optimize the Huffman tree generation. Merge sort is O(nlogn) complexity compared to insertion sorts (n^2) while still being algorithmically stable. This would cost us O(n) more space.
 * 
 * Notes: 
 * - The priority queue can just be a sorted array (insertion sort).
 * - All the Huffman trees were created with the same priority queue initial state config (ASCII ascending)
 * - When making the lookup table, do a inorder traversal of the Huffman encoding tree.
 * - Implement the queue with 2 pointers, one at each end. When enqueuing or dequeuing, just increment these pointers. This allows for O(1) enqueue and dequeue.
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class HuffCode {
    enum encodeType {
        ENCODE,
        DECODE
    }
    static final int ASCII_CHAR_COUNT = 256;
    String encodedCharacterSet[];
    PriorityQueue queue;
    Node root;

    public HuffCode(int frequencies[], Character values[]) {
        this.root = null;
        this.encodedCharacterSet = new String[ASCII_CHAR_COUNT];
        this.queue = new PriorityQueue();
        for (int i=0; i < values.length; i++) {
            if (frequencies[i] != 0) {
                this.queue.enqueue(new Node(values[i], frequencies[i], null, null));
            }
        }

        this.generateHuffTree();
        this.generateBinaryCodes(root, "");
    }

    private void generateHuffTree() {
        if (this.queue.isEmpty()) {
            System.out.println("Queue is empty.");
            return;
        }

        while (this.queue.length() != 1) {
            Node left = this.queue.dequeue();
            Node right = this.queue.dequeue();
            this.queue.enqueue(new Node(null, left.frequency + right.frequency, left, right));
        }
        this.root = this.queue.dequeue();
    }

    private void generateBinaryCodes(Node node, String code) {
        if (node.left == null && node.right == null) {
            this.encodedCharacterSet[(int) node.value] = code;
            return;
        }
        this.generateBinaryCodes(node.left, code + "0");

        this.generateBinaryCodes(node.right, code + "1");        
    }

    public String encodeMsg(String msg) {
        String encodedMsg = "";
        char[] chars = msg.toCharArray();
        for (char c:chars) {
            encodedMsg += this.encodedCharacterSet[(int) c];
        }
        return encodedMsg;
    }
    
    public String decodeMsg(String encodedMsg) {
        String decodedMsg = "";
        char[] chars = encodedMsg.toCharArray();
        Node current = this.root;
        for (char c: chars) {
            if (c == '0') {
                current = current.left;
            } else if (c == '1') {
                current = current.right;
            }
            if (current.value != null) {
                decodedMsg += current.value;
                current = this.root;
            }
        }
        return decodedMsg;
    }

    private static int[] recordFrequencies(String filename) {
        int frequencies[] = new int[ASCII_CHAR_COUNT];
        
        Scanner read = null;
        try {
            read = new Scanner(new FileInputStream(filename));
            read.useDelimiter("");  // Read single characters
        } catch (FileNotFoundException e) {
            System.out.printf("Error with file %s.\nError: %s\n", filename, e.toString());
            System.exit(1);
        }
        
        while (read.hasNext()) {
            char token = Character.toLowerCase(read.next().charAt(0));
            frequencies[(int) token] += 1;
        }
        return frequencies;
    }

    private static Character[] generateASCIICharacters() {
        Character ASCIICharacters[] = new Character[ASCII_CHAR_COUNT];
        for (int i=0; i < ASCII_CHAR_COUNT; i++) {
            ASCIICharacters[i] = (char) i;
        }
        return ASCIICharacters;
    }
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Expected 2 arguments.");
            System.exit(1);
        }
        
        String filename = args[0];
        encodeType operation = null;
        try {
            operation = encodeType.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Operation: " + args[1]);
            System.exit(1);
        }

        int frequencies[] = recordFrequencies(filename);

        HuffCode huffCodeTree = new HuffCode(frequencies, generateASCIICharacters());

        Scanner in = new Scanner(System.in);
        if (operation == encodeType.ENCODE) {
            System.out.print("Enter message to encode: ");
            System.out.println(huffCodeTree.encodeMsg(in.nextLine()));
        } else if (operation == encodeType.DECODE) {
            System.out.print("Enter message to decode: ");
            System.out.println(huffCodeTree.decodeMsg(in.nextLine()));
        }
    }
}
