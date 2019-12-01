package szwarc.company_program.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class Pracownik implements Serializable {
    private static long serialVersionUID;
    private String Pesel;
    private String Imie;
    private String Nazwisko;
    private BigDecimal Wynagrodzenie;
    private String NumerTelefonu;


    public Pracownik(String pesel, String imie, String nazwisko, BigDecimal wynagrodzenie, String numerTelefonu) {
        try {
            setPesel(pesel);
            setNumerTelefonu(numerTelefonu);
        }catch (NumberFormatException nfe) {
            throw nfe;
        }
        Imie = imie;
        Nazwisko = nazwisko;
        Wynagrodzenie = wynagrodzenie;
    }

    public void setPesel(String in){
        if(in.length() != 11) {
            throw new NumberFormatException("Wrong Pesel Format");
        }
        try {
            int msc = Integer.parseInt(in.substring(2,4));
            msc %= 20;
            if (msc > 12) throw new NumberFormatException("Wrong Pesel Format");
        }
        catch (NumberFormatException nfe) {
            throw new NumberFormatException("Wrong Pesel Format");
        }
        Pesel = in;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long serialVersionUID) {
        Pracownik.serialVersionUID = serialVersionUID;
    }

    public String getPesel() {
        return Pesel;
    }

    public String getImie() {
        return Imie;
    }

    public void setImie(String imie) {
        Imie = imie;
    }

    public String getNazwisko() {
        return Nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        Nazwisko = nazwisko;
    }

    public BigDecimal getWynagrodzenie() {
        return Wynagrodzenie;
    }

    public void setWynagrodzenie(BigDecimal wynagrodzenie) {
        Wynagrodzenie = wynagrodzenie;
    }

    public String getNumerTelefonu() {
        return NumerTelefonu;
    }

    public void setNumerTelefonu(String numerTelefonu) {
        try{
            int pom = Integer.parseInt(numerTelefonu);
        }catch(NumberFormatException nfe) {
            throw new NumberFormatException("Wrong Telephon Number Format");
        }
        NumerTelefonu = numerTelefonu;
    }
}
