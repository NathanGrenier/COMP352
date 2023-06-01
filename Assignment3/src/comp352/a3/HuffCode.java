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
 */

import java.util.Scanner;

public class HuffCode {
    enum encodeType {
        ENCODE,
        DECODE
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
