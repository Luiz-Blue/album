package application;

//Main.java
import javax.swing.SwingUtilities;

import application.SuperHeroManager;
import application.SuperHeroView;

public class Main {
public static void main(String[] args) {
   SwingUtilities.invokeLater(() -> {
       SuperHeroView view = new SuperHeroView();
       SuperHeroManager model = new SuperHeroManager();
       new SuperHeroController(view, model);
   });
}
}
