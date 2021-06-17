package db;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Entity(name = "Zwierze")
public class Zwierze {
    // TODO atrybuty kl. Zwierze
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private int idZwierzecia;
    private String imie;
    private Date dataUrodzenia;
    private String plec;
    private double wysokosc;
    private double waga;
    //private int idChoroby

    // TODO ASOCJACJE
    @ManyToMany
    private List<Wizyta> wizytaLista = new ArrayList<>();

    @ManyToOne
    private WlascicielZwierzecia wlascicielZwierzecia;

    @ManyToMany (mappedBy = "zwierzeLista")
    private List<Choroba> chorobaLista = new ArrayList<>();

    // do przechowywania kompozycji - HistoriaLeczenia
    @OneToOne(cascade = CascadeType.ALL)
    private HistoriaLeczenia historiaLeczenia;

    // TODO konstruktor
    public Zwierze(int idZwierzecia, String imie, Date dataUrodzenia, String plec, double wysokosc, double waga) {
        this.idZwierzecia = idZwierzecia;
        this.imie = imie;
        this.dataUrodzenia = dataUrodzenia;
        this.plec = plec;
        this.wysokosc = wysokosc;
        this.waga = waga;
    }

    public Zwierze() {
    }

    // TODO metody
    public int getIdZwierzecia() {
        return idZwierzecia;
    }

    public void setIdZwierzecia(int idZwierzecia) {
        this.idZwierzecia = idZwierzecia;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public Date getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(Date dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    public double getWysokosc() {
        return wysokosc;
    }

    public void setWysokosc(double wysokosc) {
        this.wysokosc = wysokosc;
    }

    public double getWaga() {
        return waga;
    }

    public void setWaga(double waga) {
        this.waga = waga;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    public List<Wizyta> getWizytaLista() {
        return wizytaLista;
    }

    public void setWizytaLista(List<Wizyta> wizytaLista) {
        this.wizytaLista = wizytaLista;
    }

    @ManyToOne
    public WlascicielZwierzecia getWlascicielZwierzecia() {
        return wlascicielZwierzecia;
    }

    public void setWlascicielZwierzecia(WlascicielZwierzecia wlascicielZwierzecia) {
        this.wlascicielZwierzecia = wlascicielZwierzecia;
    }

    @ManyToMany
    public List<Choroba> getChorobaLista() {
        return chorobaLista;
    }

    public void setChorobaLista(List<Choroba> chorobaLista) {
        this.chorobaLista = chorobaLista;
    }

    public HistoriaLeczenia getHistoriaLeczenia() {
        return historiaLeczenia;
    }

    public void setHistoriaLeczenia(HistoriaLeczenia historiaLeczenia) {
        this.historiaLeczenia = historiaLeczenia;
    }

    // TODO wyswietlListeZwierzat()
    public List<Zwierze> wyswietlListeZwierzat(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<Zwierze> lz = new ArrayList<>();

        try {
            tx = session.beginTransaction();
            List listaZwierzat = session.createQuery("FROM Zwierze").list();
            for (Iterator iterator = listaZwierzat.iterator(); iterator.hasNext(); ) {
                Zwierze zwierze = (Zwierze) iterator.next();
                lz.add(zwierze);
                System.out.print("Imie: " + zwierze.getImie()); //TODO do wywalenia1
                System.out.print("  Płeć: " + zwierze.getPlec()); //TODO do wywalenia2
            }
            tx.commit();
        }
        catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
                ex.printStackTrace();
            }
        }
        finally {
            session.close();
        }
        return lz;
    }

    // TODO wyswietlChorobyZwierzecia(ID zwierzecia) - JAK ZROBIĆ QUERRY MANY TO MANY Z JOINEM (JAK DAĆ ID-KI Z DWÓCH TABEL!)
    public List<Choroba> wyswietlChorobyZwierzecia(int idZwierzecia, SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        List<Choroba> lc = new ArrayList<>();

        // pobrać querry danego zwierzęcia i na danym querry operować
        try {
            tx = session.beginTransaction();
            //List listaChorob = session.createQuery("FROM Choroba JOIN Zwierze on Choroba.idZwierzecia = Zwierze.idZwierzecia").setParameter(1,idZwierzecia).list(); // zapytać o to prowadzącego, jak zrobić mapping many to many
            List listaChorob = session.createQuery("FROM Choroba").list();
            Zwierze zwierze = (Zwierze) session.createQuery("FROM Zwierze where Zwierze.idZwierzecia = idZwierzecia");
            //List listaChorobZwierzecia = session.createQuery("FROM " + zwierze + " ")

            for (Iterator iterator = listaChorob.iterator(); iterator.hasNext(); ) {
                Choroba choroba = (Choroba) iterator.next();
                lc.add(choroba);
                System.out.print("Nazwa: " + choroba.getNazwa()); //TODO do wywalenia3
                System.out.print("  Data diagnozy: " + choroba.getDataDiagnozy()); //TODO do wywalenia4
            }
            tx.commit();
        }
        catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
                ex.printStackTrace();
            }
        }
        finally {
            session.close();
        }
        return lc;
    }
    // TODO dodajZwierze() -> klasowa
    // TODO wyswietlChoroby()
    // TODO przypiszChorobe(ID choroby)

    // TODO wyswietlSzczegolyZwierzecia(ID zwierzecia)
    public Zwierze wyswietlSzczegolyZwierzecia(int idZwierzecia, SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        Zwierze zwierze = new Zwierze();

        try {
            tx = session.beginTransaction();
            zwierze = session.createQuery("FROM Zwierze WHERE idZwierzecia = " + idZwierzecia, Zwierze.class).getSingleResult();
            System.out.print("Imie: " + zwierze.getImie());
            System.out.print("  Płeć: " + zwierze.getPlec());
            tx.commit();
        }
        catch (HibernateException ex) {
            if (tx != null) {
                tx.rollback();
                ex.printStackTrace();
            }
        } finally {
            session.close();
        }
        return zwierze;
    }

    // TODO KLASA WEW. HistoriaLeczenia
    @Entity(name = "HistoriaLeczenia")
    private class HistoriaLeczenia {
        // TODO atrybuty
        @Id
        int idHistoriaLeczenia;
        Date dataRozpoczecia;
        @OneToOne
        Weterynarz weterynarzProwadzacy;

        //TODO konstruktor
        public HistoriaLeczenia() {
        }

        public HistoriaLeczenia(Date dataRozpoczecia, Weterynarz weterynarzProwadzacy) {
            this.dataRozpoczecia = dataRozpoczecia;
            this.weterynarzProwadzacy = weterynarzProwadzacy;
        }

        // TODO metody
        public int getIdHistoriaLeczenia() {
            return idHistoriaLeczenia;
        }

        public void setIdHistoriaLeczenia(int idHistoriaLeczenia) {
            this.idHistoriaLeczenia = idHistoriaLeczenia;
        }

        public Date getDataRozpoczecia() {
            return dataRozpoczecia;
        }

        public void setDataRozpoczecia(Date dataRozpoczecia) {
            this.dataRozpoczecia = dataRozpoczecia;
        }

        public Weterynarz getWeterynarzProwadzacy() {
            return weterynarzProwadzacy;
        }

        public void setWeterynarzProwadzacy(Weterynarz weterynarzProwadzacy) {
            this.weterynarzProwadzacy = weterynarzProwadzacy;
        }

        // TODO przypiszWeterynarza()
        // public void przypiszWeterynarza(idWeterynarza)
    }
}
