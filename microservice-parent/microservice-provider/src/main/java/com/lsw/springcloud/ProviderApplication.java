package com.lsw.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.sql.*;

@SpringBootApplication
@EnableEurekaClient // 本服务启动后会自动注册进eureka服务中
public class ProviderApplication {
	// 简例
	public class MyFirstDatabaseConnection {
		public void main() throws SQLException, ClassNotFoundException {
			String url = "jdbc:mysql://localhost:3306/lsw";
////		Class.forName("com.mysql.jdbc.Driver"); // Class.forName() loads a class. This lets DriverManager use a Driver, even if the JAR doesn’t have a META-INF/service/java.sql.Driver fle.
			// 即使不关闭IO资源，也不会将程序一直挂起。甚至再次请求也不会存在资源被占用异常.
			try (
//				Connection conn = DriverManager.getConnection(url); // 获取方式1
					Connection conn = DriverManager.getConnection( // 获取方式2
							"jdbc:mysql://localhost:3306/lsw", "lsw", "123456");
					Statement stmt = conn.createStatement();
//					Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY); // (ResultSet类型,并发方式) P571,P572 一览
					// 方式一:R
					ResultSet rs = stmt.executeQuery("select * from collect_sms")) {
//				// 方式二:CUD
//				int result = stmt.executeUpdate("insert into species values(10, 'Deer', 3)");
//				System.out.println(result);
//				result = stmt.executeUpdate("update species set name = '' where name = 'None'");
//				System.out.println(result);
//				result = stmt.executeUpdate("delete from species where id = 10");
//				System.out.println(result);
//				// 方式三 :CURD
//				String sql = ""; // 任意CRUDsql都可以. boolean isResultSet = stmt.execute(sql);
//				ResultSet rs2 = stmt.getResultSet();
//				int result2 = stmt.getUpdateCount();
				while (rs.next()) { // JDBC starts counting with one rather than zero
					System.out.println(rs.getInt("SID"));
				}
//				System.out.println(rs.getString(1));
//				System.out.println(rs.getInt(1)); // 获取第一条数据
//				System.out.println(rs.getInt(0)); // Error
			}
		}

		public void testSearchUpdateAtOneTime() throws SQLException {
			try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/lsw", "lsw", "123456");
					Statement stmt = conn.createStatement()) {
				// stmt只能执行一次，类似stream.
				// 希望之星多条时，可以如boolean result=statement.execute("select * from account;insert into account values(null,'55',265);select id as iid from account;" + ""); 关键是要判断好结果集.
				ResultSet rs = stmt.executeQuery("select count(*) from usertest");
//				stmt.executeQuery("select count(*) from usertest"); // 重复查询报错 ：  java.sql.SQLException: Operation not allowed after ResultSet closed
//				int num = stmt.executeUpdate("INSERT INTO usertest(NAME) VALUES('lisw')");
				int num2 = stmt.executeUpdate("INSERT INTO usertest(NAME) VALUES('lisw')"); // java.sql.SQLException: Operation not allowed after ResultSet closed
				rs.next();
				System.out.println(rs.getInt(1));
			}
		}
	}

	public static class temp {
		static Connection newConnection = null;

		public static Connection getDBConnetion() throws SQLException {
			// try()块在执行完后就关闭con了。因此执行会报错: com.mysql.jdbc.exceptions.jdbc4.MySQLNonTransientConnectionException: No operations allowed after connection closed.
			try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lsw", "lsw", "123456")) {
				newConnection = con;
			}
			return newConnection;
		}

		public static void main() throws SQLException {
			getDBConnetion();
			Statement st = newConnection.createStatement();
			st.executeUpdate("INSERT INTO usertest(NAME) VALUES('lisw4')");
		}
	}

	public static void main(String[] args) throws SQLException, ClassNotFoundException {
//		new ProviderApplication().new MyFirstDatabaseConnection().main();
		new ProviderApplication().new MyFirstDatabaseConnection().testSearchUpdateAtOneTime();
//		SpringApplication.run(ProviderApplication.class, args);
//		temp.main();
	}

}