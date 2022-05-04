package net.siegerpg.siege.core.skills;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.siegerpg.siege.core.Core;
import net.siegerpg.siege.core.database.DatabaseManager;
import net.siegerpg.siege.core.skills.archer.*;
import net.siegerpg.siege.core.skills.mage.*;
import net.siegerpg.siege.core.skills.warrior.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

public enum SkillData {

	;

	static HashMap< UUID, JsonObject > skillCache = new HashMap<>();

	static HashMap< UUID, Integer > skillPointCache = new HashMap<>();

	static JsonParser jsonParser = new JsonParser();

	/**
	 * Gets the skill points of a player
	 *
	 * @param player The player to get the info of
	 *
	 * @return Returns the skill points
	 */
	@NotNull
	public static Integer getSkillPoints(OfflinePlayer player) {

		if (skillPointCache.containsKey(player.getUniqueId())) {
			return skillPointCache.get(player.getUniqueId());
		}
		try (var conn = DatabaseManager.INSTANCE.getConnection()) {
			assert conn != null;
			var stmt = conn.prepareStatement("SELECT skill_points FROM userData WHERE uuid=?");
			stmt.setString(1, player
					.getUniqueId()
					.toString());
			var result = stmt.executeQuery();
			if (result.isBeforeFirst()) {
				// There are no rows so we return 0
				return 0;
			}
			result.next();
			Integer skillData = result.getInt("skill_points");
			result.close();
			skillPointCache.put(player.getUniqueId(), skillData);
			return skillData;
		} catch (SQLException e) {
			return 0;
		}
	}


	/**
	 * Gets the skill points of a player
	 *
	 * @param player The player to get the info of
	 *
	 * @return Returns the skill points
	 */
	public static void setSkillPoints(OfflinePlayer player, Integer data) {

		skillPointCache.put(player.getUniqueId(), data);
		try (
				var conn = DatabaseManager.INSTANCE.getConnection()
		) {
			assert conn != null;
			var stmt = conn.prepareStatement("UPDATE userData SET skill_points=? WHERE uuid=?");
			stmt.setInt(1, data);
			stmt.setString(2, player
					.getUniqueId()
					.toString());
			stmt.executeUpdate();

		} catch (
				SQLException ignored) {
		}

	}

	/**
	 * Gets the skill level of a player
	 *
	 * @param player The player to get the skill of
	 * @param skill  The skill to get the level of
	 *
	 * @return The level (can be null if the player doesn't have that skill)
	 */
	public static Integer getSkillLevel(OfflinePlayer player, Skill skill) {

		JsonElement data = getSkillData(player)
				.get(skill.getIdentifier());
		if (data == null) {
			return 0;
		} else {
			return data.getAsInt();
		}
	}

	/**
	 * Sets the skill level of a player to something
	 *
	 * @param player The player
	 * @param skill  The skill
	 * @param level  The skill level
	 */
	public static void setSkillLevel(OfflinePlayer player, Skill skill, int level) {

		JsonObject data = getSkillData(player);
		data.addProperty(skill.getIdentifier(), level);
		setSkillData(player, data);
	}

	@NotNull
	public static JsonObject getSkillData(OfflinePlayer player) {

		if (skillCache.containsKey(player.getUniqueId())) {
			return skillCache.get(player.getUniqueId());
		}

		try (var conn = DatabaseManager.INSTANCE.getConnection()) {
			assert conn != null;
			var stmt = conn.prepareStatement("SELECT skill_data FROM userData WHERE uuid=?");
			stmt.setString(1, player
					.getUniqueId()
					.toString());
			var result = stmt.executeQuery();
			if (result.isBeforeFirst()) {
				// There are no rows so we return an empty json object
				return jsonParser
						.parse("{}")
						.getAsJsonObject();
			}
			result.next();
			String rawSkills = result.getString("skill_data");
			result.close();
			JsonObject jsonObject = jsonParser
					.parse(rawSkills)
					.getAsJsonObject();
			skillCache.put(player.getUniqueId(), jsonObject);
			return jsonObject;
		} catch (SQLException e) {
			return jsonParser
					.parse("{}")
					.getAsJsonObject();
		}
	}

	/**
	 * Sets the skill data (asynchronously)
	 *
	 * @param player The player that owns the data
	 * @param data   The skill data
	 */
	public static void setSkillData(OfflinePlayer player, JsonObject data) {

		skillCache.put(player.getUniqueId(), data);
		Bukkit
				.getScheduler()
				.runTaskAsynchronously(Core.plugin(), () -> {
					try (var conn = DatabaseManager.INSTANCE.getConnection()) {
						assert conn != null;
						var stmt = conn.prepareStatement(
								"UPDATE userData SET skill_data=? WHERE uuid=?");
						stmt.setString(1, data.getAsString());
						stmt.setString(2, player
								.getUniqueId()
								.toString());
						stmt.executeUpdate();

					} catch (SQLException ignored) {
					}
				});
	}

	/**
	 * If a player has unlocked a skill
	 *
	 * @param player The player to check that on
	 * @param skill  The skill
	 *
	 * @return Whether or not the skill is unlocked
	 */
	public static boolean hasSkillUnlocked(@NotNull Player player, @NotNull Skill skill) {

		JsonObject data = getSkillData(player);
		JsonElement elem = data.get(skill.getIdentifier());
		return elem != null;
	}

	public static SkillClass getSkillClass(Player player) {
		if (hasSkillUnlocked(player, new CriticalShot()) || hasSkillUnlocked(player, new AchillesHeel())) {
			return SkillClass.ARCHER;
		} else if (hasSkillUnlocked(player, new Slash()) || hasSkillUnlocked(player, new Lunge())) {
			return SkillClass.WARRIOR;
		} else if (hasSkillUnlocked(player, new IceBolt()) || hasSkillUnlocked(player, new Hex()) || hasSkillUnlocked(player, new Invigorate())) {
			return SkillClass.MAGE;
		}
		return null;
	}

	/**
	 * A player can unlock a skill if the parent skill is already unlocked.
	 *
	 * @param player The player to check
	 * @param skill  The skill
	 *
	 * @return Whether the parent is unlocked
	 */
	public static boolean canUnlockSkill(@NotNull Player player, @NotNull Skill skill) {

		var parent = skill.getParent();
		if (parent == null) return true;
		return hasSkillUnlocked(player, parent);
	}

}
