package main;

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
			log.debug("==========Oracle DataBase==========");
			log.debug("==========DB Ping Test Start==========");
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String ID = "mainline";
			String PWD = "mainline";
			String PORTNO = "1521";

			String Query = "jdbc:oracle:thin:@localhost:" + PORTNO + ":Orcl";

			try {
				conn = DriverManager.getConnection(Query, ID, PWD);
				log.debug("==========DB Login Test Complete==========");
			} catch (SQLException e) {
				log.debug("==========DB Login Test Fail==========");
				e.printStackTrace();
			}
			
			System.out.println("========== SQL Query Start ==========");
			stmt = conn.createStatement();
			SQL = "delete from parse";
			stmt.execute(SQL);
			System.out.println("========== parse Table Data Delete ==========");
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


				}

				element.setAb(as.toString().replace("\n", "<p>").replace(" ", "&nbsp"));

			} catch (SQLException e) {
				e.printStackTrace();
			}

		} catch (ClassNotFoundException e) {
			log.debug("=========DB Ping Test Fail=======");
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
		boolean asBool = true;
		code += 1;
		String text = element.getText();
		text = "'"+text.replace("\'", "''").replaceAll("\"", "\"\"");

		text.replace("\'", "''");
		int textLength = text.length();
		String text_Query="";
		if(textLength > 1000) {
			for(int i=0; i<(textLength/1000)+1;i++) {
				int start =1000*i;
				int end = Math.min(textLength, start+1000);
				if(i==0) {
					text_Query = "TO_CLOB(dbms_lob.substr("+text.substring(start,end)+"',1,1000)) ";
				}else if(i>=1) {
					 String text_add="";
					 text_add = "|| TO_CLOB(dbms_lob.substr('" + text.substring(start, end) + "',"+(i+1)+",1000))";
					 text_Query +=text_add;
				}
			}
			asBool=false;
			text = text_Query;
		}
		
		if(asBool == true) {
			text = text+"'";
		}
		
		if (element.isGwanOrAttached()) {
			//System.out.println("========== GWAN function start ==========");

			ga_data += 1;
			SQL = "insert into parse (CODE , STAGE , GA_DATA , JO_DATA , TXT ) values (" + code + "," + level + ","
					+ ga_data + "," + text_data + "," + text + ")";
			//System.out.println(SQL);
		}

		else if (element.isJo()) {
			//System.out.println("========== JO function start ==========");
			jo_data += 1;

			SQL = "insert into parse (CODE , STAGE , GA_DATA , JO_DATA , TXT ) values (" + code + "," + level + ","
					+ text_data + "," + jo_data + "," +text+")";
			//System.out.println(SQL);
		} else {
			//System.out.println("========== ELSE function start ==========");
			SQL = "insert into parse (CODE , STAGE , GA_DATA , JO_DATA , TXT ) values (" + code + "," + level + ","
					+ text_data + "," + text_data + "," +text+")";
			System.out.println(SQL);
		}

		stmt.execute(SQL);
		for (AbstractElement child : element.childList) {

			id(child);

		}
		asBool = true;
	}
	}
