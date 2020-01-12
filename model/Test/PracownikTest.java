package szwarc.company_program.model.Test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import szwarc.company_program.model.Pracownik;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


public class PracownikTest {
    final static Pracownik objectTest = new Pracownik("99112304053", "abc", "def", new BigDecimal(34343), "3434343");

    public static String[] dataPesel() {
    return new String[] { "99112304053" , "04092303053" , "88012744502" , "97041105456" , "22051705456" , "88012304053" , "04092305456"};
    }

    public static String[] dataNumerTelefonu() {
        return new String[] { "3434343" , "77545454564" , "11111111" , "3434343" , "18181881" , "789456123" , "789456789"};
    }

    public static String[] dataWrongPesel() {
        return new String[] { "99151248567" , "99104578456" , "8910120457897"};
    }

    @ParameterizedTest
    @MethodSource("dataPesel")
    public void setPeselTest(String data) {
        try {
            objectTest.setPesel(data);
            assertEquals( data, objectTest.getPesel(),"MUST be equals");
        } catch (Exception e) {
            fail("An unexepted exception was caught - " + e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("dataNumerTelefonu")
    public void setNumerTelefonuTest(String data) {
        try {
            objectTest.setNumerTelefonu(data);
            assertEquals( data, objectTest.getNumerTelefonu(),"MUST be equals");
        } catch (Exception e) {
            fail("An unexepted exception was caught - " + e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("dataWrongPesel")
    public void setWrongPeselTest(String data) {
        assertThrows(NumberFormatException.class,() -> objectTest.setPesel(data));
    }

    @Test
    public void setWrongNumerTelefonuTest() {
        assertThrows(NumberFormatException.class,() -> objectTest.setPesel("78asdasdsa677"));
    }
}

