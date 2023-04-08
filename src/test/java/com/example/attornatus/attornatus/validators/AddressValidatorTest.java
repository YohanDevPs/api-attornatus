package com.example.attornatus.attornatus.validators;

import com.example.attornatus.attornatus.dto.AddressDTO;
import com.example.attornatus.attornatus.mapper.UtilModelMapper;
import com.example.attornatus.attornatus.mocks.MockAddress;
import com.example.attornatus.attornatus.models.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static com.example.attornatus.attornatus.mapper.UtilModelMapper.parseObject;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class AddressValidatorTest {

    MockAddress inputAddress;

    @InjectMocks
    private AddressValidator validator;
    @Autowired
    private UtilModelMapper utilModelMapper;

    @BeforeEach
    void setUpMocks() {
        inputAddress = new MockAddress();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateValidAddress() {
        AddressDTO dto = inputAddress.mockDTO(20);
        dto.setCep("12456-526");
        var addresses = inputAddress.mockEntityList();
        assertDoesNotThrow(() -> validator.create(dto, addresses));
    }

    @Test
    void testUpdateValidAddress() {
        AddressDTO dto = inputAddress.mockDTO(20);
        dto.setCep("12456-526");
        assertDoesNotThrow(() -> validator.update(dto));
    }

    @Test
    void testValidateCep_MustThrowException(){
        var invalidAddress = inputAddress.mockDTO();
        invalidAddress.setCep("1223-12213");

        Exception exception = assertThrows(RuntimeException.class, () -> {
            validator.validateCep(invalidAddress.getCep());
        });

        String expectedMessage = "CEP inválido";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testValidateCep_MustAcceptCep(){
        var validAddress = inputAddress.mockDTO();
        validAddress.setCep("12232-122");

        validator.validateCep(validAddress.getCep());
    }

    @Test
    void testValidateNullAddress_MustThrowException(){
         Exception exception = assertThrows(RuntimeException.class, () -> {
            validator.validateNullAddress(null);
        });

        String expectedMessage = "A person cannot register a null address";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testValidateRepeatAddress_MustThrowException(){
        var addresses = inputAddress.mockEntityList();
        var addressDTORepeated = parseObject(addresses.get(1), AddressDTO.class);

        System.out.println(addressDTORepeated.equals(addresses.get(1)));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            validator.validateRepeatAddress(addresses, addressDTORepeated);
        });

        String expectedMessage = "A person cannot register a repeated address";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void testValidateRepeatAddress_MustNotThrowException(){
        var addresses = inputAddress.mockEntityList();
        var addressDTONotRepeated = inputAddress.mockDTO(20);

        assertDoesNotThrow(() -> validator.validateRepeatAddress(addresses, addressDTONotRepeated));
    }
}
