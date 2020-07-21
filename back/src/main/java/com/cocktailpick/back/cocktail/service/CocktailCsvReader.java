package com.cocktailpick.back.cocktail.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cocktailpick.back.cocktail.dto.CocktailRequest;
import com.cocktailpick.back.common.csv.CsvReader;
import com.cocktailpick.back.common.csv.OpenCsvReader;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CocktailCsvReader {
	private static final int NAME_INDEX = 0;
	private static final int ABV_INDEX = 1;
	private static final int DESCRIPTION_INDEX = 2;
	private static final int ORIGIN_INDEX = 3;
	private static final int IMAGE_URL_INDEX = 4;
	private static final int SWEET_INDEX = 6;
	private static final int SOUR_INDEX = 7;
	private static final int BITTER_INDEX = 8;

	private static final int TAG_INDEX = 5;
	private static final int LIQUOR_INDEX = 9;
	private static final int LIQUOR_QUANTITY_INDEX = 10;
	private static final int SPECIAL_INDEX = 12;
	private static final int SPECIAL_QUANTITY_INDEX = 13;

	private final CsvReader csvReader;

	public static CocktailCsvReader from(MultipartFile file) {
		return new CocktailCsvReader(OpenCsvReader.from(file));
	}

	public List<CocktailRequest> getCocktailRequests() {
		List<String[]> lines = csvReader.readAll();

		List<CocktailRequest> cocktailRequests = new ArrayList<>();
		for (String[] line : lines) {
			CocktailRequest cocktailRequest = CocktailRequest.builder()
				.name(line[NAME_INDEX])
				.abv(Double.parseDouble(line[ABV_INDEX]))
				.description(line[DESCRIPTION_INDEX])
				.origin(line[ORIGIN_INDEX])
				.imageUrl(line[IMAGE_URL_INDEX])
				.tag(splitAndTrim(line[TAG_INDEX]))
				.sweet("1".equals(line[SWEET_INDEX]))
				.sour("1".equals(line[SOUR_INDEX]))
				.bitter("1".equals(line[BITTER_INDEX]))
				.liquor(splitAndTrim(line[LIQUOR_INDEX]))
				.liquorQuantity(splitAndTrim(line[LIQUOR_QUANTITY_INDEX]))
				.special(splitAndTrim(line[SPECIAL_INDEX]))
				.specialQuantity(splitAndTrim(line[SPECIAL_QUANTITY_INDEX]))
				.build();
			cocktailRequests.add(cocktailRequest);
		}
		return cocktailRequests;
	}

	private List<String> splitAndTrim(String input) {
		return Arrays.stream(input.split(","))
			.map(String::trim)
			.filter(StringUtils::isNotBlank)
			.collect(Collectors.toList());
	}
}
