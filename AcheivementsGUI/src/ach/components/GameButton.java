/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ach.components;

import ach.user.Game;
import ach.user.User;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Josaf
 */
public class GameButton extends JButton {

    private String name;
    private ImageIcon i;
    private URL imageURL;
    private String[] args;
    private static String game;


    public static String getGameName() {
        return game;
    }

    public GameButton(Game g){
    
        game = g.getName();
        name = g.getName();
        this.setName(name);
        this.setPreferredSize(new Dimension(217, 88));
        //imageURL = new URL(imagePath);
        i = new ImageIcon(g.getGamePath());
        this.setIcon(i);
        this.setBackground(Color.LIGHT_GRAY);
//        this.addActionListener(new ActionListener(){
//           @Override
//            public void actionPerformed(ActionEvent e) {
//                GamesScreen.setGameClicked(name);
//                
//                AchievementsScreen.main(args);
//            }
//        
//        });
        this.addMouseListener(new MouseAdapter() {
            
            //@Override
           // public void mouseClicked(MouseEvent evt) {
           //     GamesScreen.setGameClicked(evt.getComponent().getName());
            //    AchievementsScreen.main(args);
                
            //}

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                GameButton temp = (GameButton) evt.getSource();
                temp.setBackground(Color.BLACK);
                temp.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                GameButton temp = (GameButton) evt.getSource();
                temp.setBackground(Color.LIGHT_GRAY);
                temp.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
            }
        });
    }
    
    public GameButton(String n, String imagePath) {
        
        
        game = n;
        name = n;
        this.setName(name);
        this.setPreferredSize(new Dimension(217, 88));
        //imageURL = new URL(imagePath);
        i = new ImageIcon(imagePath);
        this.setIcon(i);
        this.setBackground(Color.LIGHT_GRAY);
//        this.addActionListener(new ActionListener(){
//           @Override
//            public void actionPerformed(ActionEvent e) {
//                GamesScreen.setGameClicked(name);
//                
//                AchievementsScreen.main(args);
//            }
//        
//        });
        this.addMouseListener(new MouseAdapter() {
            
            //@Override
           // public void mouseClicked(MouseEvent evt) {
           //     GamesScreen.setGameClicked(evt.getComponent().getName());
            //    AchievementsScreen.main(args);
                
            //}
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                GameButton temp = (GameButton) evt.getSource();
                temp.setBackground(Color.BLACK);
                temp.setCursor(new Cursor(Cursor.WAIT_CURSOR));
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                GameButton temp = (GameButton) evt.getSource();
                temp.setBackground(Color.BLACK);
                temp.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                GameButton temp = (GameButton) evt.getSource();
                temp.setBackground(Color.LIGHT_GRAY);
                temp.setCursor(new Cursor(Cursor.HAND_CURSOR));
                
            }
        });
}
}
