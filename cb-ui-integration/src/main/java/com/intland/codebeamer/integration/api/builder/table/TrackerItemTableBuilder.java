package com.intland.codebeamer.integration.api.builder.table;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import com.intland.swagger.client.model.TableField;
import com.intland.swagger.client.model.TableFieldValue;

public class TrackerItemTableBuilder {

	private final TableField tableField;
	private final List<TrackerItemTableRowBuilder> tableRows;

	public TrackerItemTableBuilder(final TableField tableField) {
		this.tableField = Objects.requireNonNull(tableField);
		this.tableRows = new ArrayList<>();
	}

	public TrackerItemTableBuilder addRow(Function<TrackerItemTableRowBuilder, TrackerItemTableRowBuilder> builder) {
		tableRows.add(builder.apply(new TrackerItemTableRowBuilder(tableField)));
		return this;
	}

	public TableFieldValue build() {
		TableFieldValue tableFieldValue = new TableFieldValue();
		for (TrackerItemTableRowBuilder row : tableRows) {
			tableFieldValue.addValuesItem(row.getValues());
		}
		return tableFieldValue;
	}

}
