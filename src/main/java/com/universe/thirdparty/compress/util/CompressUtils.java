package com.universe.thirdparty.compress.util;

import com.universe.thirdparty.fastjson.CollectionUtils;
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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author 刘亚楼
 * @date 2021/4/28
 */
public abstract class CompressUtils {

	public static void compressToJar(List<File> filesToArchive, String target) throws IOException {
		AbstractCompressor abstractCompressor = new JarCompressorFactory().newCompressor();
		abstractCompressor.compress(filesToArchive, target);
	}

	public static void compressToTar(List<File> filesToArchive, String target) throws IOException {
		AbstractCompressor abstractCompressor = new TarCompressorFactory().newCompressor();
		abstractCompressor.compress(filesToArchive, target);
	}

	public static void compressToZip(List<File> filesToArchive, String target) throws IOException {
		AbstractCompressor abstractCompressor = new ZipCompressorFactory().newCompressor();
		abstractCompressor.compress(filesToArchive, target);
	}

	public static void compressToGzip(List<File> filesToArchive, String target) throws IOException {
		AbstractCompressor abstractCompressor = new GzipCompressorFactory().newCompressor();
		abstractCompressor.compress(filesToArchive, target);
	}

	private interface CompressorFactory {

		AbstractCompressor newCompressor();
	}

	private static class JarCompressorFactory implements CompressorFactory {

		@Override
		public AbstractCompressor newCompressor() {
			return new JarCompressor();
		}
	}

	private static class TarCompressorFactory implements CompressorFactory {

		@Override
		public AbstractCompressor newCompressor() {
			return new TarCompressor();
		}
	}

	private static class ZipCompressorFactory implements CompressorFactory {

		@Override
		public AbstractCompressor newCompressor() {
			return new ZipCompressor();
		}
	}

	private static class GzipCompressorFactory implements CompressorFactory {

		@Override
		public AbstractCompressor newCompressor() {
			return new GzipCompressor();
		}
	}

	private static abstract class AbstractCompressor {

		public void compress(List<File> filesToArchive, String target) throws IOException {
			if (CollectionUtils.isEmpty(filesToArchive)) {
				return;
			}

			try (OutputStream os = Files.newOutputStream(Paths.get(target)); ArchiveOutputStream zipOs = createArchiveOutputStream(os)) {
				for (File file : filesToArchive) {
					ArchiveEntry archiveEntry = zipOs.createArchiveEntry(file, file.getName());
					zipOs.putArchiveEntry(archiveEntry);
					try (InputStream is = Files.newInputStream(file.toPath())) {
						IOUtils.copy(is, zipOs);
					}
					zipOs.closeArchiveEntry();
				}
			}
		}

		protected abstract ArchiveOutputStream createArchiveOutputStream(OutputStream os) throws IOException;
	}

	private static class JarCompressor extends AbstractCompressor {

		@Override
		protected ArchiveOutputStream createArchiveOutputStream(OutputStream os) throws IOException {
			return new JarArchiveOutputStream(os, StandardCharsets.UTF_8.name());
		}
	}

	private static class TarCompressor extends AbstractCompressor {

		@Override
		protected ArchiveOutputStream createArchiveOutputStream(OutputStream os) {
			return new TarArchiveOutputStream(os, StandardCharsets.UTF_8.name());
		}
	}

	private static class ZipCompressor extends AbstractCompressor {

		@Override
		protected ArchiveOutputStream createArchiveOutputStream(OutputStream os) {
			ZipArchiveOutputStream zipArchiveOutputStream = new ZipArchiveOutputStream(os);
			zipArchiveOutputStream.setEncoding(StandardCharsets.UTF_8.name());
			return new ZipArchiveOutputStream(os);
		}
	}

	private static class GzipCompressor extends AbstractCompressor {

		@Override
		protected ArchiveOutputStream createArchiveOutputStream(OutputStream os) throws IOException {
			CompressorOutputStream cos = new GzipCompressorOutputStream(os);
			return new TarArchiveOutputStream(cos, StandardCharsets.UTF_8.name());
		}
	}

}

