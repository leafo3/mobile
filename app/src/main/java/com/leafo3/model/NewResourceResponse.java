package com.leafo3.model;

/**
 * Created by Alberto Rubalcaba on 4/18/2015.
 */
public class NewResourceResponse {
    private String id;
    private boolean success;
    private int damageClass;

    public NewResourceResponse(String id, boolean success, int damageClass) {
        this.id = id;
        this.success = success;
        this.damageClass = damageClass;
    }

    public NewResourceResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getDamageClass() {
        return damageClass;
    }

    public void setDamage(int damage) {
        this.damageClass = damage;
    }
}
