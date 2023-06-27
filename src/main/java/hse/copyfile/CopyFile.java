package hse.copyfile;
// I/O
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
// Collections
import java.util.Scanner;
// Multithreading
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
// Logging
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class CopyFile {
	private static long totalBytesToCopy = 0;
	private static long copiedBytes = 0;
	private static int copiedFiles = 0;
	private static int failedFiles = 0;

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);

		if (args.length < 3) {
			System.out.println("Usage: java FileCopyUtility <source directory> <destination directory> <pool size>!");
			return;
		}

		Path source = Paths.get(args[0]);
		Path destination = Paths.get(args[1]);

		if (!Files.exists(source)) {
			System.out.println("Source directory does not exist!");
			return;
		}

		if (!Files.isDirectory(source)) {
			System.out.println("Source is not a directory!");
			return;
		}

		try {
			Files.createDirectories(destination);
		} catch (IOException e) {
			System.out.println("Failed to create destination directory!");
			return;
		}

		int poolSize = Integer.parseInt(args[2]);
		ExecutorService executorService = Executors.newFixedThreadPool(poolSize);

		try {
			Files.walk(source)
					.filter(Files::isRegularFile)
					.forEach(file -> {
						totalBytesToCopy += file.toFile().length();
						executorService.submit(() -> copyFile(file, source, destination));
					});
		} catch (IOException e) {
			System.out.println("Failed to walk source directory");
			return;
		}

		executorService.shutdown();

		Logger logger = Logger.getLogger("CopyFileLogger");
		FileHandler fileHandler;

		try {
			fileHandler = new FileHandler("copy_log.txt", true);
			logger.addHandler(fileHandler);
			SimpleFormatter formatter = new SimpleFormatter();
			fileHandler.setFormatter(formatter);
		} catch (IOException e) {
			e.printStackTrace();
		}

		while (!executorService.isTerminated()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// do nothing
			}
			double progress = (double) copiedBytes / totalBytesToCopy * 100;
			logger.log(Level.INFO, String.format("Progress: %.2f%%", progress));
		}

		System.out.printf("Copied %d files (%d bytes)\n", copiedFiles, copiedBytes);
		System.out.printf("Failed to copy %d files\n", failedFiles);
	}

	public static void copyFile(Path file, Path source, Path destination) {
		Path relativePath = source.relativize(file);
		Path destinationFile = destination.resolve(relativePath);

		try {
			Files.createDirectories(destinationFile.getParent());
			Files.copy(file, destinationFile);
			copiedBytes += file.toFile().length();
			copiedFiles++;
		} catch (IOException e) {
			System.out.printf("Failed to copy file %s\n", file);
			failedFiles++;
		}
	}
}