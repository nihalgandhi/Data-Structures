import java.util.*;

class DoubleHashEntry {
	public int key;
	public int value;
	public boolean isDeleted;

	public DoubleHashEntry(int key, int value) {
		this.key = key;
		this.value = value;
		isDeleted = false;
	}
}

class DoubleHashMap {
	private final static int TABLE_SIZE = 17;
	private final static int PRIME_NUM = 11;
	private int size = 0;

	DoubleHashEntry[] table;

	public DoubleHashMap() {
		table = new DoubleHashEntry[TABLE_SIZE];
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
		int hash2 = getPosition2(key);
		int count = 0;
		while (table[hash] != null && table[hash].key != key && count < TABLE_SIZE) {
			hash = (hash + hash2) % TABLE_SIZE;
			count++;
		}
		if (table[hash] == null || count == TABLE_SIZE || table[hash].isDeleted) {
			System.out.println("Key does not exist");
			return -1;
		} else
			return table[hash].value;
	}

	public void put(int key, int value) {
		if (size == TABLE_SIZE) {
			System.out.println("Table Full!");
			return;
		}
		int hash = getPosition(key);
		int hash2 = getPosition2(key);
		while (table[hash] != null && table[hash].key != key && !table[hash].isDeleted) {
			hash = (hash + hash2) % TABLE_SIZE;
		}
		table[hash] = new DoubleHashEntry(key, value);
		size++;
	}

	public void remove(int key) {
		int count = 0;
		int hash = getPosition(key);
		int hash2 = getPosition2(key);
		while (table[hash] == null || (table[hash].key != key && count < TABLE_SIZE)) {
			hash = (hash + hash2) % TABLE_SIZE;
			count++;
		}
		if (count == TABLE_SIZE) {
			System.out.println("Key does not exist");
		} else {
			table[hash].isDeleted = true;
			size--;
		}
	}

	public void printTable() {
		for (int i = 0; i < TABLE_SIZE; i++) {
			System.out.print("Bucket" + " " + i + " : ");
			if (table[i] != null && !table[i].isDeleted)
				System.out.print(table[i].key);
			System.out.println();
		}
	}

	private int getPosition(int key) {
		return (key % TABLE_SIZE);
	}

	private int getPosition2(int key) {
		return PRIME_NUM - (key % PRIME_NUM);
	}
}

public class DoubleHashing {
	public static void main(String[] args) {
		DoubleHashMap map = new DoubleHashMap();
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
		System.out.println("Time for inserts: " + (end - start) + "ns");
		System.out.println("Size: " + map.size());
		System.out.println("IsEmpty: " + map.isEmpty());
	}
}