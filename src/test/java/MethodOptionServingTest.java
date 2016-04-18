import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class MethodOptionServingTest {

    private MethodOptionServing methodOptionServing;

    @Before
    public void setUp() throws Exception {
        methodOptionServing = new MethodOptionServing("OPTIONS");
    }

    @Test
    public void testGetOptionsReturnsGetPostPutHeadOptions(){
        String[] options = new String[]{"GET","POST","PUT","OPTIONS", "HEAD"};
        assertArrayEquals("Array has get post put options", options, methodOptionServing.getMethodOptions());
    }
}