package application;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SuperHeroManager {
    private List<SuperHero> superHeroes;

    public SuperHeroManager() {
        superHeroes = new ArrayList<>();
    }

    public void addSuperHero(SuperHero hero) {
        superHeroes.add(hero);
    }

    public void updateSuperHero(int index, SuperHero hero) {
        superHeroes.set(index, hero);
    }

    public void deleteSuperHero(int index) {
        superHeroes.remove(index);
    }

    public List<SuperHero> getSuperHeroes() {
        return superHeroes;
    }

    // Método para salvar os super-heróis em um arquivo
    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (SuperHero hero : superHeroes) {
                writer.write(hero.getName() + "," + hero.getDescription() + "," + hero.getPowers() + "," + hero.getGroup() + "," + hero.getSkills() + "," + hero.getImagePath() + "," + hero.getVideoPath());
                writer.newLine();
            }
        }
    }

    // Método para carregar os super-heróis de um arquivo
    public void loadFromFile(String filename) throws IOException {
        superHeroes.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                SuperHero hero = new SuperHero(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], parts[6]);
                superHeroes.add(hero);
            }
        }
    }
}