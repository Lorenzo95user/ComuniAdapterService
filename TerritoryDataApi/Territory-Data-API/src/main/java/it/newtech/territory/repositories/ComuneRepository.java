package it.newtech.territory.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.newtech.territory.models.CodeCapId;
import it.newtech.territory.models.Comune;
import jakarta.transaction.Transactional;


@Repository
public interface ComuneRepository extends JpaRepository<Comune, CodeCapId> {
	
	@Query("SELECT DISTINCT c.id.cap FROM Comune c WHERE LOWER(c.denominazioneIta) = LOWER(:nome)")
	List<String> findCapByDenominazioneIta(@Param("nome") String nome);
	
	@Query("SELECT DISTINCT c.siglaProvincia FROM Comune c WHERE LOWER(c.denominazioneIta) = LOWER(:nome)")
	String findSiglaProvinciaByDenominazioneIta(@Param("nome")String nome);
	
	@Query("SELECT DISTINCT c.denominazioneRegione FROM Comune c WHERE LOWER(c.denominazioneIta) = LOWER(:nome)")
	String findDenominazioneRegioneByDenominazioneIta(@Param("nome")String nome);
	
	@Query("SELECT c.denominazioneProvincia FROM Comune c WHERE LOWER(c.denominazioneIta) = LOWER(:nome)")
	String findDenominazioneProvinciaByDenominazioneIta(@Param("nome")String nome);
	
	@Query("SELECT DISTINCT c.denominazioneRegione FROM Comune c ORDER BY c.denominazioneRegione ASC")
	List<String> findAllRegioni();

	@Query("SELECT DISTINCT  c.denominazioneProvincia FROM Comune c WHERE LOWER(c.denominazioneRegione) = LOWER(:regione) ORDER BY c.denominazioneProvincia ASC")
	List<String> findProvinceByRegione(@Param("regione") String regione);

	@Query("SELECT DISTINCT c.denominazioneIta FROM Comune c WHERE LOWER(c.denominazioneProvincia) = LOWER(:provincia) ORDER BY c.denominazioneIta ASC")
	List<String> findComuniByProvincia(@Param("provincia") String provincia);
	
	@Query("SELECT DISTINCT c FROM Comune c WHERE LOWER(c.denominazioneIta) = LOWER(:nome)")
	List<Comune> findByDenominazioneItaliana(String nome);
	
	@Query("SELECT c.id FROM Comune c WHERE LOWER(c.denominazioneIta) = LOWER (:nome) AND c.id.cap = :cap")
	CodeCapId findCodeCapIdByNome (String nome , String cap);
	
	@Transactional
	@Modifying
	@Query("DELETE FROM Comune c WHERE LOWER(c.denominazioneIta) = LOWER(:nome) AND c.id = :cod")
	void deleteComuneByIdAndNome(String nome ,CodeCapId cod);
	
	@Query("SELECT c FROM Comune c WHERE LOWER(c.denominazioneIta) = LOWER(:nome) AND c.id = :cod")
	Comune findComuneByIdAndNome(String nome,CodeCapId cod);
	
	@Query("SELECT c FROM Comune c ORDER BY c.denominazioneIta ASC")
	List<Comune> findAll();
}