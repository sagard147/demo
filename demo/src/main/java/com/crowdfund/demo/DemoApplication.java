package com.crowdfund.demo;

import com.crowdfund.demo.model.*;
import com.crowdfund.demo.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.crowdfund.demo")
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(
			ProjectRepository projectRepository,
			UserRepository userRepository,
			DonationRepository donationRepository,
			RolesRepository rolesRepository,
			UserRoleRepository userRoleRepository) {
		return args -> {

			Roles innovatorRole = new Roles();
			innovatorRole.setRole(Role.INNOVATOR);
			rolesRepository.save(innovatorRole);

			Roles donorRole = new Roles();
			donorRole.setRole(Role.DONOR);
			rolesRepository.save(donorRole);

			Project project = new Project(
					"Adidas",
					"Des",
					(long)10000,"INR");

			Project project1 = new Project(
					"nike",
					"Des",
					(long)20000,"INR");

			Project project2 = new Project(
					"Run",
					"Des",
					(long)100,"INR");

			User user1 = new User("Sam","hey1","a@a","bio1","address 1");
			User user2 = new User("Ram","bye1","b@b","bio2","address 2");

			UserRole userRole1 = new UserRole(user1, innovatorRole);
			UserRole userRole2 = new UserRole(user2, innovatorRole);

			userRepository.save(user1);
			userRepository.save(user2);
			userRoleRepository.save(userRole1);
			userRoleRepository.save(userRole2);

			project.setUser(user1);
			project1.setUser(user1);
			project2.setUser(user2);

			projectRepository.save(project);
			projectRepository.save(project1);
			projectRepository.save(project2);

			User user3 = new User("DonSam","hey","na@a","bio3","address 3");
			User user4 = new User("DonRam","bye","nb@b","bio4","address 4");
			UserRole userRole3 = new UserRole(user3, donorRole);
			UserRole userRole4 = new UserRole(user4, donorRole);
			userRepository.save(user3);
			userRepository.save(user4);
			userRoleRepository.save(userRole3);
			userRoleRepository.save(userRole4);

			Donation donation1 = new Donation( 1000L, "INR");
			donation1.setProject(project);
			donation1.setUser(user3);
			donationRepository.save(donation1);

			Donation donation2 = new Donation( 2000L, "INR");
			donation2.setProject(project1);
			donation2.setUser(user3);
			donationRepository.save(donation2);

			Donation donation3 = new Donation( 2000L, "INR");
			donation3.setProject(project);
			donation3.setUser(user4);
			donationRepository.save(donation3);

			projectRepository.findById(1L)
					.ifPresent(p -> {
						System.out.println("fetch project lazy..."+p.toString());
					});
		};
	}
}
