package flik;

import static org.junit.Assert.*;
import org.junit.Test;

public class FilkTest {

    @Test
    /** Test whether the problem is in Fika.java or in HS.java */
    public void isSameNumberTest() {
        int i = 0;
        Integer a = 128;
        Integer b = 128;
        System.out.println(a == b);
        for (int j = 0; j < 500; j++, i++) {
            System.out.println(i);
            assertTrue(Flik.isSameNumber(i, j));
        }
    }
}
