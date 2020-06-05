package group.mondi.web.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import group.mondi.web.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
	Employee findByLastName(String lastName);

	Employee findByPhotoName(String photoName);

	@Modifying
	@Query("update Employee e set e.photoName = ?1 where e.personalNumberId = ?2")
	int updatePhotoName(String photoName, long personalNumberId);

	@Query("select count(*) from Employee e where e.personalNumberId=?1")
	int countEmployesById(long id);
}
