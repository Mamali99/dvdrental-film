package utils;

public class FilmsHref {
    private String href;


    /*
    @JsonCreator
    public FilmsHref(@JsonProperty("href") String href) {
        this.href = href;
    }


     */

    public FilmsHref() {
    }

    public FilmsHref(String href) {
        this.href = href;
    }



    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }
}
