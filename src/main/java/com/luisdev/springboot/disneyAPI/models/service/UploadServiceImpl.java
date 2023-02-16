package com.luisdev.springboot.disneyAPI.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadServiceImpl implements IUploadService {

	private final static String UPLOAD_FOLDER = "upload";

	@Override
	public Resource load(String filename) throws MalformedURLException {

		Path pathImagen = getPath(filename);

		Resource recurso = null;

		recurso = new UrlResource(pathImagen.toUri());

		if (!recurso.exists() || !recurso.isReadable()) {
			throw new RuntimeException("Error: no se puede cargar la imagen: " + pathImagen.toString());
		}

		return recurso;
	}

	@Override
	public boolean delete(String filename) {
		Path rootPath = getPath(filename);
		File archivo = rootPath.toFile();

		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) {
				return true;
			}
		}
		return false;

	}

	@Override
	public void init() throws IOException {
		Files.createDirectory(Paths.get(UPLOAD_FOLDER));

	}

	public Path getPath(String filename) {
		return Paths.get(UPLOAD_FOLDER).resolve(filename).toAbsolutePath();
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPath(uniqueFilename);
		var archivoInputStream = file.getInputStream();

		Files.copy(archivoInputStream, rootPath);
		archivoInputStream.close();
		
		

		return uniqueFilename;
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOAD_FOLDER).toFile());
	}

}
