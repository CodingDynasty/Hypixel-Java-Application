package com.src.codingdynasty;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import com.src.codingdynasty.utils.ArenaStats;
import com.src.codingdynasty.utils.Boosters;
import com.src.codingdynasty.utils.GuildUtils;
import com.src.codingdynasty.utils.HungerGamesStats;
import com.src.codingdynasty.utils.McgoStats;
import com.src.codingdynasty.utils.MegaStats;
import com.src.codingdynasty.utils.PaintballStats;
import com.src.codingdynasty.utils.PlayerUtils;
import com.src.codingdynasty.utils.SkywarsStats;
import com.src.codingdynasty.utils.WallsStats;

public class jFrame extends JFrame implements ActionListener {

	private static final long serialVersionUID = 874714946478418251L;
	public static String version = "Alpha v1.2.4";

	public static String playerName;
	public static UUID playerUUID;
	public static String uuidRaw;

	JPanel name = new JPanel();

	JButton seeStat = new JButton("See Stats");
	JButton seeFriends = new JButton("See Friends");
	JButton seeGuild = new JButton("See Guild");
	JButton seeBoosters = new JButton("See Boosters");
	
	JTextField userName = new JTextField(22);
	JButton Submit = new JButton("Submit");
	JLabel icon = new JLabel();
	JLabel sword = new JLabel();

	Container c = getContentPane();
	
	JPanel info = new JPanel(new GridLayout(1, 1));
	JPanel stats = new JPanel(new GridLayout(1, 4));
	JPanel button = new JPanel(new GridLayout(2, 2));
	JPanel statsButtons = new JPanel(new GridLayout(4,2));
	JPanel gameStatsSection = new JPanel(new GridLayout(2 ,1));
	JPanel SkywarsStatsShow = new JPanel(new GridLayout(2, 7));
	JPanel MegaStatsShow = new JPanel(new GridLayout(2,4));
	JPanel WallsStatsShow = new JPanel(new GridLayout(1,5));
	JPanel PaintballStatsShow = new JPanel(new GridLayout(1,7));
	JPanel BlitzStatsShow = new JPanel(new GridLayout(2, 3));
	JPanel CSGOStatsShow = new JPanel(new GridLayout(2, 4));
	JPanel ArenaStatsShow = new JPanel(new GridLayout(3, 3));
	JPanel BoostersStatsShow = new JPanel(new GridLayout(7, 3));
	JPanel GuildShow = new JPanel(new GridLayout(2, 3));
	JPanel GuildOver = new JPanel(new GridLayout(2, 1));
	JPanel FriendsShow = new JPanel(new GridLayout(1, 1));

	public void doBackgroundColors(){
		name.setBackground(new Color(210,180,140));
		info.setBackground(new Color(210,180,140));
		stats.setBackground(new Color(210,180,140));
		button.setBackground(new Color(210,180,140));
		statsButtons.setBackground(new Color(139,119,101));
		gameStatsSection.setBackground(new Color(139,119,101));
		SkywarsStatsShow.setBackground(new Color(139,119,101));
		MegaStatsShow.setBackground(new Color(139,119,101));
		WallsStatsShow.setBackground(new Color(139,119,101));
		PaintballStatsShow.setBackground(new Color(139,119,101));
		BlitzStatsShow.setBackground(new Color(139,119,101));
		CSGOStatsShow.setBackground(new Color(139,119,101));
		ArenaStatsShow.setBackground(new Color(139,119,101));
		BoostersStatsShow.setBackground(new Color(139,119,101));
		GuildOver.setBackground(new Color(139,119,101));
		GuildShow.setBackground(new Color(139,119,101));
		FriendsShow.setBackground(new Color(139,119,101));		
	}
	
	JButton Skywars = new JButton("Skywars");
	JButton Mega = new JButton("Mega Walls");
	JButton Walls = new JButton("Walls");
	JButton Paintball = new JButton("Paintball");
	JButton HungerGames = new JButton("Blitz SG");
	JButton MCGO = new JButton("Cops and Crims");
	JButton Arena = new JButton("Arena");
	JButton back = new JButton("Back");
	
	public static boolean gameStats = false;
	public static boolean statsMenu = false;
	public static boolean mainMenu = false;
	public static boolean boosterMenu = false;
	public static boolean guildMenu = false;
	public static boolean FriendsMenu = false;

	Image playerHead = null;

	public static void main(String[] args) throws IOException {

		@SuppressWarnings("unused")
		jFrame jFrame = new jFrame();
	}
	
	public jFrame() throws IOException {
		super("Hypixel Java Application | " + version);
		
		doIconImage();
		doBackgroundColors();
		
		setDefaultCloseOperation(3);
		add(name);
		getImage();
		setupActionListeners();

		this.name.add(icon, SwingConstants.CENTER);
		this.name.add(new JLabel("Enter your current username here: "));
		this.name.add(userName);
		this.name.add(Submit);
		this.name.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.name.setAlignmentY(Component.CENTER_ALIGNMENT);
	
		this.c.add(name);

		pack();
		setVisible(true);
		setSize(600, 250);

	}

	public void getImage() {

		ImageIcon ico = new ImageIcon(getClass().getResource("/logo2.png"));
		Image o = ico.getImage()
				.getScaledInstance(400, 150, Image.SCALE_SMOOTH);

		icon = new JLabel(new ImageIcon(o));

	}
	
	public Image getPlayerHead(){
		
		Image image = null;
		
	      try {
	          URL url = new URL("https://mcapi.ca/avatar/3d/" + playerName);
	          image = ImageIO.read(url);
	        } catch (IOException e1) {
	          e1.printStackTrace();
	        }
	      
	      return image;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource().equals(this.Submit) || e.getSource().equals(this.userName)) {
			if (!this.userName.getText().isEmpty()) {
				mainMenu = true;
				
				playerName = this.userName.getText();
				playerHead = getPlayerHead();
				try {
					playerUUID = getUUID(playerName.toLowerCase());
					if(playerUUID != null){
					HypixelAPIConnector.getFriends(playerUUID);
					HypixelAPIConnector.getGuild(playerUUID);
					HypixelAPIConnector.getPlayer(playerUUID);
					HypixelAPIConnector.getStats(playerUUID);
					HypixelAPIConnector.getBoosters();
					c.remove(name);
					pack();
					makeOutLine();
					}
					
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				try {
					ImageIcon ico = new ImageIcon(getClass().getResource(
							"/HypixelSheild.png"));
					Image o = ico.getImage().getScaledInstance(40, 40,
							Image.SCALE_SMOOTH);
					ImageIcon sheild = new ImageIcon(o);
					if (playerUUID == null) {
						JOptionPane.showMessageDialog(c, "Invalid username was used!", "Invalid Username!", 0, sheild);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		}
		
		if(e.getSource().equals(seeGuild)){
			if(GuildUtils.hasGuild){
			makeGuildLayout();
			mainMenu = false;
			guildMenu = true;
			}else{
				try {
					ImageIcon ico = new ImageIcon(getClass().getResource(
							"/HypixelSheild.png"));
					Image o = ico.getImage().getScaledInstance(40, 40,
							Image.SCALE_SMOOTH);
					ImageIcon sheild = new ImageIcon(o);
						JOptionPane.showMessageDialog(c, "You are not in a Guild!", "You're not in a guild!", 0, sheild);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
		if(e.getSource().equals(seeFriends)){
			
			makeFriendsLayout();
			mainMenu = false;
			FriendsMenu = true;
			
		}
		if(e.getSource().equals(seeStat)){
			
			mainMenu = false;
			statsMenu = true;
			
			makeStatsLayout();
			
		}
		if(e.getSource().equals(seeBoosters)){
			
			makeBoostersLayout();
			statsMenu = false;
			gameStats = false;
			boosterMenu = true;
			
		}
		if(e.getSource().equals(MCGO)){
			
			makeCSGOLayout();
			gameStats = true;
			statsMenu = false;
			
			
		}
		if(e.getSource().equals(Arena)){
			
			makeArenaLayout();
			statsMenu = false;
			gameStats = true;
			
		}
		if(e.getSource().equals(Paintball)){
			
			makePaintLayout();
			statsMenu = false;
			gameStats = true;
			
		}
		if(e.getSource().equals(HungerGames)){
			
			makeBlitzLayout();
			statsMenu = false;
			gameStats = true;
			
		}
		if(e.getSource().equals(Walls)){
			
			makeWallsLayout();
			statsMenu = false;
			gameStats = true;
			
		}
		if(e.getSource().equals(Mega)){
			
			makeMegaLayout();
			statsMenu = false;
			gameStats = true;
			
		}
		if(e.getSource().equals(Skywars)){
			
			makeSkywarsLayout();
			statsMenu = false;
			gameStats = true;
			
		}
		if(e.getSource().equals(back)){
			
			if(statsMenu){
				makeOutLine();
				mainMenu = true;
				statsMenu = false;
			}
			if(gameStats){
				makeStatsLayout();
				gameStats = false;
				statsMenu = true;
			}
			if(boosterMenu){
				makeOutLine();
				mainMenu = true;
				boosterMenu = false;
			}
			if(guildMenu){
				makeOutLine();
				mainMenu = true;
				guildMenu = false;
			}
			if(FriendsMenu){
				makeOutLine();
				mainMenu = true;
				FriendsMenu = false;
			}
		}
	}

	public UUID getUUID(String name) throws Exception {
		UUID uuid = new UUIDFetcher(name).call().get(name);
		return uuid;

	}
	
	public void makeOutLine(){
		c.removeAll();
		pack();
		setSize(1200,500);
		info.add(icon, SwingConstants.CENTER);
		stats.removeAll();
		
		stats.add(new JLabel(new ImageIcon(playerHead)));
		if(PlayerUtils.rank.equalsIgnoreCase("VIP")){
			stats.add("Center",new JLabel("<html>UserName: <font color=rgb(50,255,50)>[" + PlayerUtils.rank + "] " + playerName + "</font><br>UUID: <font color=rgb(50,255,50)>" + playerUUID + "</font></html>"));
		}else if(PlayerUtils.rank.equalsIgnoreCase("VIP_PLUS")){
			stats.add("Center",new JLabel("<html>UserName: <font color=rgb(50,255,50)>[VIP</font><font color=red>+</font><font color=rgb(50,255,50)>] " + playerName + "</font><br>UUID: <font color=rgb(50,255,50)>" + playerUUID + "</font></html>"));
		}else if(PlayerUtils.rank.equalsIgnoreCase("MVP")){
			stats.add("Center",new JLabel("<html>UserName: <font color=rgb(0,255,255)>[MVP] " + playerName + "</font><br>UUID: <font color=rgb(0,255,255)>" + playerUUID + "</font></html>"));
		}else if(PlayerUtils.rank.equalsIgnoreCase("MVP_PLUS")){
			stats.add("Center",new JLabel("<html>UserName: <font color=rgb(0,255,255)>[MVP</font><font color=red>+</font><font color=rgb(0,255,255)>] " + playerName + "</font><br>UUID: <font color=rgb(0,255,255)>" + playerUUID + "</font></html>"));
		}else if(PlayerUtils.rank.equalsIgnoreCase("HELPER")){
			stats.add("Center",new JLabel("<html>UserName: <font color=blue>[Helper] " + playerName + "</font><br>UUID: <font color=blue>" + playerUUID + "</font></html>"));
		}else if(PlayerUtils.rank.equalsIgnoreCase("Moderator")){
			stats.add("Center",new JLabel("<html>UserName: <font color=rgb(0,200,0)>[Mod] " + playerName + "</font><br>UUID: <font color=rgb(0,200,0)>" + playerUUID + "</font></html>"));
		}else if(PlayerUtils.rank.equalsIgnoreCase("Admin")){
			stats.add("Center",new JLabel("<html>UserName: <font color=red>[Admin] " + playerName + "</font><br>UUID: <font color=red>" + playerUUID + "</font></html>"));
		}else if(PlayerUtils.rank.equalsIgnoreCase("Youtuber")){
			stats.add("Center",new JLabel("<html>UserName: <font color=#FFDE00>[YT] " + playerName + "</font><br>UUID: <font color=#FFDE00>" + playerUUID + "</font></html>"));
		}else{
			stats.add("Center",new JLabel("<html>UserName: [" + PlayerUtils.rank + "] " + playerName + "<br>UUID: " + playerUUID + "</html>"));
		}
		
		stats.add("Center",new JLabel("<html>Network Level: <font color=rgb(50,255,50)>" + PlayerUtils.Level + "</font><br>Network Exp: <font color=rgb(50,255,50)>" + PlayerUtils.NetworkExp + "</font></html>"));
		stats.add("Center",new JLabel("<html>Karma: <font color=rgb(186,85,211)>" + PlayerUtils.karma + "</font></html>"));

		button.removeAll();
		
		button.add(seeStat, SwingConstants.CENTER);
		button.add(seeFriends, SwingConstants.CENTER);
		button.add(seeGuild, SwingConstants.CENTER);
		button.add(seeBoosters, SwingConstants.CENTER);
		
		c.add("North", info);
		c.add("Center", stats);
		c.add("South", button);
		setVisible(true);
	}
	
	public void makeStatsLayout(){
		c.removeAll();
		pack();
		setSize(1200,500);
		statsButtons.removeAll();
		
		statsButtons.add(back,SwingConstants.CENTER);
		statsButtons.add(Arena, SwingConstants.CENTER);
		statsButtons.add(MCGO, SwingConstants.CENTER);
		statsButtons.add(HungerGames, SwingConstants.CENTER);
		statsButtons.add(Paintball, SwingConstants.CENTER);
		statsButtons.add(Walls, SwingConstants.CENTER);
		statsButtons.add(Mega, SwingConstants.CENTER);
		statsButtons.add(Skywars, SwingConstants.CENTER);
		
		c.add("North", info);
		c.add("Center", stats);
		c.add("South", statsButtons);
		setVisible(true);
	}
	
	public void makeSkywarsLayout(){
		c.removeAll();
		
		gameStatsSection.removeAll();
		SkywarsStatsShow.removeAll();
		SkywarsStatsShow.add(new JLabel("Overall Kills: " + SkywarsStats.overallKills, SwingConstants.CENTER));
		SkywarsStatsShow.add(new JLabel("Solo Kills: " + SkywarsStats.killsSolo, SwingConstants.CENTER));
		SkywarsStatsShow.add(new JLabel("Solo Deaths: " + SkywarsStats.deathsSolo, SwingConstants.CENTER));
		SkywarsStatsShow.add(new JLabel("Solo Wins: " + SkywarsStats.winsSolo, SwingConstants.CENTER));
		SkywarsStatsShow.add(new JLabel("Solo Losses: " + SkywarsStats.lossesSolo, SwingConstants.CENTER));
		SkywarsStatsShow.add(new JLabel("Overall Wins: " + SkywarsStats.wins, SwingConstants.CENTER));
		SkywarsStatsShow.add(new JLabel("Coins: " + SkywarsStats.SkyCoins, SwingConstants.CENTER));
		SkywarsStatsShow.add(new JLabel("Overall Deaths: " + SkywarsStats.overallDeaths, SwingConstants.CENTER));
		SkywarsStatsShow.add(new JLabel("Team Kills: " + SkywarsStats.teamKills, SwingConstants.CENTER));
		SkywarsStatsShow.add(new JLabel("Team Deaths: " + SkywarsStats.teamDeaths, SwingConstants.CENTER));
		SkywarsStatsShow.add(new JLabel("Team Wins: " + SkywarsStats.teamWins, SwingConstants.CENTER));
		SkywarsStatsShow.add(new JLabel("Team Losses: " + SkywarsStats.teamLosses, SwingConstants.CENTER));
		SkywarsStatsShow.add(new JLabel("Overall Losses: " + SkywarsStats.losses, SwingConstants.CENTER));
		SkywarsStatsShow.add(new JLabel("Souls: " + SkywarsStats.souls, SwingConstants.CENTER));
		
		gameStatsSection.add(SkywarsStatsShow);
		gameStatsSection.add(back);
		
		c.add("North", info);
		c.add("Center", stats);
		c.add("South", gameStatsSection);
		setVisible(true);
	}
	
	public void makeWallsLayout(){
		c.removeAll();
		
		gameStatsSection.removeAll();
		WallsStatsShow.removeAll();
		WallsStatsShow.add(new JLabel("Kills: " + WallsStats.kills, SwingConstants.CENTER));
		WallsStatsShow.add(new JLabel("Deaths: " + WallsStats.deaths, SwingConstants.CENTER));
		WallsStatsShow.add(new JLabel("Wins: " + WallsStats.wins, SwingConstants.CENTER));
		WallsStatsShow.add(new JLabel("Losses: " + WallsStats.losses, SwingConstants.CENTER));
		WallsStatsShow.add(new JLabel("Coins: " + WallsStats.coins, SwingConstants.CENTER));
		
		gameStatsSection.add(WallsStatsShow);
		gameStatsSection.add(back);
		
		c.add("North", info);
		c.add("Center", stats);
		c.add("South", gameStatsSection);
		setVisible(true);
	}
	
	public void makeMegaLayout(){
		c.removeAll();
		
		gameStatsSection.removeAll();
		MegaStatsShow.removeAll();
		MegaStatsShow.add(new JLabel("Kills: " + MegaStats.kills, SwingConstants.CENTER));
		MegaStatsShow.add(new JLabel("Wins: " + MegaStats.wins, SwingConstants.CENTER));
		MegaStatsShow.add(new JLabel("Coins: " + MegaStats.megaCoins, SwingConstants.CENTER));
		MegaStatsShow.add(new JLabel("Final Kills: " + MegaStats.finalKills, SwingConstants.CENTER));
		MegaStatsShow.add(new JLabel("Deaths: " + MegaStats.deaths, SwingConstants.CENTER));
		MegaStatsShow.add(new JLabel("Losses: " + MegaStats.losses, SwingConstants.CENTER));
		MegaStatsShow.add(new JLabel("Current Kit: " + MegaStats.currentKit, SwingConstants.CENTER));
		MegaStatsShow.add(new JLabel("Final Deaths: " + MegaStats.finalDeaths, SwingConstants.CENTER));
		
		gameStatsSection.add(MegaStatsShow);
		gameStatsSection.add(back);
		
		c.add("North", info);
		c.add("Center", stats);
		c.add("South", gameStatsSection);
		setVisible(true);
	}
	
	public void makePaintLayout(){
		c.removeAll();
		
		gameStatsSection.removeAll();
		PaintballStatsShow.removeAll();
		PaintballStatsShow.add(new JLabel("Kills: " + PaintballStats.kills, SwingConstants.CENTER));
		PaintballStatsShow.add(new JLabel("Kill Streak: " + PaintballStats.killstreak, SwingConstants.CENTER));
		PaintballStatsShow.add(new JLabel("Deaths: " + PaintballStats.deaths, SwingConstants.CENTER));
		PaintballStatsShow.add(new JLabel("Wins: " + PaintballStats.wins, SwingConstants.CENTER));
		PaintballStatsShow.add(new JLabel("Shots Fired: " + PaintballStats.shotsFired, SwingConstants.CENTER));
		PaintballStatsShow.add(new JLabel("Coins: " + PaintballStats.coins, SwingConstants.CENTER));
		
		gameStatsSection.add(PaintballStatsShow);
		gameStatsSection.add(back);
		
		c.add("North", info);
		c.add("Center", stats);
		c.add("South", gameStatsSection);
		setVisible(true);
	}
	
	public void makeBlitzLayout(){
		c.removeAll();
		
		gameStatsSection.removeAll();
		BlitzStatsShow.removeAll();
		BlitzStatsShow.add(new JLabel("Kills: " + HungerGamesStats.kills, SwingConstants.CENTER));
		BlitzStatsShow.add(new JLabel("Deaths: " + HungerGamesStats.deaths, SwingConstants.CENTER));
		BlitzStatsShow.add(new JLabel("Wins: " + HungerGamesStats.wins, SwingConstants.CENTER));
		BlitzStatsShow.add(new JLabel("Coins: " + HungerGamesStats.coins, SwingConstants.CENTER));
		String p = null;
		if(HungerGamesStats.aura != null){
			p = HungerGamesStats.aura.replace("_", " ");
		}else{
			p = "No Aura";
		}
		BlitzStatsShow.add(new JLabel("Current Aura: " + p, SwingConstants.CENTER));
		if(HungerGamesStats.taunt != null){
			p = HungerGamesStats.taunt.replace("_", " ");
		}else{
			p = "No Taunt";
		}
		BlitzStatsShow.add(new JLabel("Current Taunt: " + p, SwingConstants.CENTER));
		
		gameStatsSection.add(BlitzStatsShow);
		gameStatsSection.add(back);
		
		c.add("North", info);
		c.add("Center", stats);
		c.add("South", gameStatsSection);
		setVisible(true);
	}
	
	public void makeCSGOLayout(){
		c.removeAll();
		
		gameStatsSection.removeAll();
		CSGOStatsShow.removeAll();
		CSGOStatsShow.add(new JLabel("Kills: " + McgoStats.kills, SwingConstants.CENTER));
		CSGOStatsShow.add(new JLabel("Deaths: " + McgoStats.deaths, SwingConstants.CENTER));
		CSGOStatsShow.add(new JLabel("Wins: " + McgoStats.wins, SwingConstants.CENTER));
		CSGOStatsShow.add(new JLabel("Coins: " + McgoStats.coins, SwingConstants.CENTER));
		CSGOStatsShow.add(new JLabel("Cop Kills: " + McgoStats.copKills, SwingConstants.CENTER));
		CSGOStatsShow.add(new JLabel("Criminal Kills: " + McgoStats.criminalKills, SwingConstants.CENTER));
		CSGOStatsShow.add(new JLabel("Bombs Planted: " + McgoStats.bombsPlanted, SwingConstants.CENTER));
		CSGOStatsShow.add(new JLabel("Bombs Defused: " + McgoStats.bombsDefused, SwingConstants.CENTER));
		
		gameStatsSection.add(CSGOStatsShow);
		gameStatsSection.add(back);
		
		c.add("North", info);
		c.add("Center", stats);
		c.add("South", gameStatsSection);
		setVisible(true);
	}
	
	public void makeArenaLayout(){
		c.removeAll();
		
		gameStatsSection.removeAll();
		ArenaStatsShow.removeAll();
		ArenaStatsShow.add(new JLabel("1v1 Kills: " + ArenaStats.v1kills, SwingConstants.CENTER));
		ArenaStatsShow.add(new JLabel("1v1 Wins: " + ArenaStats.v1wins, SwingConstants.CENTER));
		ArenaStatsShow.add(new JLabel("1v1 Losses: " + ArenaStats.v1losses, SwingConstants.CENTER));
		ArenaStatsShow.add(new JLabel("2v2 Kills: " + ArenaStats.v2kills, SwingConstants.CENTER));
		ArenaStatsShow.add(new JLabel("2v2 Wins: " + ArenaStats.v2wins, SwingConstants.CENTER));
		ArenaStatsShow.add(new JLabel("2v2 Losses: " + ArenaStats.v2losses, SwingConstants.CENTER));
		ArenaStatsShow.add(new JLabel("4v4 Kills: " + ArenaStats.v4kills, SwingConstants.CENTER));
		ArenaStatsShow.add(new JLabel("4v4 Wins: " + ArenaStats.v4wins, SwingConstants.CENTER));
		ArenaStatsShow.add(new JLabel("4v4 Losses: " + ArenaStats.v4losses, SwingConstants.CENTER));
		
		gameStatsSection.add(ArenaStatsShow);
		gameStatsSection.add(back);
		
		c.add("North", info);
		c.add("Center", stats);
		c.add("South", gameStatsSection);
		setVisible(true);
	}
	
	public void makeBoostersLayout(){
		c.removeAll();
		setSize(1200, 900);
		gameStatsSection.removeAll();
		BoostersStatsShow.removeAll();
		
		String QuakeName = "";
		String WallsName = "";
		String PaintName = "";
		String HGName = "";
		String TNTName = "";
		String VAMPName = "";
		String Wall3Name = "";
		String ArcadeName = "";
		String ArenaName = "";
		String UHCName = "";
		String MCGOName = "";
		String WarName = "";
		String KartName = "";
		String SkyName = "";
		
		try{
			QuakeName = new NameFetcher(Boosters.playerUuid.get(2l)).call();
			WallsName = new NameFetcher(Boosters.playerUuid.get(3l)).call();
			PaintName = new NameFetcher(Boosters.playerUuid.get(4l)).call();
			HGName = new NameFetcher(Boosters.playerUuid.get(5l)).call();
			TNTName = new NameFetcher(Boosters.playerUuid.get(6l)).call();
			VAMPName = new NameFetcher(Boosters.playerUuid.get(7l)).call();
			Wall3Name = new NameFetcher(Boosters.playerUuid.get(13l)).call();
			ArcadeName = new NameFetcher(Boosters.playerUuid.get(14l)).call();
			ArenaName = new NameFetcher(Boosters.playerUuid.get(17l)).call();
			UHCName = new NameFetcher(Boosters.playerUuid.get(20l)).call();
			MCGOName = new NameFetcher(Boosters.playerUuid.get(21l)).call();
			WarName = new NameFetcher(Boosters.playerUuid.get(23l)).call();
			KartName = new NameFetcher(Boosters.playerUuid.get(25l)).call();
			SkyName = new NameFetcher(Boosters.playerUuid.get(51l)).call();

			if(QuakeName.equalsIgnoreCase("")){
				QuakeName = "No boosters";
			}
			if(WallsName.equalsIgnoreCase("")){
				WallsName = "No boosters";
			}
			if(PaintName.equalsIgnoreCase("")){
				PaintName = "No boosters";
			}
			if(HGName.equalsIgnoreCase("")){
				HGName = "No boosters";
			}
			if(TNTName.equalsIgnoreCase("")){
				TNTName = "No boosters";
			}
			if(VAMPName.equalsIgnoreCase("")){
				VAMPName = "No boosters";
			}
			if(Wall3Name.equalsIgnoreCase("")){
				Wall3Name = "No boosters";
			}
			if(ArcadeName.equalsIgnoreCase("")){
				ArcadeName = "No boosters";
			}
			if(ArenaName.equalsIgnoreCase("")){
				ArenaName = "No boosters";
			}
			if(UHCName.equalsIgnoreCase("")){
				UHCName = "No boosters";
			}
			if(MCGOName.equalsIgnoreCase("")){
				MCGOName = "No boosters";
			}
			if(WarName.equalsIgnoreCase("")){
				WarName = "No boosters";
			}
			if(KartName.equalsIgnoreCase("")){
				KartName = "No boosters";
			}
			if(SkyName.equalsIgnoreCase("")){
				SkyName = "No boosters";
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		BoostersStatsShow.add(new JLabel("Quake -", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Player: " + QuakeName, SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Time remaining: " + (int) (Boosters.timeLeft.get(2l) / 60) + "min(s) " + (int) (Boosters.timeLeft.get(2l) % 60) + "sec(s)", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Walls -", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Player: " + WallsName, SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Time remaining: " + (int) (Boosters.timeLeft.get(3l) / 60) + "min(s) " + (int) (Boosters.timeLeft.get(3l) % 60) + "sec(s)", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Paintball -", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Player: " + PaintName, SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Time remaining: " + (int) (Boosters.timeLeft.get(4l) / 60) + "min(s) " + (int) (Boosters.timeLeft.get(4l) % 60) + "sec(s)", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Blitz SG -", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Player: " + HGName, SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Time remaining: " + (int) (Boosters.timeLeft.get(5l) / 60) + "min(s) " + (int) (Boosters.timeLeft.get(5l) % 60) + "sec(s)", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("TNT Games -", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Player: " + TNTName, SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Time remaining: " + (int) (Boosters.timeLeft.get(6l) / 60) + "min(s) " + (int) (Boosters.timeLeft.get(6l) % 60) + "sec(s)", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("VampireZ -", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Player: " + VAMPName, SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Time remaining: " + (int) (Boosters.timeLeft.get(7l) / 60) + "min(s) " + (int) (Boosters.timeLeft.get(7l) % 60) + "sec(s)", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Mega Walls -", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Player: " + Wall3Name, SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Time remaining: " + (int) (Boosters.timeLeft.get(13l) / 60) + "min(s) " + (int) (Boosters.timeLeft.get(13l) % 60) + "sec(s)", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Arcade -", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Player: " + ArcadeName, SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Time remaining: " + (int) (Boosters.timeLeft.get(14l) / 60) + "min(s) " + (int) (Boosters.timeLeft.get(14l) % 60) + "sec(s)", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Arena -", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Player: " + ArenaName, SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Time remaining: " + (int) (Boosters.timeLeft.get(17l) / 60) + "min(s) " + (int) (Boosters.timeLeft.get(17l) % 60) + "sec(s)", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Ultimate Hardcore -", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Player: " + UHCName, SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Time remaining: " + (int) (Boosters.timeLeft.get(20l) / 60) + "min(s) " + (int) (Boosters.timeLeft.get(20l) % 60) + "sec(s)", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Cops and Crims -", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Player: " + MCGOName, SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Time remaining: " + (int) (Boosters.timeLeft.get(21l) / 60) + "min(s) " + (int) (Boosters.timeLeft.get(21l) % 60) + "sec(s)", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Warlords -", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Player: " + WarName, SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Time remaining: " + (int) (Boosters.timeLeft.get(23l) / 60) + "min(s) " + (int) (Boosters.timeLeft.get(23l) % 60) + "sec(s)", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Turno Kart Racers -", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Player: " + KartName, SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Time remaining: " + (int) (Boosters.timeLeft.get(25l) / 60) + "min(s) " + (int) (Boosters.timeLeft.get(25l) % 60) + "sec(s)", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Skywars -", SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Player: " + SkyName, SwingConstants.CENTER));
		BoostersStatsShow.add(new JLabel("Time remaining: " + (int) (Boosters.timeLeft.get(51l) / 60) + "min(s) " + (int) (Boosters.timeLeft.get(51l) % 60) + "sec(s)", SwingConstants.CENTER));
		
		gameStatsSection.add(BoostersStatsShow);
		gameStatsSection.add(back);
		
		c.add("North", info);
		c.add("Center", stats);
		c.add("South", gameStatsSection);
		setVisible(true);
	}
	
	public void makeGuildLayout(){
		c.removeAll();
		
		gameStatsSection.removeAll();
		GuildShow.removeAll();
		GuildOver.removeAll();
		pack();
		setSize(1200, 500);
		StringBuilder playerList = new StringBuilder();
		
		for(String s : GuildUtils.players){
			playerList.append(" [" + GuildUtils.guildRanks.get(s) + "] " + s).append(",");
		}
		if(playerList.toString().contains(",")){
		playerList.deleteCharAt(playerList.lastIndexOf(","));
		}
		GuildShow.add(new JLabel("Guild Name: " + GuildUtils.guildName));
		GuildShow.add(new JLabel("Member Size Level: " + GuildUtils.memberSizeLevel));
		GuildShow.add(new JLabel("Current Coins: " + GuildUtils.guildCoins));
		GuildShow.add(new JLabel("Overall Coins: " + GuildUtils.overallCoins));
		GuildShow.add(new JLabel("Bank Level: " + GuildUtils.bankLevel));
		
		GuildOver.add(GuildShow);
		GuildOver.add(new JLabel(playerList + ""));
		
		gameStatsSection.add(GuildOver);
		gameStatsSection.add(back);
		gameStatsSection.revalidate();
		
		c.add("North", info);
		c.add("Center", stats);
		c.add("South", gameStatsSection);
		setVisible(true);
	}
	
	public void makeFriendsLayout(){
		c.removeAll();
		
		gameStatsSection.removeAll();
		FriendsShow.removeAll();
		
		StringBuilder playerList = new StringBuilder();
		
		int i = 0;
		for(String s : HypixelAPIConnector.friends){
			i++;
			playerList.append(i + ": " + s + "\n");
		}

		JTextArea label = new JTextArea(playerList + "");
		label.setPreferredSize(new Dimension(1200, i * 17));
		label.revalidate();
        JScrollPane scrollPane = new JScrollPane(label);
        scrollPane.setPreferredSize(new Dimension(1200, 50));
        scrollPane.revalidate();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setWheelScrollingEnabled(true);
        FriendsShow.add(scrollPane);
        
		gameStatsSection.add(FriendsShow);
		gameStatsSection.add(back);
		
		c.add("North", info);
		c.add("Center", stats);
		c.add("South", gameStatsSection);
		setVisible(true);
	}
	
	public void setupActionListeners(){
		seeStat.addActionListener(this);
		seeFriends.addActionListener(this);
		seeGuild.addActionListener(this);
		seeBoosters.addActionListener(this);
		Submit.addActionListener(this);
		userName.addActionListener(this);
		Skywars.addActionListener(this);
		Mega.addActionListener(this);
		Walls.addActionListener(this);
		Paintball.addActionListener(this);
		HungerGames.addActionListener(this);
		MCGO.addActionListener(this);
		Arena.addActionListener(this);
		back.addActionListener(this);
	}
	
	public void doIconImage(){
		   Image im = null;
		    try {
		    im = ImageIO.read(getClass().getResource("/HypixelSheild.png"));
		    } catch (IOException ex) {
		    	ex.printStackTrace();
		    }
		    setIconImage(im);
	}

}
