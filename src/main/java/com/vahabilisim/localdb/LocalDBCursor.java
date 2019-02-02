package com.vahabilisim.localdb;

public interface LocalDBCursor {

    public boolean moveToFirst();

    public boolean moveToNext();

    public byte[] getBlob(int columnIndex);

    public String getString(int columnIndex);

    public short getShort(int columnIndex);

    public int getInt(int columnIndex);

    public long getLong(int columnIndex);

    public float getFloat(int columnIndex);

    public double getDouble(int columnIndex);
}
