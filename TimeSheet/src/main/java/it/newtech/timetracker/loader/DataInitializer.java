package it.newtech.timetracker.loader;



import it.newtech.timetracker.auth.entity.User;
import it.newtech.timetracker.auth.repository.UserRepository;
import it.newtech.timetracker.project.entity.Project;
import it.newtech.timetracker.project.entity.SubProject;
import it.newtech.timetracker.project.repository.ProjectRepository;
import it.newtech.timetracker.project.repository.SubProjectRepository;
import it.newtech.timetracker.task.entity.BillingType;
import it.newtech.timetracker.task.entity.Task;
import it.newtech.timetracker.task.repository.TaskRepository;
import it.newtech.timetracker.timesheet.entity.TimesheetEntry;
import it.newtech.timetracker.timesheet.repository.TimesheetEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

//@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final SubProjectRepository subProjectRepository;
    private final TaskRepository taskRepository;
    private final TimesheetEntryRepository timesheetRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) return; // Evita duplicati al riavvio

        System.out.println("--- Inizio popolamento dati di test ---");

        // 1. Creazione Utenti
        User mario = new User(); mario.setUsername("mario.rossi"); userRepository.save(mario);
        User lucia = new User(); lucia.setUsername("lucia.bianchi"); userRepository.save(lucia);

        // 2. Creazione Progetti (Clienti diversi)
        Project projA = new Project(); projA.setName("Sviluppo App Mobile"); projA.setCustomerName("Cliente Alpha");
        projectRepository.save(projA);

        Project projB = new Project(); projB.setName("Manutenzione Cloud"); projB.setCustomerName("Cliente Beta");
        projectRepository.save(projB);

        // 3. Creazione Sub-Progetti e Task
        SubProject sub1 = new SubProject(); sub1.setName("Frontend React"); sub1.setProject(projA);
        subProjectRepository.save(sub1);

        Task taskSviluppo = new Task(); 
        taskSviluppo.setName("Codifica Componenti");
        taskSviluppo.setSubProject(sub1);
        taskSviluppo.setBillingType(BillingType.HOURLY);
        taskRepository.save(taskSviluppo);

        // 4. Inserimento Timesheet per l'ultimo mese (30 giorni)
        Random random = new Random();
        LocalDate today = LocalDate.now();
        List<User> users = List.of(mario, lucia);

        for (int i = 0; i < 30; i++) {
            LocalDate date = today.minusDays(i);
            
            // Saltiamo i weekend per realismo
            if (date.getDayOfWeek().getValue() >= 6) continue;

            for (User user : users) {
                TimesheetEntry entry = new TimesheetEntry();
                entry.setUser(user);
                entry.setProject(projA);
                entry.setSubProject(sub1);
                entry.setTask(taskSviluppo);
                
                // Dati temporali fittizi
                entry.setStartDateTime(date.atTime(9, 0));
                entry.setEndDateTime(date.atTime(18, 0));
                
                // Simuliamo 8 ore meno 1 ora di pausa
                double hours = 8.0;
                entry.setTotalHours(hours);
                entry.setBreakDuration("01:00");
                
                // Snapshot della tariffa (es. 50€/ora per Mario, 60€ per Lucia)
                BigDecimal rate = user.getUsername().equals("mario.rossi") ? 
                                 new BigDecimal("50.00") : new BigDecimal("60.00");
                
                entry.setAppliedRate(rate);
                entry.setAppliedBillingType(BillingType.HOURLY);
                entry.setNotes("Lavoro del giorno " + date);
                
                timesheetRepository.save(entry);
            }
        }

        System.out.println("--- Popolamento completato: inserite circa 40-50 righe di timesheet ---");
    }
} 
