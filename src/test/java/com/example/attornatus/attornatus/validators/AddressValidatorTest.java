package com.example.attornatus.attornatus.validators;

import com.example.attornatus.attornatus.exeptions.RequiredObjectIsNullException;
import com.example.attornatus.attornatus.mocks.MockAddress;
import com.example.attornatus.attornatus.mocks.MockPerson;
import com.example.attornatus.attornatus.services.PersonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class AddressValidatorTest {

    MockAddress inputAddress;

    @InjectMocks
    private AddressValidator validator;

    @BeforeEach
    void setUpMocks() {
        inputAddress = new MockAddress();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testInvalidCep(){
        var invalidAddress = inputAddress.mockDTO();
        invalidAddress.setCEP("1223-12213");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            validator.cep(invalidAddress.getCEP());
        });

        String expectedMessage = "CEP inválido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testValidCep(){
        var validAddress = inputAddress.mockDTO();
        validAddress.setCEP("12232-122");

        validator.cep(validAddress.getCEP());
    }
}
