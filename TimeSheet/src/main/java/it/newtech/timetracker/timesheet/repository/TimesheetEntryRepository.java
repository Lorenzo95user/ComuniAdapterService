package it.newtech.timetracker.timesheet.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import it.newtech.timetracker.DTO.BillingReportDTO;
import it.newtech.timetracker.DTO.TSDTO;
import it.newtech.timetracker.timesheet.entity.TimesheetEntry;

@Repository
public interface TimesheetEntryRepository extends JpaRepository<TimesheetEntry, Long>{
	
	@Query("SELECT u.username , t.name , p.name FROM TimesheetEntry ts JOIN ts.project p JOIN ts.user u JOIN ts.task t GROUP BY u.username")
	List<Object[]> findStats();
	
	@Query("SELECT u.username , p.name , t.name , ts.startDateTime , ts.endDateTime , p.customerName  FROM TimesheetEntry ts JOIN ts.user u JOIN ts.task t JOIN ts.project p WHERE LOWER(u.username) LIKE LOWER(CONCAT('%',:std,'%'))")
	List<Object[]> findUserDataByName(@Param ("std") String name);
	
	@Query("SELECT SUM(ts.totalHours) , p.name , p.customerName FROM TimesheetEntry ts JOIN ts.user u JOIN ts.project p WHERE (:startDate IS NULL OR ts.startDateTime >= :startDate) AND (:endDate IS NULL OR ts.endDateTime <= :endDate) GROUP BY p.id")
	List<Object[]> findSumTotHours(LocalDateTime startDate , LocalDateTime endDate);
	
	@Query("SELECT SUM(ts.totalHours) FROM TimesheetEntry ts JOIN ts.user u WHERE LOWER(u.username) LIKE(CONCAT('%',:u,'%'))")
	Double findTotOre(@Param("u") String name);
	
	@Query("SELECT COUNT(ts.breakDuration) , u.username FROM TimesheetEntry ts JOIN ts.user u GROUP BY u.username")
	List<Object[]> findBreakHours();
	
	
	@Query("SELECT ts FROM TimesheetEntry ts JOIN ts.task t JOIN ts.project p WHERE (:startDate IS NULL OR ts.startDateTime >= :startDate) AND (:endDate IS NULL OR ts.endDateTime <= :endDate) AND (:client IS NULL OR p.customerName = :client) AND (:id IS NULL OR p.id = :id)")
	List<TimesheetEntry> findBillingEntry(LocalDateTime startDate , LocalDateTime endDate , String client , Long id);
	
	
	@Query("SELECT SUM(ts.totalHours) FROM TimesheetEntry ts JOIN ts.project p WHERE (:startDate IS NULL OR ts.startDateTime >= :startDate) AND (:endDate IS NULL OR ts.endDateTime <= :endDate) AND LOWER(p.name) LIKE LOWER(CONCAT('%',:project,'%'))")
	Double findHoursWorkedOnProject(@Param("project") String project,LocalDateTime startDate , LocalDateTime endDate);
	
	@Query("SELECT ts FROM TimesheetEntry ts JOIN FETCH ts.project p WHERE (:startDate IS NULL OR ts.startDateTime >= :startDate) AND (:endDate IS NULL OR ts.endDateTime <= :endDate)")
	List<TimesheetEntry> findAllEntries(LocalDateTime startDate , LocalDateTime endDate);
	
	@Query("SELECT SUM(ts.totalHours) , p.name FROM TimesheetEntry ts JOIN  ts.project p WHERE (:startDate IS NULL OR ts.startDateTime >= :startDate) AND (:endDate IS NULL OR ts.endDateTime <= :endDate) GROUP BY p.id  ")
	List<Object[]> findAllProjectHours(LocalDateTime startDate , LocalDateTime endDate);
	

}
