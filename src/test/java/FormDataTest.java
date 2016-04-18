import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FormDataTest {

    private String fileName;
    private File file;
    private FormData formData;

    @Before
    public void setUp() throws Exception {
        formData = new FormData();
        fileName = "form.txt";
        file = new File(fileName);
    }

    @Test
    public void testSaveData() throws Exception {
        formData.saveData("something");
        assertTrue("Save data creates a file named form", file.exists());
    }

    @Test
    public void testGetData() throws Exception{
        String data = "data=something";
        formData.saveData(data);
        assertEquals("Get data returns saved data", data, formData.getData());
    }

    @Test
    public void testDeleteData() throws Exception {
        formData.saveData("something");
        formData.deleteData();
        assertFalse("Delete data deletes form file", file.exists());
    }

    @After
    public void tearDown(){
        file.delete();
    }
}