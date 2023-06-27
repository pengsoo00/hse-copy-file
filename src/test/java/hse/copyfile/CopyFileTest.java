package hse.copyfile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CopyFileTest {

    private Path source;
    private Path destination;
    private int poolSize;
    private ExecutorService executorService;

    @BeforeEach
    public void setUp() throws IOException {
        source = Files.createTempDirectory("source");
        destination = Files.createTempDirectory("destination");
        poolSize = 2;
        executorService = Executors.newFixedThreadPool(poolSize);
    }

    @AfterEach
    public void tearDown() throws IOException {
        executorService.shutdownNow();
        Files.walk(destination)
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        // do nothing
                    }
                });
        Files.walk(source)
                .sorted((o1, o2) -> -o1.compareTo(o2))
                .forEach(path -> {
                    try {
                        Files.delete(path);
                    } catch (IOException e) {
                        // do nothing
                    }
                });
    }

    @DisplayName("CopySingleFile")
    @Test
    public void testCopySingleFile() throws IOException {
        Path file = Files.createTempFile(source, "file", ".txt");
        Files.write(file, "test content".getBytes());
        CopyFile.copyFile(file, source, destination);
        Path destinationFile = destination.resolve(source.relativize(file));
        assertTrue(Files.exists(destinationFile));
        assertEquals(Files.readAllLines(file), Files.readAllLines(destinationFile));
    }

    @DisplayName("CopyDirectory")
    @Test
    public void testCopyDirectory() throws IOException, InterruptedException {
        Path dir = Files.createTempDirectory(source, "dir");
        Path file1 = Files.createTempFile(dir, "file1", ".txt");
        Files.write(file1, "test content 1".getBytes());
        Path file2 = Files.createTempFile(dir, "file2", ".txt");
        Files.write(file2, "test content 2".getBytes());
        CopyFile.main(new String[]{source.toString(), destination.toString(), Integer.toString(poolSize)});
        Path destinationDir = destination.resolve(source.relativize(dir));
        assertTrue(Files.exists(destinationDir));
        Path destinationFile1 = destinationDir.resolve(file1.getFileName());
        assertTrue(Files.exists(destinationFile1));
        assertEquals(Files.readAllLines(file1), Files.readAllLines(destinationFile1));
        Path destinationFile2 = destinationDir.resolve(file2.getFileName());
        assertTrue(Files.exists(destinationFile2));
        assertEquals(Files.readAllLines(file2), Files.readAllLines(destinationFile2));
    }

}