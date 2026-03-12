package it.newtech.timetracker.DTO;

import java.math.BigDecimal;
import java.math.RoundingMode;

import it.newtech.timetracker.task.entity.BillingType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@NoArgsConstructor

@Builder // Abilita il pattern Builder

public class BillingReportDTO {

	private Long entryId;

	private String clientName;

	private String projectName;

	private String taskName;

	private Double hours;

	private String billingType; // "HOURLY" o "DAILY"

	private BigDecimal appliedRate;

	private BigDecimal totalRowAmount;
	
	

}