package com.intland.codebeamer.integration.configuration;

public record TracingConfiguration(String traceDirectoryPath, String previewUri, String uri, boolean openTraceApplication, boolean sources, boolean snapshots, boolean screenshots) {
	
}
