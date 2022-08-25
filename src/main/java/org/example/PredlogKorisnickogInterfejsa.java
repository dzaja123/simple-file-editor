package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PredlogKorisnickogInterfejsa extends JDialog {
    private JPanel contentPane;
    private JButton buttonClose;
    private JButton buttonSave;
    private JButton buttonOpen;
    private JButton buttonOpen2;
    private JButton buttonGetSelection;
    private JButton buttonGetSelection2;
    private JTextArea textArea;
    private JTextArea textArea1;
    String directory;

    String selection;

    public PredlogKorisnickogInterfejsa() {
        setContentPane(contentPane);
        setModal(true);

        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonClose();
            }
        });
        buttonOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                onButtonOpen(textArea);
            }
        });
        buttonOpen2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                onButtonOpen(textArea1);
            }
        });
        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onButtonSave();
            }
        });

        buttonGetSelection.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {onButtonGetSelection(); }
        });

        buttonGetSelection2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {onButtonGetSelection2(); }
        });

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onButtonClose();
            }
        });
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }

    public void saveFile(String directory, String filename, JTextArea textArea) {
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
    public void loadAndDisplayFile(String directory, String filename, JTextArea textArea) {
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
    private void onButtonOpen(JTextArea textArea) {

        FileDialog f = new FileDialog(this, "Otvori fajl", FileDialog.LOAD);
        f.setDirectory(directory);

        f.setVisible(true);
        directory = f.getDirectory();
        loadAndDisplayFile(directory, f.getFile(), textArea);
        f.dispose();
    }

    private void onButtonSave() {

        FileDialog f = new FileDialog(this, "Sacuvaj fajl", FileDialog.SAVE);
        f.setDirectory(directory);
        f.setVisible(true);
        directory = f.getDirectory();
        saveFile(directory, f.getFile(), textArea);
        //saveFile(directory, f.getFile(), textArea1);
        f.dispose();
    }

    private void onButtonGetSelection()
    {
        selection = textArea.getSelectedText();
        textArea1.append(selection);
    }

    private void onButtonGetSelection2()
    {
        selection = textArea1.getSelectedText();
        textArea.append(selection);
    }

    private void onButtonClose() {
        dispose();
    }
    public static void main(String[] args) {
        PredlogKorisnickogInterfejsa dialog = new PredlogKorisnickogInterfejsa();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
