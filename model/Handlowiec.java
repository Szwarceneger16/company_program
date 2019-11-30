package szwarc.company_program.model;

import java.math.BigDecimal;

public class Handlowiec extends Pracownik {
    private BigDecimal Prowizja;
    private BigDecimal LimitProwizji;

    public Handlowiec(String pesel, String imie, String nazwisko, BigDecimal wynagrodzenie, String numerTelefonu, BigDecimal prowizja, BigDecimal limitProwizji) {
        super(pesel, imie, nazwisko, wynagrodzenie, numerTelefonu);
        Prowizja = prowizja;
        LimitProwizji = limitProwizji;
    }

    public BigDecimal getProwizja() {
        return Prowizja;
    }

    public void setProwizja(BigDecimal prowizja) {
        Prowizja = prowizja;
    }

    public BigDecimal getLimitProwizji() {
        return LimitProwizji;
    }

    public void setLimitProwizji(BigDecimal limitProwizji) {
        LimitProwizji = limitProwizji;
    }
}
