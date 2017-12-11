import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;

public class LargeFileReader implements Iterable<String> {

	private String filePath;

	private BufferedReader br ;

    public LargeFileReader(String filePath) {
		this.filePath = filePath;
		try {
			br = new BufferedReader(new FileReader(filePath));
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
	}

    @Override
    public Iterator<String> iterator() {
        Iterator<String> it = new Iterator<String>() {

            private int currentIndex = 0;
			private String line;

            @Override
            public boolean hasNext() {
                // Check if there is another line in file.
				try {
					line = br.readLine();
				} catch (Exception e){

				}

				return line != null;
            }

            @Override
            public String next() {
				// return line from readline.
                return line;
            }

            @Override
            public void remove() {

                throw new UnsupportedOperationException();
            }
        };
        return it;
    }

    public static void main(String[] args) {
		LargeFileReader it = new LargeFileReader("test.txt");
		for (String a : it) {
			System.out.println(a);
		}
	}
}