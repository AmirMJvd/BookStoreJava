package model;

public class Report {
    private String name;
    private String imgSrc;
    private String price;
    private String date;
    private String count;
    private String profit;
    public String getName() {return name;}

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
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

    public String getCount() {return count;}

    public void setCount(String count) {
        this.count = count;
    }

    public String getProfit() {return profit;}

    public void setProfit(String profit) {
        this.profit = profit;
    }

}
