package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import java.awt.Image;

class SuperHeroController {
    private SuperHeroView view;
    private SuperHeroManager model;
    private int currentHeroIndex = -1;
    private MediaPlayer mediaPlayer;
    private JFXPanel jfxPanel;

    public SuperHeroController(SuperHeroView view, SuperHeroManager model) {
        this.view = view;
        this.model = model;

        this.view.addAddButtonListener(new AddHeroListener());
        this.view.addUpdateButtonListener(new UpdateHeroListener());
        this.view.addDeleteButtonListener(new DeleteHeroListener());
        this.view.addNextButtonListener(new NextHeroListener());
        this.view.addPreviousButtonListener(new PreviousHeroListener());
        this.view.addPlayButtonListener(new PlayVideoListener());
        this.view.addPauseButtonListener(new PauseVideoListener());
        this.view.addImageButtonListener(new AddImageListener());
        this.view.addVideoButtonListener(new AddVideoListener());
        // Inicializa o JFXPanel para reprodução de vídeo
        jfxPanel = new JFXPanel();
        view.setVideoPanel(jfxPanel);

        updateHeroList();
    }



    class AddImageListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showOpenDialog(view.getFrame());
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                // Verifica se o arquivo é uma imagem
                if (isImageFile(selectedFile)) {
                    // Associa a imagem ao super-herói selecionado
                    if (currentHeroIndex != -1) {
                        SuperHero selectedHero = model.getSuperHeroes().get(currentHeroIndex);
                        selectedHero.setImagePath(selectedFile.getAbsolutePath());
                        view.setImagePath(selectedFile.getAbsolutePath()); // Atualiza a imagem na interface
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
                // Verifica se o arquivo é um vídeo
                if (isVideoFile(selectedFile)) {
                    // Associa o vídeo ao super-herói selecionado
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
            SuperHero hero = new SuperHero(view.getName(), view.getDescription(), view.getPowers(), view.getGroup(), view.getSkills(), "path/to/image", "path/to/video");
            model.addSuperHero(hero);
            updateHeroList();
        }
    }

    class UpdateHeroListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = view.getSelectedHeroIndex();
            if (selectedIndex != -1) {
                SuperHero hero = new SuperHero(view.getName(), view.getDescription(), view.getPowers(), view.getGroup(), view.getSkills(), "path/to/image", "path/to/video");
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

    private void updateHeroList() {
        DefaultListModel<SuperHero> modelList = new DefaultListModel<>();
        for (SuperHero hero : model.getSuperHeroes()) {
            modelList.addElement(hero);
        }
        view.setHeroListModel(modelList);
        currentHeroIndex = -1;
        view.setImage(null);
    }

    private void displaySelectedHero() {
        if (currentHeroIndex != -1) {
            SuperHero selectedHero = model.getSuperHeroes().get(currentHeroIndex);

            // Exibir a imagem do super-herói
            String imagePath = selectedHero.getImagePath();
            if (imagePath != null && !imagePath.isEmpty()) {
                ImageIcon icon = new ImageIcon(imagePath);
                view.setImage(icon); // Método que deve ter na view para definir a imagem
            } else {
                view.setImage(null); // Limpa a imagem se não houver caminho
            }

            // Exibir a descrição do super-herói
            view.setDescription(selectedHero.getDescription());
        }
    }

    private void playVideo(String videoPath) {
        if (mediaPlayer != null) {
            mediaPlayer.stop(); // Para o vídeo atual, se houver
        }
        Media media = new Media(videoPath);
        mediaPlayer = new MediaPlayer(media);
        MediaView mediaView = new MediaView(mediaPlayer);
        Scene scene = new Scene(new javafx.scene.Group(mediaView));
        jfxPanel.setScene(scene);
        mediaPlayer.play();
    }
}

