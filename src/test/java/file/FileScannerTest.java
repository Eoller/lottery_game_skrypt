package file;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

/**
 * Created by unity on 18.04.18.
 */
public class FileScannerTest {

    private FileScanner fileScanner;
    private File file;

    @Before
    public void beforeTests() throws IOException {
        file = new File("FileScannerTest.txt");
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write("Game game1;" + '\n' +"Player player;");
        writer.flush();
        writer.close();
        fileScanner = new FileScanner(file);
    }

    @After
    public void afterTests(){
        file.delete();
    }

    @Test
    public void shouldReturnRightSymbol(){
        char symbol = (char) fileScanner.getNextChar();
        char nextSymbol = (char) fileScanner.getNextChar();
        assertEquals('G', symbol);
        assertEquals('a', nextSymbol);
        assertNotEquals(-1, fileScanner.isFinished());
    }

    @Test
    public void shouldGetRightPosition(){
        assertEquals(1, fileScanner.getColPos());
        assertEquals(1, fileScanner.getRowPos());
        assertEquals('G', fileScanner.showMeCurrentChar());

        /*assertEquals('G',fileScanner.getNextChar());
        assertEquals(1, fileScanner.getColPos());
        assertEquals(1, fileScanner.getRowPos());*/
    }

}