package com.universe.thirdparty.compress;

import com.universe.thirdparty.compress.util.CompressUtils;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.jar.JarArchiveOutputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.compressors.CompressorOutputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.compress.utils.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘亚楼
 * @date 2021/4/28
 */
public class CompressExample {

	private static final String BASE_PATH = System.getProperty("user.home") + File.separator + "compress";

	private static final List<File> FILES_TO_ARCHIVE = new ArrayList<>();

	static {
		File first = new File(BASE_PATH, "a.txt");
		File second = new File(BASE_PATH, "b.txt");
		FILES_TO_ARCHIVE.add(first);
		FILES_TO_ARCHIVE.add(second);
	}

	private static void compressToZipFormat() throws IOException {
		try (OutputStream os = Files.newOutputStream(Paths.get(BASE_PATH, "archive.zip"));
			 ArchiveOutputStream zipOs = new ZipArchiveOutputStream(os)) {
			for (File file : FILES_TO_ARCHIVE) {
				ArchiveEntry archiveEntry = zipOs.createArchiveEntry(file, file.getName());
				zipOs.putArchiveEntry(archiveEntry);
				try (InputStream is = Files.newInputStream(file.toPath())) {
					IOUtils.copy(is, zipOs);
				}
				zipOs.closeArchiveEntry();
			}
			zipOs.finish();
		}
	}

	private static void compressToJarFormat() throws IOException {
		try (OutputStream os = Files.newOutputStream(Paths.get(BASE_PATH, "archive.jar"));
			 ArchiveOutputStream jarOs = new JarArchiveOutputStream(os)) {
			for (File file : FILES_TO_ARCHIVE) {
				ArchiveEntry archiveEntry = jarOs.createArchiveEntry(file, file.getName());
				jarOs.putArchiveEntry(archiveEntry);
				try (InputStream is = Files.newInputStream(file.toPath())) {
					IOUtils.copy(is, jarOs);
				}
				jarOs.closeArchiveEntry();
			}
			jarOs.finish();
		}
	}

	private static void compressToTarFormat() throws IOException {
		try (OutputStream os = Files.newOutputStream(Paths.get(BASE_PATH, "archive.tar"));
			 ArchiveOutputStream tarOs = new TarArchiveOutputStream(os)) {
			for (File file : FILES_TO_ARCHIVE) {
				ArchiveEntry archiveEntry = tarOs.createArchiveEntry(file, file.getName());
				tarOs.putArchiveEntry(archiveEntry);
				try (InputStream is = Files.newInputStream(file.toPath())) {
					IOUtils.copy(is, tarOs);
				}
				tarOs.closeArchiveEntry();
			}
			tarOs.finish();
		}
	}

	private static void compressToTarGZFormat() throws IOException {
		try (OutputStream os = Files.newOutputStream(Paths.get(BASE_PATH, "archive.tar.gz"));
			 CompressorOutputStream cos = new GzipCompressorOutputStream(os);
			 ArchiveOutputStream gzipOs = new TarArchiveOutputStream(cos)) {
			for (File file : FILES_TO_ARCHIVE) {
				ArchiveEntry archiveEntry = gzipOs.createArchiveEntry(file, file.getName());
				gzipOs.putArchiveEntry(archiveEntry);
				try (InputStream is = Files.newInputStream(file.toPath())) {
					IOUtils.copy(is, gzipOs);
				}
				gzipOs.closeArchiveEntry();
			}
			gzipOs.finish();
		}
	}


	public static void main(String[] args) throws IOException {
		Map<String, InputStream> files2Archive = new HashMap<>();
		try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(BASE_PATH))) {
			for (Path path : directoryStream) {
				files2Archive.put(path.getFileName().toString(), Files.newInputStream(path));
			}
		}
		OutputStream os = Files.newOutputStream(Paths.get(BASE_PATH, "archive.zip"));
		CompressUtils.compressToZip(files2Archive, os);
	}
}
