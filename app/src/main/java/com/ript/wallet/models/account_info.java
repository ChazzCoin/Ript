package com.ript.wallet.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class account_info extends RealmObject {

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

    @PrimaryKey
    @SerializedName("Account")
    private String Account;
    @SerializedName("Balance")
    private String Balance;
    @SerializedName("Flags")
    private String Flags;
    @SerializedName("LedgerEntryType")
    private String LedgerEntryType;
    @SerializedName("index")
    private String index;
    @SerializedName("OwnerCount")
    private String OwnerCount;
    @SerializedName("PreviousTxnID")
    private String PreviousTxnID;
    @SerializedName("PreviousTxnLgrSeq")
    private String PreviousTxnLgrSeq;
    @SerializedName("Sequence")
    private String Sequence;

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getBalance() {
        return Balance;
    }

    public void setBalance(String balance) {
        Balance = balance;
    }

    public String getFlags() {
        return Flags;
    }

    public void setFlags(String flags) {
        Flags = flags;
    }

    public String getLedgerEntryType() {
        return LedgerEntryType;
    }

    public void setLedgerEntryType(String ledgerEntryType) {
        LedgerEntryType = ledgerEntryType;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getOwnerCount() {
        return OwnerCount;
    }

    public void setOwnerCount(String ownerCount) {
        OwnerCount = ownerCount;
    }

    public String getPreviousTxnID() {
        return PreviousTxnID;
    }

    public void setPreviousTxnID(String previousTxnID) {
        PreviousTxnID = previousTxnID;
    }

    public String getPreviousTxnLgrSeq() {
        return PreviousTxnLgrSeq;
    }

    public void setPreviousTxnLgrSeq(String previousTxnLgrSeq) {
        PreviousTxnLgrSeq = previousTxnLgrSeq;
    }

    public String getSequence() {
        return Sequence;
    }

    public void setSequence(String sequence) {
        Sequence = sequence;
    }
}
