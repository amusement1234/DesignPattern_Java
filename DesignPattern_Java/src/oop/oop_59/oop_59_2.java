// 应用举例一：JdbcTemplate


public class JdbcDemo {
    public User queryUser(long id) {
      Connection conn = null;
      Statement stmt = null;
      try {
        //1.加载驱动
        Class.forName("com.mysql.jdbc.Driver");
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", "xzg", "xzg");
  
        //2.创建statement类对象，用来执行SQL语句
        stmt = conn.createStatement();
  
        //3.ResultSet类，用来存放获取的结果集
        String sql = "select * from user where id=" + id;
        ResultSet resultSet = stmt.executeQuery(sql);
  
        String eid = null, ename = null, price = null;
  
        while (resultSet.next()) {
          User user = new User();
          user.setId(resultSet.getLong("id"));
          user.setName(resultSet.getString("name"));
          user.setTelephone(resultSet.getString("telephone"));
          return user;
        }
      } catch (ClassNotFoundException e) {
        // TODO: log...
      } catch (SQLException e) {
        // TODO: log...
      } finally {
        if (conn != null)
          try {
            conn.close();
          } catch (SQLException e) {
            // TODO: log...
          }
        if (stmt != null)
          try {
            stmt.close();
          } catch (SQLException e) {
            // TODO: log...
          }
      }
      return null;
    }
  
  }






//   我用 JdbcTemplate 重写了上面的例子，代码简单了很多，如下所示：
public class JdbcTemplateDemo {
    private JdbcTemplate jdbcTemplate;
  
    public User queryUser(long id) {
      String sql = "select * from user where id="+id;
      return jdbcTemplate.query(sql, new UserRowMapper()).get(0);
    }
  
    class UserRowMapper implements RowMapper<User> {
      public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setTelephone(rs.getString("telephone"));
        return user;
      }
    }
  }








  
@Override
public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {
 return query(sql, new RowMapperResultSetExtractor<T>(rowMapper));
}

@Override
public <T> T query(final String sql, final ResultSetExtractor<T> rse) throws DataAccessException {
 Assert.notNull(sql, "SQL must not be null");
 Assert.notNull(rse, "ResultSetExtractor must not be null");
 if (logger.isDebugEnabled()) {
  logger.debug("Executing SQL query [" + sql + "]");
 }

 class QueryStatementCallback implements StatementCallback<T>, SqlProvider {
  @Override
  public T doInStatement(Statement stmt) throws SQLException {
   ResultSet rs = null;
   try {
    rs = stmt.executeQuery(sql);
    ResultSet rsToUse = rs;
    if (nativeJdbcExtractor != null) {
     rsToUse = nativeJdbcExtractor.getNativeResultSet(rs);
    }
    return rse.extractData(rsToUse);
   }
   finally {
    JdbcUtils.closeResultSet(rs);
   }
  }
  @Override
  public String getSql() {
   return sql;
  }
 }

 return execute(new QueryStatementCallback());
}

@Override
public <T> T execute(StatementCallback<T> action) throws DataAccessException {
 Assert.notNull(action, "Callback object must not be null");

 Connection con = DataSourceUtils.getConnection(getDataSource());
 Statement stmt = null;
 try {
  Connection conToUse = con;
  if (this.nativeJdbcExtractor != null &&
    this.nativeJdbcExtractor.isNativeConnectionNecessaryForNativeStatements()) {
   conToUse = this.nativeJdbcExtractor.getNativeConnection(con);
  }
  stmt = conToUse.createStatement();
  applyStatementSettings(stmt);
  Statement stmtToUse = stmt;
  if (this.nativeJdbcExtractor != null) {
   stmtToUse = this.nativeJdbcExtractor.getNativeStatement(stmt);
  }
  T result = action.doInStatement(stmtToUse);
  handleWarnings(stmt);
  return result;
 }
 catch (SQLException ex) {
  // Release Connection early, to avoid potential connection pool deadlock
  // in the case when the exception translator hasn't been initialized yet.
  JdbcUtils.closeStatement(stmt);
  stmt = null;
  DataSourceUtils.releaseConnection(con, getDataSource());
  con = null;
  throw getExceptionTranslator().translate("StatementCallback", getSql(action), ex);
 }
 finally {
  JdbcUtils.closeStatement(stmt);
  DataSourceUtils.releaseConnection(con, getDataSource());
 }
}