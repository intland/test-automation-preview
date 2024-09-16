package com.intland.codebeamer.integration.api.builder.table;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

import com.intland.codebeamer.integration.api.builder.wiki.WikiMarkupBuilder;
import com.intland.codebeamer.integration.classic.component.field.HtmlColor;
import com.intland.swagger.client.model.AbstractField;
import com.intland.swagger.client.model.AbstractFieldValue;
import com.intland.swagger.client.model.AbstractReference;
import com.intland.swagger.client.model.BoolFieldValue;
import com.intland.swagger.client.model.ChoiceFieldValue;
import com.intland.swagger.client.model.ColorFieldValue;
import com.intland.swagger.client.model.CountryFieldValue;
import com.intland.swagger.client.model.DateFieldValue;
import com.intland.swagger.client.model.DecimalFieldValue;
import com.intland.swagger.client.model.IntegerFieldValue;
import com.intland.swagger.client.model.LanguageFieldValue;
import com.intland.swagger.client.model.TableField;
import com.intland.swagger.client.model.TextFieldValue;
import com.intland.swagger.client.model.WikiTextFieldValue;

public class TrackerItemTableRowBuilder {

	private final TableField tableField;
	private final List<AbstractFieldValue> values;

	public TrackerItemTableRowBuilder(final TableField tableField) {
		this.tableField = Objects.requireNonNull(tableField);
		this.values = new ArrayList<>();
	}

	public TrackerItemTableRowBuilder setTextFor(String fieldName, String text) {
		values.add(new TextFieldValue()
				.value(text)
				.fieldId(getColumnField(fieldName).getId()));
		return this;
	}

	public TrackerItemTableRowBuilder setChoiceOptionsFor(String fieldName, List<AbstractReference> references) {
		values.add(new ChoiceFieldValue()
				.values(references)
				.fieldId(getColumnField(fieldName).getId()));
		return this;
	}

	public TrackerItemTableRowBuilder setIntegerFor(String fieldName, Integer number) {
		values.add(new IntegerFieldValue()
				.value(number)
				.fieldId(getColumnField(fieldName).getId()));
		return this;
	}

	public TrackerItemTableRowBuilder setDecimalFor(String fieldName, Double decimalValue) {
		values.add(new DecimalFieldValue()
				.value(decimalValue)
				.fieldId(getColumnField(fieldName).getId()));
		return this;
	}

	public TrackerItemTableRowBuilder setDateFor(String fieldName, Date date) {
		values.add(new DateFieldValue()
				.value(date)
				.fieldId(getColumnField(fieldName).getId()));
		return this;
	}

	public TrackerItemTableRowBuilder setBooleanFor(String fieldName, boolean value) {
		values.add(new BoolFieldValue()
				.value(value)
				.fieldId(getColumnField(fieldName).getId()));
		return this;
	}

	public TrackerItemTableRowBuilder setLanguageFor(String fieldName, Set<String> languages) {
		values.add(new LanguageFieldValue()
				.values(languages)
				.fieldId(getColumnField(fieldName).getId()));
		return this;
	}

	public TrackerItemTableRowBuilder setCountryFor(String fieldName, Set<String> countries) {
		values.add(new CountryFieldValue()
				.values(countries)
				.fieldId(getColumnField(fieldName).getId()));
		return this;
	}

	public TrackerItemTableRowBuilder setWikiTextFor(String fieldName, String text) {
		values.add(new WikiTextFieldValue()
				.value(text)
				.fieldId(getColumnField(fieldName).getId()));
		return this;
	}

	public TrackerItemTableRowBuilder setWikiTextFor(String fieldName, Function<WikiMarkupBuilder, WikiMarkupBuilder> markupBuilder) {
		String markup = markupBuilder.apply(new WikiMarkupBuilder()).build();
		values.add(new WikiTextFieldValue()
				.value(markup)
				.fieldId(getColumnField(fieldName).getId()));
		return this;
	}

	public TrackerItemTableRowBuilder setColorFor(String fieldName, HtmlColor color) {
		values.add(new ColorFieldValue()
				.value(color.toString())
				.fieldId(getColumnField(fieldName).getId()));
		return this;
	}

	public List<AbstractFieldValue> getValues() {
		return values;
	}

	private AbstractField getColumnField(String columnName) {
		return Objects.requireNonNull(tableField.getColumns()).stream()
				.filter(it -> Objects.equals(it.getName(), columnName))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Could not find table column with name: %s".formatted(columnName)));
	}

}
