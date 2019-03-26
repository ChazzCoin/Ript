package com.ript.wallet.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SubscribedAccounts extends RealmObject {

    /**
     * {
     *   "id": 1,
     *   "status": "success",
     *   "type": "response",
     *   "result": {
     *     "fee_base": 10,
     *     "fee_ref": 10,
     *     "hostid": "ROWE",
     *     "ledger_hash": "D6806EFCF6BE0CE0395EE4E2E54551779C83090E2E86BA3E7E47CD2D67F1CF3A",
     *     "ledger_index": 46073108,
     *     "ledger_time": 606882111,
     *     "load_base": 256,
     *     "load_factor": 256,
     *     "pubkey_node": "n9LbkoB9ReSbaA9SGL317fm6CvjLcFG8hGoierLYfwiCDsEXHcP3",
     *     "random": "E4A77B7F5A61923A5C2B0C08371E725B5C28708AEAD839A99CC07B4397799217",
     *     "reserve_base": 20000000,
     *     "reserve_inc": 5000000,
     *     "server_status": "full",
     *     "validated_ledgers": "32570-46073108"
     *   }
     * }
     */

    @PrimaryKey
    @SerializedName("fee_base")
    private String fee_base;
    @SerializedName("fee_ref")
    private String fee_ref;
    @SerializedName("hostid")
    private String hostid;
    @SerializedName("ledger_hash")
    private String ledger_hash;
    @SerializedName("ledger_index")
    private String ledger_index;
    @SerializedName("ledger_time")
    private String ledger_time;
    @SerializedName("load_base")
    private String load_base;
    @SerializedName("load_factor")
    private String load_factor;
    @SerializedName("pubkey_node")
    private String pubkey_node;
    @SerializedName("random")
    private String random;
    @SerializedName("reserve_base")
    private String reserve_base;
    @SerializedName("reserve_inc")
    private String reserve_inc;
    @SerializedName("server_status")
    private String server_status;
    @SerializedName("validated_ledgers")
    private String validated_ledgers;

    public String getFee_base() {
        return fee_base;
    }

    public void setFee_base(String fee_base) {
        this.fee_base = fee_base;
    }

    public String getFee_ref() {
        return fee_ref;
    }

    public void setFee_ref(String fee_ref) {
        this.fee_ref = fee_ref;
    }

    public String getHostid() {
        return hostid;
    }

    public void setHostid(String hostid) {
        this.hostid = hostid;
    }

    public String getLedger_hash() {
        return ledger_hash;
    }

    public void setLedger_hash(String ledger_hash) {
        this.ledger_hash = ledger_hash;
    }

    public String getLedger_index() {
        return ledger_index;
    }

    public void setLedger_index(String ledger_index) {
        this.ledger_index = ledger_index;
    }

    public String getLedger_time() {
        return ledger_time;
    }

    public void setLedger_time(String ledger_time) {
        this.ledger_time = ledger_time;
    }

    public String getLoad_base() {
        return load_base;
    }

    public void setLoad_base(String load_base) {
        this.load_base = load_base;
    }

    public String getLoad_factor() {
        return load_factor;
    }

    public void setLoad_factor(String load_factor) {
        this.load_factor = load_factor;
    }

    public String getPubkey_node() {
        return pubkey_node;
    }

    public void setPubkey_node(String pubkey_node) {
        this.pubkey_node = pubkey_node;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getReserve_base() {
        return reserve_base;
    }

    public void setReserve_base(String reserve_base) {
        this.reserve_base = reserve_base;
    }

    public String getReserve_inc() {
        return reserve_inc;
    }

    public void setReserve_inc(String reserve_inc) {
        this.reserve_inc = reserve_inc;
    }

    public String getServer_status() {
        return server_status;
    }

    public void setServer_status(String server_status) {
        this.server_status = server_status;
    }

    public String getValidated_ledgers() {
        return validated_ledgers;
    }

    public void setValidated_ledgers(String validated_ledgers) {
        this.validated_ledgers = validated_ledgers;
    }
}
