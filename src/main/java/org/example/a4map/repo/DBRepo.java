package org.example.a4map.repo;

import com.github.javafaker.Faker;
import org.example.a4map.domain.*;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class DBRepo<T extends Entity> extends Repo<T> {
    private static final String JDBC_URL = "jdbc:sqlite:src/objects.db";
    private final Class<T> entityType; // Tipul entității
    private Connection connection;

    public DBRepo(Class<T> entityType) throws SQLException {
        this.entityType = entityType; // Inițializăm tipul entității
        try {
            openConnection();
            createSchema();
            loadData();
        } catch (SQLException e) {
            throw new SQLException("Eroare la inițializarea bazei de date.", e);
        }
    }

    private void openConnection() throws SQLException {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(JDBC_URL);

        if (connection == null || connection.isClosed()) {
            connection = dataSource.getConnection();
        }
    }

    private void createSchema() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS pacienti (id INTEGER PRIMARY KEY, nume TEXT, prenume TEXT, anul_nasterii INTEGER)");
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS programari (id INTEGER PRIMARY KEY, id_pacient INTEGER, data TEXT, scop TEXT, FOREIGN KEY (id_pacient) REFERENCES pacienti(id))");
        }
    }

    @Override
    public void addEntity(T entity) {
        if (entity instanceof Pacient pacient) {
            String query = "INSERT INTO pacienti (id, nume, prenume, anul_nasterii) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, pacient.getID());
                stmt.setString(2, pacient.getNumePacient());
                stmt.setString(3, pacient.getPrenumePacient());
                stmt.setInt(4, pacient.getAnulNasterii());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Eroare la adăugarea pacientului.", e);
            }
        } else if (entity instanceof Programare programare) {
            String query = "INSERT INTO programari (id, id_pacient, data, scop) VALUES (?, ?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setInt(1, programare.getID());
                stmt.setInt(2, programare.getPacientInfo());
                stmt.setString(3, programare.getDataProgramarii().toString());
                stmt.setString(4, programare.getScopulProgramarii());
                stmt.executeUpdate();
            } catch (SQLException e) {
                throw new RuntimeException("Eroare la adăugarea programării.", e);
            }
        }
    }

    @Override
    public T findByID(int id) {
        try {
            if (entityType == Pacient.class) {
                String query = "SELECT * FROM pacienti WHERE id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setInt(1, id);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        return (T) new Pacient(rs.getInt("id"), rs.getString("nume"), rs.getString("prenume"), rs.getInt("anul_nasterii"));
                    }
                }
            } else if (entityType == Programare.class) {
                String query = "SELECT * FROM programari WHERE id = ?";
                try (PreparedStatement stmt = connection.prepareStatement(query)) {
                    stmt.setInt(1, id);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        return (T) new Programare(rs.getInt("id"), (Pacient) findByID(rs.getInt("id_pacient")), LocalDateTime.parse(rs.getString("data")), rs.getString("scop"));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la găsirea entității.", e);
        }
        return null;
    }

    @Override
    public ArrayList<T> findAll() {
        ArrayList<T> entities = new ArrayList<>();
        try {
            String query = (entityType == Pacient.class) ? "SELECT * FROM pacienti" : "SELECT * FROM programari";
            try (Statement stmt = connection.createStatement();
                 ResultSet rs = stmt.executeQuery(query)) {
                while (rs.next()) {
                    if (entityType == Pacient.class) {
                        entities.add((T) new Pacient(rs.getInt("id"), rs.getString("nume"), rs.getString("prenume"), rs.getInt("anul_nasterii")));
                    } else if (entityType == Programare.class) {
                        entities.add((T) new Programare(rs.getInt("id"), (Pacient) findByID(rs.getInt("id_pacient")), LocalDateTime.parse(rs.getString("data")), rs.getString("scop")));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Eroare la găsirea entităților.", e);
        }
        return entities;
    }

    public void loadData() throws SQLException {
        Faker faker = new Faker();
        Random random = new Random();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        try {
            connection.setAutoCommit(false);

            for (int i = 1; i <= 100; i++) {
                addEntity((T) new Pacient(i, faker.name().lastName(), faker.name().firstName(), random.nextInt(60) + 1960));
            }

            for (int i = 1; i <= 100; i++) {
                int idPacient = random.nextInt(100) + 1;
                LocalDateTime data = LocalDateTime.now().minusDays(random.nextInt(365)).withHour(random.nextInt(9) + 8).withMinute(0).withSecond(0);
                addEntity((T) new Programare(i, (Pacient) findByID(idPacient), data, faker.medical().symptoms()));
            }

            connection.commit();
        } catch (Exception e) {
            connection.rollback();
            throw new SQLException("Eroare la încărcarea datelor.", e);
        } finally {
            connection.setAutoCommit(true);
        }
    }
}
