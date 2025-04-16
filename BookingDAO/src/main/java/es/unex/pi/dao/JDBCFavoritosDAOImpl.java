package es.unex.pi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import es.unex.pi.model.Favoritos;
import es.unex.pi.model.Property;

public class JDBCFavoritosDAOImpl implements FavoritosDAO {

    private Connection conn;
    private static final Logger logger = Logger.getLogger(JDBCFavoritosDAOImpl.class.getName());

    @Override
    public long add(Favoritos favorito) {
        long favID = -1; // Valor por defecto en caso de error
        PreparedStatement ps = null;

        try {
            // Insertar en la tabla sin incluir isFavorite
            String query = "INSERT INTO Favoritos (idu, idp) VALUES (?, ?)";
            ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, favorito.getIdu());
            ps.setLong(2, favorito.getIdp());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    favID = generatedKeys.getLong(1);
                    logger.info("Added favorite with ID " + favID + " for user " + favorito.getIdu() + " for property " + favorito.getIdp());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar recursos
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return favID;
    }

    @Override
    public List<Favoritos> getAllByUser(long idu) {
        List<Favoritos> favorites = new ArrayList<>();
        if (conn != null) {
            try {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT * FROM Favoritos WHERE idu = " + idu);
                while (rs.next()) {
                    Favoritos favorito = new Favoritos();
                    favorito.setIdu(rs.getInt("idu"));
                    favorito.setIdp(rs.getInt("idp"));
                    favorites.add(favorito);
                }
                logger.info("Fetched all favorites for user " + idu);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return favorites;
    }
    
    @Override
    public List<Favoritos> getAll() {
        List<Favoritos> favoritosList = new ArrayList<>();

        try {
            String query = "SELECT * FROM Favoritos";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Favoritos favorito = new Favoritos();
                favorito.setId(rs.getLong("id"));
                favorito.setIdu(rs.getLong("idu"));
                favorito.setIdp(rs.getLong("idp"));
                favoritosList.add(favorito);
            }

            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favoritosList;
    }
    
    @Override
    public boolean delete(long idu, long idp) {
        boolean done = false;
        if (conn != null) {
            PreparedStatement stmt = null;
            try {
                // Eliminar el registro correspondiente a favoritos
                stmt = conn.prepareStatement("DELETE FROM favoritos WHERE idu = ? AND idp = ?");
                stmt.setLong(1, idu);
                stmt.setLong(2, idp);
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    logger.info("Deleting favoritos for user " + idu + " and property " + idp);
                    done = true;
                }
            } catch (SQLException e) {
                logger.severe("Error deleting favoritos: " + e.getMessage());
            } finally {
                if (stmt != null) {
                    try {
                        stmt.close();
                    } catch (SQLException e) {
                        logger.severe("Error closing statement: " + e.getMessage());
                    }
                }
            }
        }
        return done;
    }
    
    @Override
    public Property getPropertyById(long idp) {
        Property property = null;
        if (conn != null) {
            try {
                PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Properties WHERE id = ?");
                stmt.setLong(1, idp);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    property = new Property();
                    property.setId(rs.getLong("id"));
                    property.setName(rs.getString("name"));
                    property.setCity(rs.getString("city"));
                    // Agregar más propiedades si es necesario
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return property;
    }
    
    @Override
    public boolean isFavorite(long userId, long propertyId) {
        boolean isFavorite = false;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Verificar si existe un registro para determinar si es favorito
            String query = "SELECT 1 FROM Favoritos WHERE idu = ? AND idp = ?";
            ps = conn.prepareStatement(query);
            ps.setLong(1, userId);
            ps.setLong(2, propertyId);

            rs = ps.executeQuery();
            isFavorite = rs.next(); // Devuelve true si existe al menos un registro
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return isFavorite;
    }
    
    @Override
    public List<Long> getFavoritePropertyIds(long userId) {
        List<Long> favoriteIds = new ArrayList<>();
        String query = "SELECT idp FROM Favoritos WHERE idu = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                favoriteIds.add(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return favoriteIds;
    }
    
    @Override
    public void setConnection(Connection conn) {
        this.conn = conn;
    }
}
