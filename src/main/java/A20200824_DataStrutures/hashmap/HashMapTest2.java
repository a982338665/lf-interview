package A20200824_DataStrutures.hashmap;

/**
 * create by lishengbo on 2018-07-05 14:43
 */
public class HashMapTest2 {

   /* public V put(K key, V value) {
        if (table == EMPTY_TABLE) {
            inflateTable(threshold);
        }
        if (key == null)
            return putForNullKey(value);
        int hash = hash(key);
        int i = indexFor(hash, table.length);
        for (HashMap.Entry<K,V> e = table[i]; e != null; e = e.next) {
            Object k;
            if (e.hash == hash && ((k = e.key) == key || key.equals(k))) {
                V oldValue = e.value;
                e.value = value;
                e.recordAccess(this);
                return oldValue;
            }
        }

        modCount++;
        addEntry(hash, key, value, i);
        return null;
    }*/

    public static void main(String[] args) {

        int hash=2885;
        int length=16;
        int i = hash & (length - 1);
        System.out.println("i-"+i);


    }
}
