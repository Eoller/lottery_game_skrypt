package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileScanner implements Scanner {

    private FileReader fileReader;

    private char current;

    private int colPos = 0;

    private int rowPos = 1;

    public FileScanner(File file) {
        try {
            fileReader = new FileReader(file);
            getNextChar();
        } catch (FileNotFoundException e) {
            System.out.println("There is no such file!");
            e.printStackTrace();
        }
    }

    public char showMeCurrentChar() {
        return current;
    }

    public char getNextChar() {
        char forReturn = current;
        setUpNext();
        return forReturn;
    }

    private void setUpNext() {
        char next;
        try {
            next = (char) fileReader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(next == '\n'){
            rowPos++;
            colPos=0;
            setUpNext();
        }else {
            colPos++;
            current = next;
        }
    }

    public boolean isFinished() {
        return ((int) current) == -1;
    }

    public int getColPos() {
        return colPos;
    }

    public int getRowPos() {
        return rowPos;
    }
}
