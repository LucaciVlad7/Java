package Domain;

public class MenuItem {
    private int id_item;
    private String category;
    private String item;
    private Float price;
    private String currency;

    public MenuItem(int id_item, String category, String item, Float price, String currency) {
        this.id_item = id_item;
        this.category = category;
        this.item = item;
        this.price = price;
        this.currency = currency;
    }

    public int getId_item() {
        return id_item;
    }

    public String getCategory() {
        return category;
    }

    public String getItem() {
        return item;
    }

    public Float getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }
}
