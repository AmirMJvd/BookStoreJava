package model;

public class Book {
    private String name;
    private String imgSrc;
    private String price;
    private String color;
    private String writer;
    private String translator;
    private String category;
    private String dragoman;
    private String nasher;
    private String count;

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getTranslator() {
        return translator;
    }

    public void setTranslator(String translator) {
        this.translator = translator;
    }

    public String getNasher() {
        return nasher;
    }

    public void setNasher(String nasher) {
        this.nasher = nasher;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getCategory() {return category;}

    public void setCategory(String category) {this.category = category;}


/*    public String getPublisher() {
        return Publisher;
    }

    public void setPublisher(String publisher) {
        Publisher = publisher;
    }

    public String getDragoman() {
        return dragoman;
    }

    public void setDragoman(String dragoman) {
        this.dragoman = dragoman;
    }
*/
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}