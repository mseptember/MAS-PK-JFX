module pk.mas {
    requires javafx.controls;
    requires org.hibernate.orm.core;
    requires java.persistence;
    requires java.naming;
    requires java.sql;
    requires com.sun.xml.bind;
    requires net.bytebuddy;
    requires javafx.graphics;
    requires javafx.fxml;

    exports pk.mas;
    exports db;

    opens db;
}