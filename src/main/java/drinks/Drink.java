package drinks;

public class Drink {
    private String type;
    private String brand;
    private int percentage;
    private int age;
    private String origin;
    private String manufacturer;

    public Drink() {
    }

    public Drink(String type, String brand, int percentage, int age, String origin, String manufacturer) {
        this.type = type;
        this.brand = brand;
        this.percentage = percentage;
        this.age = age;
        this.origin = origin;
        this.manufacturer = manufacturer;
    }

    public String getBrand() {
        return brand;
    }

    public String getType() {
        return type;
    }

    public int getPercentage() {
        return percentage;
    }

    public int getAge() {
        return age;
    }

    public String getOrigin() {
        return origin;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        String drink = "[" + type + " " + brand + " " + percentage + " " + age + " " + origin + " " +
                manufacturer + "]";
        return drink;
    }
}
