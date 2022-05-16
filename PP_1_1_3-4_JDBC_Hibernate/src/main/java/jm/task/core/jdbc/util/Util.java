package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static SessionFactory sessionFactory;
    private static String URL = "jdbc:mysql://localhost:3306/MyDB?useSLL=false";
    private static String USER = "root";
    private static String PASSWORD = "Rfgbnjirf1488";
    private static final Connection connection;
    
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.setProperty("hibernate.current_session_context_class", "thread");
                configuration.setProperty(Environment.URL, URL);
                configuration.setProperty(Environment.USER, USER);
                configuration.setProperty(Environment.PASS, PASSWORD);
                configuration.setProperty("hibernate.connection.release_mode", "auto");
                configuration.setProperty("hibernate.show_sql", "true");
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (HibernateException e) {
                e.getStackTrace();
                System.out.println(e.getMessage());
                throw new RuntimeException();
            }
        } return sessionFactory;
    }

    static {
        try {
            connection = DriverManager.getConnection(URL, "root", "Rfgbnjirf1488");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
