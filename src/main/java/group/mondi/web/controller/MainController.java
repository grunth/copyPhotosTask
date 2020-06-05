package group.mondi.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import group.mondi.web.model.Properties;

@RestController
@RequestMapping(value = "/props")
public class MainController {

	@Autowired
	private Properties props;

	public MainController() {
	}

	@GetMapping("getisstarted")
	public Boolean getisStarted() {
		return props.isStarted;
	}

	@PutMapping("setisstarted/{isstarted}")
	public Boolean updateIsStarted(@PathVariable Boolean isstarted) {
		props.isStarted = isstarted;
		return props.isStarted;
	}

}
