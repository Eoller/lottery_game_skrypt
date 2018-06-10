package file;

/**
 * Created by Yahor_Melnik on 03-Jun-18.
 */
public interface Scanner {

    public char showMeCurrentChar();

    public char getNextChar();

    public boolean isFinished();

    public int getLineNumber();

    public int getColNumber();
}
