package com.as.batch.reader;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.as.batch.domain.Employee;
import com.as.batch.dto.EmployeeDTO;
import com.as.batch.mapper.DomainMapper;

public class EmployeeReader implements ItemReader<Employee> {

	private final String apiUrl;
	private final RestTemplate restTemplate;
	private Iterator<EmployeeDTO> itr;
	private DomainMapper domainMapper;

	public EmployeeReader(String apiUrl, RestTemplate restTemplate, DomainMapper domainMapper) {
		super();
		this.apiUrl = apiUrl;
		this.restTemplate = restTemplate;
		this.domainMapper = domainMapper;
	}

	@Override
	public Employee read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

		if (itr == null) {
			List<EmployeeDTO> payloads = exchange(apiUrl, HttpMethod.GET,
					new ParameterizedTypeReference<List<EmployeeDTO>>() {
					});
			itr = payloads.iterator();
		}

		if (itr != null && itr.hasNext()) {
			return domainMapper.mapEmployee(itr.next());
		}

		return null;
	}

	public <T> T exchange(String uri, HttpMethod httpMethod, ParameterizedTypeReference<T> responseType) {
		return restTemplate.exchange(uri, httpMethod, null, responseType).getBody();
	}

	public <T> List<T> exchangeAsList(String uri, HttpMethod httpMethod,
			ParameterizedTypeReference<List<T>> responseType) {
		return restTemplate.exchange(uri, httpMethod, null, responseType).getBody();
	}

	public <K, V> Map<K, V> exchangeAsMap(String uri, HttpMethod httpMethod,
			ParameterizedTypeReference<Map<K, V>> responseType) {
		return restTemplate.exchange(uri, httpMethod, null, responseType).getBody();
	}
}
