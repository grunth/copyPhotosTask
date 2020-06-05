package group.mondi.web.model;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.Data;

@Data
@Service
public class Properties {
	
	@Value("${PathFrom}")
	public String pathFrom;
	
	@Value("${PathTo}")
	public String pathTo;
	
	@Value("${IsStarted}")
	public Boolean isStarted;
}
