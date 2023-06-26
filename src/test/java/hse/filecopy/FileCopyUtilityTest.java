package hse.filecopy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class FileCopyUtilityTest {
    @Test
    void copyFileTest() {
        Path sourcePath = Paths.get("source/test.txt");
        Path destinationPath = Paths.get("destination/test.txt");
        long sourceFileSize = sourcePath.toFile().length();
        FileCopyUtility.copyFile(sourcePath, sourcePath.getParent(), destinationPath.getParent());
        Assertions.assertTrue(destinationPath.toFile().exists(), "Destination file was not created");
        long destinationFileSize = destinationPath.toFile().length();
        Assertions.assertEquals(sourceFileSize, destinationFileSize, "Copied file size does not match source file size");
    }
}