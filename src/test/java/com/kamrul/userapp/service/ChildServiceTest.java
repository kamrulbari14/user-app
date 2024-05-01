package com.kamrul.userapp.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import com.kamrul.userapp.dto.address.AddressDto;
import com.kamrul.userapp.dto.user.ChildDto;
import com.kamrul.userapp.dto.user.ParentDto;
import com.kamrul.userapp.entity.address.Address;
import com.kamrul.userapp.entity.user.Child;
import com.kamrul.userapp.entity.user.Parent;
import com.kamrul.userapp.enums.ActiveStatus;
import com.kamrul.userapp.exception.ResponseProviderException;
import com.kamrul.userapp.repository.ChildRepository;
import com.kamrul.userapp.service.impl.ChildServiceImpl;
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
public class ChildServiceTest {

  @Mock
  private ChildRepository childRepository;

  @Mock
  private ParentService parentService;

  @InjectMocks
  private ChildServiceImpl childService;

  private ChildDto childDto;

  private Child child;

  @BeforeEach
  public void setUp() {
    AddressDto addressDto = AddressDto.builder().street("146/E, Road-2").city("Dhaka")
        .state("Bangladesh").zip("1207").build();

    ParentDto parentDto = ParentDto.parentBuilder().id(1L).firstName("Kamrul").lastName("Bari")
        .address(addressDto).build();

    childDto = ChildDto.childBuilder().firstName("Kamrul").lastName("Bari").parent(parentDto)
        .build();

    Address address = Address.builder().street("146/E, Road-2").city("Dhaka").state("Bangladesh")
        .zip("1207").build();

    Parent parent = Parent.parentBuilder().id(1L).firstName("Kamrul").lastName("Bari")
        .address(address).build();

    child = Child.childBuilder().id(1L).firstName("Kamrul").lastName("Bari").parent(parent).build();
  }

  @Test
  public void testSaveChildUser_Success() {
    // Mock parentService to return a parent
    Mockito.when(parentService.getParentUserById(Mockito.anyLong())).thenReturn(new ParentDto());
    // Mock childRepository to return a saved child
    Mockito.when(childRepository.save(any())).thenReturn(child);

    // Call the method under test
    ChildDto savedChild = childService.saveChildUser(childDto);

    // Verify the result
    assertNotNull(savedChild.getId());
    // Add more assertions as needed
  }


  @Test
  public void testGetChildUserById_Success() {
    Mockito.when(childRepository.findByIdAndActiveStatus(Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(Optional.of(child));

    ChildDto childDto = childService.getChildUserById(1L);

    assertNotNull(childDto);
    // Add assertions to verify childDto properties
  }

  @Test
  public void testGetAllChildUserByActiveStatus_Success() {
    Mockito.when(childRepository.findAllByActiveStatus(Mockito.anyInt()))
        .thenReturn(Collections.singletonList(new Child()));

    List<ChildDto> childDtoList = childService.getAllChildUserByActiveStatus(ActiveStatus.ACTIVE);

    assertFalse(childDtoList.isEmpty());
    // Add more assertions as needed
  }

  @Test
  public void testUpdateChildUser_Success() {
    Child existingChild = new Child();
    Mockito.when(childRepository.findByIdAndActiveStatus(Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(Optional.of(existingChild));
    Mockito.when(childRepository.save(any())).thenReturn(existingChild);

    ChildDto updatedChildDto = childService.updateChildUser(childDto, 1L);

    assertNotNull(updatedChildDto);
    // Add assertions to verify updatedChildDto properties
  }

  @Test
  public void testUpdateChildUser_NotFound() {
    Mockito.when(childRepository.findByIdAndActiveStatus(Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(Optional.empty());

    assertThrows(ResponseProviderException.class, () -> childService.updateChildUser(childDto, 1L));
  }

  @Test
  public void testDeleteChildUser_SoftDelete_Success() {
    Child existingChild = new Child();
    Mockito.when(childRepository.findByIdAndActiveStatus(Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(Optional.of(existingChild));
    Mockito.when(childRepository.save(any())).thenReturn(existingChild);

    assertTrue(childService.deleteChildUser(1L, false));
    // Add more assertions as needed
  }

  @Test
  public void testDeleteChildUser_SoftDelete_NotFound() {
    Mockito.when(childRepository.findByIdAndActiveStatus(Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(Optional.empty());

    assertThrows(ResponseProviderException.class, () -> childService.deleteChildUser(1L, false));
  }

  @Test
  public void testDeleteChildUser_HardDelete_Success() {
    Mockito.when(childRepository.findByIdAndActiveStatus(Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(Optional.of(new Child()));

    assertTrue(childService.deleteChildUser(1L, true));
  }
}