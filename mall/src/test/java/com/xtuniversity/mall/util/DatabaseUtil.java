package com.xtuniversity.mall.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
	private final static Logger LOGGER = LoggerFactory.getLogger(DatabaseUtil.class);


	private static final String SQL = "SELECT * FROM ";// 数据库操作

	static {
		try {
			Class.forName(FreemarkerTest.DRIVER);
		} catch (ClassNotFoundException e) {
			LOGGER.error("can not load jdbc driver", e);
		}
	}

	/**
	 * 获取数据库连接
	 *
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			conn = DriverManager.getConnection(FreemarkerTest.URL, FreemarkerTest.USERNAME, FreemarkerTest.PASSWORD);
		} catch (SQLException e) {
			LOGGER.error("get connection failure", e);
		}
		return conn;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				LOGGER.error("close connection failure", e);
			}
		}
	}

	/**
	 * 获取数据库下的所有表名
	 */
	public static List<String> getTableNames() {
		List<String> tableNames = new ArrayList<>();
		Connection conn = getConnection();
		ResultSet rs = null;
		try {
			// 获取数据库的元数据
			DatabaseMetaData db = conn.getMetaData();
			// 从元数据中获取到所有的表名
			rs = db.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				tableNames.add(rs.getString(3));
			}
		} catch (SQLException e) {
			LOGGER.error("getTableNames failure", e);
		} finally {
			try {
				rs.close();
				closeConnection(conn);
			} catch (SQLException e) {
				LOGGER.error("close ResultSet failure", e);
			}
		}
		return tableNames;
	}

	/**
	 * 获取表中所有字段名称
	 * 
	 * @param tableName
	 *            表名
	 * @return
	 */
	public static List<String> getColumnNames(String tableName) {
		List<String> columnNames = new ArrayList<>();
		// 与数据库的连接
		Connection conn = getConnection();
		PreparedStatement pStemt = null;
		String tableSql = SQL + tableName;
		try {
			pStemt = conn.prepareStatement(tableSql);
			// 结果集元数据
			ResultSetMetaData rsmd = pStemt.getMetaData();
			// 表列数
			int size = rsmd.getColumnCount();
			for (int i = 0; i < size; i++) {
				columnNames.add(rsmd.getColumnName(i + 1));
			}
		} catch (SQLException e) {
			LOGGER.error("getColumnNames failure", e);
		} finally {
			if (pStemt != null) {
				try {
					pStemt.close();
					closeConnection(conn);
				} catch (SQLException e) {
					LOGGER.error("getColumnNames close pstem and connection failure", e);
				}
			}
		}
		return columnNames;
	}

	/**
	 * 获取表中所有字段类型
	 * 
	 * @param tableName
	 * @return
	 */
	public static List<String> getColumnTypes(String tableName) {
		List<String> columnTypes = new ArrayList<>();
		// 与数据库的连接
		Connection conn = getConnection();
		PreparedStatement pStemt = null;
		String tableSql = SQL + tableName;
		try {
			pStemt = conn.prepareStatement(tableSql);
			// 结果集元数据
			ResultSetMetaData rsmd = pStemt.getMetaData();
			// 表列数
			int size = rsmd.getColumnCount();
			for (int i = 0; i < size; i++) {
				columnTypes.add(rsmd.getColumnTypeName(i + 1));
			}
		} catch (SQLException e) {
			LOGGER.error("getColumnTypes failure", e);
		} finally {
			if (pStemt != null) {
				try {
					pStemt.close();
					closeConnection(conn);
				} catch (SQLException e) {
					LOGGER.error("getColumnTypes close pstem and connection failure", e);
				}
			}
		}
		return columnTypes;
	}

	/**
	 * 获取表中字段的所有注释
	 * 
	 * @param tableName
	 * @return
	 */
	public static List<String> getColumnComments(String tableName) {
		// 与数据库的连接
		Connection conn = getConnection();
		PreparedStatement pStemt = null;
		String tableSql = SQL + tableName;
		List<String> columnComments = new ArrayList<>();// 列名注释集合
		ResultSet rs = null;
		try {
			pStemt = conn.prepareStatement(tableSql);
			rs = pStemt.executeQuery("show full columns from " + tableName);
			while (rs.next()) {
				columnComments.add(rs.getString("Comment"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
					closeConnection(conn);
				} catch (SQLException e) {
					LOGGER.error("getColumnComments close ResultSet and connection failure", e);
				}
			}
		}
		return columnComments;
	}

	/**
	 * 获取表信息
	 * 
	 * @param tableName
	 *            表名
	 * @return String[字段,类型,注释]
	 */
	public static List<String[]> getTableColumnInfo(String tableName) {
		List<String> columnNames = getColumnNames(tableName);
		List<String> columnTypes = getColumnTypes(tableName);
		List<String> columnComments = getColumnComments(tableName);
		List<String[]> list = new ArrayList<>();
		for (int i = 0; i < columnNames.size(); i++) {
			String columnName = columnNames.get(i);
			String columnType = columnTypes.get(i);
			String columnComment = columnComments.get(i);
			String[] infos = new String[3];
			infos[0] = columnName;
			infos[1] = columnType;
			infos[2] = columnComment;
			list.add(infos);
		}
		return list;
	}
	
	public static void main(String[] args) {
		List<String> columnTypes = getColumnTypes("test");
		for (String s : columnTypes) {
			System.out.println(s);
		}
	}

}
