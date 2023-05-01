package ru.javaops.webapp.storage;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractArrayStorageTest {

    private Storage storage;
    private static final String UUID_1 = "uuid3";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid1";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid2";
    private static final Resume RESUME_3 = new Resume(UUID_3);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }


    @Test
    void size() {
        assertEquals(3, storage.size());
    }

    @Test
    void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void save() {
        storage.save(new Resume("new_Resume"));
        assertEquals(4, storage.size());
        assertNotEquals("new_resume", storage.get("new_Resume").getUuid());
    }

    @Test
    void update() {
        Resume newResume = new Resume(UUID_1);
        storage.update(newResume);
        assertSame(newResume, storage.get(UUID_1));
    }

    @Test
    void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    void saveOverflow() {
       try {
           for (int i = 3; i < AbstractArrayStorage.STORAGE_LIMIT-1; i++) {
               storage.save(new Resume());
           }
       } catch (StorageException ex) {
           Assertions.fail();
       }
        storage.save(new Resume());
    }

    @Test
    void saveOverflowExceptions() {
        assertThrows(StorageException.class, () -> {
                for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                    storage.save(new Resume());
                }
        } );

    }

    @Test
    void saveExist() {
        assertThrows(ExistStorageException.class, () -> storage.save(RESUME_3));

    }

    @Test
    void getNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void deleteNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void updateNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }

    @Test
    void delete() {
        storage.delete(UUID_2);
        assertEquals(2, storage.size());
    }

    @Test
    void getAll() {
        Resume[] array = storage.getAll();
        assertEquals(3, array.length);
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }
}