package db;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import pk.mas.SystemInfo;


public class Main extends Application {
    private static SessionFactory sessionFactory;

    public static void main(String[] args) {
        StandardServiceRegistry registry = null;

        //launch();

        try {
            registry = new StandardServiceRegistryBuilder().configure() // configures settings from hibernate.cfg.xml
                    .build();

            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sessionFactory.openSession();
            session.beginTransaction();

            // Do something within the session, e.g. create/retrieve objects, // etc.

            // utworzenie rekordu Zwierze
            /* Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse("20-04-2018");
            Integer zwierze1 = main.dodajZwierze(1, "Kluska", date1, "k", 4, 1);*/


            // wyswietl liste zwierzat - test
            Zwierze zwierze = new Zwierze();

            // wyswietlListeZwierzat test
            zwierze.wyswietlListeZwierzat(sessionFactory);

            // wyswietlSzczegolyZwierzecia test
            zwierze.wyswietlSzczegolyZwierzecia(1, sessionFactory);

            //wyswietlChorobyZwierzecia test
            //zwierze.wyswietlChorobyZwierzecia(1, sessionFactory);

            launch();

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        } finally {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 600, 400));
        stage.show();
    }

    //TODO metoda zwracająca listę chorób otrzymująca jako argument idZwierzęcia
}

/*import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}*/
