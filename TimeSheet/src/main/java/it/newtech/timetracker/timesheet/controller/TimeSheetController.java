package it.newtech.timetracker.timesheet.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.newtech.timetracker.DTO.BillingReportDTO;
import it.newtech.timetracker.DTO.TSDTO;
import it.newtech.timetracker.timesheet.entity.TimesheetEntry;
import it.newtech.timetracker.timesheet.service.TimeSheetService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class TimeSheetController {
	
	
	private TimeSheetService timeSheetService;
	
//	dipendenti , task , progetto
	@GetMapping("stats")
	public List<Object[]> getStats(){
		return timeSheetService.getStats(); 
	}
//	nome dipendente , progetto ,task , ora inizio e ora fine (con data del giorno)
	@GetMapping("user/{name}")
	public List<TSDTO> getUserDatas(@PathVariable String name){
		return timeSheetService.getUserData(name);
	}
//	ore dedicate ai clienti
	@GetMapping("client/hours")
	public List<Object[]> getHoursOnClient(@RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDate startDate ,@RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDate endDate) {
		LocalDateTime startDay = (startDate != null) ? startDate.atStartOfDay() : LocalDateTime.of(1970, 1, 1, 0, 0);
		LocalDateTime endDay = (endDate != null) ? endDate.atTime(LocalTime.MAX) : LocalDateTime.now().with(LocalTime.MAX);
		return timeSheetService.getWorkedHours(startDay,endDay);
	}
//	tot ore pausa divise per dipendente
	@GetMapping("user/break")
	public List<Object[]> getBreak(){
		return timeSheetService.getBreak();
		
	}
//	cliente (con progetto e task) , orario di lavoro giornaliero , tipo fatturazione (con costo applicato) e totale fatturato in quel giorno
	@GetMapping("find/{name}")
	public ResponseEntity<?> getBilling(@RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDate startDate , @RequestParam @DateTimeFormat(iso = ISO.DATE_TIME) LocalDate endDate , @PathVariable String name ,@RequestParam(required = false) Long id){
		try {
			LocalDateTime startDay = (startDate != null) ? startDate.atStartOfDay() : LocalDateTime.of(1970, 1, 1, 0, 0);
			LocalDateTime endDay = (endDate != null) ? endDate.atTime(LocalTime.MAX) : LocalDateTime.now().with(LocalTime.MAX);
			return ResponseEntity.ok(timeSheetService.getBillingDTO(startDay , endDay , name , id));
		}catch(Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
//	 nome cliente , progetto , sottoprogetto e task
	@GetMapping("project/find/{id}")
	public ResponseEntity<?> getProjectClient(@PathVariable (required = false) String client , @RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE_TIME) LocalDate startDate , @RequestParam(required=false) @DateTimeFormat(iso = ISO.DATE_TIME)LocalDate endDate ,@PathVariable(required=false) Long id){
		try {
			LocalDateTime startDay = (startDate != null) ? startDate.atStartOfDay() : LocalDateTime.of(1970, 1, 1, 0, 0);
			LocalDateTime endDay = (endDate != null) ? endDate.atTime(LocalTime.MAX) : LocalDateTime.now().with(LocalTime.MAX);
			return ResponseEntity.ok(timeSheetService.getProjClient(startDay, endDay, client, id));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
//	ore lavorate su un progetto
	@GetMapping("project/time/{project}")
	public ResponseEntity<?> getHoursOnProject(@PathVariable String project , @RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDate startDate ,@RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDate endDate){
		LocalDateTime startDay = (startDate != null) ? startDate.atStartOfDay() : LocalDateTime.of(1970, 1, 1, 0, 0);
		LocalDateTime endDay = (endDate != null) ? endDate.atTime(LocalTime.MAX) : LocalDateTime.now().with(LocalTime.MAX);
		return ResponseEntity.ok(timeSheetService.getHoursOnProject(project ,startDay , endDay));
	}
//	ore lavorate dai dipendenti sui progetti
	@GetMapping("/project/users/hours")
	public ResponseEntity<?> getUsersAndHoursOnProject(@RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDate startDate ,@RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDate endDate){
		try {
			LocalDateTime startDay = (startDate != null) ? startDate.atStartOfDay() : LocalDateTime.of(1970, 1, 1, 0, 0);
			LocalDateTime endDay = (endDate != null) ? endDate.atTime(LocalTime.MAX) : LocalDateTime.now().with(LocalTime.MAX);
			return ResponseEntity.ok(timeSheetService.getDistinctProjects(startDay, endDay));
		}catch(Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
		
	}
//	ore dedicate ai progetti (diviso per progetto)
	@GetMapping("project/hours")
	public ResponseEntity<?> getAllProjectsHours(@RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDate startDate ,@RequestParam(required=false) @DateTimeFormat(iso=ISO.DATE_TIME) LocalDate endDate){
		try {
			LocalDateTime startDay = (startDate != null) ? startDate.atStartOfDay() : LocalDateTime.of(1970, 1, 1, 0, 0);
			LocalDateTime endDay = (endDate != null) ? endDate.atTime(LocalTime.MAX) : LocalDateTime.now().with(LocalTime.MAX);
			return ResponseEntity.ok(timeSheetService.getProjectHours(startDay, endDay));
		}catch( Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
