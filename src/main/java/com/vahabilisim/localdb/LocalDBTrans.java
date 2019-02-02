package com.vahabilisim.localdb;

import java.util.Map;

public interface LocalDBTrans {

    public boolean success();

    public void commit();

    public void rollback();

    public void execSQL(String sql);

    public void execSQL(String sql, Object[] bindArgs);

    public void insert(String table, String nullColumnHack, Map<String, Object> values);

    public int delete(String table, String whereClause, String[] whereArgs);

    public int update(String table, Map<String, Object> values, String whereClause, String[] whereArgs);

    public LocalDBCursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy, String limit);
}
