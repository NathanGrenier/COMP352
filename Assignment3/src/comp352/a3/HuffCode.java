package comp352.a3;
/*
 * Q1: What is the purpose of using a priority queue in Huffman coding, and how does it help to generate an optimal code?
 * A1: 
 * 
 * Q2: How does the length of a Huffman code relate to the frequency of the corresponding symbol, and why is this useful for data compression?
 * A2: 
 * 
 * Q3: What is the time complexity of building a Huffman code, and how can you optimize it?
 * A3: 
 * 
 * Notes: 
 * - The priority queue can just be a sorted array (insertion sort).
 * - All the huffman trees were created with the same priority queue initial state config (ASCII ascending)
 * - When we make the lookup table, use an array with the size of all the ASCII charaters (2^8). Decode the character into its ASCII value and use that as the index to lookup in the array.
 * - When making the lookup table, do a inorder traversal of the huffman encoding tree. 
 * - For counting the frequency of characters, you can also use an array with a max size equal to the total number of ASCII characters (2^8). When you encounter a character, increment the counter in the array at the index equal to the character's ASCII value.   
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
        this.queue = new PriorityQueue(ASCII_CHAR_COUNT);
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

        while (this.queue.lastIndex != 0) {
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
