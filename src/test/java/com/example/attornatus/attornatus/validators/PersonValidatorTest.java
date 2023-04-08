package com.example.attornatus.attornatus.validators;

import com.example.attornatus.attornatus.mapper.UtilModelMapper;
import com.example.attornatus.attornatus.mocks.MockAddress;
import com.example.attornatus.attornatus.mocks.MockPerson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class PersonValidatorTest {
    MockAddress inputAddress;
    MockPerson inputPerson;

    @InjectMocks
    private PersonValidator validator;
    @Autowired
    private UtilModelMapper utilModelMapper;

    @BeforeEach
    void setUpMocks() {
        inputAddress = new MockAddress();
        inputPerson = new MockPerson();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateValidAddress_MustThrowException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            validator.create(null);
        });

        String expectedMessage = "Not allowed register a null person";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testCreateValidAddress_MustNotThrowException() {
        var validDTO = inputPerson.mockDTO(1);
        assertDoesNotThrow(() -> validator.create(validDTO));
    }

    @Test
    void testUpdateValidAddress_MustThrowException() {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            validator.update(null);
        });

        String expectedMessage = "Not allowed register a null person";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testUpdateValidAddress_MustNotThrowException() {
        var validDTO = inputPerson.mockDTO(1);
        assertDoesNotThrow(() -> validator.create(validDTO));
    }
}
