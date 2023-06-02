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

import java.util.Scanner;

public class HuffCode {
    enum encodeType {
        ENCODE,
        DECODE
    }

    
    private class PriorityQueue {
        
    }
    private class Node {

    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Expected 2 arguments.");
            System.exit(1);
        }
        
        String filename = args[0];

        try {
            encodeType operation = encodeType.valueOf(args[1].toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Operation: " + args[1]);
            System.exit(1);
        }


        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
    }
}
