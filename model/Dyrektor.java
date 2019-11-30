package szwarc.company_program.model;

import java.math.BigDecimal;

public class Dyrektor extends Pracownik {
    private BigDecimal DodatekSluzbowy;
    private String KartaSluzbowa;
    private BigDecimal LimitKosztow;

    public Dyrektor(String pesel, String imie, String nazwisko, BigDecimal wynagrodzenie, String numerTelefonu, BigDecimal dodatekSluzbowy, String kartaSluzbowa, BigDecimal limitKosztow) {
        super(pesel, imie, nazwisko, wynagrodzenie, numerTelefonu);
        DodatekSluzbowy = dodatekSluzbowy;
        KartaSluzbowa = kartaSluzbowa;
        LimitKosztow = limitKosztow;
    }

    public BigDecimal getDodatekSluzbowy() {
        return DodatekSluzbowy;
    }

    public void setDodatekSluzbowy(BigDecimal dodatekSluzbowy) {
        DodatekSluzbowy = dodatekSluzbowy;
    }

    public String getKartaSluzbowa() {
        return KartaSluzbowa;
    }

    public void setKartaSluzbowa(String kartaSluzbowa) {
        KartaSluzbowa = kartaSluzbowa;
    }

    public BigDecimal getLimitKosztow() {
        return LimitKosztow;
    }

    public void setLimitKosztow(BigDecimal limitKosztow) {
        LimitKosztow = limitKosztow;
    }
}
