package sportsmate.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.util.Scanner;


public class PersonalMatchDAO extends DAO {
  private Connection conn;
  private String sql;
  private PreparedStatement pStatement;
  private int pmatch_id;
  private int player_id;
  private String location;
  private Date game_date;
  private Time start_at;
  private Time end_at;
  private int game_type;
  private int num_initial_players;
  private int num_players_joined;


  public void createPersonalMatch(int userID, String location, String game_date, String startAt,
      String endAt, String game_type, int num_initial_players) {

    try {
      conn = getConnection();

      SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
      sdf.setTimeZone(TimeZone.getTimeZone("EST"));
      //java.sql.Date sqlDate = new java.sql.Date(date.getTime());
      java.util.Date date = sdf.parse(game_date);
      java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());

      sql = "INSERT INTO personal_match VALUES (default, ?,?,?,?,?,?,?,?)";
      pStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      pStatement.setInt(1, userID);
      pStatement.setString(2, location);
      //pStatement.setDate(3, date.valueOf(game_date));
      pStatement.setTimestamp(3, sqlDate);
      pStatement.setTime(4, Time.valueOf(startAt));
      pStatement.setTime(5, Time.valueOf(endAt));
      pStatement.setString(6, game_type);
      pStatement.setInt(7, num_initial_players );
      pStatement.setInt(8, 0 );
      pStatement.executeUpdate();
      System.out.println("\nYou have successfully created a personal match!");
    }
    catch (Exception e){
      System.err.printf ("Cannot connect to server%n%s", e);
      System.err.println(e.getMessage());
      e.printStackTrace();
    }

    if (conn != null) {
      try {
        conn.close ();
        //System.out.println ("Disconnected from database.");
      }
      catch (Exception e) { /* ignore close errors */ }
    }
  }


  public void listAllPersonalMatches(int loggedInUserID) {

    String sql = "select * from personal_match";

    try {
      conn = getConnection();
      PreparedStatement pStatement = conn.prepareStatement(sql);
      ResultSet resultSet = pStatement.executeQuery();

      System.out.printf("%n%-11s%-14s%-11s%-13s%-13s%-11s%-12s%-18s%-19s%n",
          "Match ID", "Listed by ID", "Location", "Date", "Start Time", "End Time", "Game Type",
          "Initial Players", "Players Joined");

      while (resultSet.next()) {
        pmatch_id = resultSet.getInt("pmatch_id");
        player_id = resultSet.getInt("player_id");
        location = resultSet.getString("location");
        game_date = resultSet.getDate("game_date");
        start_at = resultSet.getTime("start_at");
        end_at = resultSet.getTime("end_at");
        game_type = resultSet.getInt("game_type");
        num_initial_players = resultSet.getInt("num_initial_players");
        num_players_joined = resultSet.getInt("num_players_joined");

        System.out.printf("%-11s%-14s%-11s%-13s%-13s%-11s%-12s%-18s%-19s%n",
            pmatch_id, player_id, location, game_date, start_at, end_at, game_type,
            num_initial_players, num_players_joined);


      }


      joinPersonalMatch(loggedInUserID);

    }
    catch (Exception e){
      System.err.printf ("Cannot connect to server%n%s", e);
      System.err.println(e.getMessage());
      e.printStackTrace();
    }

    if (conn != null) {
      try {
        conn.close ();
        //System.out.println ("Disconnected from database.");
      }
      catch (Exception e) { /* ignore close errors */ }
    }
  }

  public void joinPersonalMatch(int p_id) {
    Scanner sc = new Scanner(System.in);

    System.out.println("\n\n" + "=====================================================" +
        "\nEnter the Match ID of the game you would like to join");

    System.out.printf("%n> ");
    int match_id = Integer.parseInt(sc.next());

    try {
      conn = getConnection();
      sql = "INSERT INTO personal_match_players VALUES (?,?)";
      pStatement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      pStatement.setInt(1, p_id);
      pStatement.setInt(2, match_id );
      pStatement.executeUpdate();
      System.out.println("\nYou have successfully joined a personal match!");
      System.exit(1);

    }
    catch (Exception e){
      System.err.printf ("Cannot connect to server%n%s", e);
      System.err.println(e.getMessage());
      e.printStackTrace();
    }

    if (conn != null) {
      try {
        conn.close ();
        //System.out.println ("Disconnected from database.");
      }
      catch (Exception e) { /* ignore close errors */ }
    }


  }


  public void searchPersonalMatchesByLocation() {}


}



