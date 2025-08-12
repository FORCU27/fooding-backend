import java.util.List;

public class Store {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPriceCategory() {
        return priceCategory;
    }

    public void setPriceCategory(String priceCategory) {
        this.priceCategory = priceCategory;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDirection() { return direction; }
    public void setDirection(String direction) { this.direction = direction; }

    public List<StoreMenu> getMenuList() { return menuList; }
    public void setStoreMenu(List<StoreMenu> menuList) { this.menuList = menuList; }

    public Store(String name, String description, String address, String priceCategory, String information, String region, String category, String direction, List<StoreMenu> menuList) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.priceCategory = priceCategory;
        this.information = information;
        this.region = region;
        this.category = category;
        this.direction = direction;
        this.menuList = menuList;
    }

    private String name;
    private String description;
    private String address;
    private String priceCategory;
    private String information;
    private String region;
    private String category;
    private String direction;

    private List<StoreMenu> menuList;

}
