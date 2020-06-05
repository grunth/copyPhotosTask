package group.mondi.web.services;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group.mondi.web.model.Properties;

@Service
public class FilesService {
	
	private static final Logger logger = LoggerFactory.getLogger(FilesService.class);
	
	@Autowired
	private Properties props;

	public FilesService() {
	}
	
	public Set<File> getSetFiles(String dir) {
		return Stream.of(new File(dir).listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".jpg");
			}
		})).filter(file -> !file.isDirectory()).collect(Collectors.toSet());
	}
	
	
	public boolean isFileInSourceFolderOlder(File source, File dest) throws IOException {
		BasicFileAttributes attr_source = Files.readAttributes(source.toPath(), BasicFileAttributes.class);
		BasicFileAttributes attr_dest = Files.readAttributes(dest.toPath(), BasicFileAttributes.class);
		return attr_source.lastModifiedTime().toMillis()>attr_dest.lastModifiedTime().toMillis();
	}
	
	
	public void copyFile(File source, File dest) throws IOException {
		Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
	}

	public int copyAllFiles() throws IOException {
		logger.info("Start copying all files");
		int i = 0;
		for (File f : getSetFiles(props.pathFrom.toLowerCase())) {
			copyFile(f, new File(props.pathTo + "\\" + f.getName()));
			i++;
		}

		return i;
	}

	public long getPersonalNumberIdFromPhotoNameString(String photoName) {
		String ret = "0";
		Pattern pattern1 = Pattern.compile("10+");
		Pattern pattern2 = Pattern.compile("-");
		Matcher matcher1 = pattern1.matcher(photoName);
		Matcher matcher2 = pattern2.matcher(photoName);

		while (matcher1.find() && matcher2.find()) {
			ret = photoName.substring(matcher1.end(), matcher2.start());
		}

		return Long.parseLong(ret);

	}
	
}
