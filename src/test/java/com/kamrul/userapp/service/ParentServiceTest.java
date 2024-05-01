package com.kamrul.userapp.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import com.kamrul.userapp.dto.address.AddressDto;
import com.kamrul.userapp.dto.user.ParentDto;
import com.kamrul.userapp.entity.address.Address;
import com.kamrul.userapp.entity.user.Parent;
import com.kamrul.userapp.enums.ActiveStatus;
import com.kamrul.userapp.exception.ResponseProviderException;
import com.kamrul.userapp.repository.ParentRepository;
import com.kamrul.userapp.service.impl.ParentServiceImpl;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ParentServiceTest {

  @Mock
  private ParentRepository parentRepository;

  @InjectMocks
  private ParentServiceImpl parentService;

  private ParentDto parentDto;

  private Parent parent;

  @BeforeEach
  public void setUp() {
    AddressDto addressDto = AddressDto.builder().street("146/E, Road-2").city("Dhaka")
        .state("Bangladesh").zip("1207").build();

    parentDto = ParentDto.parentBuilder().firstName("Kamrul").lastName("Bari").address(addressDto)
        .build();

    Address address = Address.builder().street("146/E, Road-2").city("Dhaka").state("Bangladesh")
        .zip("1207").build();

    parent = Parent.parentBuilder().id(1L).firstName("Kamrul").lastName("Bari").address(address)
        .build();
  }

  @Test
  public void testSaveParentUser_Success() {
    // Mock parentRepository to return a saved parent
    Mockito.when(parentRepository.save(any())).thenReturn(parent);

    // Call the method under test
    ParentDto savedParent = parentService.saveParentUser(parentDto);

    // Verify the result
    assertNotNull(savedParent.getId());
    // Add more assertions as needed
  }


  @Test
  public void testGetParentUserById_Success() {
    Mockito.when(parentRepository.findByIdAndActiveStatus(Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(Optional.of(parent));

    ParentDto parentDto = parentService.getParentUserById(1L);

    assertNotNull(parentDto);
    // Add assertions to verify parentDto properties
  }

  @Test
  public void testGetAllParentUserByActiveStatus_Success() {
    Mockito.when(parentRepository.findAllByActiveStatus(Mockito.anyInt()))
        .thenReturn(Collections.singletonList(new Parent()));

    List<ParentDto> parentDtoList = parentService.getAllParentUserByActiveStatus(
        ActiveStatus.ACTIVE);

    assertFalse(parentDtoList.isEmpty());
    // Add more assertions as needed
  }

  @Test
  public void testUpdateParentUser_Success() {
    Parent existingParent = new Parent();
    Mockito.when(parentRepository.findByIdAndActiveStatus(Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(Optional.of(existingParent));
    Mockito.when(parentRepository.save(any())).thenReturn(existingParent);

    ParentDto updatedParentDto = parentService.updateParentUser(parentDto, 1L);

    assertNotNull(updatedParentDto);
    // Add assertions to verify updatedParentDto properties
  }

  @Test
  public void testUpdateParentUser_NotFound() {
    Mockito.when(parentRepository.findByIdAndActiveStatus(Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(Optional.empty());

    assertThrows(ResponseProviderException.class, () -> parentService.updateParentUser(parentDto, 1L));
  }

  @Test
  public void testDeleteParentUser_SoftDelete_Success() {
    Parent existingParent = new Parent();
    Mockito.when(parentRepository.findByIdAndActiveStatus(Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(Optional.of(existingParent));
    Mockito.when(parentRepository.save(any())).thenReturn(existingParent);

    assertTrue(parentService.deleteParentUser(1L, false));
    // Add more assertions as needed
  }

  @Test
  public void testDeleteParentUser_SoftDelete_NotFound() {
    Mockito.when(parentRepository.findByIdAndActiveStatus(Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(Optional.empty());

    assertThrows(ResponseProviderException.class, () -> parentService.deleteParentUser(1L, false));
  }

  @Test
  public void testDeleteParentUser_HardDelete_Success() {
    Mockito.when(parentRepository.findByIdAndActiveStatus(Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(Optional.of(new Parent()));

    assertTrue(parentService.deleteParentUser(1L, true));
  }
}