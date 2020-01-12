package szwarc.company_program.controller.Test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import szwarc.company_program.model.Dyrektor;
import szwarc.company_program.model.Handlowiec;
import szwarc.company_program.model.Pracownik;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static szwarc.company_program.controller.Operations.*;

public class OperationsTest implements Serializable {
    static HashMap<String, Pracownik> bazaDanychTest = new HashMap<>();
    static HashMap<String, Pracownik> bazaDanychTest2 = new HashMap<>();

    public static Pracownik[] dataPracownik() {
        return new Pracownik[] {
                new Dyrektor("99112304053", "abc", "def", new BigDecimal(34343), "3434343", new BigDecimal(34343), "4535345", new BigDecimal(34343)),
                new Handlowiec("99032304053", "abcd", "dddef", new BigDecimal(34343), "3434343", new BigDecimal(34343), new BigDecimal(34343))
        };
    }

    @BeforeEach
    public void initEach(){
        bazaDanychTest = new HashMap<>();
        Pracownik obj = new Dyrektor("99112304053", "abc", "def", new BigDecimal(34343), "3434343", new BigDecimal(34343), "4535345", new BigDecimal(34343));
        bazaDanychTest.put(obj.getPesel(),obj);
        obj = new Handlowiec("99032304053", "abcd", "dddef", new BigDecimal(34343), "3434343", new BigDecimal(34343), new BigDecimal(34343));
        bazaDanychTest.put(obj.getPesel(),obj);
    }

    @ParameterizedTest
    @MethodSource("dataPracownik")
    public void dodajPracownikaTest(Pracownik in) {
        bazaDanychTest = new HashMap<>();
        assertTrue(dodajPracownika(this.bazaDanychTest,in));
        assertEquals(in,this.bazaDanychTest.get(in.getPesel()));
        //assertSame(in.getClass(),this.bazaDanychTest.get(in.getPesel()).getClass());
    }

    @ParameterizedTest
    @MethodSource("dataPracownik")
    public void znajdzPracownikaTest(Pracownik in) {
        assertEquals(in.getClass(),znajdzPracownika(this.bazaDanychTest,in.getPesel()).getClass());
        //assertSame(in.getClass(),this.bazaDanychTest.get(in.getPesel()).getClass());
    }

    @Test
    public void odczytBazyDanychTest() {
//        bazaDanychTest = new HashMap<>();
//        try {
//            bazaDanychTest = odczytBazyDanych( "te1.zip");
//        }catch (Exception e){
//            fail("Database seralize method fail" + e.getMessage());
//        }
//        assertEquals(bazaDanychTest2,bazaDanychTest);
        bazaDanychTest = new HashMap<>();
        try {
            bazaDanychTest = odczytBazyDanych( "te2.gzip");
        }catch (Exception e){
            fail("Database seralize method fail" + e.getMessage());
        }
        assertEquals(bazaDanychTest2.getClass(),bazaDanychTest.getClass());
    }

    @Test
    public void zapisBazyDanychTest() {
        try {
            //assertTrue(zapisBazyDanych(bazaDanychTest, "te1.zip"));
            assertTrue(zapisBazyDanych(bazaDanychTest, "te2.gzip"));
        }catch (Exception e){
            fail("Database seralize method fail" + e.getMessage());
        }
        bazaDanychTest2 = bazaDanychTest;
    }

    @ParameterizedTest
    @MethodSource("dataPracownik")
    public void usunPracownikaTest(Pracownik in) {
        assertTrue(usunPracownika(this.bazaDanychTest,in.getPesel()));
        assertFalse(this.bazaDanychTest.containsKey(in.getPesel()));
        //assertSame(in.getClass(),this.bazaDanychTest.get(in.getPesel()).getClass());
    }

}