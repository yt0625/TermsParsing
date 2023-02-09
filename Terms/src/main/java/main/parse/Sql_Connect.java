package main.parse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

import org.slf4j.LoggerFactory;

//ojdbc로 연결
public class Sql_Connect {

	int code;
	int ga_data = 0;
	int jo_data = 0;
	int text_data = 0;
	String SQL;
	String Sql;
	Connection conn = null;
	Statement stmt = null;
	protected static final org.slf4j.Logger log = LoggerFactory.getLogger("Sql_Connect");
	public void input(AbstractElement element) {

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String ID = "root";
			String PWD = "root";
			String PORTNO = "3306";
			String DBNAME = "mariaDB";
			String TIMEZONE = "serverTimezone=UTC";

			String Query = "jdbc:mysql://localhost:" + PORTNO + "/" + DBNAME + "?" + TIMEZONE;

			try {
				conn = DriverManager.getConnection(Query, ID, PWD);
				System.out.println("DB Ping Test complete");			
				log.debug("DB Ping Test Complete");
			} catch (SQLException e) {
			
				log.debug("DB Ping Test Fail");

				e.printStackTrace();
			}

			stmt = conn.createStatement();
			SQL = "set SQL_SAFE_UPDATES = 0";
			stmt.execute(SQL);
			SQL = "delete from parse";
			stmt.execute(SQL);

			// inputData(element);
			id(element);

			PreparedStatement ps = null;
			ResultSet rs = null;
			String sql = "select * from parse";
			StringBuffer as = new StringBuffer();

			try {
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				System.out.println("CODE\tLEVEL\tGA\tJO\tTEXT");
				while (rs.next()) {
					String code = rs.getString(1);
					String level = rs.getString(2);
					String ga_data = rs.getString(3);
					String jo_data = rs.getString(4);
					String text = rs.getString(5);
					as.append(text + "\n");
					System.out.println(code + "\t" + level + "\t" + ga_data + "\t" + jo_data + "\t" + text);
					// System.out.println(text);

				}

				element.setAb(as.toString().replace("\n", "<p>").replace(" ", "&nbsp"));

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (ClassNotFoundException e) {
			System.out.println("DB�������");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void id(AbstractElement element) throws Exception {
		int level = element.getNo();

		code += 1;
		String text = element.getText();
		if (element.isGwanOrAttached()) {

			ga_data += 1;
			SQL = "insert into parse (CODE , LEVEL , GA_DATA , JO_DATA , TEXT ) values (" + code + "," + level + ","
					+ ga_data + "," + text_data + ", '" + text.replace("\'", "\\\'").replaceAll("\"", "\\\"") + "')";

		}

		else if (element.isJo()) {
			jo_data += 1;

			SQL = "insert into parse (CODE , LEVEL , GA_DATA , JO_DATA , TEXT ) values (" + code + "," + level + ","
					+ text_data + "," + jo_data + ", '" + text.replace("\'", "\\\'").replaceAll("\"", "\\\"") + "')";

		} else {

			SQL = "insert into parse (CODE , LEVEL , GA_DATA , JO_DATA , TEXT ) values (" + code + "," + level + ","
					+ text_data + "," + text_data + ", '" + text.replace("\'", "\\\'").replaceAll("\"", "\\\"") + "')";

		}

		stmt.execute(SQL);
		for (AbstractElement child : element.childList) {

			id(child);

		}
	}
}
