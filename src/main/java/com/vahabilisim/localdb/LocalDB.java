package com.vahabilisim.localdb;

public interface LocalDB {

    public LocalDBTrans startReadableTrans() throws LocalDBException;

    public LocalDBTrans startWritableTrans() throws LocalDBException;

    public void onCreate(LocalDBTrans trans) throws LocalDBException;

    public void onUpgrade(LocalDBTrans trans, int oldVersion, int newVersion) throws LocalDBException;

}
