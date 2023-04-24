package nus.iss.Neko.Server.models;

public class CatDetailsResponse {
    private Cat cat;
    private boolean isSaved;

    public Cat getCat() {
        return cat;
    }
    public void setCat(Cat cat) {
        this.cat = cat;
    }
    public boolean isSaved() {
        return isSaved;
    }
    public void setSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }
}
