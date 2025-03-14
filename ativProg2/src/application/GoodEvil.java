package application;

public abstract class GoodEvil {
    private String name;
    private String description;

    public GoodEvil(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public abstract void usePower(); // Método abstrato

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
}