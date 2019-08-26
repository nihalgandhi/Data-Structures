import java.util.*;

class LinearHashEntry {
	public int key;
	public int value;

	public LinearHashEntry(int key, int value) {
		this.key = key;
		this.value = value;
	}
}

class LinearHashMap {
	private final static int TABLE_SIZE = 509;
	private int size;

	LinearHashEntry[] table;

	public LinearHashMap() {
		size = 0;
		table = new LinearHashEntry[TABLE_SIZE];
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
		int count = 0;
		while (table[hash] != null && table[hash].key != key && count < TABLE_SIZE) {
			hash = (hash + 1) % TABLE_SIZE;
			count++;
		}
		if (table[hash] == null || count == TABLE_SIZE) {
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
		while (table[hash] != null && table[hash].key != key)
			hash = (hash + 1) % TABLE_SIZE;
		if (table[hash] == null)
			size++;
		table[hash] = new LinearHashEntry(key, value);
	}

	public void remove(int key) {
		int count = 0;
		int hash = getPosition(key);
		while (table[hash] != null && table[hash].key != key && count < TABLE_SIZE) {
			hash = (hash + 1) % TABLE_SIZE;
			count++;
		}
		if (table[hash] == null || count == TABLE_SIZE) {
			System.out.println("Key does not exist");
			return;
		}
		int hashNext = (hash + 1) % TABLE_SIZE;
		count++;
		while (table[hashNext] != null && count < TABLE_SIZE) {
			int hashNextIndex = table[hashNext].key % TABLE_SIZE;
			if (hashNextIndex <= hash || hashNextIndex > hashNext) {
				table[hash] = table[hashNext];
				hash = hashNext;
			}
			hashNext = (hashNext + 1) % TABLE_SIZE;
			count++;
		}
		table[hash] = null;
		size--;
	}

	public void printTable() {
		for (int i = 0; i < TABLE_SIZE; i++) {
			System.out.print("Bucket" + " " + i + " : ");
			if (table[i] != null)
				System.out.print(table[i].key);
			System.out.println();
		}
	}

	private int getPosition(int key) {
		return (key % TABLE_SIZE);
	}
}

public class LinearHashing {
	public static void main(String[] args) {
		LinearHashMap map = new LinearHashMap();
		System.out.println("IsEmpty: " + map.isEmpty());
		Random rand = new Random();
		long start = System.nanoTime();
		for (int i = 0; i < 500; i++) {
			int key = rand.nextInt(5000);
			map.put(key, i + 1);
		}
		long end = System.nanoTime();
		long diff = end - start;
		System.out.println("Time: " + diff + "ns");
		long start2 = System.nanoTime();
		for (int i = 0; i < 300; i++) {
			int key = rand.nextInt(5000);
			map.get(key);
			//System.out.println("map.delete(" + key + ")");
		}
		long end2 = System.nanoTime();
		System.out.println("Time: " + (end2-start2) + "ns");
//		long start = System.nanoTime();
//		map.put(20, 1);
//		map.put(34, 2);
//		map.put(52, 3);
//		map.put(70, 4);
//		map.put(90, 5);
//		map.put(112, 6);
//		map.put(24, 7);
//		map.put(40, 8);
//		map.put(100, 9);
//		map.put(63, 10);
//		map.put(81, 11);
		System.out.println("After insertion:");
//		map.printTable();
//		map.get(24);
//		map.get(52);
//		map.get(40);
//		map.get(112);
//		map.get(100);
//		map.get(63);
//		map.get(90);
//		map.get(34);
//		map.remove(100);
//		map.remove(70);
//		map.remove(52);
//		map.remove(81);
//		map.remove(63);
//		map.remove(34);
//		map.remove(24);
//		map.remove(20);
//		map.printTable();
//		System.out.println("Time taken: " + (end - start) + "ns");
		System.out.println("Size: " + map.size());
		System.out.println("IsEmpty: " + map.isEmpty());
	}
}