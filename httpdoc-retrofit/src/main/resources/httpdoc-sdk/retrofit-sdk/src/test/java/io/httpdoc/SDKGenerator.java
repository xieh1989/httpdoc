package io.httpdoc;

import io.httpdoc.core.Document;
import io.httpdoc.core.generation.Generation;
import io.httpdoc.jackson.deserialization.JsonDeserializer;
import io.httpdoc.retrofit.RetrofitMergedGenerator;
import io.httpdoc.retrofit.RetrofitSupplier;
import org.junit.Test;

import java.net.URL;

public class SDKGenerator {

    @Test
    public void generate() throws Exception {
        RetrofitMergedGenerator generator = new RetrofitMergedGenerator();
//        generator.include(RetrofitCallGenerator.class);       // default
//        generator.include(RetrofitRxJavaGenerator.class);     // need retrofit2: adapter-rxjava2
//        generator.include(RetrofitJava8Generator.class);      // need retrofit2: adapter-java8
//        generator.include(RetrofitGuavaGenerator.class);      // need retrofit2: adapter-guava

        Document document = Document.from(new URL("http://localhost:8080/httpdoc.json"), new JsonDeserializer());
        Generation generation = new Generation(document);
        generation.setDirectory(System.getProperty("user.dir") + "/src/main/java"); // set generate directory
        generation.setSupplier(new RetrofitSupplier());                             // set type supplier
//        generation.setPkg();                                                      // set default package
//        generation.setPkgForced();                                                // set forced package
//        generation.setStrategy();                                                 // set generate strategy

        generator.generate(generation);
    }

}
