package nus.iss.Neko.Server.models;

import java.io.Serializable;
import java.util.List;

public class CatListResponse implements Serializable {
    private List<Cat> cats;
    private String nextURL;

    public List<Cat> getCats() {
        return cats;
    }
    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }
    public String getNextURL() {
        return nextURL;
    }
    public void setNextURL(String nextURL) {
        this.nextURL = nextURL;
    }    
}
