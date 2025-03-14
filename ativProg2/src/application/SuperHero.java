package application;

public class SuperHero extends GoodEvil {
    private String powers;
    private String group;
    private String skills;
    private String imagePath;
    private String videoPath;

    public SuperHero(String name, String description, String powers, String group, String skills, String imagePath, String videoPath) {
        super(name, description);
        this.powers = powers;
        this.group = group;
        this.skills = skills;
        this.imagePath = imagePath;
        this.videoPath = videoPath;
    }

    // Getters e Setters
    public String getPowers() {
        return powers;
    }

    public void setPowers(String powers) {
        this.powers = powers;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    @Override
    public String toString() {
        return getName(); // Exibir apenas o nome na lista
    }
}