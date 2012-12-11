package ach.gui;

import ach.GNAComparator;
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
import java.awt.Rectangle;
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
import javax.swing.AbstractButton;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.basic.BasicProgressBarUI;
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
    private static ArrayList<String> fSList;
    private static ArrayList<Achievement> achList;
    private static Game currentGame;
    private User user;
    private JFrame frameref = this;
    private int gamesSize;
    private Boolean filter;
    private JRadioButton a_alphabetical;
    private JRadioButton a_date;
    private JRadioButton a_default;
    private JRadioButton a_all;
    private JRadioButton a_complete;
    private JRadioButton a_incomplete;
    private JRadioButton g_ascending;
    private JRadioButton g_descending;
    private JRadioButton g_percentage;
    private JRadioButton g_alphabetical;
    private JRadioButton g_percentageDec;
    private JCheckBox g_complete;

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
        UIManager.put("ProgressBar.background", new Color(50, 50, 50));
        UIManager.put("ProgressBar.foreground", new Color(30, 30, 30));
        UIManager.put("ProgressBar.selectionBackground", Color.white);
        UIManager.put("ProgressBar.selectionForeground", Color.white);

        setUser(freshGUI.getUser());
        gamesSize = getUser().getGames().size();
        initComponents();
        changeUser.setFont(new Font("Tahoma", Font.PLAIN, 11));
        //Set Avatar
        ImageIcon iI = new ImageIcon(getUser().getAvatarPath());
        avatarLabel.setIcon(iI);
        //avatarLabel.setIcon(new ImageIcon(getUser().getAvatarURL()));

        setGamesScreen();
        g_descending.setSelected(true);
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
        setResizable(false);

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
        backArrow.setVisible(false);
        oneGameProgressLabel.setVisible(false);
        oneGameProgress.setVisible(false);
        games_stageHeadline.setText("My Games (" + gamesSize + ")");
        double totalAchieved = getUser().getTotalAchieved();
        double totalAchievements = getUser().getTotalAchievements();
        double cent = (double) (totalAchieved / totalAchievements);
        int per = (int) (cent * 100);
        globalProgress.setMaximum((int) (totalAchievements));
        globalProgress.setValue((int) (totalAchieved));
        String paint = ("Global: " + ((int) (totalAchieved)) + "/" + ((int) (totalAchievements)) + " (" + per + "%)");
        globalProgress.setString(paint);

        globalProgress.setStringPainted(true);
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
        backArrow.setVisible(true);
        games_stageHeadline.setText(gameName);

        HashMap<String, Game> gamesHM = getUser().getGames();
        Game g = gamesHM.get(gameName);
        oneGameProgress.setVisible(true);
        oneGameProgress.setMaximum(g.getTotalAchievements());
        oneGameProgress.setValue(g.getAchieved());
        double total = g.getTotalAchievements();
        double achieved = g.getAchieved();
        double per = (((double) achieved / (double) total) * 100);
        int percent = (int) per;
        oneGameProgress.setStringPainted(true);
        oneGameProgress.setString((int) achieved + "/" + (int) total + " (" + percent + "%)");
        //oneGameProgressLabel.setVisible(true);
        JPanel p = generateAchievements(g);
        System.out.println(p.getPreferredSize());
        games_scrollPane.setViewportView(p);
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
        avatarContainer = new javax.swing.JPanel();
        avatarLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        globalProgress = new javax.swing.JProgressBar();
        globalProgressLabel = new javax.swing.JLabel();
        changeUser = new javax.swing.JLabel();
        centerStagePanel = new javax.swing.JPanel();
        games_stageHeadline = new javax.swing.JLabel();
        sep = new javax.swing.JSeparator();
        games_scrollPane = new javax.swing.JScrollPane();
        oneGameProgress = new javax.swing.JProgressBar();
        oneGameProgressLabel = new javax.swing.JLabel();
        refreshArrow = new javax.swing.JLabel();
        backArrow = new javax.swing.JLabel();
        filteringPane = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setMinimumSize(new java.awt.Dimension(1000, 466));

        containerPanel.setBackground(new java.awt.Color(0, 0, 0));

        topPanel.setBackground(new java.awt.Color(0, 0, 0));

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

        jLabel2.setBackground(new java.awt.Color(0, 0, 0));
        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText(user.getSteamID());

        globalProgressLabel.setForeground(new java.awt.Color(255, 255, 255));
        globalProgressLabel.setText(" ");

        changeUser.setBackground(new java.awt.Color(0, 0, 0));
        changeUser.setForeground(new java.awt.Color(150, 150, 150));
        changeUser.setText("change user");
        changeUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                changeUserMouseClick(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                changeUserOnHover(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                changeUserOnExit(evt);
            }
        });

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(avatarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(topPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 664, Short.MAX_VALUE)
                        .addComponent(globalProgressLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(globalProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(topPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(changeUser)
                            .addComponent(jLabel2))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(avatarContainer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(topPanelLayout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(globalProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(globalProgressLabel))
                            .addContainerGap()))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, topPanelLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(changeUser))))
        );

        centerStagePanel.setBackground(new java.awt.Color(0, 0, 0));

        games_stageHeadline.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        games_stageHeadline.setForeground(new java.awt.Color(240, 240, 240));
        games_stageHeadline.setText("My Games");

        games_scrollPane.setBorder(null);
        games_scrollPane.setMaximumSize(new java.awt.Dimension(52767, 52767));
        games_scrollPane.addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                speedUpMouseWheel(evt);
            }
        });

        oneGameProgressLabel.setForeground(new java.awt.Color(250, 250, 250));
        oneGameProgressLabel.setText("jLabel1");

        refreshArrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ach/gui/refresh_icon.png"))); // NOI18N

        backArrow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ach/gui/back_icon.png"))); // NOI18N
        backArrow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backArrowMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout centerStagePanelLayout = new javax.swing.GroupLayout(centerStagePanel);
        centerStagePanel.setLayout(centerStagePanelLayout);
        centerStagePanelLayout.setHorizontalGroup(
            centerStagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(centerStagePanelLayout.createSequentialGroup()
                .addGroup(centerStagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sep)
                    .addGroup(centerStagePanelLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(games_scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(centerStagePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(backArrow)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(games_stageHeadline)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 355, Short.MAX_VALUE)
                        .addComponent(oneGameProgressLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(oneGameProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(refreshArrow)))
                .addContainerGap())
        );
        centerStagePanelLayout.setVerticalGroup(
            centerStagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(centerStagePanelLayout.createSequentialGroup()
                .addGroup(centerStagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(centerStagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(centerStagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(games_stageHeadline)
                            .addComponent(oneGameProgressLabel))
                        .addComponent(backArrow))
                    .addComponent(oneGameProgress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshArrow, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sep, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(games_scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
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
                        .addComponent(filteringPane)
                        .addContainerGap())))
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

    private void changeUserMouseClick(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeUserMouseClick
        this.setVisible(false);
        String[] args = null;
        freshGUI.main(args);
    }//GEN-LAST:event_changeUserMouseClick

    private void changeUserOnHover(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeUserOnHover
        JLabel tempCU = (JLabel) evt.getSource();
        tempCU.setFont(new Font("Tahoma", Font.BOLD, 11));
        tempCU.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_changeUserOnHover

    private void changeUserOnExit(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_changeUserOnExit
        JLabel tempCU = (JLabel) evt.getSource();
        tempCU.setFont(new Font("Tahoma", Font.PLAIN, 11));
    }//GEN-LAST:event_changeUserOnExit

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
    private javax.swing.JLabel changeUser;
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

    private JPanel filterGames(String type) {
        if (!fSList.isEmpty()) {
            fSList = GNAComparator.sortHoursPlayed(fSList, games, type);
            JPanel gamesPanel = new JPanel();
            GameButton b;
            Game g;
            int numGames = 0;
            int panelHeight = 0;
            for (int i = 0; i < fSList.size(); i++) {
                g = games.get(fSList.get(i));
                GamePanel gp = new GamePanel(g);
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
                panelHeight = temp * 155;
                if (numGames % 3 > 0) {
                    panelHeight += 155;
                }

            }

            gamesPanel.setPreferredSize(new Dimension(710, panelHeight));
            gamesPanel.setBackground(Color.BLACK);
            System.out.println("Found " + numGames + " games.");

            //switchFiltering(generateGamesFilter());
            return gamesPanel;

        } else {

            JPanel emptyPanel = new JPanel();
            emptyPanel.setBackground(Color.black);
            JLabel msg = new JLabel("No games match your criteria");
            msg.setForeground(Color.white);
            emptyPanel.add(msg);
            return emptyPanel;
        }
    }

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
        ArrayList<String> keys = u.getGameKeys();
        fSList = GNAComparator.sortHoursPlayed(keys, games, GNAComparator.GSHPHTL);
        //sortedKeys = GNAComparator.sortHoursPlayed(u.getGameKeys(), games, GNAComparator.ALPHA);

        int numGames = 0;

        GameButton b;
        Game g;

        int panelHeight = 0;
        for (int i = 0; i < fSList.size(); i++) {
            g = games.get(fSList.get(i));

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
                    currentGame = games.get(gameName); //MB
                    setAchievementsScreen(gameName);


                }
            });
            gamesPanel.add(gp);
            numGames++;
            int temp = numGames / 3;
            panelHeight = temp * 155;
            if (numGames % 3 > 0) {
                panelHeight += 155;
            }

        }

        gamesPanel.setPreferredSize(new Dimension(710, panelHeight));
        gamesPanel.setBackground(Color.BLACK);
        System.out.println("Found " + numGames + " games.");

        switchFiltering(generateGamesFilter());

        return gamesPanel;
    }

    /**
     *
     * @param g An ArrayList of games sorted by GNAComparator, to be made into a
     * panel that will be inside games_scrollPane
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
                    currentGame = games.get(gameName); //MB
                    setAchievementsScreen(gameName);


                }
            });
            gamesPanel.add(gp);
            numGames++;
            int temp = numGames / 3;

            panelHeight = temp * 155;
            if (numGames % 3 > 0) {
                panelHeight += 155;
            }

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

    public JPanel filterAchievements(Game g, String parameter) {
        JPanel achievementPanelContainer = new JPanel();
        achievementPanelContainer.setLayout(new FlowLayout());

        //Get the game that was clicked
        //  System.out.println(gameName);

        System.out.println("So far so good:" + g);
        ArrayList<Achievement> achievements;
        if (parameter.equalsIgnoreCase(GNAComparator.ASFDEFAL)) {
            achievements = achList;
        } else {
            achievements = achList;
            if (parameter.equalsIgnoreCase(GNAComparator.GASALPHA)) {
                achList = GNAComparator.sortAch(achievements, parameter);
                achievements = achList;
            } else if (parameter.equalsIgnoreCase(GNAComparator.ASDAREA)) {
                achList = GNAComparator.sortAch(achievements, parameter);
                achievements = achList;
            }

        }

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
                ap = new AchievementPanel(o[1], o[2], o[0], o[3]);
                achievementPanelContainer.add(ap);
            } catch (IOException e) {
                System.out.println("IOException, achievement panel could not be created!");
            }


        }

        int apHeight = achCount * 85;
        achievementPanelContainer.setBackground(Color.BLACK);
        achievementPanelContainer.setPreferredSize(new Dimension(710, apHeight));

        Dimension pref = achievementPanelContainer.getPreferredSize();
        achievementPanelContainer.setMaximumSize(pref);
        achievementPanelContainer.setMinimumSize(pref);

        //switchFiltering(generateAchievementsFilter());
        return achievementPanelContainer;
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
        achList = achievements;
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
                ap = new AchievementPanel(o[1], o[2], o[0], o[3]);
                achievementPanelContainer.add(ap);
            } catch (IOException e) {
                System.out.println("IOException, achievement panel could not be created!");
            }


        }

        int apHeight = achCount * 85;
        achievementPanelContainer.setBackground(Color.BLACK);
        achievementPanelContainer.setPreferredSize(new Dimension(710, apHeight));

        Dimension pref = achievementPanelContainer.getPreferredSize();
        achievementPanelContainer.setMaximumSize(pref);
        achievementPanelContainer.setMinimumSize(pref);

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

        GamesFilterListener gfl = new GamesFilterListener();

        panel.setBackground(Color.BLACK);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(190, 200));
        JLabel filterHeadline = new JLabel("Filtering Options");
        filterHeadline.setForeground(Color.white);
        Font fontH1 = new Font("Tahoma", Font.PLAIN, 18);
        filterHeadline.setFont(fontH1);

        Font fontH2 = new Font("Tahoma", Font.PLAIN, 16);
//        Font fontH2B = new Font("Tahoma", Font.BOLD, 16);
        JLabel filterHeadline2 = new JLabel("Sort Games By:");
        filterHeadline2.setForeground(Color.white);
        filterHeadline2.setFont(fontH2);

//        JLabel filterHeadlineHours = new JLabel("Hours played");
//        filterHeadline2.setForeground(Color.white);
//        filterHeadline2.setFont(fontH2B);
//        
//        JLabel filterHeadlinePercentage = new JLabel("Percentage complete");
//        filterHeadline2.setForeground(Color.white);
//        filterHeadline2.setFont(fontH2B);

        Font normal = new Font("Tahoma", Font.PLAIN, 14);

        JTextField search = new JTextField();
        search.setMaximumSize(new Dimension(250, 28));

        search.setText("Search Games");
        search.setForeground(new Color(150, 150, 150));
        search.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String gameName = e.getActionCommand();
                    currentGame = games.get(gameName); //MB
                    setAchievementsScreen(gameName);


                }
            });

        ButtonGroup gameSortingOptions = new ButtonGroup();

        g_ascending = new JRadioButton();
        g_descending = new JRadioButton();
        g_percentage = new JRadioButton();
        g_alphabetical = new JRadioButton();
        g_complete = new JCheckBox();
        g_percentageDec = new JRadioButton();
        gameSortingOptions.add(g_ascending);
        gameSortingOptions.add(g_descending);
        gameSortingOptions.add(g_percentage);
        gameSortingOptions.add(g_alphabetical);
        gameSortingOptions.add(g_percentageDec);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(search);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(filterHeadline);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(g_complete);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(filterHeadline2);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));



        g_ascending.setText("Ascending (Hours Played)");
        g_ascending.setFont(normal);
        g_ascending.addActionListener(gfl);

        g_descending.setText("Descending (Hours Played)");
        g_descending.setFont(normal);
//        g_descending.setSelected(true);
        g_descending.addActionListener(gfl);

        g_percentage.setText("Ascending (% Complete)");
        g_percentage.setFont(normal);
        g_percentage.addActionListener(gfl);

        g_percentageDec.setText("Descending (% Complete)");
        g_percentageDec.setFont(normal);
        g_percentageDec.addActionListener(gfl);

        g_alphabetical.setText("Alphabetical");
        g_alphabetical.setFont(normal);
        g_alphabetical.addActionListener(gfl);

        g_complete.setText("Games 100% complete");
        g_complete.setFont(normal);
        g_complete.setActionCommand("g_complete");
        g_complete.addActionListener(gfl);

        g_ascending.setForeground(new Color(240, 240, 240));
        g_descending.setForeground(new Color(240, 240, 240));

        g_ascending.setBackground(new Color(0, 0, 0));
        g_descending.setBackground(new Color(0, 0, 0));

        g_percentage.setForeground(new Color(240, 240, 240));
        g_percentage.setBackground(new Color(0, 0, 0));

        g_percentageDec.setForeground(new Color(240, 240, 240));
        g_percentageDec.setBackground(new Color(0, 0, 0));

        g_alphabetical.setForeground(new Color(240, 240, 240));
        g_alphabetical.setBackground(new Color(0, 0, 0));

        g_complete.setForeground(new Color(240, 240, 240));
        g_complete.setBackground(new Color(0, 0, 0));

        panel.add(g_descending);
        panel.add(g_ascending);
        panel.add(g_percentageDec);
        panel.add(g_percentage);
        panel.add(g_alphabetical);


        return panel;
    }

    /**
     * Handles the events that take place in the games filtering panel.
     */
    private class GamesFilterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            Boolean filterComplete = g_complete.isSelected();
            Boolean sortAscending = g_ascending.isSelected();
            Boolean sortDescending = g_descending.isSelected();
            boolean sortAlphabetical = g_alphabetical.isSelected();
            boolean sortPercentageDec = g_percentageDec.isSelected();


            if (filterComplete) {
                g_percentage.setEnabled(false);
                g_percentageDec.setEnabled(false);
                fSList = GNAComparator.filterGames(games, GNAComparator.GFOHPAF);
            } else {
                g_percentage.setEnabled(true);
                g_percentageDec.setEnabled(true);
                fSList = GNAComparator.filterGames(games, GNAComparator.GFNULVA);
            }


            //  if (filterComplete) {
            //    if (sortAscending) {
            //        System.out.println("Show only games with 100% completed achievements, sorted by hours played (ascending)");
            //         
//                    switchScrollPane(generateFilteredGames(GNAComparator.filterCompletedAchievements(games)));
            //   } else if (sortDescending) {
            //       System.out.println("Show only games with 100% completed achievements, sorted by hours played (descending)");
            //   } else {
            //       System.out.println("Show only games with 100% completed achievements, sorted by percentage complete (this doesn't make sense but we'll have to do something for this case.");
            //   }
            // } else {
            if (sortAscending) {
                System.out.println("Show all games, sorted by hours played (ascending)");
                switchScrollPane(filterGames(GNAComparator.GSHPLTH));
            } else if (sortDescending) {
                System.out.println("Show all games, sorted by hours played (descending)");
                switchScrollPane(filterGames(GNAComparator.GSHPHTL));
            } else if (sortAlphabetical) {
                System.out.println("Show all games, sorted Alphabetically");
                switchScrollPane(filterGames(GNAComparator.GASALPHA));
            } else if (sortPercentageDec) {
                System.out.println("Show all games, sorted by percentage complete (descending)");
                switchScrollPane(filterGames(GNAComparator.GSDCENT));
            } else {
                System.out.println("Show all games, sorted by percentage complete (acsending)");
                switchScrollPane(filterGames(GNAComparator.GSPCENT));
            }
            // }

        }
    }

    /**
     * Handles the events that take place in the achievements filtering panel.
     */
    private class AchievementsFilterListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            AbstractButton x = (AbstractButton) e.getSource();
            String aC = x.getActionCommand();
            boolean filterComplete = a_complete.isSelected();
            boolean filterIncomplete = a_incomplete.isSelected();
            boolean filterAll = a_all.isSelected();
            boolean sortDate = a_date.isSelected();
            boolean sortAlphabetical = a_alphabetical.isSelected();
            boolean isDefault = a_default.isSelected();

            //  if (!filterAll) {
            //      a_default.setEnabled(false);
            // } else {
            //    a_default.setEnabled(true);
            //}
            //This part is horribly inefficient, Later there needs to be a check to see if it is already sorted the way
            //it appears so filter or sort arent called each time. and are only called when it changes.


            if (sortDate) {
                System.out.println("Show ALL achievements, sorted by date");
                if (filterComplete) {
                    achList = GNAComparator.filterAchievements(currentGame.getAchievements(), GNAComparator.AFACHUL);
                } else if (filterIncomplete) {
                    achList = GNAComparator.filterAchievements(currentGame.getAchievements(), GNAComparator.AFACHLK);
                } else if (filterAll){
                    achList = currentGame.getAchievements();
                }
                games_scrollPane.setViewportView(filterAchievements(currentGame, GNAComparator.ASDAREA));
                //ArrayList<Achievement> ach = 
            } else if (sortAlphabetical) {
                System.out.println("Show ALL achievements, sorted alphabetically");
                if (filterComplete) {
                    achList = GNAComparator.filterAchievements(currentGame.getAchievements(), GNAComparator.AFACHUL);
                } else if (filterIncomplete) {
                    achList = GNAComparator.filterAchievements(currentGame.getAchievements(), GNAComparator.AFACHLK);
                } else if (filterAll){
                    achList = currentGame.getAchievements();
                }
                games_scrollPane.setViewportView(filterAchievements(currentGame, GNAComparator.GASALPHA));
            } else if (isDefault) {
                System.out.println("Show ALL achievements, sorted by \"default\"");
                if (filterComplete) {
                    achList = GNAComparator.getDefaults(currentGame.getAchievements(), false);
                } else if (filterIncomplete) {
                    achList = GNAComparator.getDefaults(currentGame.getAchievements(), true);
                } else if (filterAll){
                    achList = currentGame.getAchievements();
                }
                games_scrollPane.setViewportView(filterAchievements(currentGame, GNAComparator.ASFDEFAL));
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

        AchievementsFilterListener afl = new AchievementsFilterListener();

        panel.setBackground(Color.BLACK);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(190, 200));
        JLabel filterHeadline = new JLabel("Filtering Options");
        filterHeadline.setForeground(Color.white);
        Font fontH1 = new Font("Tahoma", Font.PLAIN, 18);
        filterHeadline.setFont(fontH1);

        Font fontH2 = new Font("Tahoma", Font.PLAIN, 16);
//        Font fontH2B = new Font("Tahoma", Font.BOLD, 16);
        JLabel filterHeadline2 = new JLabel("Sort Achievements By:");
        filterHeadline2.setForeground(Color.white);
        filterHeadline2.setFont(fontH2);

//        JLabel filterHeadlineHours = new JLabel("Hours played");
//        filterHeadline2.setForeground(Color.white);
//        filterHeadline2.setFont(fontH2B);
//        
//        JLabel filterHeadlinePercentage = new JLabel("Percentage complete");
//        filterHeadline2.setForeground(Color.white);
//        filterHeadline2.setFont(fontH2B);

        Font normal = new Font("Tahoma", Font.PLAIN, 14);

        JTextField search = new JTextField();
        search.setMaximumSize(new Dimension(250, 28));

        search.setText("Search Achievements");
        search.setForeground(new Color(150, 150, 150));

        ButtonGroup achFilteringOptions = new ButtonGroup();
        ButtonGroup achSortingOptions = new ButtonGroup();



        a_alphabetical = new JRadioButton();
        a_date = new JRadioButton();
        a_default = new JRadioButton();
        a_default.setSelected(true);


        //      percentage = new JRadioButton();
        a_all = new JRadioButton();
        a_all.setSelected(true);
        a_complete = new JRadioButton();
        a_incomplete = new JRadioButton();
        achFilteringOptions.add(a_complete);
        achFilteringOptions.add(a_incomplete);
        achFilteringOptions.add(a_all);
        achSortingOptions.add(a_alphabetical);
        achSortingOptions.add(a_date);
        achSortingOptions.add(a_default);



        //    achSortingOptions.add(percentage);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(search);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(filterHeadline);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(a_all);
        panel.add(a_complete);
        panel.add(a_incomplete);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(filterHeadline2);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        a_alphabetical.setText("Alphabetical");
        a_alphabetical.setFont(normal);
        a_alphabetical.addActionListener(afl);

        a_all.setText("All achievements");
        a_all.setFont(normal);
        a_all.addActionListener(afl);

        a_date.setText("Date achieved");
        a_date.setFont(normal);
        a_date.addActionListener(afl);

        a_default.setText("Default");
        a_default.setFont(normal);
        a_default.setToolTipText("The default way Valve sorts them");
        a_default.addActionListener(afl);
//        percentage.setText("Almost complete");
//        percentage.setFont(normal);

        a_complete.setText("Achieved");
        a_complete.setFont(normal);
        a_complete.addActionListener(afl);

        a_incomplete.setText("Not Achieved");
        a_incomplete.setFont(normal);
        a_incomplete.addActionListener(afl);

        a_alphabetical.setForeground(new Color(240, 240, 240));
        a_date.setForeground(new Color(240, 240, 240));
        a_default.setForeground(new Color(240, 240, 240));

        a_alphabetical.setBackground(new Color(0, 0, 0));
        a_date.setBackground(new Color(0, 0, 0));
        a_default.setBackground(new Color(0, 0, 0));

//        percentage.setForeground(new Color(240, 240, 240));
//        percentage.setBackground(new Color(0, 0, 0));
//   
        a_complete.setForeground(new Color(240, 240, 240));
        a_complete.setBackground(new Color(0, 0, 0));

        a_incomplete.setForeground(new Color(240, 240, 240));
        a_incomplete.setBackground(new Color(0, 0, 0));

        a_all.setForeground(new Color(240, 240, 240));
        a_all.setBackground(new Color(0, 0, 0));

        panel.add(a_alphabetical);
        panel.add(a_date);
        panel.add(a_default);
//        panel.add(percentage);

        return panel;
    }
}
