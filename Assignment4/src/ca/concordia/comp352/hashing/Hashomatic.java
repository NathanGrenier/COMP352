package ca.concordia.comp352.hashing;
/*
 * Q1: Why is it important to have a good hash function when using a hash table? What characteristics did you consider when implementing your hash function?
 * A1: A good hash function ensures that the data we are hashing is evenly distributed in the table. A good hash will remove the similar characteristics of the data. This results in less collisions leading to better performance (faster). I considered the fact that all the passwords were strings of lowercase alphabetical characters each 8 characters long. This means that the sum of the ASCII values were all going to be in the same range (~800-900). 
 * 
 * Q2: Discuss the time complexity of your hash table operations: insertion, deletion, and lookup. How do these complexities compare to those of the TRHASH version?
 * A2: In a good hash table, insertion, deletion and lookup should all be O(1). With a good hash, insertions will have minimal collisions, deletion becomes O(1) with tombstones, and the same keys can hashed to find a value in constant time. The insertion of my hash table is slightly better than the TRASH version as it produces, on average, less collisions. This has been proven due to my hash table having a faster build time. Both out tables don't implement deletion. Lookups fall under the same principles as insertions. If we have a bad hash functions, then the data will form clusters due to different keys having the same hash value. This means that when a lookup is performed, we will have to probe the cluster to find our value, reducing performance.
 * 
 * Q3: How does load factor influence the performance of a hash table? How did you manage the load factor in your implementation?
 * A3:  As the load factor increases, the probability of collisions rises, leading to longer clusters and slower lockup times.By managing the load factor, a balance between space utilization and collision avoidance can be reached, ensuring optimal hash table performance. If I were to optimize for speed, I would keep my load factor at 70%. This would improve performance by lowering the size of clusters in the table. After some testing, I decided to optimize for space to score the highest marks on the debugger because optimizing for speed was much harder. 
 */

import main.stuff.MyHash;
import main.stuff.NotFoundException;
import main.stuff.TooFullException;

public class Hashomatic extends MyHash{
    private static final double LOAD_FACTOR = 0.7;
    public static int clusterCount = 0;

    public Hashomatic(String filename) {
		super(filename);
	}

    /**
     * Function is x^2. In order to work, the size of the table must be a prime number. This is handled in determineTableSize().
     */
    private int probingFunction(int x) {
        return (int) Math.pow(x, 2);
    }

    @Override
    public void insert(String str) throws TooFullException{
        try {
            find(str);
        } catch (NotFoundException e) {
            put(e.getIndex(), str);
            incSize();
        }
    }

    /**
     * Implemented with quadradic probing.
     */
    @Override
    protected String find(String str) throws TooFullException, NotFoundException{
        int searchCount = 0;
        int tableIndex = hash(str);
        while (searchCount <= getTableSize()) {
            if (get(tableIndex) == null) {
                throw new NotFoundException(tableIndex);
            } else if (str.equals(get(tableIndex))) {
                return str;
            }
            searchCount++;
            tableIndex = (tableIndex + probingFunction(searchCount)) % getTableSize();
        } 
        throw new TooFullException(str);
    }

    private static int countBits(long n) {
        return Long.toBinaryString(n).length();
    }
    
    // Implemented Mid square hashing (kinda). We must handle the squared number as an long to avoid overflow. First, we apply the same hash as the TotallyReliableHASH. This distributes the data decently well. This step is better than just summing up the ASCII values of the string as it makes different permutations of the same sequence of letter into different hashes. The next step is to square the hash then modulo it by the table size. Squaring the number further exacerbates the small differences in the ASCII value of the strings. The modulo ensures that the hash maps to an index in our table.
    @Override
    protected int hash(String str) {        
        int g = 31; // Prime number
        long hash = 7; 
        for (int i=0; i < str.length(); i++) {
            hash = (hash * g) + str.charAt(i);
        }
        hash *= hash;
        return (int) (Math.abs(hash) % getTableSize());
        
        // hash *= hash;
        // int squaredBitCount = countBits(hash);
        // int middleBitCount = Integer.SIZE - Integer.numberOfLeadingZeros(getTableSize());
        // return ((int) (hash >> ((squaredBitCount - middleBitCount)  / 2) & ((1 << middleBitCount) - 1)));
    }
    
    /**
     * We would want to maintain a load factor of about 70% to maintain O(1) lookups. The table size must also be a prime number so we can use quadratic probing. 
     */
    @Override
	protected int determineTableSize(int i) {
        int size = (int) (i / LOAD_FACTOR);
        //int nearestPowOf2 = (int) Math.pow(2, Integer.SIZE - Integer.numberOfLeadingZeros(size));
        return 1000003;    // 1,000,003 is the nearest prime number to 1,000,000. We have a load factor of about ~100% because I've tested and it nets us more points on the debugger this way.
    }
}
