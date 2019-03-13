package com.ysy.processor;

//import com.google.common.collect.ImmutableSet;

import com.google.auto.service.AutoService;
import com.google.common.collect.ImmutableSet;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.ysy.annotation.YsyIntent;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
public class YsyIntentProcessor extends AbstractProcessor {

    private static final String METHOD_PREFIX = "intent";
    private static final ClassName CLASS_INTENT = ClassName.get("android.content", "Intent");
    private static final ClassName CLASS_CONTEXT = ClassName.get("android.content", "Context");

    private Messager messager;
    private Map<String, String> activitiesWithPackage;
    private Elements elements;
    private Filer filer;

    /**
     * Gives you paintbrushes to start painting. Filer(to generate file), Messager(debugging),
     * Utility classes. You can get these classes with processing environment.
     */
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager = processingEnvironment.getMessager();
        activitiesWithPackage = new HashMap<>();
        elements = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
    }

    /**
     * Brain of your processor. Starts rounding and gives you annotated classes, methods, fields,
     * annotation etc. It gives you all annotated elements here. And you start doing all calculation
     * and generate your new class file here.
     */
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        try {
            // 1- Find all annotated element
            for (Element element : roundEnvironment.getElementsAnnotatedWith(YsyIntent.class)) {
                if (element.getKind() != ElementKind.CLASS) {
                    messager.printMessage(Diagnostic.Kind.ERROR, "Must be applied to class.");
                    return true;
                }

                TypeElement typeElement = (TypeElement) element;
                activitiesWithPackage.put(typeElement.getSimpleName().toString(),
                        elements.getPackageOf(typeElement).getQualifiedName().toString());
            }

            // 2- Generate a class
            TypeSpec.Builder navigatorClass = TypeSpec
                    .classBuilder("Navigator")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

            for (Map.Entry<String, String> element : activitiesWithPackage.entrySet()) {
                String activityName = element.getKey();
                String packageName = element.getValue();
                ClassName activityClass = ClassName.get(packageName, activityName);
                MethodSpec intentMethod = MethodSpec
                        .methodBuilder(METHOD_PREFIX + activityName)
                        .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                        .returns(CLASS_INTENT)
                        .addParameter(CLASS_CONTEXT, "context")
                        .addStatement("return new $T($L, $L)", CLASS_INTENT, "context", activityClass + ".class")
                        .build();
                navigatorClass.addMethod(intentMethod);
            }

            // 3- Write generated class to a file
            JavaFile.builder("com.ysy.jetpackdemo", navigatorClass.build())
                    .build()
                    .writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    /**
     * We return only our custom annotation set in this method. We can say that return value of
     * this method will be given to us as process method’s first parameter.
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return ImmutableSet.of(YsyIntent.class.getCanonicalName());
    }

    /**
     * We always return latest java version.
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }
}
