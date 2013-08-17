package au.net.huni.migration;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.StatementCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
		"classpath:/META-INF/spring/applicationContext.xml", 
		"classpath:/META-INF/spring-test/applicationContext-test.xml"})
@Ignore // Not relevant yet.
public class WhenFirstVersionSystemIsBootstrapped {

	@Autowired
	private DataSource dataSource;

	@Test
	public void authorTableShouldExist() throws SQLException {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		ResultSetMetaData resultSetMetaData = (ResultSetMetaData) jdbcTemplate
				.execute(new StatementCallback<ResultSetMetaData>() {

					public ResultSetMetaData doInStatement(Statement statement)
							throws SQLException, DataAccessException {
						return statement.executeQuery("SELECT * FROM author").getMetaData();
					}
				});
		assertThat(resultSetMetaData.getTableName(2), is("AUTHOR"));
		assertThat(resultSetMetaData.getColumnName(1), is("ID"));
		assertThat(resultSetMetaData.getColumnName(2), is("FIRSTNAME"));
		assertThat(resultSetMetaData.getColumnName(3), is("LASTNAME"));
		assertThat(resultSetMetaData.getColumnName(4), is("BIRTHDATE"));
	}

	@Test
	public void bookTableShouldExist() throws SQLException {

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		ResultSetMetaData resultSetMetaData = (ResultSetMetaData) jdbcTemplate
				.execute(new StatementCallback<ResultSetMetaData>() {

					public ResultSetMetaData doInStatement(
							final Statement statement) throws SQLException,
							DataAccessException {
						return statement.executeQuery("SELECT * FROM book").getMetaData();
					}
				});
		assertThat(resultSetMetaData.getTableName(2), is("BOOK"));
		assertThat(resultSetMetaData.getColumnName(1), is("ID"));
		assertThat(resultSetMetaData.getColumnName(2), is("TITLE"));
		assertThat(resultSetMetaData.getColumnName(3), is("PUBLICATIONDATE"));
		assertThat(resultSetMetaData.getColumnName(4), is("NUMBEROFPAGES"));
		assertThat(resultSetMetaData.getColumnName(5), is("AUTHOR_ID"));
	}
}
