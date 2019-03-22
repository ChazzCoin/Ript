package com.ript.wallet.models;

import io.realm.RealmObject;

public class RootAccount extends RealmObject {

    /**
     * "id": 2,
     *   "status": "success",
     *   "type": "response",
     *   "result": {
     *     "account_data": {
     *       "Account": "r9cZA1mLK5R5Am25ArfXFmqgNwjZgnfk59",
     *       "Balance": "13314753787",
     *       "Flags": 0,
     *       "LedgerEntryType": "AccountRoot",
     *       "OwnerCount": 17,
     *       "PreviousTxnID": "5A18ACA848D7786E2AC27A2FFB54483F2B6367B4375A4AE00D6DCC60B668D0BD",
     *       "PreviousTxnLgrSeq": 42135765,
     *       "Sequence": 1406,
     *       "index": "4F83A2CF7E70F77F79A307E6A472BFC2585B806A70833CCD1C26105BAE0D6E05"
     *     },
     *     "ledger_current_index": 45980964,
     *     "validated": false
     */

    private String account;
    private String balance;
    private String flags;
    private String ledgerEntryType;
    private String index;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getFlags() {
        return flags;
    }

    public void setFlags(String flags) {
        this.flags = flags;
    }

    public String getLedgerEntryType() {
        return ledgerEntryType;
    }

    public void setLedgerEntryType(String ledgerEntryType) {
        this.ledgerEntryType = ledgerEntryType;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
