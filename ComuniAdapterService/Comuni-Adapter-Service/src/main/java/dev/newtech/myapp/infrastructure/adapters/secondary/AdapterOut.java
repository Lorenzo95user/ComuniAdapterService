package dev.newtech.myapp.infrastructure.adapters.secondary;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import dev.newtech.myapp.domain.model.ComuneDto;
import dev.newtech.myapp.domain.ports.out.PortOut;

@Component
@AllArgsConstructor
@Slf4j
public class AdapterOut implements PortOut {

	private final RestClient restClient;
	private Mapper mapper;

	@Override
	public List<ComuneDto> getAllComune() {
		try {
			List<Comune> comuniList = restClient.get().uri("/all").retrieve()
					.body(new ParameterizedTypeReference<List<Comune>>() {
					});
			if (comuniList.isEmpty()) {
				log.warn("nessun dato trovato da servizio web");
				return List.of();
			}
			List<ComuneDto> dtoList = comuniList.stream().map(mapper::toComuneDto).toList();
			StringBuilder sb = new StringBuilder ("--- COMUNI TROVATI ---\n");
			dtoList.forEach(dto -> {
				sb.append("NOME : ").append(dto.getNome()).append(" , ").append("PROVINCIA : ").append(dto.getProvincia()).append(" , ").append("SIGLA : ")
				.append(dto.getSigla()).append(" , ").append("CAP : ").append(dto.getCap())
				.append(" , ").append("CODICE ISTAT : ").append(dto.getIstat()).append(" \n");
			});
			log.debug("Comuni trovati  :\n {}", sb.toString());
			return dtoList;
		} catch (HttpClientErrorException e) {
			return List.of();
		}
	}

	public List<String> getRegioni() {
		try {
			var regioni = restClient.get().uri("/regioni").retrieve()
					.body(new ParameterizedTypeReference<List<String>>() {
					});
			int totale = regioni.size();
			if (regioni.isEmpty()) {
				log.warn("Nessuna Regione trovata da servizio web");
				return List.of();
			}
			StringBuilder sb = new StringBuilder("REGIONI :").append(totale).append("\n");
			regioni.forEach(regione -> sb.append(regione.toString()).append(" -  "));
			log.debug("\n{}", sb.toString());
			return regioni;
		} catch (Exception e) {
			log.error("Error : {}", e);
			return List.of();
		}

	}

	@Override
	public List<String> getProvince(String regione) {
		try {
			var province = restClient.get()
					.uri(uriBuilder -> uriBuilder.path("/province").queryParam("regione", regione).build()).retrieve()
					.body(new ParameterizedTypeReference<List<String>>() {
					});
			int totale = province.size();
			if (province.isEmpty()) {
				log.warn("Nessuna Provincia trovata da servizio web");
				return List.of();
			}
			StringBuilder sb = new StringBuilder("PROVINCE :").append(totale).append("\n");
			province.forEach(provincia -> sb.append(provincia.toString()).append(" -  "));
			log.debug("\n{}", sb.toString());
			return province;
		} catch (Exception e) {
			log.error("Error : {}", e);
			return List.of();
		}

	}

	@Override
	public List<String> getComuni(String provincia) {
		try {
			var comuni = restClient.get()
					.uri(uriBuilder -> uriBuilder.path("/comuni").queryParam("provincia", provincia).build()).retrieve()
					.body(new ParameterizedTypeReference<List<String>>() {
					});
			int totale = comuni.size();
			if (comuni.isEmpty()) {
				log.warn("Nessun Comune trovato da servizio web");
				return List.of();
			}
			StringBuilder sb = new StringBuilder("COMUNI :").append(totale).append("\n");
			comuni.forEach(comune -> sb.append(comune.toString()).append(" -  "));
			log.debug("\n{}", sb.toString());
			return comuni;
		} catch (Exception e) {
			log.error("Error : {}", e);
			return List.of();
		}
	}

	@Override
	public List<String> getCapByComune(String comune) {
		try {
			var cap = restClient.get().uri(uriBuilder -> uriBuilder.path("/find/cap").queryParam("nome",comune)
					.build()).retrieve().body(new ParameterizedTypeReference <List<String>>() {});
			if(cap.isEmpty()) {
				log.warn("Nessun Comune trovato da servizio web");
				return List.of();
			}
			StringBuilder sb = new StringBuilder("CAP :").append("\n");
			cap.forEach(c -> sb.append(c.toString()).append(" -  "));
			log.debug("\n{}", sb.toString());
			return cap;
		}catch(Exception e) {
			log.error("Error : {}", e);
			return List.of();
		}
	}

	@Override
	public List<String> getSiglaByComune(String comune) {
		try {
			var sigla = restClient.get().uri(uriBuilder -> uriBuilder.path("/find/sigla").queryParam("nome",comune)
					.build()).retrieve().body(new ParameterizedTypeReference <List<String>>(){});
			if(sigla.isEmpty()) {
				log.warn("Nessun Comune trovato da servizio web");
				return List.of();
			}
			StringBuilder sb = new StringBuilder("SIGLE :").append("\n");
			sigla.forEach(s -> sb.append(s.toString()).append(" -  "));
			log.debug("\n{}", sb.toString());
			return sigla;
		}catch(Exception e) {
			log.error("Error : {}", e);
			return List.of();
		}
	}

}
