package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

class SuperHeroController {
    private SuperHeroView view;
    private SuperHeroManager model;
    private int currentHeroIndex = -1;
    private MediaPlayer mediaPlayer;
    private JFXPanel jfxPanel;

    public SuperHeroController(SuperHeroView view, SuperHeroManager model) {
        this.view = view;
        this.model = model;

        // Inicializa o JFXPanel para reprodução de vídeo
        jfxPanel = new JFXPanel();
        view.setVideoPanel(jfxPanel);

        // Adiciona listeners aos botões
        this.view.addAddButtonListener(new AddHeroListener());
        this.view.addUpdateButtonListener(new UpdateHeroListener());
        this.view.addDeleteButtonListener(new DeleteHeroListener());
        this.view.addNextButtonListener(new NextHeroListener());
        this.view.addPreviousButtonListener(new PreviousHeroListener());
        this.view.addPlayButtonListener(new PlayVideoListener());
        this.view.addPauseButtonListener(new PauseVideoListener());
        this.view.addImageButtonListener(new AddImageListener());
        this.view.addVideoButtonListener(new AddVideoListener());
        this.view.addSaveButtonListener(new SaveButtonListener());
        this.view.addLoadButtonListener(new LoadButtonListener());

        updateHeroList();
    }

    class AddImageListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showOpenDialog(view.getFrame());
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (isImageFile(selectedFile)) {
                    if (currentHeroIndex != -1) {
                        SuperHero selectedHero = model.getSuperHeroes().get(currentHeroIndex);
                        selectedHero.setImagePath(selectedFile.getAbsolutePath());
                        view.setImagePath(selectedFile.getAbsolutePath());

                        // Exibe a imagem na interface
                        ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
                        view.setImage(icon);
                    }
                } else {
                    JOptionPane.showMessageDialog(view.getFrame(), "Por favor, selecione um arquivo de imagem válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private boolean isImageFile(File file) {
            String filename = file.getName().toLowerCase();
            return filename.endsWith(".jpg") || filename.endsWith(".jpeg") || filename.endsWith(".png") || filename.endsWith(".gif");
        }
    }

    class AddVideoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showOpenDialog(view.getFrame());
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                if (isVideoFile(selectedFile)) {
                    if (currentHeroIndex != -1) {
                        SuperHero selectedHero = model.getSuperHeroes().get(currentHeroIndex);
                        selectedHero.setVideoPath(selectedFile.getAbsolutePath());
                    }
                } else {
                    JOptionPane.showMessageDialog(view.getFrame(), "Por favor, selecione um arquivo de vídeo válido.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        private boolean isVideoFile(File file) {
            String filename = file.getName().toLowerCase();
            return filename.endsWith(".mp4") || filename.endsWith(".mkv") || filename.endsWith(".avi");
        }
    }

    class AddHeroListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String type = view.getSelectedType(); // Obtém o tipo selecionado (Herói ou Vilão)
            SuperHero hero;

            if (type.equals("Herói")) {
                hero = new Hero(
                    view.getName(),
                    view.getDescription(),
                    view.getPowers(),
                    view.getGroup(),
                    view.getSkills(),
                    "path/to/image",
                    "path/to/video"
                );
            } else {
                hero = new Villan(
                    view.getName(),
                    view.getDescription(),
                    view.getPowers(),
                    view.getGroup(),
                    view.getSkills(),
                    "path/to/image",
                    "path/to/video"
                );
            }

            model.addSuperHero(hero);
            updateHeroList();
        }
    }

    class UpdateHeroListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = view.getSelectedHeroIndex();
            if (selectedIndex != -1) {
                String type = view.getSelectedType(); // Obtém o tipo selecionado (Herói ou Vilão)
                SuperHero hero;

                if (type.equals("Herói")) {
                    hero = new Hero(
                        view.getName(),
                        view.getDescription(),
                        view.getPowers(),
                        view.getGroup(),
                        view.getSkills(),
                        "path/to/image",
                        "path/to/video"
                    );
                } else {
                    hero = new Villan(
                        view.getName(),
                        view.getDescription(),
                        view.getPowers(),
                        view.getGroup(),
                        view.getSkills(),
                        "path/to/image",
                        "path/to/video"
                    );
                }

                model.updateSuperHero(selectedIndex, hero);
                updateHeroList();
            }
        }
    }

    class DeleteHeroListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = view.getSelectedHeroIndex();
            if (selectedIndex != -1) {
                model.deleteSuperHero(selectedIndex);
                updateHeroList();
            }
        }
    }

    class NextHeroListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (model.getSuperHeroes().isEmpty()) return;
            currentHeroIndex = (currentHeroIndex + 1) % model.getSuperHeroes().size();
            view.setSelectedHeroIndex(currentHeroIndex);
            displaySelectedHero();
        }
    }

    class PreviousHeroListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (model.getSuperHeroes().isEmpty()) return;
            currentHeroIndex = (currentHeroIndex - 1 + model.getSuperHeroes().size()) % model.getSuperHeroes().size();
            view.setSelectedHeroIndex(currentHeroIndex);
            displaySelectedHero();
        }
    }

    class PlayVideoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (currentHeroIndex != -1) {
                SuperHero selectedHero = model.getSuperHeroes().get(currentHeroIndex);
                String videoPath = selectedHero.getVideoPath();
                Platform.runLater(() -> playVideo(videoPath));
            }
        }
    }

    class PauseVideoListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
        }
    }

    class SaveButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showSaveDialog(view.getFrame());
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    model.saveToFile(selectedFile.getAbsolutePath());
                    JOptionPane.showMessageDialog(view.getFrame(), "Dados salvos com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(view.getFrame(), "Erro ao salvar arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    class LoadButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(view.getFrame());
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                try {
                    model.loadFromFile(selectedFile.getAbsolutePath());
                    updateHeroList();
                    JOptionPane.showMessageDialog(view.getFrame(), "Dados carregados com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(view.getFrame(), "Erro ao carregar arquivo.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void updateHeroList() {
        DefaultListModel<SuperHero> modelList = new DefaultListModel<>();
        for (SuperHero hero : model.getSuperHeroes()) {
            modelList.addElement(hero);
        }
        view.setHeroListModel(modelList);
        currentHeroIndex = -1;
        view.setImage(null);
        view.setDescription("");
        view.setName(""); 
        view.setPowers(""); 
        view.setGroup(""); 
        view.setSkills(""); 
    }

    private void displaySelectedHero() {
        if (currentHeroIndex != -1 && currentHeroIndex < model.getSuperHeroes().size()) {
            SuperHero selectedHero = model.getSuperHeroes().get(currentHeroIndex);

            // Exibir a imagem do super-herói
            String imagePath = selectedHero.getImagePath();
            if (imagePath != null && !imagePath.isEmpty()) {
                ImageIcon icon = new ImageIcon(imagePath);
                view.setImage(icon);
            } else {
                view.setImage(null); // Limpa a imagem se não houver caminho
            }

            // Exibir a descrição do super-herói
            view.setDescription(selectedHero.getDescription());
        }
    }

    private void playVideo(String videoPath) {
        if (videoPath == null || videoPath.isEmpty()) {
            JOptionPane.showMessageDialog(view.getFrame(), "Nenhum vídeo selecionado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        Media media = new Media(new File(videoPath).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        Scene scene = new Scene(new javafx.scene.Group(mediaView));
        jfxPanel.setScene(scene);
        mediaPlayer.play();
    }
}