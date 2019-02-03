package com.vahabilisim.localdb;

public interface LocalDBCore {

    public LocalDBTrans startReadableTrans() throws LocalDBException;

    public LocalDBTrans startWritableTrans() throws LocalDBException;

    public void onCreate(LocalDBTrans trans);

    public void onUpgrade(LocalDBTrans trans, int oldVersion, int newVersion);

}
