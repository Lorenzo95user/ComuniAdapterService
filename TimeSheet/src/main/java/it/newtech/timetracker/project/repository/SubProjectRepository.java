package it.newtech.timetracker.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import it.newtech.timetracker.project.entity.SubProject;

@Repository
public interface SubProjectRepository extends JpaRepository<SubProject, Long> {

}
