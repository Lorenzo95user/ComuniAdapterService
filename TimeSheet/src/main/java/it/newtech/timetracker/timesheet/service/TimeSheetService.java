package it.newtech.timetracker.timesheet.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import it.newtech.timetracker.DTO.BillingReportDTO;
import it.newtech.timetracker.DTO.ProjectDTO;
import it.newtech.timetracker.DTO.TSDTO;
import it.newtech.timetracker.DTO.UserDTO;
import it.newtech.timetracker.timesheet.entity.TimesheetEntry;
import it.newtech.timetracker.timesheet.repository.TimesheetEntryRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j

public class TimeSheetService {
	
	
	private TimesheetEntryRepository timesheetEntryRepository;
	
	public List<Object[]> getStats(){
		return timesheetEntryRepository.findStats();
	}
	public List<TSDTO> getUserData(String name){
		 List<Object[]> u = timesheetEntryRepository.findUserDataByName(name);
		 if(u.isEmpty()) {
			 return null;
		 }
		 return u.stream().map(c -> {
			 TSDTO dto = new TSDTO();
			 dto.setNome_impiegato((String) c[0]);
			 dto.setNome_progetto((String) c[1]);
			 dto.setNome_compito((String) c[2]);
			 dto.setOra_inizio((java.time.LocalDateTime) c[3]);
			 dto.setOra_fine((java.time.LocalDateTime) c[4]);
			 dto.setAzienda((String) c[5]);
			 return dto;
		 }).toList();
	}
	public List<Object[]> getWorkedHours(LocalDateTime startDay , LocalDateTime endDay) {
		return timesheetEntryRepository.findSumTotHours(startDay,endDay);
	}
	public List<Object[]> getBreak(){
		return timesheetEntryRepository.findBreakHours();
	}
	
	public List<BillingReportDTO> getBillingDTO(LocalDateTime start , LocalDateTime end , String name,Long id) {
		try {
			List<TimesheetEntry> timeSheetEntry = timesheetEntryRepository.findBillingEntry(start ,end, name , id);
			log.debug("carico il timesheetentry");if(timeSheetEntry.isEmpty()) {
				log.warn("Non è stato trovato alcun dato nella data richiesta");
				return List.of();
			}
			List<BillingReportDTO> billingReportDTOList = timeSheetEntry.stream()
					.map(ts -> {BillingReportDTO dto = new BillingReportDTO();
			        dto.setEntryId(ts.getId());
			        dto.setClientName(ts.getProject().getCustomerName());
			        dto.setProjectName(ts.getProject().getName());
			        dto.setTaskName(ts.getTask().getName());
			        dto.setHours(ts.getTotalHours());
			        dto.setBillingType(ts.getAppliedBillingType().toString());
			        dto.setAppliedRate(ts.getAppliedRate());
			        dto.setTotalRowAmount(ts.getAmount()); 
			        return dto;
			    }).toList();
		    return billingReportDTOList;
			}catch (Exception e){
				log.error(e.getMessage());
				return List.of();
			}
	}
	
	public List<ProjectDTO> getProjClient (LocalDateTime startDate , LocalDateTime endDate , String client , Long id){
			log.debug("sto cercando");
			try {
				List<TimesheetEntry> timesheetEntriesList = timesheetEntryRepository.findBillingEntry(startDate, endDate , client ,id);
				if(timesheetEntriesList.isEmpty()) {
					log.warn("non trovato");
					return List.of();
				}
				double sum = timesheetEntriesList.stream().mapToDouble(t -> t.getTotalHours()).sum();
				Map<String,ProjectDTO> projDtoList = timesheetEntriesList.stream().map(ts ->{
					ProjectDTO dto = new ProjectDTO();
					dto.setCliente(ts.getProject().getCustomerName());
					dto.setProgetto(ts.getProject().getName());
					dto.setSotto_progetto(ts.getSubProject().getName());
					dto.setCompito(ts.getTask().getName());
					dto.setHours(sum);
					return dto;
				}).collect(Collectors.toMap(ProjectDTO::getProgetto,dto -> dto,(existing,replacement)->existing));
				return new ArrayList<>(projDtoList.values());
			}catch (Exception e) {
				log.error(e.getMessage());
				return List.of();
			}
	}
	public Double getHoursOnProject(String project , LocalDateTime startDate , LocalDateTime endDate){
		return timesheetEntryRepository.findHoursWorkedOnProject(project,startDate,endDate);
	
		
	}
	public List<UserDTO> getDistinctProjects(LocalDateTime startDate , LocalDateTime endDate) {
		try {
			log.debug("eseguo ricerca progetti");
		    List<TimesheetEntry> allEntries = timesheetEntryRepository.findAllEntries(startDate, endDate);
		    if(allEntries.isEmpty()) {
		    	log.warn("nessun risultato trovato");
		    	return List.of();
		    }
		    double totHrs = allEntries.stream().mapToDouble(TimesheetEntry::getTotalHours).sum();
		    // Usiamo una mappa per tenere solo un record per ogni nome progetto
		    // La chiave è il nome del progetto, il valore è il DTO mappato
		    Map<String, UserDTO> distinctUsers = allEntries.stream()
		        .map(ts -> {
		        	UserDTO dto = new UserDTO();
		            dto.setName(ts.getUser().getUsername());
		            dto.setProjectName(ts.getProject().getName());
		            dto.setTotHours(totHrs); // Qui hai le ore della singola riga
		            return dto;
		        })
		        // Questo "collect" mantiene solo il primo DTO trovato per ogni nome progetto
		        .collect(Collectors.toMap(UserDTO::getName,dto -> dto,(existing, replacement) -> existing));
	
		    return new ArrayList<>(distinctUsers.values());
		}catch (Exception e) {
			log.error(e.getMessage());
			return List.of();
		}
	}
	
	public List<Object[]> getProjectHours(LocalDateTime startDate , LocalDateTime endDate){
		try {
			log.debug("cerco progetti");
			List<Object[]> list = timesheetEntryRepository.findAllProjectHours(startDate, endDate);
			if(list.isEmpty()) {
				log.warn("non è stato trovato alcun progetto");
				return List.of();
			}
			return list;
		} catch(Exception e) {
			log.error(e.getMessage());
			return List.of();
		}
		
	}

	
	
}