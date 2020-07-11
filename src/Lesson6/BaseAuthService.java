package Lesson6;

import java.sql.*;


public class BaseAuthService implements AuthService {

    private static final String DATABASE_NAME = "chatsrv.s3db";
    private  MyServer myServer;

    private Connection connection;

    @Override
    public void start() {
        try {
            this.connect();
            myServer.PrintMessage("Сервис аутентификации запущен");

        } catch (SQLException | ClassNotFoundException e) {
            myServer.ErrorMessage("",e);
        }

    }

    @Override
    public void stop() {
        disconnect();
        myServer.PrintMessage("Сервис аутентификации остановлен");
    }


    public BaseAuthService(MyServer _myServer) {
        this.myServer = _myServer;
    }

    @Override
    public String getNickByLoginPass(String login, String pass) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT nickname FROM users WHERE login = ? AND pass = ? LIMIT 1");
            ps.setString(1, login);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                return rs.getString(1);
            }
            ps.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public boolean changeNickName(String login, String pass,String nickname){
        boolean res=false;
        try {
            String sql = "UPDATE users SET nickname = ? WHERE login = ? and pass = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, nickname);
            ps.setString(2, login);
            ps.setString(3, pass);
            res =  ps.executeUpdate() > 0;
            ps.close();
        } catch (SQLException e) {
            myServer.ErrorMessage("",e);
        }
        return res;
    }



    private void disconnect() {
        try {
            this.connection.close();
        } catch (Exception e) {
            myServer.ErrorMessage("",e);
        }
    }
    private void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        String url = "jdbc:sqlite:" + BaseAuthService.DATABASE_NAME;
        this.connection = DriverManager.getConnection(url);
    }
}
