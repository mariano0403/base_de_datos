package baseDatos;

import java.sql.*;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
bbdd de peliculas
datos de la tabla: 
    id, nombre, genero.
*/

public class base1 {
    
    
    Connection connect;
    ResultSet rs;
    public Connection connect(){
        
        try {
            Class.forName("org.sqlite.JDBC");
            connect = DriverManager.getConnection("jdbc:sqlite:pelis.sqlite");
            if (connect!= null)
            {
                System.out.println("conectado exitosamente!");
            }
            else
                System.out.println("error de coneccion!");
        } catch ( Exception e ) {
            JOptionPane.showMessageDialog(null, e.getMessage().toString());
            System.exit(0);
        }
        return connect;
    }
    
    public void leer(){
        Connection c = null;
        Statement stmt = null;
        try {
           Class.forName("org.sqlite.JDBC");
           c = DriverManager.getConnection("jdbc:sqlite:pelis.sqlite");
           c.setAutoCommit(false);
           System.out.println("Opened database successfully");

           stmt = c.createStatement();
           ResultSet rs = stmt.executeQuery( "SELECT * FROM pelicula;" );

           while ( rs.next() ) {
              int id = rs.getInt("id");
              String  nombre = rs.getString("nombre");
              
              String  genero = rs.getString("genero");
              

              System.out.print( "ID = " + id+"\t" );
              System.out.print( "nombre = " +"\t"+ nombre +"\t");
              System.out.print("genero = " + genero);
              
              System.out.println();
           }
           rs.close();
           stmt.close();
           c.close();
        } catch ( Exception e ) {
           System.err.println( e.getClass().getName() + ": " + e.getMessage() );
           System.exit(0);
        }
        System.out.println("Operation done successfully");
       }
    
    public void insertarDatos(String nombre, String genero)
    {
        String sql = "INSERT INTO pelicula(nombre,genero) VALUES(?,?)";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, genero);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }  
    }
    
    public void eliminarFila(int id)
    {
        String sql = "DELETE FROM pelicula WHERE id = ?";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public void actualizarDatos(int id, String nombre, String genero)
    {
        String sql = "UPDATE pelicula SET nombre = ? , "
                + "genero = ? "
                + "WHERE id = ?";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, nombre);
            pstmt.setString(2, genero);
            pstmt.setInt(3, id);
            // update 
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void main(String[] args)
    {
        base1 b1 = new base1();
        b1.connect();
        //b1.insertarDatos("pepelopp","chupapija");
        b1.leer();
        //b1.actualizarDatos(2, "american pie", "comedia");
        //System.out.print("datos actualizados");
        //b1.leer();
        b1.eliminarFila(1);
        b1.leer();
        
    }
}
    
    
