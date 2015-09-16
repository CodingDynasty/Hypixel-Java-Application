package com.src.codingdynasty;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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

public class HypixelAPIConnector {

	private static final String PROFILE_URL = "https://api.hypixel.net/";
	private static final String APIKey = "a98f936c-9d03-4ac3-b08d-e88e3918bb04";
	private static final JSONParser jsonParser = new JSONParser();
	public static ArrayList<String> friends = new ArrayList<String>();
	public static String guildId = "";
	public static String guildName = "";

	public static String getFriends(UUID playerUUID) throws Exception {
		if (playerUUID != null) {
			String s = playerUUID.toString();
			s = s.replace("-", "");
			String reply = null;

			HttpURLConnection connection;

			try {
				connection = createConnectionUuid("friends", s);

				if (connection.getResponseCode() == 200) {
					String myString = IOUtils.toString(
							connection.getInputStream(), "UTF-8");
					connection.disconnect();
					JSONObject jsonObject = (JSONObject) jsonParser
							.parse(myString);
					JSONArray friendArray = (JSONArray) jsonObject
							.get("records");

					for (int i = 0; i < friendArray.size(); i++) {

						JSONObject a = (JSONObject) friendArray.get(i);
						String uuid = (String) a.get("uuidReceiver");
						if (uuid != null) {
							if (!uuid.equals(jFrame.uuidRaw)) {

								String name = new NameFetcher(uuid).call();

								friends.add(name);

							}
						}

					}

				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return reply;
		} else {
			return null;
		}
	}

	public static String getPlayer(UUID playerUUID) throws Exception {
		if (playerUUID != null) {
			String s = playerUUID.toString();
			s = s.replace("-", "");
			String reply = null;

			HttpURLConnection connection;

			try {
				connection = createConnectionUuid("player", s);

				if (connection.getResponseCode() == 200) {
					String myString = IOUtils.toString(
							connection.getInputStream(), "UTF-8");
					connection.disconnect();
					JSONObject jsonObject = (JSONObject) jsonParser
							.parse(myString);
					JSONObject player = (JSONObject) jsonObject.get("player");

					Long karma = (Long) player.get("karma");
					Long NetworkLevel = (Long) player.get("networkLevel");
					Long NetworkExp = (Long) player.get("networkExp");
					String rank = (String) player.get("rank");
					if (rank == null) {
						rank = (String) player.get("packageRank");
						if (rank == null) {
							rank = "Player";
						}
					}

					PlayerUtils.karma = karma;
					PlayerUtils.Level = NetworkLevel;
					PlayerUtils.NetworkExp = NetworkExp;
					PlayerUtils.rank = rank;

				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return reply;
		} else {
			return null;
		}
	}

	public static String getGuild(UUID playerUUID) throws Exception {
		if (playerUUID != null) {
			String s = playerUUID.toString();
			s = s.replace("-", "");
			String reply = null;

			HttpURLConnection connection;

			Boolean hasGuild = false;

			try {
				connection = createConnectionByUuid("findGuild", s);

				if (connection.getResponseCode() == 200) {
					String myString = IOUtils.toString(
							connection.getInputStream(), "UTF-8");
					connection.disconnect();

					JSONObject getId = (JSONObject) jsonParser.parse(myString);
					String id = (String) getId.get("guild");

					guildId = id;

					hasGuild = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (hasGuild) {
				try {
					connection = createConnectionById("guild", guildId);

					if (connection.getResponseCode() == 200) {

						String myString = IOUtils.toString(
								connection.getInputStream(), "UTF-8");
						connection.disconnect();

						JSONObject getId = (JSONObject) jsonParser
								.parse(myString);
						JSONObject guildS = (JSONObject) getId.get("guild");
						if (guildS != null) {
							String guildN = (String) guildS.get("name");
							Long Coins = (Long) guildS.get("coins");
							Long memberSizeLevel = (Long) guildS.get("memberSizeLevel");
							Long bankLevel = (Long) guildS.get("bankSizeLevel");
							Long overallCoins = (Long) guildS.get("coinsEver");

							JSONArray members = (JSONArray) guildS
									.get("members");

							for (int i = 0; i < members.size(); i++) {

								JSONObject o = (JSONObject) members.get(i);
								String uuid = (String) o.get("uuid");
								String rank = (String) o.get("rank");
								String name = new NameFetcher(uuid).call();

								GuildUtils.players.add(name);
								GuildUtils.guildRanks.put(name, rank);
							}

							GuildUtils.guildName = guildN;
							GuildUtils.guildCoins = Coins;
							GuildUtils.memberSizeLevel = memberSizeLevel;
							GuildUtils.bankLevel = bankLevel;
							GuildUtils.overallCoins = overallCoins;

						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
			return reply;
		} else {
			return null;
		}
	}

	public static String getStats(UUID playerUUID) throws Exception {
		if (playerUUID != null) {
			String s = playerUUID.toString();
			s = s.replace("-", "");
			String reply = null;

			HttpURLConnection connection;

			try {
				connection = createConnectionUuid("player", s);

				if (connection.getResponseCode() == 200) {
					String myString = IOUtils.toString(
							connection.getInputStream(), "UTF-8");
					connection.disconnect();

					JSONObject getId = (JSONObject) jsonParser.parse(myString);
					JSONObject player = (JSONObject) getId.get("player");
					JSONObject stats = (JSONObject) player.get("stats");
					JSONObject Skywars = null;
					if(stats != null){
					Skywars = (JSONObject) stats.get("SkyWars");
					}else{
						return "";
					}
					if (Skywars != null) {
						Long overallKills = (Long) Skywars.get("kills");
						Long overallDeaths = (Long) Skywars.get("deaths");
						Long soloWins = (Long) Skywars.get("wins_solo");
						Long soloGames = (Long) Skywars.get("losses_solo");
						Long soloKills = (Long) Skywars.get("kills_solo");
						Long soloDeaths = (Long) Skywars.get("deaths_solo");
						Long teamWins = (Long) Skywars.get("wins_team");
						Long teamLosses = (Long) Skywars.get("losses_team");
						Long teamKills = (Long) Skywars.get("kills_team");
						Long teamDeaths = (Long) Skywars.get("deaths_team");
						Long SkyCoins = (Long) Skywars.get("coins");
						Long souls = (Long) Skywars.get("souls");
						Long Skywins = (Long) Skywars.get("wins");
						Long Skylosses = (Long) Skywars.get("losses");

						SkywarsStats.overallKills = overallKills;
						SkywarsStats.overallDeaths = overallDeaths;
						SkywarsStats.winsSolo = soloWins;
						SkywarsStats.lossesSolo = soloGames;
						SkywarsStats.killsSolo = soloKills;
						SkywarsStats.deathsSolo = soloDeaths;
						SkywarsStats.teamWins = teamWins;
						SkywarsStats.teamLosses = teamLosses;
						SkywarsStats.teamKills = teamKills;
						SkywarsStats.teamDeaths = teamDeaths;
						SkywarsStats.SkyCoins = SkyCoins;
						SkywarsStats.souls = souls;
						SkywarsStats.wins = Skywins;
						SkywarsStats.losses = Skylosses;

					}

					JSONObject Mega = (JSONObject) stats.get("Walls3");

					if (Mega != null) {

						Long wins = (Long) Mega.get("wins");
						Long losses = (Long) Mega.get("losses");
						Long megaCoins = (Long) Mega.get("coins");
						Long megaKills = (Long) Mega.get("kills");
						Long megaDeaths = (Long) Mega.get("deaths");
						String megaKit = (String) Mega.get("chosen_class");
						Long finalKills = (Long) Mega.get("finalKills");
						Long finalDeaths = (Long) Mega.get("finalDeaths");

						MegaStats.wins = wins;
						MegaStats.losses = losses;
						MegaStats.megaCoins = megaCoins;
						MegaStats.kills = megaKills;
						MegaStats.deaths = megaDeaths;
						MegaStats.currentKit = megaKit;
						MegaStats.finalKills = finalKills;
						MegaStats.finalDeaths = finalDeaths;

					}

					JSONObject Walls = (JSONObject) stats.get("Walls");

					if (Walls != null) {

						Long wins = (Long) Walls.get("wins");
						Long losses = (Long) Walls.get("losses");
						Long kills = (Long) Walls.get("kills");
						Long deaths = (Long) Walls.get("deaths");
						Long WallCoin = (Long) Walls.get("coins");

						WallsStats.wins = wins;
						WallsStats.losses = losses;
						WallsStats.kills = kills;
						WallsStats.deaths = deaths;
						WallsStats.coins = WallCoin;

					}

					JSONObject Paintball = (JSONObject) stats.get("Paintball");

					if (Paintball != null) {

						Long wins = (Long) Paintball.get("wins");
						Long kills = (Long) Paintball.get("kills");
						Long deaths = (Long) Paintball.get("deaths");
						Long killstreak = (Long) Paintball.get("killstreaks");
						Long shotsFired = (Long) Paintball.get("shots_fired");
						Long pCoins = (Long) Paintball.get("coins");

						PaintballStats.wins = wins;
						PaintballStats.kills = kills;
						PaintballStats.deaths = deaths;
						PaintballStats.killstreak = killstreak;
						PaintballStats.shotsFired = shotsFired;
						PaintballStats.coins = pCoins;

					}

					JSONObject MCGO = (JSONObject) stats.get("MCGO");

					if (MCGO != null) {

						Long wins = (Long) MCGO.get("game_wins");
						Long kills = (Long) MCGO.get("kills");
						Long deaths = (Long) MCGO.get("deaths");
						Long copKills = (Long) MCGO.get("cop_kills");
						Long criminal_kills = (Long) MCGO.get("criminal_kills");
						Long MCGOCoins = (Long) MCGO.get("coins");
						Long bombsPlanted = (Long) MCGO.get("bombs_planted");
						Long bombsDefused = (Long) MCGO.get("bombs_defused");

						McgoStats.kills = kills;
						McgoStats.copKills = copKills;
						McgoStats.criminalKills = criminal_kills;
						McgoStats.coins = MCGOCoins;
						McgoStats.deaths = deaths;
						McgoStats.wins = wins;
						McgoStats.bombsDefused = bombsDefused;
						McgoStats.bombsPlanted = bombsPlanted;

					}

					JSONObject HungerGames = (JSONObject) stats
							.get("HungerGames");

					if (HungerGames != null) {

						Long wins = (Long) HungerGames.get("wins");
						Long kills = (Long) HungerGames.get("kills");
						Long deaths = (Long) HungerGames.get("deaths");
						Long WallCoin = (Long) HungerGames.get("coins");
						String aura = (String) HungerGames.get("aura");
						String taunt = (String) HungerGames.get("chosen_taunt");

						HungerGamesStats.wins = wins;
						HungerGamesStats.kills = kills;
						HungerGamesStats.deaths = deaths;
						HungerGamesStats.coins = WallCoin;
						HungerGamesStats.aura = aura;
						HungerGamesStats.taunt = taunt;

					}

					JSONObject Arena = (JSONObject) stats.get("Arena");

					if (Arena != null) {

						Long v1_wins = (Long) Arena.get("wins_1v1");
						Long v2_wins = (Long) Arena.get("wins_2v2");
						Long v4_wins = (Long) Arena.get("wins_4v4");
						Long v1_kills = (Long) Arena.get("kills_1v1");
						Long v2_kills = (Long) Arena.get("kills_2v2");
						Long v4_kills = (Long) Arena.get("kills_4v4");
						Long v1_losses = (Long) Arena.get("losses_1v1");
						Long v2_losses = (Long) Arena.get("losses_2v2");
						Long v4_losses = (Long) Arena.get("losses_4v4");

						ArenaStats.v1kills = v1_kills;
						ArenaStats.v2kills = v2_kills;
						ArenaStats.v4kills = v4_kills;
						ArenaStats.v1wins = v1_wins;
						ArenaStats.v2wins = v2_wins;
						ArenaStats.v4wins = v4_wins;
						ArenaStats.v1losses = v1_losses;
						ArenaStats.v2losses = v2_losses;
						ArenaStats.v4losses = v4_losses;

					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			return reply;
		} else {
			return null;
		}
	}

	public static String getBoosters() throws Exception {
		String reply = null;

		HttpURLConnection connection;

		try {
			connection = createConnectionBoosters();

			if (connection.getResponseCode() == 200) {
				String myString = IOUtils.toString(connection.getInputStream(),
						"UTF-8");
				connection.disconnect();
				JSONObject jsonObject = (JSONObject) jsonParser.parse(myString);

				JSONArray jsonArray = (JSONArray) jsonObject.get("boosters");

				for (int i = 0; i < jsonArray.size(); i++) {

					JSONObject arrayObject = (JSONObject) jsonArray.get(i);
					Long gameId = (Long) arrayObject.get("gameType");
					String playerUuid = (String) arrayObject
							.get("purchaserUuid");
					Long timeRemaining = (Long) arrayObject.get("length");

					if (Boosters.playerUuid.containsKey(gameId)) {

					} else {
						Boosters.playerUuid.put(gameId, playerUuid);
						Boosters.timeLeft.put(gameId, timeRemaining);
					}
				}
				for(long key = 0l;key < 53l; key++){
					if(!Boosters.playerUuid.containsKey(key)){
						Boosters.playerUuid.put(key, "No Boosters");
						Boosters.timeLeft.put(key, 0l);
					}
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return reply;

	}

	private static HttpURLConnection createConnectionUuid(String type,
			String uuid) throws Exception {
		URL url = new URL(PROFILE_URL + type + "?key=" + APIKey + "&uuid="
				+ uuid);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setUseCaches(true);
		connection.addRequestProperty("User-Agent", "Mozilla/4.76");
		connection.setDoOutput(true);
		return connection;
	}

	private static HttpURLConnection createConnectionByUuid(String type,
			String uuid) throws Exception {
		URL url = new URL(PROFILE_URL + type + "?key=" + APIKey + "&byUuid="
				+ uuid);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setUseCaches(true);
		connection.addRequestProperty("User-Agent", "Mozilla/4.76");
		connection.setDoOutput(true);
		return connection;
	}

	private static HttpURLConnection createConnectionById(String type,
			String uuid) throws Exception {
		URL url = new URL(PROFILE_URL + type + "?key=" + APIKey + "&id=" + uuid);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setUseCaches(true);
		connection.addRequestProperty("User-Agent", "Mozilla/4.76");
		connection.setDoOutput(true);
		return connection;
	}

	private static HttpURLConnection createConnectionBoosters()
			throws Exception {
		URL url = new URL(PROFILE_URL + "boosters" + "?key=" + APIKey);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setUseCaches(true);
		connection.addRequestProperty("User-Agent", "Mozilla/4.76");
		connection.setDoOutput(true);
		return connection;
	}

}
