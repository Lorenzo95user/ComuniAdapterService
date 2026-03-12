package it.newtech.timetracker.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.newtech.timetracker.project.entity.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	@Query("SELECT p.customerName , p.name , s.name , t.name FROM Project p JOIN p.subProjects s JOIN s.tasks t WHERE p.customerName = :name ")
	List<Object[]> findClientsProjects(String name);
	
	@Query("SELECT s.name , t.name FROM SubProject s JOIN s.tasks t")
	List<Object[]> findSubAndTask();
	
	@Query("SELECT p FROM Project p LEFT JOIN FETCH p.subProjects")
	List<Project> findAllP();

}
