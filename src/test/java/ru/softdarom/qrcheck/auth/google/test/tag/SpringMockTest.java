package ru.softdarom.qrcheck.auth.google.test.tag;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.softdarom.qrcheck.auth.google.GoogleAuthApplication;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Tag("spring-mock")
@SpringBootTest(classes = GoogleAuthApplication.class)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("mock-test")
public @interface SpringMockTest {
}