package uk.ac.tees.aad.studentNumber;

public class Hotel {
    private String name;
    private String address;
    private String rating;
    private String pricePerNight;
    private int imageResource;

    public Hotel(String name, String address, String rating, String pricePerNight, int imageResource) {
        this.name = name;
        this.address = address;
        this.rating = rating;
        this.pricePerNight = pricePerNight;
        this.imageResource = imageResource;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getRating() {
        return rating;
    }

    public String getPricePerNight() {
        return pricePerNight;
    }

    public int getImageResource() {
        return imageResource;
    }
}
