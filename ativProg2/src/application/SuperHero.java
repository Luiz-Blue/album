package application;

// SuperHero.java
import java.util.List;

public class SuperHero {
    private String name;
    private String description;
    private String powers;
    private String group;
    private String skills;
    private String imagePath;
    private String videoPath;

    public SuperHero(String name, String description, String powers, String group, String skills, String imagePath, String videoPath) {
        this.name = name;
        this.description = description;
        this.powers = powers;
        this.group = group;
        this.skills = skills;
        this.imagePath = imagePath;
        this.videoPath = videoPath;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getPowers() { return powers; }
    public String getGroup() { return group; }
    public String getSkills() { return skills; }
    public String getImagePath() { return imagePath; }
    public String getVideoPath() { return videoPath; }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }
    
    @Override
    public String toString() {
        return name; // Exibir apenas o nome na lista
    }
}
