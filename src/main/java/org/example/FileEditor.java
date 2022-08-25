package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileEditor extends JDialog {
    private JPanel contentPane;
    private JButton buttonOpen;
    private JButton buttonClose;
    private JButton buttonSave;
    private JTextArea textArea;
    private JButton buttonGetSelection;
    String directory;
    String selection;
    public FileEditor() {
        setContentPane(contentPane);
        setModal(true);

        buttonOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonOpen();
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonSave();
            }
        });
        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonClose();
            }
        });
        buttonGetSelection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonGetSelection();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            onButtonClose();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonClose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    public void saveFile(String directory, String filename) {
        if ((filename == null) || (filename.length() == 0))
            return;
        File file;
        FileWriter out = null;
        try {
            file = new File(directory, filename);
            out = new FileWriter(file);
            textArea.getLineCount();
            String s = textArea.getText();
            out.write(s);
        }

        catch (IOException e) {
            textArea.setText(e.getClass().getName() + ": " + e.getMessage());
            this.setTitle("FileViewer: " + filename + ": I/O Exception");
        }
        finally {
            try {
                if (out != null)
                    out.close();
            }
            catch (IOException e) {
            }
        }
    }

    public void loadAndDisplayFile(String directory, String filename) {
        if ((filename == null) || (filename.length() == 0))
            return;
        File file;
        FileReader in = null;

        try {
            file = new File(directory, filename);
            in = new FileReader(file);
            char[] buffer = new char[4096];
            int len;
            textArea.setText("");
            while ((len = in.read(buffer)) != -1) {
                String s = new String(buffer, 0, len);
                textArea.append(s);
            }
            this.setTitle("FileViewer: " + filename);
            textArea.setCaretPosition(0);
        }
        catch (IOException e) {
            textArea.setText(e.getClass().getName() + ": " + e.getMessage());
            this.setTitle("FileViewer: " + filename + ": I/O Exception");
        }
        finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
    }

    private void onButtonOpen() {
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.LOAD);
        f.setDirectory(directory);
        f.setVisible(true);
        directory = f.getDirectory();
        loadAndDisplayFile(directory, f.getFile());
        f.dispose();
    }

    private void onButtonSave() {
        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.SAVE);
        f.setDirectory(directory); // Set the default directory
        f.setVisible(true);
        directory = f.getDirectory();
        saveFile(directory, f.getFile());
        f.dispose();
    }

    private void onButtonClose() {
        dispose();
    }

    private void onButtonGetSelection()
    {
        selection = textArea.getSelectedText();
    }

    public static void main(String[] args) {
        FileEditor dialog = new FileEditor();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
