# vaha-localdb

#### What is it?
An interface library for transactional local databases that can be used both on desktop platforms (such as Linux, MacOS and Windows) and on Android platform.

#### What is its benefit?
Without changing your business logic you can use same Java codes on the platforms supported by the specific implementations of this library.

#### Is there any implementation of it?
For now, there are two implementations of this library.
- An implementation dependent to [org.xerial.sqlite-jdbc](https://github.com/xerial/sqlite-jdbc) for desktop platforms can be found on [vaha-localdb-sqlite](https://github.com/vahithanoglu/vaha-localdb-sqlite).
- An implementation powered natively for Android platform can be found on [vaha-localdb-android](https://github.com/vahithanoglu/vaha-localdb-android).

#### Example.java (a basic business logic example independent of platforms)
```java
package com.vahabilisim.localdb.example;

import com.vahabilisim.localdb.LocalDBCore;
import com.vahabilisim.localdb.LocalDBCursor;
import com.vahabilisim.localdb.LocalDBException;
import com.vahabilisim.localdb.LocalDBTrans;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Example {

    public static class Car {

        public long id;
        public String vendor;
        public String model;
        public int year;

        public Car(long id, String vendor, String model, int year) {
            this.id = id;
            this.vendor = vendor;
            this.model = model;
            this.year = year;
        }
    }

    //private static final Logger LOGGER = Logger.getLogger(ExampleWrapper.class);
    private final LocalDBCore core;

    public Example(LocalDBCore core) {
        this.core = core;
    }

    public List<Car> queryAll() {
        final List<Car> retVal = new LinkedList<>();

        LocalDBTrans trans = null;
        try {
            trans = core.startReadableTrans();
            final LocalDBCursor cursor = trans.query("car",
                    new String[]{"id", "vendor", "model", "year"},
                    null, null, null, null, null, null);

            if (trans.success() && cursor.moveToFirst()) {
                do {
                    retVal.add(new Car(cursor.getLong(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getInt(3)));
                } while (cursor.moveToNext());
            }
        } catch (LocalDBException ex) {
            //LOGGER.error("Cannot query records", ex);

        } finally {
            Optional.ofNullable(trans)
                    .ifPresent(tx -> {
                        tx.commit();
                    });
        }
        return retVal;
    }

    public List<Car> query(String vendor) {
        final List<Car> retVal = new LinkedList<>();

        LocalDBTrans trans = null;
        try {
            trans = core.startReadableTrans();
            final LocalDBCursor cursor = trans.query("car",
                    new String[]{"id", "vendor", "model", "year"},
                    "vendor = ?", new String[]{vendor}, null, null, null, null);

            if (trans.success() && cursor.moveToFirst()) {
                do {
                    retVal.add(new Car(cursor.getLong(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getInt(3)));
                } while (cursor.moveToNext());
            }
        } catch (LocalDBException ex) {
            //LOGGER.error("Cannot query records", ex);

        } finally {
            Optional.ofNullable(trans)
                    .ifPresent(tx -> {
                        tx.commit();
                    });
        }
        return retVal;
    }

    public boolean insert(Car car) {
        final Map<String, Object> values = new HashMap<>();
        values.put("id", car.id);
        values.put("vendor", car.vendor);
        values.put("model", car.model);
        values.put("year", car.year);

        LocalDBTrans trans = null;

        try {
            trans = core.startWritableTrans();
            trans.insert("car", null, values);

        } catch (LocalDBException ex) {
            //LOGGER.error("Cannot insert record", ex);

        } finally {
            Optional.ofNullable(trans)
                    .ifPresent(tx -> {
                        tx.commit();
                    });
        }
        return Optional.ofNullable(trans)
                .map(tx -> tx.success())
                .orElse(false);
    }

    public boolean update(Car car) {
        final Map<String, Object> values = new HashMap<>();
        values.put("vendor", car.vendor);
        values.put("model", car.model);
        values.put("year", car.year);

        LocalDBTrans trans = null;
        try {
            trans = core.startWritableTrans();
            trans.update("car", values, "id = ?",
                    new String[]{String.valueOf(car.id)});

        } catch (LocalDBException ex) {
            //LOGGER.error("Cannot update record", ex);

        } finally {
            Optional.ofNullable(trans)
                    .ifPresent(tx -> {
                        tx.commit();
                    });
        }
        return Optional.ofNullable(trans)
                .map(tx -> tx.success())
                .orElse(false);
    }

    public boolean delete(Car car) {
        LocalDBTrans trans = null;
        try {
            trans = core.startWritableTrans();
            trans.delete("car", "id = ?",
                    new String[]{String.valueOf(car.id)});

        } catch (LocalDBException ex) {
            //LOGGER.error("Cannot delete record", ex);

        } finally {
            Optional.ofNullable(trans)
                    .ifPresent(tx -> {
                        tx.commit();
                    });
        }
        return Optional.ofNullable(trans)
                .map(tx -> tx.success())
                .orElse(false);
    }

    public boolean insert2(List<Car> cars) {
        LocalDBTrans trans = null;
        try {
            trans = core.startWritableTrans();
            final Map<String, Object> values = new HashMap<>();
            for (Car car : cars) {
                values.clear();
                values.put("id", car.id);
                values.put("vendor", car.vendor);
                values.put("model", car.model);
                values.put("year", car.year);
                trans.insert("car", null, values);
            }

        } catch (LocalDBException ex) {
            //LOGGER.error("Cannot insert records", ex);

        } finally {
            Optional.ofNullable(trans)
                    .ifPresent(tx -> {
                        tx.commit();
                    });
        }
        return Optional.ofNullable(trans)
                .map(tx -> tx.success())
                .orElse(false);
    }

    public int update2(int oldYear, int newYear) throws LocalDBException {
        final Map<String, Object> values = new HashMap<>();
        values.put("year", newYear);

        int updatedRecordCount = 0;
        LocalDBTrans trans = null;
        try {
            trans = core.startWritableTrans();
            updatedRecordCount = trans.update("car", values, "year = ?",
                    new String[]{String.valueOf(oldYear)});

        } finally {
            Optional.ofNullable(trans)
                    .ifPresent(tx -> {
                        tx.commit();
                    });
        }
        return updatedRecordCount;
    }

    public int delete2(String vendor, String model) throws LocalDBException {
        int deletedRecordCount = 0;
        LocalDBTrans trans = null;
        try {
            trans = core.startWritableTrans();
            deletedRecordCount = trans.delete("car", "vendor = ? AND model = ?",
                    new String[]{vendor, model});

        } finally {
            Optional.ofNullable(trans)
                    .ifPresent(tx -> {
                        tx.commit();
                    });
        }
        return deletedRecordCount;
    }
}

```

