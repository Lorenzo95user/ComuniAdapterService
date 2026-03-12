package it.newtech.timetracker.timesheet.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import it.newtech.timetracker.auth.entity.User;
import it.newtech.timetracker.project.entity.Project;
import it.newtech.timetracker.project.entity.SubProject;
import it.newtech.timetracker.task.entity.BillingType;
import it.newtech.timetracker.task.entity.Task;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "timesheet_entries")
@Data
public class TimesheetEntry {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;

@Column(nullable = false)
private LocalDateTime startDateTime;

@Column(nullable = false)
private LocalDateTime endDateTime;

private Double totalHours;

private String breakDuration;      // Questo testuale per la comodità della UI (es. "01:30")

   
    /**
     * Il Project (Livello 1) può essere derivato dal Task,
     * ma tenerlo qui è utile per l'integrità referenziale.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    @JsonIgnore
    private Project project;
   

    /**
     * OPZIONALE MA CONSIGLIATO: Riferimento diretto al SubProject (Livello 2).
     * Velocizza le query di reporting senza dover fare troppi JOIN.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sub_project_id")
    @JsonManagedReference
    private SubProject subProject;
   
    // Relazione con la nuova entità Task
    /**
     * RELAZIONE CHIAVE: Il Timesheet punta al Task (Livello 3).
     * Risalendo dal Task, JPA può arrivare al SubProject e al Project.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", nullable = false)
    @JsonManagedReference
    private Task task;

   
// Fondamentale: chi ha registrato queste ore?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;

    // Opzionale: puoi tenere una nota testuale per dettagli extra
    @Column(columnDefinition = "TEXT")
    private String notes;
   
   
    // Snapshot del prezzo al momento del salvataggio
    @Column(precision = 10, scale = 2)
    private BigDecimal appliedRate ;
   
    @Enumerated(EnumType.STRING)
    private BillingType appliedBillingType;

   
    private LocalDateTime createdAt = LocalDateTime.now();
   
 // Metodo per calcolare il costo reale di questa riga
    public BigDecimal calculateCost() {
        if (appliedBillingType == BillingType.HOURLY) {
            return appliedRate.multiply(BigDecimal.valueOf(totalHours));
        } else {
            // Se giornaliero: (Ore lavorate / Ore in una giornata) * Tariffa Giornaliera
            double days = totalHours / 8.0; // O usa il valore dinamico del task
            return appliedRate.multiply(BigDecimal.valueOf(days));
        }
    }
    public BigDecimal getAmount() {
        if (appliedRate == null || totalHours == null) return BigDecimal.ZERO;

        if (appliedBillingType == BillingType.HOURLY) {
            // Calcolo Orario: Ore * Prezzo
            return appliedRate.multiply(BigDecimal.valueOf(totalHours));
        } else {
            // Calcolo Giornaliero: (Ore / 8) * Prezzo
            // Assumendo 8 ore come giornata standard
            BigDecimal days = BigDecimal.valueOf(totalHours).divide(BigDecimal.valueOf(8), 2, RoundingMode.HALF_UP);
            return appliedRate.multiply(days);
        }
    }
}