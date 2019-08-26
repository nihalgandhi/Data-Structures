import java.util.*;

class CuckooHashEntry {
    public int key;
    public int value;

    public CuckooHashEntry(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

class CuckooHashMap {
    private final static int TABLE_SIZE = 17;
    private int size = 0;

    CuckooHashEntry[][] table;

    public CuckooHashMap() {
        table = new CuckooHashEntry[2][TABLE_SIZE];
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < TABLE_SIZE; j++)
                table[i][j] = null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return (size == 0);
    }

    public void clear() {
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < TABLE_SIZE; j++)
                table[i][j] = null;
    }

    public int get(int key) {
        int hashFirst = getPosition(key, 0);
        int hashSecond = getPosition(key, 1);
        if (table[0][hashFirst] != null && table[0][hashFirst].key == key) {
            return table[0][hashFirst].value;
        } else if (table[1][hashSecond] != null && table[1][hashSecond].key == key) {
            return table[1][hashSecond].value;
        } else {
            System.out.println("Key does not exist");
            return -1;
        }
    }

    public void put(int key, int value) {
        if (size == 2*TABLE_SIZE) {
            System.out.println("Table Full!");
            return;
        }
        int hashFirst = getPosition(key, 0);
        int hashSecond = getPosition(key, 1);
        if((table[0][hashFirst] != null && table[0][hashFirst].key == key)) {
            table[0][hashFirst].value = value;
            return;
        }
        else if((table[1][hashSecond] != null && table[1][hashSecond].key == key)) {
            table[1][hashSecond].value = value;
            return;
        }
        int count = 0; 
        if(table[0][hashFirst] != null && table[0][hashFirst].key != key) {
            if(putRec(key, value, count, key, 0))
                size++;
        }
        else {
            table[0][hashFirst] = new CuckooHashEntry(key, value);
            size++;
        }
    }
    private boolean putRec(int key, int value, int count, int originalKey, int table_id) {
        if(key == originalKey)
            count++;
        if(count == 3) {
            System.out.println("Infinite Loop");
            return false;
        }
        int hash = getPosition(key, table_id);
        CuckooHashEntry temp = table[table_id][hash];
        table[table_id][hash] = new CuckooHashEntry(key, value);
        if(temp != null) {
            return putRec(temp.key, temp.value, count, originalKey, 1 - table_id);
        }
        return true;
    }

    public void remove(int key) {
        int hashFirst = getPosition(key, 0);
        int hashSecond = getPosition(key, 1);
        if (table[0][hashFirst] != null && table[0][hashFirst].key == key) {
            table[0][hashFirst] = null;
            size--;
        } else if (table[1][hashSecond] != null && table[1][hashSecond].key == key) {
            table[1][hashSecond] = null;
            size--;
        } else {
            System.out.println("Key does not exist");
        }
    }

    public void printTable() {
        for(int i = 0; i < 2; i++) {
            for (int j = 0; j < TABLE_SIZE; j++) {
                if (table[i][j] != null)
                    System.out.print(table[i][j].key + " ");
                else
                    System.out.print("- ");
            }
            System.out.println();
        }
    }

    private int getPosition(int key, int id) {
        return (id == 0) ? (key % TABLE_SIZE) : ((key / TABLE_SIZE) % TABLE_SIZE);
    }
}

public class CuckooHashing {
    public static void main(String[] args) {
        CuckooHashMap map = new CuckooHashMap();
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
        System.out.println("Get: " + map.get(85));
    }
}