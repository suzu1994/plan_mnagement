import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.*;

public class DataManagement {
    public EvaluatedState state;

    public void setData(){
        Connection con = null;
 
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        try {
 
            // ドライバクラスをロード
            Class.forName("com.mysql.jdbc.Driver");
 
            // データベースへ接続
            con = DriverManager.getConnection("jdbc:mysql://localhost/plan_db?autoReconnect=true&useSSL=false","root","Taylor_swift1213");
            String state_str;
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy'/'MM'/'dd");

            if(state == EvaluatedState.ICHI){
                state_str = "1: 2 is not completed...";
            }else if(state == EvaluatedState.NI){
                state_str= "2: 1 is completed!";
            }else{
                state_str = "3: 2 is completed!!!";
            }

            String sql1 = "delete from plan_table where time = ?";
            String sql = "insert into plan_table (time,value) VALUES(?,?)";

            // ステートメントオブジェクトを生成
            ps1 = con.prepareStatement(sql1);
            ps = con.prepareStatement(sql);

            ps1.setString(1, sdf.format(date));
            ps.setString(1,sdf.format(date));
            ps.setString(2, state_str);
            // クエリーを実行して結果セットを取得
            ps1.executeUpdate();
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
 
                // close処理
                if(ps != null){
                    ps.close();
                }
 
                // close処理
                if(con != null){
                    con.close();
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
    public void outputResultToText(){
        Connection con = null;
 
        PreparedStatement ps = null;

        try {
 
            // ドライバクラスをロード
            Class.forName("com.mysql.jdbc.Driver");
 
            // データベースへ接続
            con = DriverManager.getConnection("jdbc:mysql://localhost/plan_db?autoReconnect=true&useSSL=false","root","Taylor_swift1213");
            

            
            String sql = "select * from plan_table";

            // ステートメントオブジェクトを生成
            
            ps = con.prepareStatement(sql);

            // クエリーを実行して結果セットを取得
            ResultSet rs = ps.executeQuery();
            FileWriter fw = null;
            try{
                fw = new FileWriter("/home/terazumi/デスクトップ/plan_result.txt");
                while(rs.next()) {
    
                    // nameデータを取得
                    String time = rs.getString("time");
                    // bloodTypeデータを取得
                    String value = rs.getString("value");
                    fw.write(time + " --> " + value + "\n");
                    fw.flush();
                }
            }catch(IOException e){

            }finally{
                if(fw != null){
                    try{
                        fw.close();
                    }catch(IOException e2){
                        
                    }
                }
            }
                
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
 
                // close処理
                if(ps != null){
                    ps.close();
                }
 
                // close処理
                if(con != null){
                    con.close();
                }
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}