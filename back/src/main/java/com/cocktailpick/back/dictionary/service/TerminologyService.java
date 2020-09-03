package com.cocktailpick.back.dictionary.service;

import static com.cocktailpick.back.common.exceptions.ErrorCode.*;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.back.common.csv.OpenCsvReader;
import com.cocktailpick.back.common.exceptions.EntityNotFoundException;
import com.cocktailpick.back.dictionary.domain.Terminology;
import com.cocktailpick.back.dictionary.domain.TerminologyRepository;
import com.cocktailpick.back.dictionary.dto.TerminologyResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Service
public class TerminologyService {
	private final TerminologyRepository terminologyRepository;

	@Transactional(readOnly = true)
	public List<TerminologyResponse> findAllTerminologies() {
		return Collections.unmodifiableList(TerminologyResponse.listOf(terminologyRepository.findAll()));
	}

	@Transactional(readOnly = true)
	public TerminologyResponse findTerminology(Long id) {
		return TerminologyResponse.of(
			terminologyRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(TERMINOLOGY_NOT_FOUND)));
	}

	@Transactional
	public Long save(Terminology terminology) {
		return terminologyRepository.save(terminology).getId();
	}

	@Transactional
	public void saveAll(MultipartFile file) {
		TerminologyCsvReader terminologyCsvReader = new TerminologyCsvReader(OpenCsvReader.from(file));
		List<Terminology> terminologies = terminologyCsvReader.getTerminologies();

		terminologyRepository.saveAll(terminologies);
	}

	@Transactional
	public void update(Terminology terminology, Long id) {
		Terminology persistTerminology = terminologyRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException(TERMINOLOGY_NOT_FOUND));

		persistTerminology.update(terminology);
	}

	@Transactional
	public void delete(Long id) {
		terminologyRepository.deleteById(id);
	}
}
