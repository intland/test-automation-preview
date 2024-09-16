/*
 * Copyright by Intland Software
 *
 * All rights reserved.
 *
 * This software is the confidential and proprietary information
 * of Intland Software. ("Confidential Information"). You
 * shall not disclose such Confidential Information and shall use
 * it only in accordance with the terms of the license agreement
 * you entered into with Intland.
 */

package com.intland.codebeamer.integration.sitemap;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tika.utils.StringUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.intland.codebeamer.integration.sitemap.annotation.Component;
import com.intland.codebeamer.integration.sitemap.annotation.Page;
import com.intland.codebeamer.integration.test.annotation.TestCase;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerComponent;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerDialog;
import com.intland.codebeamer.integration.ui.AbstractCodebeamerPage;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.AnnotationParameterValueList;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.FieldInfo;
import io.github.classgraph.ScanResult;

@Test
public class FrameworkDevelopmentTests {

	private static final Logger logger = LogManager.getLogger(FrameworkDevelopmentTests.class);

	@Test(dataProvider = "applicationTypes")
	public void testPageAnnotationPresence(String application) {
		try (final ScanResult scanResult = new ClassGraph()
				.enableAnnotationInfo()
				.acceptPackages("com.intland.codebeamer.integration.%s".formatted(application))
				.scan()) {

			assertNotNull(scanResult);
			ClassInfoList pages = scanResult.getSubclasses(AbstractCodebeamerPage.class);

			assertTrue(CollectionUtils.isNotEmpty(pages));
			List<String> pageWithoutAnnotation = pages.stream()
					.filter(p -> !p.hasAnnotation(Page.class))
					.filter(p -> !p.isAbstract())
					.map(ClassInfo::getName)
					.toList();

			assertTrue(CollectionUtils.isEmpty(pageWithoutAnnotation), """
					Page annotation is missing from the following pages: \n%s"""
					.formatted(String.join(System.lineSeparator(), pageWithoutAnnotation)));
		}
	}

	@Test(dataProvider = "applicationTypes")
	public void testComponentAnnotationPresence(String application) {
		try (final ScanResult scanResult = new ClassGraph()
				.enableAnnotationInfo()
				.enableFieldInfo()
				.ignoreFieldVisibility()
				.acceptPackages("com.intland.codebeamer.integration.%s".formatted(application))
				.scan()) {

			assertNotNull(scanResult);

			Map<String, ClassInfo> classes = scanResult.getAllClassesAsMap().entrySet().stream()
					.filter(entry -> entry.getValue().extendsSuperclass(AbstractCodebeamerPage.class)
							|| entry.getValue().extendsSuperclass(AbstractCodebeamerComponent.class)
							|| entry.getValue().extendsSuperclass(AbstractCodebeamerDialog.class))
					.collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));

			List<String> missingComponentAnnotation = scanResult.getAllClasses()
					.stream()
					.map(ClassInfo::getDeclaredFieldInfo)
					.flatMap(Collection::stream)
					.filter(f -> {
						ClassInfo classInfo = classes.get(f.getTypeDescriptor().toString());
						if (classInfo == null) {
							return false;
						}

						return classInfo.extendsSuperclass(AbstractCodebeamerComponent.class);
					})
					.filter(f -> !f.getClassInfo().isAbstract())
					.filter(f -> !f.hasAnnotation(Component.class))
					.map(f -> f.getClassInfo().getName() + " " + f.getName())
					.toList();

			assertTrue(CollectionUtils.isEmpty(missingComponentAnnotation), """
					Component annotation is missing from the following fields: \n%s"""
					.formatted(String.join(System.lineSeparator(), missingComponentAnnotation)));
		}
	}

	@Test
	public void testTestCaseAnnotationUsage() {
		try (final ScanResult scanResult = new ClassGraph()
				.enableClassInfo()
				.enableAnnotationInfo()
				.enableMethodInfo()
				.acceptPackages("com.intland.codebeamer.integration")
				.scan()) {

			assertNotNull(scanResult);
			ClassInfoList testClasses = scanResult.getClassesWithMethodAnnotation(TestCase.class);

			assertTrue(CollectionUtils.isNotEmpty(testClasses));
			List<String> testCaseAnnotationMisusage = testClasses
					.stream()
					.map(ClassInfo::getDeclaredMethodInfo)
					.flatMap(Collection::stream)
					.filter(Objects::nonNull)
					.filter(methodInfo -> {
						AnnotationInfo annotationInfo = methodInfo.getAnnotationInfo(TestCase.class);
						if (annotationInfo != null) {
							AnnotationParameterValueList parameterValues = annotationInfo.getParameterValues(false);
							return parameterValues
									.getValue("expectedHttpErrors") == null ||
									((String) parameterValues.getValue("link")).contains("codebeamer.com");
						}
						return false;
					})
					.map(m -> m.getClassInfo().getName() + "#" + m.getName())
					.toList();

			assertTrue(CollectionUtils.isEmpty(testCaseAnnotationMisusage), """
					Test Case annotation in cb-ui-integration module can only be used to mark methods with expected http errors.
					Move these methods to cb-ui-integration-tests module or use the annotation properly. \n%s"""
					.formatted(String.join(System.lineSeparator(), testCaseAnnotationMisusage)));
		}
	}

	@Test(dataProvider = "applicationTypes")
	public void generateSiteMap(String application) {
		try (final ScanResult scanResult = new ClassGraph()
				.enableAnnotationInfo()
				.enableFieldInfo()
				.ignoreFieldVisibility()
				.acceptPackages("com.intland.codebeamer.integration.%s".formatted(application))
				.scan()) {

			assertNotNull(scanResult);
			List<ClassInfo> pages = scanResult.getClassesWithAnnotation(Page.class)
					.stream()
					.sorted(Comparator.comparing(ClassInfo::getSimpleName))
					.toList();

			assertTrue(CollectionUtils.isNotEmpty(pages));

			Map<String, ClassInfo> classes = scanResult.getAllClassesAsMap();

			StringBuilder sb = new StringBuilder(System.lineSeparator());
			for (ClassInfo page : pages) {
				AnnotationInfo annotationInfo = page.getAnnotationInfo(Page.class);
				String pageName = getAnnotatationValue(annotationInfo);

				sb.append(pageName);
				sb.append(System.lineSeparator());

				collectComponents(page, sb, classes, 0);
			}

			sb.append(System.lineSeparator());
			logger.info(sb.toString());
		}
	}

	private void collectComponents(ClassInfo pageOrComponent, StringBuilder sb, Map<String, ClassInfo> classes, int level) {
		List<FieldInfo> fieldInfos = pageOrComponent.getFieldInfo().stream()
				.filter(f -> f.hasAnnotation(Component.class)).toList();

		int indentation = level + 2;
		for (FieldInfo field : fieldInfos) {
			AnnotationInfo componentAnnotation = field.getAnnotationInfo(Component.class);
			boolean includeInSitemap = Boolean.TRUE
					.equals(componentAnnotation.getParameterValues(true).getValue("includeInSitemap"));

			if (!includeInSitemap) {
				continue;
			}

			String componentName = getAnnotatationValue(componentAnnotation);

			sb.append(StringUtils.repeat(" ", indentation));
			sb.append(componentName);
			sb.append(System.lineSeparator());

			ClassInfo classInfo = classes.get(field.getTypeDescriptor().toString());
			if (classInfo == null) {
				continue;
			}

			if (classInfo.extendsSuperclass(AbstractCodebeamerComponent.class)) {
				collectComponents(classInfo, sb, classes, level++);
			}
		}
	}

	private String getAnnotatationValue(AnnotationInfo annotationInfo) {
		return String.valueOf(annotationInfo.getParameterValues(true)
				.getValue("value"));
	}

	@DataProvider
	public Object[][] applicationTypes() {
		return new Object[][] {
				{ "classic" },
				{ "nextgen" }
		};
	}
}
