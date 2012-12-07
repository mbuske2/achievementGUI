package ach.gui;

import ach.GameComparator;
import ach.components.AchievementPanel;
import ach.components.GameButton;
import ach.components.GamePanel;
import ach.user.Achievement;
import ach.user.Game;
import ach.user.User;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.AbstractRegionPainter;

/**
 *
 * @author Josaf
 */
public class GUIFrame extends JFrame {

    //Variables to determine screen state
    private final String SS_GAMES = "games";
    private final String SS_ACHIEVEMENTS = "achievements";
    private String screenState;
    protected static HashMap<String, Game> games;
    private User user;
    private JFrame frameref = this;

    /**
     * Sets the state of the screen for functions which need to know if the
     * current screen is a list of games or list of achievements.
     *
     * @param s Pass SS_GAMES to set the state to a games screen and
     * SS_ACHIEVEMENTS to set the state to an achievements screen.
     */
    private void setScreenState(String s) {
        screenState = s;
    }

    /**
     * Gets the current state of the screen (either games or achievements)
     *
     * @return A string indicating the current state of the screen.
     */
    private String getScreenState() {
        return screenState;
    }

    /**
     * Sets the user of the GUIFrame
     *
     * @param u The user object you wish you set as the current user
     */
    private void setUser(User u) {
        user = u;
    }

    /**
     * Gives you the user passed from the login screen
     *
     * @return The current user.
     */
    private User getUser() {
        return user;
    }

    /**
     * Builds the main GUI Frame
     *
     * @throws MalformedURLException
     */
    public GUIFrame() throws MalformedURLException {
        super("STEAM ACHIEVEMENTS");
        UIManager.put("ProgressBar.background", new Color(100, 100, 100));
        UIManager.put("ProgressBar.foreground", new Color(50, 50, 50));
        UIManager.put("ProgressBar.selectionBackground", Color.black);
        UIManager.put("ProgressBar.selectionForeground", Color.white);
        setUser(freshGUI.getUser());
        initComponents();
        //Set Avatar
        ImageIcon iI = new ImageIcon(getUser().getAvatarPath());
        avatarLabel.setIcon(iI);
        //avatarLabel.setIcon(new ImageIcon(getUser().getAvatarURL()));

        setGamesScreen();
        games_scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        games_scrollPane.setBackground(Color.BLACK);
        backArrow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JLabel temp = (JLabel) evt.getSource();
                temp.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JLabel temp = (JLabel) evt.getSource();
                temp.setCursor(new Cursor(Cursor.HAND_CURSOR));

            }
        });
        refreshArrow.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                JLabel temp = (JLabel) evt.getSource();
                temp.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                JLabel temp = (JLabel) evt.getSource();
                temp.setCursor(new Cursor(Cursor.HAND_CURSOR));

            }
        });
        //This centers the window on the screen
        this.setLocationRelativeTo(null);

        //SwingUtilities.updateComponentTreeUI(frameref);
//        AbstractRegionPainter myPainter = new AbstractRegionPainter() {
//
//            @Override
//            protected PaintContext getPaintContext() {
//                throw new UnsupportedOperationException("Not supported yet.");
//            }
//
//            @Override
//            protected void doPaint(Graphics2D g, JComponent c, int width, int height, Object[] extendedCacheKeys) {
//                c.setBackground(Color.red);
//                        g.setBackground(Color.yellow);
//                
//            }
//        };

    }

    /**
     * Turns the GUIFrame into a Games screen for the user returned by
     * getUser().
     *
     */
    private void setGamesScreen() throws MalformedURLException {
        setScreenState(SS_GAMES);
        oneGameProgressLabel.setVisible(false);
        oneGameProgress.setVisible(false);
        games_stageHeadline.setText("My Games");
        double totalAchieved = getUser().getTotalAchieved();
        double totalAchievements = getUser().getTotalAchievements();
        double cent = (double) (totalAchieved / totalAchievements);
        int per = (int) (cent * 100);
        globalProgress.setMaximum((int) (totalAchievements));
        globalProgress.setValue((int) (totalAchieved));
        globalProgressLabel.setText(((int) (totalAchieved)) + "/" + ((int) (totalAchievements)) + " (" + per + "%)");
        games_scrollPane.setViewportView(generateGames(getUser()));

        backArrow.setToolTipText("Back to login screen");
        refreshArrow.setToolTipText("Refresh games list");
    }

    /**
     * Turns the GUIFrame into an Achievements screen.
     *
     * @param gameName The name of the game you want to set an achievements
     * screen for.
     */
    private void setAchievementsScreen(String gameName) {
        setScreenState(SS_ACHIEVEMENTS);
        games_stageHeadline.setText(gameName);

        HashMap<String, Game> gamesHM = getUser().getGames();
        Game g = gamesHM.get(gameName);
        oneGameProgress.setVisible(true);
        oneGameProgress.setMaximum(g.getTotalAchievements());
        oneGameProgress.setValue(g.getAchieved());
        int total = g.getTotalAchievements();
        int achieved = g.getAchieved();
        oneGameProgressLabel.setForeground(Color.WHITE);
        oneGameProgressLabel.setText(gameName + ": " + achieved + "/" + total);
        oneGameProgressLabel.setVisible(true);
        games_scrollPane.setViewportView(generateAchievements(g));
        backArrow.setToolTipText("Back to your games.");
        refreshArrow.setToolTipText("Refresh achievements for " + gameName);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        containerPanel = new javax.swing.JPanel();
        topPanel = new javax.swing.JPanel();
        backArrow = new javax.swing.JLabel();
        avatarContainer = new javax.swing.JPanel();
        avatarLabel = new javax.swing.JLabel();
        refreshArrow = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        globalProgress = new javax.swing.JProgressBar();
        globalProgressLabel = new javax.swing.JLabel();
        centerStagePanel = new javax.swing.JPanel();
        games_stageHeadline = new javax.swing.JLabel();
        sep = new javax.swing.JSeparator();
        games_scrollPane = new javax.swing.JScrollPane();
        oneGameProgress = new javax.swing.JProgressBar();
        oneGameProgressLabel = new javax.swing.JLabel();
        filteringPane = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setMinimumSize(new java.awt.Dimension(1000, 466));

        containerPanel.setBackground(new java.awt.Color(0, 0, 0));

        topPanel.setBackground(new java.awt.Color(0, 0, 0));

        backArrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ach/gui/back_icon.png"))); // NOI18N
        backArrow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backArrowMouseClicked(evt);
            }
        });

        avatarContainer.setBackground(new java.awt.Color(30, 30, 30));

        avatarLabel.setMinimumSize(new java.awt.Dimension(64, 64));
        avatarLabel.setPreferredSize(new java.awt.Dimension(64, 64));

        javax.swing.GroupLayout avatarContainerLayout = new javax.swing.GroupLayout(avatarContainer);
        avatarContainer.setLayout(avatarContainerLayout);
        avatarContainerLayout.setHorizontalGroup(
            avatarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, avatarContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(avatarLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        avatarContainerLayout.setVerticalGroup(
            avatarContainerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, avatarContainerLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(avatarLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        refreshArrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ach/gui/refresh_icon.png"))); // NOI18N

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText(user.getSteamID());

        globalProgressLabel.setForeground(new java.awt.Color(255, 255, 255));
        globalProgressLabel.setText(" ");

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(avatarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(topPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(backArrow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(refreshArrow))
                    .addGroup(topPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(globalProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(globalProgressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(avatarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createSequentialGroup()
                        .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(topPanelLayout.createSequentialGroup()
                                .addComponent(refreshArrow)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(globalProgressLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(globalProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(topPanelLayout.createSequentialGroup()
                                .addComponent(backArrow)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)))
                        .addContainerGap())))
        );

        centerStagePanel.setBackground(new java.awt.Color(0, 0, 0));

        games_stageHeadline.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        games_stageHeadline.setForeground(new java.awt.Color(240, 240, 240));
        games_stageHeadline.setText("My Games");

        games_scrollPane.setBorder(null);
        games_scrollPane.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                speedUpMouseWheel(evt);
            }
        });

        oneGameProgress.setToolTipText("null");

        oneGameProgressLabel.setForeground(new java.awt.Color(250, 250, 250));
        oneGameProgressLabel.setText("jLabel1");

        javax.swing.GroupLayout centerStagePanelLayout = new javax.swing.GroupLayout(centerStagePanel);
        centerStagePanel.setLayout(centerStagePanelLayout);
        centerStagePanelLayout.setHorizontalGroup(
            centerStagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(centerStagePanelLayout.createSequentialGroup()
                .addGroup(centerStagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sep)
                    .addGroup(centerStagePanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(games_scrollPane))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, centerStagePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(games_stageHeadline)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 417, Short.MAX_VALUE)
                        .addGroup(centerStagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(oneGameProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(oneGameProgressLabel))
                        .addGap(61, 61, 61)))
                .addContainerGap())
        );
        centerStagePanelLayout.setVerticalGroup(
            centerStagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(centerStagePanelLayout.createSequentialGroup()
                .addGroup(centerStagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(games_stageHeadline)
                    .addGroup(centerStagePanelLayout.createSequentialGroup()
                        .addComponent(oneGameProgressLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(oneGameProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sep, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(games_scrollPane)
                .addContainerGap())
        );

        filteringPane.setBorder(null);

        javax.swing.GroupLayout containerPanelLayout = new javax.swing.GroupLayout(containerPanel);
        containerPanel.setLayout(containerPanelLayout);
        containerPanelLayout.setHorizontalGroup(
            containerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(topPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(containerPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filteringPane, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(centerStagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        containerPanelLayout.setVerticalGroup(
            containerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, containerPanelLayout.createSequentialGroup()
                .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(containerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(centerStagePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(containerPanelLayout.createSequentialGroup()
                        .addComponent(filteringPane, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 194, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(containerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(containerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Called when the back arrow is pressed. Current screen state decides
     * functionality
     *
     * @param evt Mouse click
     */
    private void backArrowMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_backArrowMouseClicked
        if (getScreenState().equals("achievements")) {
            try {
                setGamesScreen();
            } catch (MalformedURLException ex) {
                Logger.getLogger(GUIFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else if (getScreenState().equals("games")) {
            System.out.println("Do you want to switch users?");
            this.setVisible(false);
            String[] args = null;
            freshGUI.main(args);
        }
    }//GEN-LAST:event_backArrowMouseClicked

    private void speedUpMouseWheel(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_speedUpMouseWheel
    }//GEN-LAST:event_speedUpMouseWheel

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                System.out.println(info.getName());
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUIFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUIFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUIFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUIFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>



        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new GUIFrame().setVisible(true);
                } catch (MalformedURLException ex) {
                    Logger.getLogger(GUIFrame.class.getName()).log(Level.SEVERE, null, ex);
                }


            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel avatarContainer;
    private javax.swing.JLabel avatarLabel;
    private javax.swing.JLabel backArrow;
    private javax.swing.JPanel centerStagePanel;
    private javax.swing.JPanel containerPanel;
    private javax.swing.JScrollPane filteringPane;
    private javax.swing.JScrollPane games_scrollPane;
    private javax.swing.JLabel games_stageHeadline;
    private javax.swing.JProgressBar globalProgress;
    private javax.swing.JLabel globalProgressLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JProgressBar oneGameProgress;
    private javax.swing.JLabel oneGameProgressLabel;
    private javax.swing.JLabel refreshArrow;
    private javax.swing.JSeparator sep;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables

    /**
     * Generates a JPanel populated with games (Usually to be inserted into
     * games_scrollPane)
     *
     * @param u The user object to grab games from
     * @return A JPanel populated with games (Usually to be inserted into
     * games_scrollPane)
     */
    public JPanel generateGames(User u) {
        JPanel gamesPanel = new JPanel();

        games = u.getGames();
        ArrayList<String> sortedKeys;
        sortedKeys = GameComparator.sortHoursPlayed(u.getGameKeys(), games, GameComparator.HPHTL);
        //sortedKeys = GameComparator.sortHoursPlayed(u.getGameKeys(), games, GameComparator.ALPHA);

        int numGames = 0;

        GameButton b;
        Game g;

        int panelHeight = 0;
        for (int i = 0; i < sortedKeys.size(); i++) {
            g = games.get(sortedKeys.get(i));

            GamePanel gp = new GamePanel(g);

            String gameName = g.getName();
            String logoPath = g.getGamePath();

//            b = new GameButton(gameName, logoPath);
//            b.setActionCommand(g.getName());
//            b.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String gameName = e.getActionCommand();
//                    try {
//
//                        setAchievementsScreen(gameName);
//
//                    } catch (MalformedURLException ex) {
//                        Logger.getLogger(GUIFrame.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            });
//            gamesPanel.add(b);
            GameButton tempButton = gp.getButton();
            tempButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String gameName = e.getActionCommand();

                    setAchievementsScreen(gameName);


                }
            });
            gamesPanel.add(gp);
            numGames++;
            int temp = numGames / 3;
            panelHeight = temp * 212;

        }

        gamesPanel.setPreferredSize(new Dimension(710, panelHeight));
        gamesPanel.setBackground(Color.BLACK);
        System.out.println("Found " + numGames + " games.");

        switchFiltering(generateGamesFilter());

        return gamesPanel;
    }

    /**
     *
     * @param g An ArrayList of games sorted by GameComparator, to be made into
     * a panel that will be inside games_scrollPane
     * @return The JPanel populated with the sorted games.
     */
    public JPanel generateFilteredGames(ArrayList<Game> g) {
        JPanel gamesPanel = new JPanel();
        ArrayList<Game> tempGames;
        tempGames = g;
        int panelHeight = 0;
        int numGames = 0;
        for (int i = 0; i < tempGames.size(); i++) {
            Game game = tempGames.get(i);

            GamePanel gp = new GamePanel(game);
            String gameName = game.getName();
            String logoPath = game.getGamePath();

//            b = new GameButton(gameName, logoPath);
//            b.setActionCommand(g.getName());
//            b.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    String gameName = e.getActionCommand();
//                    try {
//
//                        setAchievementsScreen(gameName);
//
//                    } catch (MalformedURLException ex) {
//                        Logger.getLogger(GUIFrame.class.getName()).log(Level.SEVERE, null, ex);
//                    }
//                }
//            });
//            gamesPanel.add(b);
            GameButton tempButton = gp.getButton();
            tempButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String gameName = e.getActionCommand();

                    setAchievementsScreen(gameName);


                }
            });
            gamesPanel.add(gp);
            numGames++;
            int temp = numGames / 3;
            panelHeight = temp * 212;

        }

        gamesPanel.setPreferredSize(new Dimension(710, panelHeight));
        gamesPanel.setBackground(Color.BLACK);
        System.out.println("Found " + numGames + " games.");



        return gamesPanel;
    }

    /**
     * Switches out the Center Stage (games_scrollPane) with another panel.
     *
     * @param panel The panel you wish to insert into games_scrollPane (aka
     * Center Stage).
     */
    private void switchScrollPane(JPanel panel) {
        games_scrollPane.setViewportView(panel);
    }

    /**
     * Switches out the filtering panel (filteringPane)
     *
     * @param jp The panel you'd like to place in the filtering panel
     * (filteringPane)
     */
    private void switchFiltering(JPanel jp) {
        filteringPane.setViewportView(jp);
    }

    /**
     * Generates a JPanel filled with achievement panels to place into center
     * stage.
     *
     * @param g The game object of the game you'd like achievements for
     * @return A JPanel populated with all the achievements from the game passed
     * in the parameter.
     */
    public JPanel generateAchievements(Game g) {
        //gamesPanel is the container for all the games


        //Table to populate with achievements
        JPanel achievementPanelContainer = new JPanel();
        achievementPanelContainer.setLayout(new FlowLayout());

        //Get the game that was clicked
        //  System.out.println(gameName);

        System.out.println("So far so good:" + g);

        ArrayList<Achievement> achievements = g.getAchievements();
        String[] o = new String[4];
        Iterator it2 = achievements.iterator();
        AchievementPanel ap;

        int achCount = 0;
        while (it2.hasNext()) {
            Achievement a = (Achievement) it2.next();
            achCount++;
            if (a.getUnlockedStatus()) {
                //o[0] = a.getIconClosedURLPath();
                o[0] = a.getIconOpenPath();
                o[3] = "Yes";
            } else {
                //o[0] = a.getIconOpenURLPath();
                o[0] = a.getIconClosedPath();
                o[3] = "No";
            }
            o[1] = a.getName().toString();
            o[2] = a.getDescription().toString();
            //System.out.println(o[0] + " " + o[1] + " " + o[2] + " " + o[3]);
            try {
                ap = new AchievementPanel(o[1], o[2], o[0]);
                achievementPanelContainer.add(ap);
            } catch (IOException e) {
                System.out.println("IOException, achievement panel could not be created!");
            }


        }

        int apHeight = achCount * 85;
        achievementPanelContainer.setBackground(Color.BLACK);
        achievementPanelContainer.setPreferredSize(new Dimension(710, apHeight));


        switchFiltering(generateAchievementsFilter());
        return achievementPanelContainer;
    }

    /**
     * Generates the filtering panel for the games screen.
     *
     * @return The JPanel containing all the filtering options for the games
     * screen.
     */
    private JPanel generateGamesFilter() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(190, 200));
        JLabel filterHeadline = new JLabel("Filtering Options");
        filterHeadline.setForeground(Color.white);
        Font fontH1 = new Font("Tahoma", Font.PLAIN, 18);
        filterHeadline.setFont(fontH1);

        Font fontH2 = new Font("Tahoma", Font.PLAIN, 16);
        JLabel filterHeadline2 = new JLabel("Sort Games By:");
        filterHeadline2.setForeground(Color.white);
        filterHeadline2.setFont(fontH2);

        Font normal = new Font("Tahoma", Font.PLAIN, 14);

        JTextField search = new JTextField();
        search.setMaximumSize(new Dimension(250, 28));

        search.setText("Search Games");
        search.setForeground(new Color(150, 150, 150));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(search);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(filterHeadline);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(filterHeadline2);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        JCheckBox ascending;
        JCheckBox descending;
        JCheckBox complete;

        ascending = new JCheckBox();
        descending = new JCheckBox();
        complete = new JCheckBox();

        ascending.setText("Ascending");
        ascending.setFont(normal);

        descending.setText("Descending");
        descending.setFont(normal);

        complete.setText("All achievements complete");
        complete.setFont(normal);
        complete.setActionCommand("g_complete");
        complete.addItemListener(new CheckBoxListener());

        ascending.setForeground(new Color(240, 240, 240));
        descending.setForeground(new Color(240, 240, 240));
        complete.setForeground(new Color(240, 240, 240));
        ascending.setBackground(new Color(0, 0, 0));
        descending.setBackground(new Color(0, 0, 0));
        complete.setBackground(new Color(0, 0, 0));

        panel.add(ascending);
        panel.add(descending);
        panel.add(complete);

        return panel;
    }

    private class CheckBoxListener implements ItemListener {

        @Override
        public void itemStateChanged(ItemEvent e) {
            JCheckBox x = (JCheckBox) e.getSource();
            String aC = x.getActionCommand();
            if (aC.equalsIgnoreCase("g_complete")) {
                if (x.isSelected()) {
                    System.out.println("Show only games who have all achievements completed.");
                    switchScrollPane(generateFilteredGames(GameComparator.filterCompletedAchievements(games)));
                } else {
                    System.out.println("This is where it'd go back to the full list.");


                }
            }
        }
    }

    /**
     * Generates the filtering panel for the achievements screen
     *
     * @return The JPanel containing all the filtering options for the
     * achievements screen.
     */
    private JPanel generateAchievementsFilter() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(190, 200));
        JLabel filterHeadline = new JLabel("Filtering Options");
        filterHeadline.setForeground(Color.white);
        Font fontH1 = new Font("Tahoma", Font.PLAIN, 18);
        filterHeadline.setFont(fontH1);

        Font fontH2 = new Font("Tahoma", Font.PLAIN, 16);
        JLabel filterHeadline2 = new JLabel("Sort Achievements By:");
        filterHeadline2.setForeground(Color.white);
        filterHeadline2.setFont(fontH2);

        Font normal = new Font("Tahoma", Font.PLAIN, 14);

        JTextField search = new JTextField();
        search.setMaximumSize(new Dimension(250, 28));

        search.setText("Search Games");
        search.setForeground(new Color(150, 150, 150));
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(search);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(filterHeadline);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(filterHeadline2);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        JCheckBox ascending;
        JCheckBox descending;
        JCheckBox recent;

        ascending = new JCheckBox();
        descending = new JCheckBox();
        recent = new JCheckBox();

        ascending.setText("Achieved");
        ascending.setFont(normal);
        descending.setText("Not achieved");
        descending.setFont(normal);
        recent.setText("Recently achieved");
        recent.setFont(normal);
        ascending.setForeground(new Color(240, 240, 240));
        descending.setForeground(new Color(240, 240, 240));
        recent.setForeground(new Color(240, 240, 240));

        panel.add(ascending);
        panel.add(descending);
        panel.add(recent);
        return panel;
    }
}
