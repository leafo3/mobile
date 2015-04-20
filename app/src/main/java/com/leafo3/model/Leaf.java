package com.leafo3.model;

/**
 * Created by Alberto Rubalcaba on 4/18/2015.
 */
public class Leaf {
    private int id;
    private String name;
    private int damageClass;
    private String imageUrl;
    private String location;
    private String damagedImageUrl;
    private String title;
    private String comment;

    public Leaf(int id, String name, int damageClass, String imageUrl, String location, String damageImageUrl, String title, String comment) {
        this.id = id;
        this.name = name;
        this.damageClass = damageClass;
        this.imageUrl = imageUrl;
        this.location = location;
        this.damagedImageUrl = damageImageUrl;
        this.title = title;
        this.comment = comment;
    }

    public Leaf() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDamageClass() {
        return damageClass;
    }

    public void setDamageClass(int damageClass) {
        this.damageClass = damageClass;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDamagedImageUrl() {
        return damagedImageUrl;
    }

    public void setDamagedImageUrl(String damagedImageUrl) {
        this.damagedImageUrl = damagedImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
