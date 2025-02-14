package model;

public class History {

    private String date;

    private String price;



    public History(String date, String price ) {
        this.date = date;
        this.price = price;

    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDate() {return date;}

    public void setDate(String date) {
        this.date = date;
    }
}
