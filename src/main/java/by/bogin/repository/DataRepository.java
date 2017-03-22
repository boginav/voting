package by.bogin.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import by.bogin.entity.OptionObject;
import by.bogin.entity.ThemeObject;
import by.bogin.entity.VoiceObject;

@org.springframework.stereotype.Repository("dataRespitory")
@Component
public class DataRepository {

	@Autowired
	protected JdbcOperations jdbcOperations;

	public Long persist(ThemeObject object) {

		if (object.getId() == null) {
			final String INSERT_SQL = "INSERT INTO themetable(themeName) VALUES (?);";

			final String themeName = object.getThemeName();
			KeyHolder keyHolder = new GeneratedKeyHolder();
			PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {

				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
					PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] { "id" });
					ps.setString(1, themeName);
					return ps;
				}
			};

			jdbcOperations.update(preparedStatementCreator, keyHolder);

			return keyHolder.getKey().longValue();
		} else {

			Object[] params = new Object[] { object.getActive(), object.getId() };
			int[] types = new int[] { Types.BOOLEAN, Types.INTEGER };

			jdbcOperations.update("UPDATE themetable SET active=? WHERE id=?;", params, types);

			return object.getId();
		}

	}

	public void persist(OptionObject object) {

		Object[] params = new Object[] { object.getOptionName(), object.getIdTheme() };
		int[] types = new int[] { Types.VARCHAR, Types.INTEGER };

		jdbcOperations.update("INSERT INTO optionstable(optionName, idTheme) VALUES (?,?);", params, types);
	}

	public void persist(VoiceObject object) {

		Object[] params = new Object[] { object.getIdTheme(), object.getIdOption() };
		int[] types = new int[] { Types.INTEGER, Types.INTEGER };

		jdbcOperations.update("INSERT INTO voicetable( idTheme, idOption) VALUES (?,?);", params, types);
	}

	public ThemeObject findThemeById(Long id) {

		Object[] params = new Object[] { id };
		int[] types = new int[] { Types.INTEGER };
		SqlRowSet rowSet = jdbcOperations.queryForRowSet("SELECT * FROM themetable p WHERE p.id= ?;", params, types);

		if (rowSet.next()) {
			rowSet.getString("themeName");
			return new ThemeObject(rowSet.getLong("id"), rowSet.getString("themeName"), rowSet.getBoolean("active"));

		} else {
			return null;
		}
	}

	public Set<OptionObject> findOptionById(Long idTheme) {

		Set<OptionObject> result = new HashSet<>();
		Object[] params = new Object[] { idTheme };
		int[] types = new int[] { Types.INTEGER };
		SqlRowSet rowSet = jdbcOperations.queryForRowSet("SELECT * FROM optionstable p WHERE p.idTheme= ?;", params,
				types);

		while (rowSet.next()) {
			result.add(
					new OptionObject(rowSet.getLong("id"), rowSet.getString("optionName"), rowSet.getLong("idTheme")));
		}
		return result;
	}

	public Map<Long, Long> findVoiceStatistic(Long idTheme) {

		Map<Long, Long> result = new HashMap<>();
		Object[] params = new Object[] { idTheme };
		int[] types = new int[] { Types.INTEGER };
		SqlRowSet rowSet = jdbcOperations.queryForRowSet(
				"SELECT COUNT(*)  AS countOfVoice, idOption  FROM voicetable p WHERE p.idTheme= ? GROUP BY idOption;",
				params, types);

		while (rowSet.next()) {
			result.put(rowSet.getLong("idOption"), rowSet.getLong("countOfVoice"));
		}
		return result;
	}

}
