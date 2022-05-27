import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];

    void clear() {
        Arrays.fill(storage, null);
    }

    void save(Resume r) {
        if (storage[0] == null) storage[0] = r;
        else {
            System.arraycopy(storage, 0, storage, 1, 9999);
            storage[0] = r;
        }
    }

    String get(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null && storage[i].uuid.equals(uuid)) {
                return storage[i].uuid;
            }
        }
        return null;
    }

    void delete(String uuid) {
        int index = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i].uuid.equals(uuid)) {
                index = i;
                break;
            }
        }
        System.arraycopy(storage, index + 1, storage, index, storage.length - index - 1);
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        int counter = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) counter++;
        }
        Resume[] newStorage = new Resume[counter];
        System.arraycopy(storage, 0, newStorage, 0, counter);
        return newStorage;
    }

    int size() {
        int counter = 0;
        for (int i = 0; i < storage.length; i++) {
            if (storage[i] != null) counter++;
        }
        return counter;
    }
}
