package ach.components;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Joe
 */
public class AchievementPanel extends JPanel {
    
    JLabel img;
    JLabel title;
    JLabel description;
    Boolean unlocked;
    URL imageURL;
    JPanel right;
    
    public AchievementPanel(String t, String desc, String imagePath, String status) throws IOException {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(new Color(30,30,30));
        //imageURL = new URL(imagePath);
        LineBorder border;
        if (status.equalsIgnoreCase("yes")) {
            unlocked=true;
            //(Color color, int thickness, boolean roundedCorner)
            border = new LineBorder(Color.GREEN,1);
        } else {
            unlocked=false;
            border = new LineBorder(Color.RED,1);
        }
        
        ImageIcon icon = new ImageIcon(imagePath);
        img = new JLabel();
        
        right = new JPanel();
        right.setBackground(new Color(30,30,30));
        
        title = new JLabel();
        title.setBackground(Color.BLACK);
        title.setForeground(Color.WHITE);
        
        String html1 = "<html><body style='width: ";
        String html2 = "px'>";

        description = new JLabel(html1+"450"+html2+desc);
        description.setBackground(Color.BLACK);
        description.setForeground(Color.GRAY);
//      
        
//        JTextPane achievementInfo = new JTextPane();
//        achievementInfo.setText("<html>"+t + "<br>" + desc+"</html>");
        
        img.setIcon(icon);
        img.setBorder(border);
        title.setText(t);
     //   description.setText(desc);
        
     //   img.setBounds(5, 5, 64, 64);
        
        this.add(img);
//        right.add(achievementInfo);
        right.add(title);
        right.add(description);
        this.add(right);
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
            
        Dimension d = new Dimension(725,80);
        this.setPreferredSize(d);
        this.setMinimumSize(d);
        this.setMaximumSize(d);
    }
    
    AchievementPanel() {
        
    }
    
}
