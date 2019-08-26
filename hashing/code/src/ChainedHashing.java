import java.util.*;

class HashNode {
	int key;
	int value;
	HashNode next;

	public HashNode(int key, int value) {
		this.key = key;
		this.value = value;
		this.next = null;
	}
}

class ChainedHashMap {
	private final static int TABLE_SIZE = 13;
	private int size;
	private HashNode[] table;

	public ChainedHashMap() {
		size = 0;
		table = new HashNode[TABLE_SIZE];
		for (int i = 0; i < TABLE_SIZE; i++)
			table[i] = null;
	}

	public int size() {
		return size;
	}

	public boolean isEmpty() {
		return (size == 0);
	}

	public void clear() {
		for (int i = 0; i < TABLE_SIZE; i++)
			table[i] = null;
	}

	public int get(int key) {
		int hash = getPosition(key);
		if (table[hash] == null) {
			System.out.println("Key does not exist");
			return -1;
		} else {
			HashNode entry = table[hash];
			while (entry != null && entry.key != key)
				entry = entry.next;
			if (entry == null) {
				System.out.println("Key does not exist");
				return -1;
			} else
				return entry.value;
		}
	}

	public void put(int key, int value) {
		int hash = getPosition(key);
		if (table[hash] == null) {
			table[hash] = new HashNode(key, value);
			size++;
		} else {
			HashNode entry = table[hash];
			while (entry.next != null && entry.key != key)
				entry = entry.next;
			if (entry.key == key)
				entry.value = value;
			else {
				entry.next = new HashNode(key, value);
				size++;
			}
		}
	}

	public void remove(int key) {
		int hash = getPosition(key);
		if (table[hash] == null) {
			System.out.println("Key does not exist");
		} else {
			HashNode prevEntry = null;
			HashNode entry = table[hash];
			while (entry.next != null && entry.key != key) {
				prevEntry = entry;
				entry = entry.next;
			}
			if (entry.key == key) {
				if (prevEntry == null)
					table[hash] = entry.next;
				else
					prevEntry.next = entry.next;
				size--;
			} else {
				System.out.println("Key does not exist");
			}
		}
	}

	private int getPosition(int key) {
		return (key % TABLE_SIZE);
	}

	/* Function to print hash table */
	public void printTable() {
		for (int i = 0; i < TABLE_SIZE; i++) {
			System.out.print("Bucket " + i + " : ");
			HashNode entry = table[i];
			while (entry != null) {
				System.out.print(entry.key + " ");
				entry = entry.next;
			}
			System.out.println();
		}
	}
}

public class ChainedHashing {
	public static void main(String[] args) {
		ChainedHashMap map = new ChainedHashMap();
		System.out.println("IsEmpty: " + map.isEmpty());
//		Random rand = new Random();
//		long start = System.nanoTime();
//		for (int i = 0; i < 1000; i++) {
//			map.put(rand.nextInt(1000), i + 1);
//		}
//		long end = System.nanoTime();
//		long diff = end - start;
//		System.out.println("Time: " + diff + "ns");
		map.put(20, 1);
		map.put(34, 2);
		map.put(52, 3);
		map.put(70, 4);
		map.put(90, 5);
		map.put(112, 6);
		map.put(24, 7);
		map.put(40, 8);
		map.put(100, 9);
		map.put(63, 10);
		map.put(81, 11);
		System.out.println("After insertion:");
		map.printTable();
		map.get(24);
		map.get(52);
		map.get(40);
		map.get(112);
		map.get(100);
		map.get(63);
		map.get(90);
		map.get(34);
		long start = System.nanoTime();
		map.remove(100);
		map.remove(70);
		map.remove(52);
		map.remove(81);
		map.remove(63);
		map.remove(34);
		map.remove(24);
		map.remove(20);
		long end = System.nanoTime();
		map.printTable();
		System.out.println("Time taken: " + (end - start) + "ns");
		System.out.println("Size: " + map.size());
		System.out.println("IsEmpty: " + map.isEmpty());
	}
}