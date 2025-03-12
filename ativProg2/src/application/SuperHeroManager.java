package application;

//SuperHeroManager.java
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
}


