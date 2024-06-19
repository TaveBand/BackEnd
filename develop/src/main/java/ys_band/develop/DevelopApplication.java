package ys_band.develop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ys_band.develop.service.EmailService;

@SpringBootApplication
public class DevelopApplication{

	public static void main(String[] args)  {
		SpringApplication.run(DevelopApplication.class, args);
	}


}
