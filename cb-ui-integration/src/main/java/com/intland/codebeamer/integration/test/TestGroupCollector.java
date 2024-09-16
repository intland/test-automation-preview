package com.intland.codebeamer.integration.test;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.reflections.Reflections;
import org.testng.annotations.Test;

public class TestGroupCollector {

	public static void main(String[] args) throws IOException {
		Objects.checkIndex(0, args.length);
		Objects.checkIndex(1, args.length);
		String groups = new Reflections(args[0]).getTypesAnnotatedWith(Test.class).stream()
		  .flatMap(annotatedClass -> Stream.of(annotatedClass.getAnnotation(Test.class).groups()))
		  .distinct()
		  .collect(Collectors.joining(","));
		
		FileUtils.write(new File(args[1], "groups.txt"), groups, StandardCharsets.UTF_8);
	}
	
}
