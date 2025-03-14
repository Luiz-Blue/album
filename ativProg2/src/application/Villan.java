package application;

public class Villan extends SuperHero {
    public Villan(String name, String description, String powers, String group, String skills, String imagePath, String videoPath) {
        super(name, description, powers, group, skills, imagePath, videoPath);
    }

    @Override
    public String toString() {
        return "Vilão: " + getName();
    }
}