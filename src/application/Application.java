package application;

import control.Command;
import control.NextImageCommand;
import control.PrevImageCommand;
import model.Image;
import view.ImageDisplay;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Application extends JFrame{
    private Map<String, Command> commands = new HashMap<>();
    private ImageDisplay imageDisplay;
    
    public static void main(String[] args) {
        new Application().setVisible(true);
    }    
    
    private JPanel toolbar() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.add(prevButton());
        panel.add(nextButton());
        
        return panel;
    }

    public Application() {
        deployUI();
        createCommands();
    } 

    private void createCommands() {
        commands.put("Next", new NextImageCommand(imageDisplay));
        commands.put("Prev", new PrevImageCommand(imageDisplay));
    }

    private ImagePanel imagePanel() {
        ImagePanel panel = new ImagePanel (firstImage());
        this.imageDisplay = panel;
        return panel;
    }

    private Image firstImage() {
        FileImageReader fileReader = new FileImageReader("/Users/roberto/Desktop/data");
        return fileReader.read();
    }

    private void deployUI() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.getContentPane().add(components());
        this.setPreferredSize(new Dimension(500, 500));
        this.setLocationRelativeTo(null);
       
        pack();
    }

    private JButton nextButton() {
        JButton button = new JButton(">");
        button.addActionListener(doCommand("Next"));
        return button;
    }

    private JButton prevButton()  {
        JButton button = new JButton("<");
        button.addActionListener(doCommand("Prev"));
        return button;
    }

    private ActionListener doCommand(String command) {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commands.get(command).execute();
            }
        };
    }    

    private JPanel components() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(toolbar(),BorderLayout.SOUTH);
        panel.add(imagePanel(),BorderLayout.CENTER);
        return panel;
    }

}
