package group.mondi.web.schedullers;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import group.mondi.web.model.Properties;
import group.mondi.web.repositories.EmployeeServiceImpl;
import group.mondi.web.services.FilesService;

@Configuration
@EnableScheduling
public class ScheduledJobs {
	private static final Logger logger = LoggerFactory.getLogger(ScheduledJobs.class);
	
	@Autowired
	private Properties props;

	@Autowired
	private FilesService fileService;

	@Autowired
	private EmployeeServiceImpl employeeService;

	// every 15sec
	@Scheduled(cron = "0/15 * * * * *")
	public void startJob() {

		if (props.isStarted) {
			logger.info("Задание стартовало");

			for (File file : fileService.getSetFiles(props.pathFrom.toLowerCase())) {
				long personalNumberId = fileService.getPersonalNumberIdFromPhotoNameString(file.getName());
				// System.out.println("Взял файл: " + file.getName() + " PersonalNumberId: " +
				// personalNumberId);

				// ----Файл есть в Target
				if (new File(props.pathTo + "\\" + file.getName()).exists()) {
					try {
						if(fileService.isFileInSourceFolderOlder(file, new File(props.pathTo + "\\" + file.getName()))) {
							logger.info("В исходной папке обнаружен более новый файл фотографии " + file.getName() + ", personalNumberId: " + personalNumberId + ", скопирован в папку назначения");
							fileService.copyFile(file, new File(props.pathTo + "\\" + file.getName()));

						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					// System.out.println("Файл " + file.getName() + " существует в папке
					// назначения");
					// TODO логика когда файл есть
				}

				// ----Новый файл - еще не скопирован в Target
				else {
					// пытаемся скопировать файл
					try {
						fileService.copyFile(file, new File(props.pathTo + "\\" + file.getName()));
						logger.info("Файл " + file.getName() + " сохранен в папке назначения, " + "ID = " + file.getName());
						// System.out.println(employeeService.countEmployesById(71368L));
					} catch (IOException e) {
						e.printStackTrace();
					}

					int cnt = employeeService.countEmployesById(personalNumberId);
					if (cnt == 1) {
						int rowsUpdated = employeeService.updatePhotoName(file.getName(), personalNumberId);
						logger.info("Обновлено " + rowsUpdated + " строк." + " Имя файла: " + file.getName()
								+ ", personalNumberId: " + personalNumberId);
					}

					else if (cnt == 0) {
						logger.info("Не обновлено! Не найдено " + " имя файла: " + file.getName()
								+ ", personalNumberId: " + personalNumberId);
					}

					else if (cnt > 1) {
						logger.info("Не обновлено! Найдено больше 1 записей с" + " имя файла: " + file.getName()
								+ ", personalNumberId: " + personalNumberId);
					}

					else {
						logger.info("Неизвестная ошибка при попытке выяснить количество строк которые обновляем");
					}

					logger.info("********************************************************************");

				}

				// int i = employeeService.updatePhotoName(file.getName(), file.getName().)

			}

		}
	}
}
