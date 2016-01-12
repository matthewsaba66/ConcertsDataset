package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


/*try to work on table libro.libri*/
public class LibriDAO {

	 private Connection connect = null;
	  private Statement statement = null;
	  private PreparedStatement preparedStatement = null;
	  private ResultSet resultSet = null;

	  public void readDataBase() throws Exception {
	    try {
	      // This will load the MySQL driver, each DB has its own driver
	      Class.forName("com.mysql.jdbc.Driver");
	      // Setup the connection with the DB
	      connect = DriverManager
	          .getConnection("jdbc:mysql://localhost/libro?"
	              + "user=root&password=root");

	      // Statements allow to issue SQL queries to the database
	      statement = connect.createStatement();
	      
	      
	      // Result set get the result of the SQL query
	      resultSet = statement
	          .executeQuery("select * from libro.libri");
	      writeResultSet(resultSet);

	      // PreparedStatements can use variables and are more efficient
	      preparedStatement = connect
	          .prepareStatement("insert into  libro.libri values ( ?, ?, ?, ? )");
	      
	      // Parameters start with 1
	      preparedStatement.setString(1, "tp-link");
	      preparedStatement.setString(2, "5");
	      preparedStatement.setString(3, "cremisini");
	      preparedStatement.setString(4, "rometre");
	      preparedStatement.executeUpdate();

	      preparedStatement = connect
	          .prepareStatement("SELECT * from libro.libri");
	      resultSet = preparedStatement.executeQuery();
	      writeResultSet(resultSet);

	      // Remove again the insert comment
	      preparedStatement = connect
	      .prepareStatement("delete from libro.libri where titolo= ? ; ");
	      preparedStatement.setString(1, "hunger games");
	      preparedStatement.executeUpdate();
	      
	      resultSet = statement
	      .executeQuery("select * from libro.libri");
	      writeMetaData(resultSet);
	      
	    } catch (Exception e) {
	      throw e;
	    } finally {
	      close();
	    }

	  }

	  private void writeMetaData(ResultSet resultSet) throws SQLException {
	    //   Now get some metadata from the database
	    // Result set get the result of the SQL query
	    
	    System.out.println("The columns in the table are: ");
	    
	    System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
	    for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++){
	      System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
	    }
	  }

	  private void writeResultSet(ResultSet resultSet) throws SQLException {
	    // ResultSet is initially before the first data set
	    while (resultSet.next()) {
	      // It is possible to get the columns via name
	      // also possible to get the columns via the column number
	      // which starts at 1
	      // e.g. resultSet.getSTring(2);
	      String titolo = resultSet.getString("titolo");
	      String isbn = resultSet.getString("isbn");
	      String autore = resultSet.getString("autore");
	      String editore = resultSet.getString("editore");
	      System.out.println("Titolo: " + titolo);
	      System.out.println("ISBN: " + isbn);
	      System.out.println("Autore: " + autore);
	      System.out.println("Editore: " + editore);
	    }
	  }

	  // You need to close the resultSet
	  private void close() {
	    try {
	      if (resultSet != null) {
	        resultSet.close();
	      }

	      if (statement != null) {
	        statement.close();
	      }

	      if (connect != null) {
	        connect.close();
	      }
	    } catch (Exception e) {

	    }
	  }
	  
	  public static void main(String[] args) throws Exception {
		    LibriDAO dao = new LibriDAO();
		    dao.readDataBase();
		  }

	} 
	

