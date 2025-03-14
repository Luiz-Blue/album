package application;

import javax.swing.*;
import javafx.embed.swing.JFXPanel;
import java.awt.*;
import java.awt.event.ActionListener;

class SuperHeroView {
    private JFrame frame;
    private JTextField nameField, descriptionField, powersField, groupField, skillsField;
    private JButton addButton, updateButton, deleteButton, nextButton, previousButton, playButton, pauseButton;
    private JButton addImageButton, addVideoButton, saveButton, loadButton;
    private JList<SuperHero> heroList;
    private JLabel imageLabel;
    private JPanel videoPanel;
    private JFXPanel jfxPanel;
    private JLabel descriptionLabel;
    private JComboBox<String> typeComboBox; // Novo campo para escolher entre Herói ou Vilão
    private String imagePath;
    private String videoPath;

    public SuperHeroView() {
        frame = new JFrame("Álbum de Figurinhas de Super-Heróis");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Painel de entrada
        JPanel inputPanel = new JPanel(new GridLayout(7, 2)); // Aumentei para 7 linhas para acomodar o novo campo
        nameField = new JTextField();
        descriptionField = new JTextField();
        powersField = new JTextField();
        groupField = new JTextField();
        skillsField = new JTextField();

        // Novo campo: JComboBox para escolher entre Herói ou Vilão
        typeComboBox = new JComboBox<>(new String[]{"Herói", "Vilão"});
        inputPanel.add(new JLabel("Tipo:"));
        inputPanel.add(typeComboBox);

        inputPanel.add(new JLabel("Nome:"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Descrição:"));
        inputPanel.add(descriptionField);
        inputPanel.add(new JLabel("Poderes:"));
        inputPanel.add(powersField);
        inputPanel.add(new JLabel("Grupo:"));
        inputPanel.add(groupField);
        inputPanel.add(new JLabel("Habilidades:"));
        inputPanel.add(skillsField);
        descriptionLabel = new JLabel();
        frame.add(descriptionLabel, BorderLayout.SOUTH);

        // Botões
        addButton = new JButton("Adicionar");
        updateButton = new JButton("Atualizar");
        deleteButton = new JButton("Excluir");
        nextButton = new JButton("Próximo");
        previousButton = new JButton("Anterior");
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        addImageButton = new JButton("Adicionar Imagem");
        addVideoButton = new JButton("Adicionar Vídeo");
        saveButton = new JButton("Salvar");
        loadButton = new JButton("Carregar");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(playButton);
        buttonPanel.add(pauseButton);
        buttonPanel.add(addImageButton);
        buttonPanel.add(addVideoButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);

        // Lista de super-heróis
        heroList = new JList<>();
        JScrollPane listScrollPane = new JScrollPane(heroList);

        // Área de exibição de imagem e vídeo
        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        videoPanel = new JPanel();

        // Adicionando componentes ao frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(listScrollPane, BorderLayout.WEST);
        frame.add(imageLabel, BorderLayout.EAST);
        frame.add(videoPanel, BorderLayout.SOUTH);

        // Inicializa o JFXPanel
        jfxPanel = new JFXPanel();
        frame.add(jfxPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    // Método para obter o tipo selecionado (Herói ou Vilão)
    public String getSelectedType() {
        return (String) typeComboBox.getSelectedItem();
    }

    // Métodos de acesso e configuração
    public String getName() { return nameField.getText(); }
    public String getDescription() { return descriptionField.getText(); }
    public String getPowers() { return powersField.getText(); }
    public String getGroup() { return groupField.getText(); }
    public String getSkills() { return skillsField.getText(); }
    public void setHeroListModel(DefaultListModel<SuperHero> model) { heroList.setModel(model); }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public void setVideoPath(String videoPath) { this.videoPath = videoPath; }
    public void setVideoPanel(JFXPanel panel) {
        videoPanel.removeAll();
        videoPanel.add(panel);
        videoPanel.revalidate();
        videoPanel.repaint();
    }
    public int getSelectedHeroIndex() { return heroList.getSelectedIndex(); }
    public void setSelectedHeroIndex(int index) { heroList.setSelectedIndex(index); }
    public void setImage(ImageIcon image) { imageLabel.setIcon(image); }
    public void setDescription(String description) { descriptionLabel.setText(description); }

    // Métodos para limpar os campos de entrada
    public void setName(String name) { nameField.setText(name); }
    public void setPowers(String powers) { powersField.setText(powers); }
    public void setGroup(String group) { groupField.setText(group); }
    public void setSkills(String skills) { skillsField.setText(skills); }

    // Métodos para adicionar listeners
    public void addAddButtonListener(ActionListener listener) { addButton.addActionListener(listener); }
    public void addUpdateButtonListener(ActionListener listener) { updateButton.addActionListener(listener); }
    public void addDeleteButtonListener(ActionListener listener) { deleteButton.addActionListener(listener); }
    public void addNextButtonListener(ActionListener listener) { nextButton.addActionListener(listener); }
    public void addPreviousButtonListener(ActionListener listener) { previousButton.addActionListener(listener); }
    public void addPlayButtonListener(ActionListener listener) { playButton.addActionListener(listener); }
    public void addPauseButtonListener(ActionListener listener) { pauseButton.addActionListener(listener); }
    public void addImageButtonListener(ActionListener listener) { addImageButton.addActionListener(listener); }
    public void addVideoButtonListener(ActionListener listener) { addVideoButton.addActionListener(listener); }
    public void addSaveButtonListener(ActionListener listener) { saveButton.addActionListener(listener); }
    public void addLoadButtonListener(ActionListener listener) { loadButton.addActionListener(listener); }

    // Método para obter o frame
    public JFrame getFrame() {
        return frame;
    }
}