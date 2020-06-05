package group.mondi.web.repositories;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group.mondi.web.model.Employee;

@Service
public class EmployeeServiceImpl {
	
	@Autowired
	private EmployeeRepository employeeRepository;

	public EmployeeServiceImpl() {
	}

	public List<Employee> findAll() {
		return employeeRepository.findAll();
	}

	public Employee findOneByLastName(String lastName) {
		return employeeRepository.findByLastName(lastName);
	}

	public Employee findOneByPhotoName(String photoName) {
		return employeeRepository.findByPhotoName(photoName);
	}

	@Transactional
	public int updatePhotoName(String photoName, long personalNumberId) {
		return employeeRepository.updatePhotoName(photoName, personalNumberId);
	}

	public int countEmployesById(long id) {
		return employeeRepository.countEmployesById(id);
	}
}
