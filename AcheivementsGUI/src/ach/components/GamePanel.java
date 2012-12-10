/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ach.components;

import ach.user.Game;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import javax.swing.*;
import javax.swing.border.LineBorder;
/**
 *
 * @author Josaf
 */
public class GamePanel extends JPanel {
    
    JLabel title = new JLabel();
    JProgressBar achieved = new JProgressBar();
    GameButton b;
 
    public GamePanel(Game g) {
        //achieved.setPreferredSize(new Dimension(200,10));
        //achieved.setMinimumSize(new Dimension(200,10));
        setBorder(new LineBorder(new Color(60,60,60),1));
        this.setBackground(new Color(10,10,10));
        setPreferredSize(new Dimension(230,150));
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        title.setText(g.getName());
        title.setForeground(new Color(240,240,240));
        title.setFont(new Font("Tahoma",Font.BOLD,16));
        b = new GameButton(g);
            b.setActionCommand(g.getName());
        double percent = ((double)g.getAchieved() / (double)g.getTotalAchievements()) * 100;
        int p = (int)percent;
        String tooltip = g.getName() + " ("+p+"%): " + 
                g.getAchieved() + " / " + g.getTotalAchievements();
        String painted = "("+p+"%): " + 
                g.getAchieved() + " / " + g.getTotalAchievements();
        
        achieved.setMaximum(g.getTotalAchievements());
        achieved.setValue(g.getAchieved());
        achieved.setToolTipText(tooltip);
        achieved.setStringPainted(true);
//        achieved.setForeground(Color.white);
        achieved.setString(painted);
        
        this.add(Box.createRigidArea(new Dimension(0,10)));
        this.add(title);
        this.add(Box.createRigidArea(new Dimension(0, 10)));
        this.add(b);
        this.add(Box.createRigidArea(new Dimension(0, 7)));
        this.add(achieved);
        
    }
    
    public GameButton getButton() {
        return b;
    }
    
    public GamePanel() {
        super();
    }
    
}
