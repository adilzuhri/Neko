package nus.iss.Neko.Server.models;

import java.io.Serializable;

public class Cat implements Serializable {

    private String cat_id;
    private String storedUUID;
    private String label;
    private String image;
    private String link;

    public String getCat_id() {
        return cat_id;
    }
    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }
    public String getStoredUUID() {
        return storedUUID;
    }
    public void setStoredUUID(String storedUUID) {
        this.storedUUID = storedUUID;
    }
    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public String getLink() {
        return link;
    }
    public void setLink(String link) {
        this.link = link;
    }
    
}
